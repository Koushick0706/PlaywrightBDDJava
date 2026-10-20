package com.example.playwrightbdd.pages;

import com.example.playwrightbdd.config.TestConfig;
import com.example.playwrightbdd.locators.LoginPageLocators;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void open() {
        page.navigate(TestConfig.baseUrl().replaceAll("/$", "") + "/#/auth/login");
    }

    public void login(String email, String password) {
        page.locator(LoginPageLocators.EMAIL_INPUT).fill(email);
        page.locator(LoginPageLocators.PASSWORD_INPUT).fill(password);
        page.locator(LoginPageLocators.SUBMIT_BUTTON).click();
    }

    public boolean isLoginPage() {
        return page.url().contains("#/auth/login")
                && page.locator(LoginPageLocators.EMAIL_INPUT).isVisible();
    }

    public boolean isShoppingDashboard() {
        return page.url().contains("#/dashboard/");
    }

    public void waitForShoppingDashboard() {
        page.waitForURL(url -> url.contains("#/dashboard/"),
                new Page.WaitForURLOptions().setTimeout(10_000));
    }

    public String visibleFeedback() {
        Locator feedback = page.locator(LoginPageLocators.FEEDBACK);
        for (int i = 0; i < feedback.count(); i++) {
            Locator candidate = feedback.nth(i);
            if (candidate.isVisible()) {
                return candidate.innerText().trim();
            }
        }
        return "";
    }
}
