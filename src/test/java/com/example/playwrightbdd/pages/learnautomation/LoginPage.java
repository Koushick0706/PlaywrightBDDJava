package com.example.playwrightbdd.pages.learnautomation;

import com.example.playwrightbdd.locators.learnautomation.LoginPageLocators;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    private final Page page;
    private final String baseUrl;

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void open() {
        page.navigate(baseUrl + "/login");
    }

    public void login(String email, String password) {
       fillEmail(email);
       fillPassword(password);
        clickSignInButton();
    }

    public boolean isLoginPage() {
        return page.url().contains("/login")
                && page.locator(LoginPageLocators.EMAIL_INPUT).isVisible();
    }

    public void NewUser() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(LoginPageLocators.NEW_USER)).click();
    }

    public void fillEmail(String email) {
        page.getByPlaceholder(LoginPageLocators.EMAIL_INPUT).fill(email);
    }

    public void fillPassword(String password) {
        page.getByPlaceholder(LoginPageLocators.PASSWORD_INPUT).fill(password);
    }

    public void clickSignInButton() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(LoginPageLocators.SUBMIT_BUTTON)).click();
    }

    public void checkLoginSuccess() {
        Locator loginCheck = page.getByAltText(LoginPageLocators.LOGIN_CHECK);
        if (!loginCheck.isVisible()) {
            throw new RuntimeException("Login failed: Login check element not visible.");
        }else{
            System.out.println("Login successful: Login check element is visible.");
            loginCheck.click();
        }
    }


}
