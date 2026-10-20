package com.example.playwrightbdd.context;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestContext {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;
    private String testDataKey;
    private Map<String, String> testData = Map.of();

    public Playwright playwright() {
        return playwright;
    }

    public void setPlaywright(Playwright playwright) {
        this.playwright = playwright;
    }

    public Browser browser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public BrowserContext browserContext() {
        return browserContext;
    }

    public void setBrowserContext(BrowserContext browserContext) {
        this.browserContext = browserContext;
    }

    public Page page() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setTestData(String scenarioKey, Map<String, String> values) {
        this.testDataKey = scenarioKey;
        this.testData = Map.copyOf(new LinkedHashMap<>(values));
    }

    public String testDataKey() {
        return testDataKey;
    }

    public Map<String, String> testData() {
        return testData;
    }
}
