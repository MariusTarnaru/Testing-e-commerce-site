Feature: Access to the site and login

  @smoke
  Scenario: Access to the site and Search one product. Get a list of all the products found
    Given access to the site
    And take the menu 'Despre noi'
    When search by product 'Arduino'
    Then take a list of all products

  @smoke
  Scenario: Access to the site and Search one product. Get a list of all the products found. Sort
    Given access to the site
    When select 'Audio' list from 'Categorii' navlist
    And sort the product list by price in descending order
    And press 'Show all' button and count '202' products
    And select 'Set 2 Boxe Turn' from product container and get available quantity