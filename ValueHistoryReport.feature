@ValueHistoryReport
Feature: Checking the value history table

  @ValueHistoryReport
  Scenario Outline: Checking the values in Value History Table
    #Given user is on the Browse page
    Given 'voyantasupport@aew.com' logs into the system with 'Altus2015!'
    And the user goes to Building page
    When the user selects building name "<Buildingname>"
    And the user clicks Asset Overview button
    #And the user can see "<Buildingname>" page
    And user selects Value History Table
    And export the value history report
    Then saved report should match with the expected report with name '<Filename>'

    Examples:

      | Buildingname            | Filename                  |
      | Palladium Apartments    | Palladium_Apartments.xlsx |
      | Cameron Court           | CameronCourt.xlsx         |
      | Arborpoint at Market St | ArborpointatMarketSt.xlsx |
      | 360 Lexington           | Lexington.xlsx            |

