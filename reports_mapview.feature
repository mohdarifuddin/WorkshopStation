@Map_View
Feature: As a Voyanta User, I want to see Map View Asset Validity Report

  Background:
    Given 'automationmap@assetvalidity.com' logs into the system with 'password1!'

  @WithoutAnyFilters
  Scenario Outline: Checking the Valid Assets data, without any selected filter
    Given user navigates to 'Map' page
    When user select the filters dropdown and clear button
    And user applies Filter
    Then the actual properties count should match with expected '<ExpectedResult>'

    Examples: Without any filters only Valid Asset should show on the Properties panel
      | ExpectedResult |
      | 7              |

  @WithoutSectorAndBenchmarkregion
  Scenario Outline: Checking the Valid Assets data, without having Sector and BenchmarkRegion
    Given user navigates to 'Map' page
    When user select the filters dropdown and clear button
    When selects the map filters as below
      | Asset Manager | <Asset Manager> |
    And user applies Filter
    Then user verifies the properties count '<Total Properties>' and Asset Name '<Asset Name>'

    Examples: Without Sector and Benchmark Region, valid asset shown in properties panel
      | Asset Manager     | Total Properties | Asset Name          |
      | NoSectorandRegion | 1                | Map-AssetValidity01 |


  @WithAllFilters
  Scenario Outline: Checking the Valid Assets data, with all filter
    Given user navigates to 'Map' page
    When user select the filters dropdown and clear button
    When selects the map filters as below
      | Property Type | <Property Type> |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
    And user applies Filter
    Then user verifies the properties count '<Total Properties>' and Asset Name '<Asset Name>'

    Examples: With all filters only valid asset shown in properties panel
      | Asset Manager | Property Type | Region | Total Properties | Asset Name       |
      | Murali        | Residential   | Europe | 1                | Map-completeinfo |

  @WithPurchase=0
  Scenario Outline: Checking the Valid Assets data for the Map View
    Given user navigates to 'Map' page
    When user select the filters dropdown and clear button
    When selects the map filters as below
      | Property Type | <Property Type> |
      | Region        | <Region>        |
    And user applies Filter
    Then user verifies the properties count '<Total Properties>' and Asset Name '<Asset Name>'

    Examples: With AssetTransaction and Purchase type with zero ownership
      | Property Type | Region | Total Properties | Asset Name                           |
      | Residential   | Europe | 2                | Map-WithPurchase=0, Map-completeinfo |

  @WithSale
  Scenario Outline: Checking the Valid Assets data for the Map View
    Given user navigates to 'Map' page
    When user select the filters dropdown and clear button
    When selects the map filters as below
      | Property Type | <Property Type> |
      | Region        | <Region>        |
    And user applies Filter
    Then user verifies the properties count '<Total Properties>' and Asset Name '<Asset Name>'

    Examples: With AssetTransaction and Sale type with some ownership Value
      | Property Type | Region        | Total Properties | Asset Name    |
      | Office        | North America | 1                | Map-WithSale2 |

  @WithZoomInAndDefaultFilters
  Scenario Outline: Checking the Valid Assets data for the Map View
    Given user navigates to 'Map' page
    When user select the filters dropdown and clear button
    And user applies Filter
    Then the actual properties count should match with expected '<ExpectedResult>'
    And the user 'Zoom IN' on the Map view
    Then user verifies properties in view '<View Properties>' and Asset Name '<Asset Name>'

    Examples: With AssetTransaction and Sale type with some ownership Value
      | ExpectedResult | View Properties | Asset Name                                                                                        |
      | 7              | 5               | Map-AssetValidity01, Map-Assetcoordinates, Map-WithPurchase, Map-WithPurchase=0, Map-completeinfo |
