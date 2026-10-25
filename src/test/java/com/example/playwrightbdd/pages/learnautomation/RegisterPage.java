package com.example.playwrightbdd.pages.learnautomation;

import com.example.playwrightbdd.context.TestContext;
import com.example.playwrightbdd.locators.learnautomation.RegisterPageLocators;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RegisterPage {
    private final Page page;
    private final String baseUrl;

    public RegisterPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void open() {
        page.navigate(baseUrl + "/signup");
    }


    public void fillRegistrationForm() {
        List<Map<String, String>> values = TestContext.testData();
        System.out.println("Filling registration form with values: " + values);
        System.out.println("Filling registration form with Name: " + values.get(0).get("Name"));
        page.getByPlaceholder(RegisterPageLocators.FIRST_NAME_INPUT).fill(values.get(0).get("Name"));
        System.out.println("Filling registration form with Email: " + values.get(0).get("Email"));
        page.getByPlaceholder(RegisterPageLocators.EMAIL_INPUT).fill(values.get(0).get("Email"));
        page.getByTitle(RegisterPageLocators.PASSWORD).fill(values.get(0).get("Password"));
        System.out.println("Filling registration form with Interests: " + values.get(0).get("Interests"));
        Locator interstesOption = page.locator(RegisterPageLocators.INTERESTS_DIV);
        List<String> interests = Arrays.asList(values.get(0).get("Interests").split(","));
        for(String interest : interests){
            interstesOption.filter(new Locator.FilterOptions().setHasText(interest))
                    .locator(RegisterPageLocators.INTERESTS_CHECK_BOX)
                    .check();
        }
       String value = page.locator(RegisterPageLocators.GENDER).textContent();
        if(value !=null && value.equals(values.get(0).get("Gender"))){
            page.locator(RegisterPageLocators.GENDER).check();
        }

        List<String> selectedStates = Arrays.asList(values.get(0).get("State").split(","));
        for(String state : selectedStates){
            page.locator(RegisterPageLocators.STATE).selectOption(new SelectOption().setLabel(state));
        }

        List<String> selectedHobbies = Arrays.asList(values.get(0).get("Hobbies").split(","));
        page.locator(RegisterPageLocators.HOBBIES).selectOption(selectedHobbies.toArray(new String[0]));
        page.locator(RegisterPageLocators.HOBBIES).scrollIntoViewIfNeeded();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(RegisterPageLocators.SUBMIT_BUTTON)).scrollIntoViewIfNeeded();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(RegisterPageLocators.SUBMIT_BUTTON)).click();


    }

    public void checkSuccessMessage() {
        String successMessage = page.locator("//*[text()='" + RegisterPageLocators.SUCCESS_MESSAGE + "']").textContent();
        if(successMessage != null && successMessage.equals(RegisterPageLocators.SUCCESS_MESSAGE)){
            System.out.println("Registration successful: " + successMessage);
        } else {
            System.out.println("Registration failed or success message not found.");
        }
    }
}
