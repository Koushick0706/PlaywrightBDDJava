package com.example.playwrightbdd.steps;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class HomePageSteps {
    private final TestContext context;

    public HomePageSteps(TestContext context) {
        this.context = context;
    }

    @Given("the browser is open")
    public void theBrowserIsOpen() {
        Assertions.assertNotNull(context.page(), "Browser page should be initialized by the scenario hook");
    }

    @When("I open the home page")
    public void iOpenTheHomePage() {
        homePage().open();
    }

    @Then("the home page should be displayed")
    public void theHomePageShouldBeDisplayed() {
        Assertions.assertTrue(homePage().isLoaded(), "Expected the page body to be visible");
        Assertions.assertFalse(homePage().title().isBlank(), "Expected the page title to be present");
    }

    private HomePage homePage() {
        return new HomePage(context.page());
    }
}
