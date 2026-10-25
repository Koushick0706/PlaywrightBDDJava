package com.example.playwrightbdd.pages.ToolShop;

import com.example.playwrightbdd.locators.ToolShop.HomePageLocators;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import java.util.List;

public class HomePage {

    private final Page page;
    private final String baseUrl;

    public HomePage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void open() {
        page.navigate(baseUrl);
    }

    public void openSignInPage() {
        page.locator(HomePageLocators.SIGN_IN_LINK).click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void visitHomePage() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.getByText(HomePageLocators.HOME_PAGE,new Page.GetByTextOptions().setExact(true)).click();
    }


}
