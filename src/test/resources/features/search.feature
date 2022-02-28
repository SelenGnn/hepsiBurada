Feature: Search

  Scenario: Rate a Review
    Given I open browser
    And I maximize browser
    And I open "HepsiBurada" page
    And I fill:
      | searchBox  | iphone 13 |
    And I click "searchButton"
    And I click "aProduct"
    And I click "productReviewsTab"
    And I scroll the page
    When I click "productReviewRating"
    Then I see "Teşekkür Ederiz." text in "productReviewRatingText"

