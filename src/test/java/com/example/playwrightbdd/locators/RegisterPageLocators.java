package com.example.playwrightbdd.locators;

public final class RegisterPageLocators {
    public static final String FIRST_NAME_INPUT = "#firstName";
    public static final String LAST_NAME_INPUT = "#lastName";
    public static final String EMAIL_INPUT = "#userEmail";
    public static final String PHONE_INPUT = "#userMobile";
    public static final String OCCUPATION_SELECT = "select[formcontrolname='occupation']";
    public static final String PASSWORD_INPUT = "#userPassword";
    public static final String CONFIRM_PASSWORD_INPUT = "#confirmPassword";
    public static final String AGE_CONFIRMATION_CHECKBOX = "input[formcontrolname='required']";
    public static final String SUBMIT_BUTTON = "#login";
    public static final String BODY = "body";

    public static String genderRadio(String gender) {
        return "input[formcontrolname='gender'][value='" + gender + "']";
    }

    private RegisterPageLocators() {
    }
}
