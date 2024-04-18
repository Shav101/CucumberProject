@login @regression
Feature: Crater app user management
  Users with permissions should be able interact 
  with the application on successful login

  Background: 
    Given As a user, I am on the login page

  @validlogin @smoketest
  Scenario: Successful login
    When I enter valid username and valid password
    And I click on login button
    Then I should be on user profile page

  @invalidlogin
  Scenario: Invalid username login
    When I enter invalid username and valid password
    And I click on login button
    Then I should see an error message
    And I should not be logged in

  @invalidPasswordLogin
  Scenario: Invalid password login
    When I enter valid username and invalid password
    And I click on login button
    Then I should see an error message
    And I should not be logged in


  @invalidLoginAttempts
  Scenario Outline: Invalid credential login attempt
  When I enter email "<email>" and password "<password>"
  And I click on login button
  Then I should see an error message
  And I should not be logged in
  
  Examples:
  | email                           | password         |
  | entityadmin@primetechschool.com | hellopassword    |
  | entityAd@primetechschool.com    | primetech@school |
  | invalid@primetechschool.com     | primetech@school |
  
  
  
  
  @validLoginAttempts
  Scenario Outline: valid credential login attempt
  When I enter email "<email>" and password "<password>"
  And I click on login button
  Then I should be on user profile page
  
  Examples:
  | email                           | password         |
  | entityadmin@primetechschool.com | primetech@school |
  | entityadmin@primetechschool.com | primetech@school |
  | entityadmin@primetechschool.com | primetech@school |


  
  
  
  