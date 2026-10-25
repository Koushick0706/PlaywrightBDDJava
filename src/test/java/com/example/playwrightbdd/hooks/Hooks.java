package com.example.playwrightbdd.hooks;

import com.example.playwrightbdd.config.TestConfig;
import com.example.playwrightbdd.context.TestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Hooks {
    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void startBrowser() {
        Playwright playwright = Playwright.create();
        context.setPlaywright(playwright);

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(TestConfig.headless());
        context.setBrowser(switch (TestConfig.browserName()) {
            case "chromium" -> playwright.chromium().launch(launchOptions);
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> throw new IllegalArgumentException(
                    "Unsupported browser '%s'. Use chromium, firefox, or webkit.".formatted(TestConfig.browserName()));
        });
        BrowserContext browserContext = context.browser().newContext(new Browser.NewContextOptions().setViewportSize((Integer) TestConfig.viewportWidth(), (Integer) TestConfig.viewportHeight()));
        context.setBrowserContext(browserContext);
        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        context.setPage(browserContext.newPage());
    }

    @After
    public void stopBrowser(Scenario scenario) {
        try {
            attachTestData(scenario);
        } catch (Exception e) {
            scenario.log("Could not attach test data: " + e.getMessage());
        }
        Path tracePath;
        try {
            tracePath = saveAndAttachTrace(scenario);
        } finally {
            closeBrowserResources();
        }
        if (tracePath != null) {
            try {
                openTraceViewer(tracePath);
            } catch (IOException e) {
                scenario.log("Could not open Playwright Trace Viewer: " + e.getMessage());
            }
        }
    }

    private void attachTestData(Scenario scenario) {
        if (context.testDataKey() == null || context.testData().isEmpty()) {
            return;
        }

        Map<String, Object> attachment = new LinkedHashMap<>();
        attachment.put("testDataScenarioKey", context.testDataKey());
        attachment.put("browser", TestConfig.browserName());
        attachment.put("baseUrl", context.baseUrl());
        Map<String, String> safeValues = new LinkedHashMap<>(context.testData().get(0));
        safeValues.replaceAll((key, value) -> "password".equalsIgnoreCase(key) ? "[REDACTED]" : value);
        attachment.put("values", safeValues);

        try {
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(attachment);
            scenario.attach(json.getBytes(StandardCharsets.UTF_8), "application/json", "Test data.json");
        } catch (Exception e) {
            throw new IllegalStateException("Could not serialize test data", e);
        }
    }

    private Path saveAndAttachTrace(Scenario scenario) {
        if (context.browserContext() == null) {
            return null;
        }

        Path tracePath = Path.of("src", "test", "resources", "TracingFiles", traceFileName(scenario));
        try {
            Files.createDirectories(tracePath.getParent());
            context.browserContext().tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        } catch (Exception e) {
            scenario.log("Could not save the Playwright trace: " + e.getMessage());
            return null;
        }

        try {
            scenario.attach(Files.readAllBytes(tracePath), "application/zip", tracePath.getFileName().toString());
        } catch (Exception e) {
            scenario.log("Could not attach the Playwright trace: " + e.getMessage());
        }
        return tracePath.toAbsolutePath();
    }

    private void openTraceViewer(Path tracePath) throws IOException {
        String javaExecutable = Path.of(System.getProperty("java.home"), "bin",
                System.getProperty("os.name").toLowerCase().contains("win") ? "java.exe" : "java")
                .toString();
        new ProcessBuilder(javaExecutable,
                "-cp", System.getProperty("java.class.path"),
                "com.microsoft.playwright.CLI", "show-trace", tracePath.toString())
                .directory(Path.of("").toAbsolutePath().toFile())
                .inheritIO()
                .start();
    }

    private String traceFileName(Scenario scenario) {
        String scenarioName = scenario.getName().replaceAll("[^A-Za-z0-9._-]", "-");
        return scenarioName + "-" + UUID.randomUUID() + ".zip";
    }

    private void closeBrowserResources() {
        try {
            if (context.browserContext() != null) {
                context.browserContext().close();
            }
        } finally {
            try {
                if (context.browser() != null) {
                    context.browser().close();
                }
            } finally {
                if (context.playwright() != null) {
                    context.playwright().close();
                }
            }
        }
    }
}
