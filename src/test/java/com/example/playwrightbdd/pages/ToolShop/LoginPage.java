package com.example.playwrightbdd.pages.ToolShop;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.locators.ToolShop.LoginPageLocators;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;
import java.util.Map;

public class LoginPage {

    private final Page page;
    private final String baseUrl;

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void clickonRegisterLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(LoginPageLocators.REGISTER_LINK)).click();
    }

    public void login() {
        if (!page.url().contains("/auth/login")) {
            page.getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName(LoginPageLocators.LOGIN_BUTTON)).click();
            page.waitForURL("**/auth/login");
        }

        List<Map<String,String>> testData = TestContext.testData();
        Locator email = page.getByPlaceholder(LoginPageLocators.EMAIL_INPUT,
                new Page.GetByPlaceholderOptions().setExact(true));
        Locator password = page.getByPlaceholder(LoginPageLocators.PASSWORD_INPUT,
                new Page.GetByPlaceholderOptions().setExact(true));
        email.fill(testData.get(0).get("Email"));
        password.fill(testData.get(0).get("Password"));
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(LoginPageLocators.LOGIN_BUTTON)).click();
    }
}
