Feature: Tools Shop
  As a customer
  I want to access the tools shop
  So that I can browse and purchase tools

  Background:
    Given the application base URL is "https://practicesoftwaretesting.com/"
    And the home page is open for toolsshop

  @tools-shop @smoke
  Scenario Outline: Access the tools shop
    When I navigate to the tools shop login page
    Then I should see link for registering a new account
    Then I should be redirected to the registration page and register a new account '<register-login>' using csv file '<csvFileLocation>'
    When I log in with the new account into tools Shop
    Then I should reach the tools shop dashboard and search
    ##TODO: Add steps for searching and adding tools to the cart
    And I should see the list of available tools
    When I select a tool and add it to the cart
    Then the tool should be added to the cart successfully
    When I proceed to checkout
    Then I should be able to complete the purchase successfully
    Examples:
      | register-login |csvFileLocation|
      | TC_001         |src/test/resources/testdata/toolsshop.csv|