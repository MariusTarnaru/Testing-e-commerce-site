Feature: Access to the site and login

  @smoke
  Scenario: Access to the site and Search one product. Get a list of all the products found
    Given access to the site
    When search by product 'Arduino'
    Then take a list of all products