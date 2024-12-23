Feature: Add cart feature

  @Web
  Scenario: Verify item added to cart
    Given User launch the ebay portal
    When User search an item "book"
    When User select item from search results
    When User add item to cart
    Then Verify number of items in cart updated
    Then close the browser

    @API
    Scenario: Verify Get api response
      Given User send Get request
      Then Verify response
