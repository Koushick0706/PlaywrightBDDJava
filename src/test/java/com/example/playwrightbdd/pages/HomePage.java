package com.example.playwrightbdd.pages;

import com.example.playwrightbdd.config.TestConfig;
import com.example.playwrightbdd.locators.HomePageLocators;
import com.microsoft.playwright.Page;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public void open() {
        page.navigate(TestConfig.baseUrl());
    }

    public String title() {
        return page.title();
    }

    public boolean isLoaded() {
        return page.locator(HomePageLocators.BODY).isVisible();
    }
}
