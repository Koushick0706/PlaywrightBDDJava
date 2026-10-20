@smoke
Feature: Home page
  As a visitor
  I want to open the home page
  So that I can confirm the site is available

  Scenario: Open the home page
    Given the browser is open
    When I open the home page
    Then the home page should be displayed
