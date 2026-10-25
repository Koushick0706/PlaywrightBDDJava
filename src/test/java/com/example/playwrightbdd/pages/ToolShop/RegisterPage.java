package com.example.playwrightbdd.pages.ToolShop;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.locators.ToolShop.RegisterPageLocators;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;
import java.util.Map;

public class RegisterPage {

    private final Page page;
    private final String baseUrl;

    public RegisterPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void registerNewAccount() {
        List<Map<String,String>> testData = TestContext.testData();
        System.out.println("Filling registration form with values: " + testData);
        page.getByPlaceholder(RegisterPageLocators.FIRST_NAME_INPUT).fill(testData.get(0).get("FirstName"));
        page.getByPlaceholder(RegisterPageLocators.LAST_NAME_INPUT).fill(testData.get(0).get("LastName"));
        page.getByPlaceholder(RegisterPageLocators.DATE_OF_BIRTH_INPUT).fill(testData.get(0).get("DateOfBirth"));
        page.selectOption(RegisterPageLocators.COUNTRY_DROPDOWN, testData.get(0).get("Country"));
        page.getByPlaceholder(RegisterPageLocators.POSTAL_CODE_INPUT).fill(testData.get(0).get("PostalCode"));
        page.getByPlaceholder(RegisterPageLocators.HOUSE_NUMBER_INPUT).fill(testData.get(0).get("HouseNumber"));
        page.getByPlaceholder(RegisterPageLocators.STREET_INPUT).fill(testData.get(0).get("Street"));
        page.getByPlaceholder(RegisterPageLocators.CITY_INPUT).fill(testData.get(0).get("City"));
        page.getByPlaceholder(RegisterPageLocators.STATE_INPUT).fill(testData.get(0).get("State"));
        page.getByPlaceholder(RegisterPageLocators.PHONE_NUMBER_INPUT).fill(testData.get(0).get("PhoneNumber"));
        page.getByPlaceholder(RegisterPageLocators.EMAIL_INPUT).fill(testData.get(0).get("Email"));
        page.getByPlaceholder(RegisterPageLocators.PASSWORD_INPUT).fill(testData.get(0).get("Password"));
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(RegisterPageLocators.SUBMIT_BUTTON)).click();
    }
}
