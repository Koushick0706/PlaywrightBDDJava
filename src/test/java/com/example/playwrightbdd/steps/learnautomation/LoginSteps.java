package com.example.playwrightbdd.steps.learnautomation;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.pages.learnautomation.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    private LoginPage loginPage(){
        return new LoginPage(context.page(), context.baseUrl());
    }

    @Given("the application base URL is {string}")
    public void setApplicationBaseUrl(String baseUrl) {
        context.setBaseUrl(baseUrl);
    }

    @Given("the login page is open")
    public void theLoginPageIsOpen() {
        loginPage().open();
    }

    @And("navigate to the register page")
    public void navigateToTheRegisterPage() {
        loginPage().NewUser();
    }

    @When("I log in with the new account")
    public void logInWithTheNewAccount() {
        List<Map<String, String>> testData = context.testData();
        loginPage().login(testData.get(0).get("Email"), testData.get(0).get("Password"));
    }

    @Then("I should reach the shopping dashboard")
    public void iShouldReachTheShoppingDashboard() {
        loginPage().checkLoginSuccess();
    }

}
