@InvestmentDiversification
Feature: As a Voyanta user, I want to see Investmentreport

  Background:
    Given 'admin@Invreports.com' logs into the system with 'password1!'
    And user navigate to Reports 'Investments' page

  @InvestmentDiversificationTestCase1
  Scenario Outline: Checking Investment Diversification report
    # Checking with Investment object
    When user clears the existing filters
    And user selects the investment filters as belows
      | Quarter Start | <Quarter Start> |
      | Quarter End   | <Quarter End>   |
      | Currency      | <Currency>      |
      | Ownership     | <Ownership>     |
    And user applies the filter
    And user goes to report '<Report Name>' and select '<Measure Type>'
    And user goes to report '<Report Name>' and select '<Percentage Type>'
    And user exports the '<Report Name>' report
    Then the exported report should match with expected '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | Ownership                                | Measure Type          | Percentage Type        | Report Name                | ExpectedReport                              |
      | 2020-2024, 2021, Q1 | 2020-2024, 2021, Q1 | USD      | InvDivName04 ,InvDivName05 ,InvDivName06 | Measure, Sector       | Percentage, Percentage | Investment Diversification | Investment_InvestmentDIVINVSector.xlsx      |
      | 2020-2024, 2021, Q1 | 2020-2024, 2021, Q1 | USD      | InvDivName08                             | Measure, Region       | Percentage, Value      | Investment Diversification | Investment_InvestmentDIVINVRegion.xlsx      |
      | 2020-2024, 2021, Q1 | 2020-2024, 2021, Q1 | USD      | InvDivName04 ,InvDivName05 ,InvDivName06 | Measure, Lifecycle    | Percentage, Percentage | Investment Diversification | Investment_InvestmentDIVINVLifecycle.xlsx   |
      | 2020-2024, 2021, Q1 | 2020-2024, 2021, Q1 | USD      | InvDivName04 ,InvDivName05 ,InvDivName06 | Measure, Risk Profile | Percentage, Value      | Investment Diversification | Investment_InvestmentDIVINVRiskProfile.xlsx |

  @InvestmentDiversificationTestCase2
  Scenario Outline: Checking Investment Diversification report
    # Checking with Asset object
    When user clears the existing filters
    And user selects the investment filters as belows
      | Quarter Start | <Quarter Start> |
      | Quarter End   | <Quarter End>   |
    And user applies the filter
    When user selects the existing filters
    And user selects the investment filters as belows
      | Ownership     | <Ownership>     |
      | Currency      | <Currency>      |
    And user applies the filter
    And user goes to report '<Report Name>' and select '<Measure Type>'
    And user goes to report '<Report Name>' and select '<Percentage Type>'
    And user exports the '<Report Name>' report
    Then the exported report should match with expected '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Ownership    | Currency | Measure Type          | Percentage Type        | Report Name                | ExpectedReport                                |
      | 2010-2014, 2014, Q1 | 2010-2014, 2014, Q1 | LegDivName01 | GBP      | Measure, Sector       | Percentage, Value      | Investment Diversification | Investment_InvestmentDIVAssetSector.xlsx      |
      | 2010-2014, 2014, Q1 | 2010-2014, 2014, Q1 | LegDivName01 | GBP      | Measure, Region       | Percentage, Value      | Investment Diversification | Investment_InvestmentDIVAssetRegion.xlsx      |
      | 2010-2014, 2014, Q1 | 2010-2014, 2014, Q1 | LegDivName01 | GBP      | Measure, Lifecycle    | Percentage, Percentage | Investment Diversification | Investment_InvestmentDIVAssetLifecycle.xlsx   |
      | 2010-2014, 2014, Q1 | 2010-2014, 2014, Q1 | LegDivName01 | GBP      | Measure, Risk Profile | Percentage, Value      | Investment Diversification | Investment_InvestmentDIVAssetRiskProfile.xlsx |
