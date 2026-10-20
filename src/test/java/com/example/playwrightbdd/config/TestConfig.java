package com.example.playwrightbdd.config;

public final class TestConfig {
    private static final String DEFAULT_BASE_URL = "https://rahulshettyacademy.com/client";

    private TestConfig() {
    }

    public static String baseUrl() {
        return System.getProperty("base.url", DEFAULT_BASE_URL);
    }

    public static String browserName() {
        return System.getProperty("browser", "chromium").trim().toLowerCase();
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}
