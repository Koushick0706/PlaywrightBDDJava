package com.example.playwrightbdd.steps.ToolShop;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.data.CsvTestData;
import com.example.playwrightbdd.pages.ToolShop.LoginPage;
import com.example.playwrightbdd.pages.ToolShop.RegisterPage;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.Map;

public class RegisterSteps {

    private final TestContext context;

    public RegisterSteps(TestContext context) {
        this.context = context;
    }

    public RegisterPage registerPage() {
        return new RegisterPage(context.page(), context.baseUrl());
    }

    @Then("I should be redirected to the registration page and register a new account {string} using csv file {string}")
    public void registerNewAccount(String registerLogin, String csvFileLocation) {
        try {
            Map<String,String> credentials = CsvTestData.readScenario(registerLogin, csvFileLocation);
            context.setTestData(registerLogin, List.of(credentials));
            registerPage().registerNewAccount();
        } catch (Exception e) {
            throw new RuntimeException("Tools Shop registration failed for test data row: " + registerLogin, e);
        }
    }
}
