# Playwright Java BDD Framework

A Maven starter framework for browser tests using Playwright Java and Cucumber.

[![Maven](https://img.shields.io/badge/Maven-3.8%2B-green?logo=apachemaven)](https://maven.apache.org/)
[![Java](https://img.shields.io/badge/Java-17%2B-green?logo=openjdk)](https://openjdk.org/)
[![JUnit](https://img.shields.io/badge/JUnit-5.14.2-green?logo=junit5)](https://junit.org/junit5/)
[![Playwright Java](https://img.shields.io/badge/Playwright_Java-1.63.0-green?logo=playwright)](https://playwright.dev/java/)

## Prerequisites

- Java 17+
- Maven 3.8+
- JUnit 5.14.2
- Playwright Java 1.63.0

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

Set the application URL in a feature with `Given the application base URL is "https://example.com"`. The URL is scenario-scoped, so a feature can target a different UI without changing `TestConfig`. If a feature omits that step, the default URL is the Rahul Shetty Academy client app; `-Dbase.url` can override that fallback. Choose a browser with system properties:

```bash
mvn test -Dbrowser=chromium -Dheadless=true
```

Use `-Dbase.url=https://example.com` only for features that omit the base URL step.

Supported browser values are `chromium`, `firefox`, and `webkit`. Set `-Dheadless=false` to show the browser UI.

## Account access scenarios

`src/test/resources/features/account_access.feature` covers rejection of invalid login credentials and a successful registration followed by login. The registration scenario generates a unique email and creates an account on the practice site each time it runs.

### CSV test data

Registration test data is read from `src/test/resources/testdata/registerdata.csv`. The reader matches the requested `TestcaseNo` and returns only that row. The current row key is `TC001`.
Run only account access scenarios with:

```bash
mvn clean test -Dcucumber.filter.tags="@account"
```

Allure captures the Cucumber scenarios and steps in `target/allure-results`. Each scenario includes its resolved test data as a JSON attachment (passwords are redacted) and a downloadable Playwright trace ZIP attachment. The trace ZIP is also saved under `src/test/resources/TracingFiles`. After each scenario, the teardown hook closes the browser and automatically opens the saved trace in Playwright Trace Viewer. Open the scenario details in Allure to view or download its attachments. View the report with:

```bash
mvn allure:serve
```

To reopen a trace manually, run this command from the project root (the directory containing `pom.xml`) and replace `TRACE_FILE.zip` with the trace filename:

```bash
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace src/test/resources/TracingFiles/TRACE_FILE.zip"
```
