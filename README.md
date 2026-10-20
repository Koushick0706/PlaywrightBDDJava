# Playwright Java BDD Framework

A Maven starter framework for browser tests using Playwright Java and Cucumber.

## Prerequisites

- Java 17+
- Maven 3.8+

## Project layout

```text
src
└── test
    ├── java/com/example/playwrightbdd
    │   ├── config/       # Environment and browser configuration
    │   ├── context/      # Scenario-scoped Playwright objects
    │   ├── hooks/        # Cucumber setup and teardown
    │   ├── locators/     # Centralized page selectors
    │   ├── pages/        # Page Object Model classes
    │   ├── runners/      # JUnit/Cucumber entry point
    │   └── steps/        # Gherkin step definitions
    └── resources
        └── features/     # Gherkin specifications
```

## Install Playwright browsers

After Maven has resolved dependencies, install the browser binaries:

```bash
mvn exec:java -Dexec.classpathScope=test -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
```

## Run scenarios

```bash
mvn test
```

Filter by Cucumber tag:

```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

The default base URL is the Rahul Shetty Academy client app. Override it and choose a browser with system properties:

```bash
mvn test -Dbase.url=https://rahulshettyacademy.com/client -Dbrowser=chromium -Dheadless=true
```

Supported browser values are `chromium`, `firefox`, and `webkit`. Set `-Dheadless=false` to show the browser UI.

## Account access scenarios

`src/test/resources/features/account_access.feature` covers rejection of invalid login credentials and a successful registration followed by login. The registration scenario generates a unique email and creates an account on the practice site each time it runs.

### Excel test data

Account test data lives in [`src/test/resources/testdata/account-access.xlsx`](src/test/resources/testdata/account-access.xlsx), on the `AccountAccess` sheet. The feature step passes a `scenarioKey`, and Fillo selects the matching row.

| `scenarioKey` | Used by | Purpose |
| --- | --- | --- |
| `invalid-login` | Reject login with invalid credentials | Supplies the email and password used by the negative login scenario. |
| `register-login` | Register a new customer and log in | Supplies the customer details used for registration and the later login. |

Each row has these columns:

| Column | Description |
| --- | --- |
| `scenarioKey` | Unique row key referenced by the feature file. |
| `firstName`, `lastName` | Customer name for registration. |
| `email` | Email template. `{uuid}` is replaced with a generated unique value when the scenario runs. |
| `phone` | Phone template. `{randomPhone}` is replaced with a generated phone number during registration. |
| `password` | Password supplied to login or registration. |
| `occupation`, `gender` | Additional registration form values. |

Edit the workbook row to change scenario data. `ExcelTestData.updateScenarioValue(key, column, value)` updates a supported data column. The default workbook location is project-relative; resolution checks the current working directory and its parent directories. To use another workbook, pass `-Dtestdata.file=path/to/workbook.xlsx`.

Fillo 1.24 has a broken `.xlsx` file-extension handler. The framework works around this by making a temporary `.xlsm`-named copy for Fillo to read or update; the actual workbook remains `.xlsx`, and updates are copied back to it. Do not rename the workbook manually.

Run only account access scenarios with:

```bash
mvn clean test -Dcucumber.filter.tags="@account"
```

Allure captures the Cucumber scenarios and steps in `target/allure-results`. Each scenario includes its resolved Excel test data as a JSON attachment (passwords are redacted) and a downloadable Playwright trace ZIP attachment. The trace ZIP is also saved under `target/traces`. Open the scenario details in Allure and select either attachment to view or download it. View the report with:

```bash
mvn allure:serve
```

To inspect a trace in Playwright Trace Viewer, download the ZIP attachment from the Allure scenario or run:

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace target/traces/<trace-file>.zip"
```





