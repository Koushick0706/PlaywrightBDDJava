package com.example.playwrightbdd.steps.ToolShop;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.pages.ToolShop.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    public LoginPage loginPage() {
        return new LoginPage(context.page(), context.baseUrl());
    }

    @Then("I should see link for registering a new account")
    public void registerLinkShouldBeVisible() {
        loginPage().clickonRegisterLink();
    }

    @When("I log in with the new account into tools Shop")
    public void loginWithNewAccount() {
        loginPage().login();
    }
}
