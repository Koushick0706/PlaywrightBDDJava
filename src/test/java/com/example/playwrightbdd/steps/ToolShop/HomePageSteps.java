package com.example.playwrightbdd.steps.ToolShop;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.pages.ToolShop.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class HomePageSteps {

    private final TestContext context;

    public HomePageSteps(TestContext context) {
        this.context = context;
    }

    public HomePage homePage() {
        return new HomePage(context.page(), context.baseUrl());
    }

    @Given("the home page is open for toolsshop")
    public void theHomePageIsOpen() {
        homePage().open();
    }

    @And("I navigate to the tools shop login page")
    public void navigateToLoginPage() {
        homePage().openSignInPage();
    }

    @And("I should reach the tools shop dashboard and search")
    public void visitDashboard() {
        homePage().visitHomePage();
    }


}
