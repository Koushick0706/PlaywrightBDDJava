@account
Feature: Customer account access
  As a customer
  I want to register and log in
  So that I can access the shopping application

  Background:
    Given the application base URL is "https://freelance-learn-automation.vercel.app"
    And the login page is open

  @register @login @smoke
  Scenario Outline: Register a new customer and log in
    And  navigate to the register page
    When I register a new customer using data row '<register-login>' using csv file '<csvFileLocation>'
    Then the account registration should succeed
    When I log in with the new account
    Then I should reach the shopping dashboard
    Examples:
      | register-login |csvFileLocation|
      | TC_001         |src/test/resources/testdata/registerdata.csv|
      | TC_002         |src/test/resources/testdata/registerdata.csv|
      | TC_003         |src/test/resources/testdata/registerdata.csv|
