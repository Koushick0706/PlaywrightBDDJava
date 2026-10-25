package com.example.playwrightbdd.steps.learnautomation;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.data.CsvTestData;
import com.example.playwrightbdd.pages.learnautomation.RegisterPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

public class RegisterSteps {
    private final TestContext context;

    public RegisterSteps(TestContext context) {
        this.context = context;
    }

    private RegisterPage registerPage() {
        return new RegisterPage(context.page(), context.baseUrl());
    }

    @When("I register a new customer using data row {string} using csv file {string}")
    public void registerUsingDataRow(String rowKey, String csvFileLocation) {
        try {
            Map<String, String> row = CsvTestData.readScenario(rowKey, csvFileLocation);
            context.setTestData(rowKey, List.of(row));
            registerPage().fillRegistrationForm();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read test data for row: " + rowKey, e);
        }
    }

    @Then("the account registration should succeed")
    public void checkRegistrationSucceed() {
        registerPage().checkSuccessMessage();
    }
}
