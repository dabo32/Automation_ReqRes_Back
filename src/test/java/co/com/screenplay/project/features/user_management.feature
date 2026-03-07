@UserManagement
Feature: User management on ReqRes API
  As a BDG automation analyst
  I want to test the ReqRes API endpoints
  To ensure the integrity of user management operations

  @ListUsers
  Scenario: List users and validate data existence
    Given the analyst consumes the list users endpoint
    When the analyst saves the query response in the data model
    Then the analyst validates with seeThat that the list contains at least one user

  @RegisterUserSuccess
  Scenario: Register a new user successfully
    Given the analyst consumes the register user service
    When the analyst sends the required data and saves the response in the model
    Then the analyst validates with seeThat that the creation response is as expected

  @RegisterUserFailure
  Scenario: Attempt to register a user without mandatory data
    Given the analyst consumes the register service without sending a password
    When the analyst attempts to perform the incomplete registration
    Then the analyst validates with seeThat that the error is "Missing password"

  @UpdateUser
  Scenario: Update existing user information
    Given the analyst consumes the service to update a user
    When the analyst sends the new user information to the system
    Then the analyst validates with seeThat that the update is correct

  @DeleteUser
  Scenario: Delete a user successfully
    Given the analyst consumes the service to delete a user by ID
    When the analyst requests the record deletion in the system
    Then the analyst validates with seeThat that the status code is 204