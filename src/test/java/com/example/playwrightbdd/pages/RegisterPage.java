package com.example.playwrightbdd.pages;

import com.example.playwrightbdd.config.TestConfig;
import com.example.playwrightbdd.locators.RegisterPageLocators;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public class RegisterPage {
    private final Page page;

    public RegisterPage(Page page) {
        this.page = page;
    }

    public void open() {
        page.navigate(TestConfig.baseUrl().replaceAll("/$", "") + "/#/auth/register");
    }

    public void register(String firstName, String lastName, String email, String phone, String password,
                         String occupation, String gender) {
        page.locator(RegisterPageLocators.FIRST_NAME_INPUT).fill(firstName);
        page.locator(RegisterPageLocators.LAST_NAME_INPUT).fill(lastName);
        page.locator(RegisterPageLocators.EMAIL_INPUT).fill(email);
        page.locator(RegisterPageLocators.PHONE_INPUT).fill(phone);
        page.locator(RegisterPageLocators.OCCUPATION_SELECT).selectOption(
                new SelectOption().setLabel(occupation));
        page.locator(RegisterPageLocators.genderRadio(gender)).check();
        page.locator(RegisterPageLocators.PASSWORD_INPUT).fill(password);
        page.locator(RegisterPageLocators.CONFIRM_PASSWORD_INPUT).fill(password);
        page.locator(RegisterPageLocators.AGE_CONFIRMATION_CHECKBOX).check();
        page.waitForResponse(response -> response.url().contains("/api/ecom/auth/register"),
                new Page.WaitForResponseOptions().setTimeout(10_000),
                () -> page.locator(RegisterPageLocators.SUBMIT_BUTTON).click());
    }

    public boolean registrationSucceeded() {
        String bodyText = registrationStatusText().toLowerCase();
        return bodyText.contains("account created")
                || bodyText.contains("registered successfully")
                || page.url().contains("#/auth/login");
    }

    public String registrationStatusText() {
        return page.locator(RegisterPageLocators.BODY).innerText();
    }

}
