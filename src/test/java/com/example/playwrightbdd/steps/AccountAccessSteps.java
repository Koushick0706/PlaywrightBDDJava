package com.example.playwrightbdd.steps;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.data.ExcelTestData;
import com.example.playwrightbdd.pages.LoginPage;
import com.example.playwrightbdd.pages.RegisterPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class AccountAccessSteps {
    private final TestContext context;
    private String registeredEmail;
    private String registeredPassword;

    public AccountAccessSteps(TestContext context) {
        this.context = context;
    }

    @Given("the login page is open")
    public void theLoginPageIsOpen() {
        loginPage().open();
        Assertions.assertTrue(loginPage().isLoginPage(), "Expected the client login page to be displayed");
    }

    @When("I log in with invalid credentials from Excel row {string}")
    public void iLogInWithInvalidCredentialsFromExcel(String rowKey) {
        Map<String, String> data = new LinkedHashMap<>(ExcelTestData.readScenario(rowKey));
        String email = data.get("email").replace("{uuid}", UUID.randomUUID().toString());
        data.put("email", email);
        context.setTestData(rowKey, data);
        loginPage().login(email, data.get("password"));
    }

    @Then("the login should be rejected with an error message")
    public void theLoginShouldBeRejectedWithAnErrorMessage() {
        Assertions.assertTrue(loginPage().isLoginPage(), "Expected to remain on the login page");
        Assertions.assertFalse(loginPage().visibleFeedback().isBlank(), "Expected an authentication error message");
    }

    @When("I register a new customer using Excel row {string}")
    public void iRegisterANewCustomerUsingExcel(String rowKey) {
        Map<String, String> data = new LinkedHashMap<>(ExcelTestData.readScenario(rowKey));
        registeredEmail = data.get("email").replace("{uuid}", UUID.randomUUID().toString());
        registeredPassword = data.get("password");
        String phone = data.get("phone").replace("{randomPhone}", randomPhoneNumber());
        data.put("email", registeredEmail);
        data.put("phone", phone);
        context.setTestData(rowKey, data);
        RegisterPage registerPage = new RegisterPage(context.page());
        registerPage.open();
        registerPage.register(data.get("firstName"), data.get("lastName"), registeredEmail,
                phone, registeredPassword, data.get("occupation"), data.get("gender"));
    }

    @Then("the account registration should succeed")
    public void theAccountRegistrationShouldSucceed() {
        RegisterPage registerPage = new RegisterPage(context.page());
        Assertions.assertTrue(registerPage.registrationSucceeded(),
                "Expected account creation confirmation or login route. URL: " + context.page().url()
                        + "; page text: " + registerPage.registrationStatusText());
    }

    @When("I log in with the new account")
    public void iLogInWithTheNewAccount() {
        loginPage().open();
        loginPage().login(registeredEmail, registeredPassword);
        loginPage().waitForShoppingDashboard();
    }

    @Then("I should reach the shopping dashboard")
    public void iShouldReachTheShoppingDashboard() {
        Assertions.assertTrue(loginPage().isShoppingDashboard(),
                "Expected successful login to open the shopping dashboard");
    }

    private LoginPage loginPage() {
        return new LoginPage(context.page());
    }

    private String randomPhoneNumber() {
        return Long.toString(ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L));
    }
}
