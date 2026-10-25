package com.example.playwrightbdd.config;

public final class TestConfig {
    private static final String DEFAULT_BASE_URL = "https://freelance-learn-automation.vercel.app/";

    private TestConfig() {
    }

    public static String baseUrl() {
        return resolveBaseUrl(System.getProperty("base.url", DEFAULT_BASE_URL));
    }

    public static String resolveBaseUrl(String configuredUrl) {
        if (configuredUrl == null || configuredUrl.isBlank()) {
            throw new IllegalArgumentException("Base URL must not be blank");
        }

        String baseUrl = configuredUrl.trim().replaceAll("/+$", "");
        if (!baseUrl.matches("https?://.+")) {
            throw new IllegalArgumentException("Base URL must start with http:// or https://");
        }
        return baseUrl;
    }

    public static String browserName() {
        return System.getProperty("browser", "chromium").trim().toLowerCase();
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }

    public static Object viewportWidth() {
        return Integer.parseInt(System.getProperty("viewport.width", "1280"));
    }

    public static Object viewportHeight() {
        return Integer.parseInt(System.getProperty("viewport.height", "720"));
    }
}
