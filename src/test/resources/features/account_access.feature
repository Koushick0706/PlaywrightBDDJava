@account
Feature: Customer account access
  As a customer
  I want to register and log in
  So that I can access the shopping application

  Background:
    Given the login page is open

  @login @negative
  Scenario: Reject login with invalid credentials
    When I log in with invalid credentials from Excel row "invalid-login"
    Then the login should be rejected with an error message

  @register @login @smoke
  Scenario: Register a new customer and log in
    When I register a new customer using Excel row "register-login"
    Then the account registration should succeed
    When I log in with the new account
    Then I should reach the shopping dashboard
