package com.example.playwrightbdd.context;

import com.example.playwrightbdd.config.TestConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.util.List;
import java.util.Map;

public class TestContext {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;
    private String testDataKey;
    private String baseUrl = TestConfig.baseUrl();
    private static List<Map<String, String>> testData = List.of();

    public String baseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = TestConfig.resolveBaseUrl(baseUrl);
    }

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

    public void setTestData(String scenarioKey, List<Map<String, String>> values) {
        this.testDataKey = scenarioKey;
        this.testData = List.copyOf(values);
    }

    public String testDataKey() {
        return testDataKey;
    }

    public static List<Map<String, String>> testData() {
        return testData;
    }
}
