@InvestmentAllInvestments
Feature: As a Voyanta user, I want to see Investmentreport

  Background:
    Given 'admin@Invreports.com' logs into the system with 'password1!'
    And user navigate to Reports 'Investments' page

  @Top10OwnersTestcase1
  Scenario Outline: Checking Top 10 Owners report
    # Ownership
    When user clears the existing filters
    And user selects the investment filters as belows
      | Ownership | <Ownership> |
      | Currency  | <Currency>  |
    And user applies the filter
    And user exports the '<Report Name>' report
    Then the exported report should match with expected '<ExpectedReport>'

    Examples:
      | Ownership   | Currency | Report Name   | ExpectedReport                       |
      | Ownership01 | AUD      | Top 10 Owners | Investment_Top10OwnersOwnership.xlsx |

  @Top10OwnersTestcase2
  Scenario Outline: Checking Top 10 Owners report
    # Top 10 Owners
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
    And user exports the '<Report Name>' report
    Then the exported report should match with expected '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Ownership | Currency | Report Name   | ExpectedReport              |
      | 2020-2024, 2024, Q1 | 2020-2024, 2024, Q1 | INVNAME60 | GBP      | Top 10 Owners | Investment_Top10Owners2.xlsx |

  @Top10OwnersTestCase3
  Scenario Outline: Checking Top 10 Owners report
    #error message If more than one Investment is selected in the Ownership Filter
    When user clears the existing filters
    And user selects the investment filters as belows
      | Quarter Start | <Quarter Start> |
      | Quarter End   | <Quarter End>   |
    And user applies the filter
    When user selects the existing filters
    And user selects the investment filters as belows
      | Ownership     | <Ownership>     |
    And user applies the filter
    Then user sees the <Error Message> on <Report Name> report

    Examples:
      | Quarter Start       | Quarter End         | Ownership               | Report Name   | Error Message                                                            |
      | 2020-2024, 2024, Q1 | 2020-2024, 2024, Q1 | INVNAME60 , Ownership01 | Top 10 Owners | NO REPORT AVAILABLE  Please select a single Investment to display owners |

  @AllInvestmentReports
  Scenario Outline: Checking all reports data
    # covers all 5 reports
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
    And user goes to report '<Report1>' and select '<PercentageType>'
    And user exports the '<Report1>' report
    Then the exported report should match with expected '<ExpectedReport1>'
    And user exports the '<Report2>' report
    Then the exported report should match with expected '<ExpectedReport2>'
    And user exports the '<Report3>' report
    Then the exported report should match with expected '<ExpectedReport3>'
    And user exports the '<Report4>' report
    Then the exported report should match with expected '<ExpectedReport4>'
    And user exports the '<Report5>' report
    Then the exported report should match with expected '<ExpectedReport5>'


    Examples:
      | Quarter Start       | Quarter End         | Ownership | Currency | PercentageType    | Report1                    | Report2                    | Report3           | Report4            | Report5       | ExpectedReport1                           | ExpectedReport2                          | ExpectedReport3                  | ExpectedReport4                  | ExpectedReport5             |
      | 2010-2014, 2012, Q1 | 2010-2014, 2012, Q1 | INVNAME51 | GBP      | Percentage, Value | Investment Diversification | Investment Valuation Trend | Commitments Trend | Top 10 Investments | Top 10 Owners | Investment_InvestmentDiversification.xlsx | Investment_InvestmentValuationTrend.xlsx | Investment_CommitmentsTrend.xlsx | Investment_Top10Investments.xlsx | Investment_Top10Owners.xlsx |

  @CurrencyConversion
  Scenario Outline: Checking all reports data
    # covers all 5 reports
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
    And user goes to report '<Report1>' and select '<PercentageType>'
    And user exports the '<Report1>' report
    Then the exported report should match with expected '<ExpectedReport1>'
    And user exports the '<Report2>' report
    Then the exported report should match with expected '<ExpectedReport2>'
    And user exports the '<Report3>' report
    Then the exported report should match with expected '<ExpectedReport3>'
    And user exports the '<Report4>' report
    Then the exported report should match with expected '<ExpectedReport4>'
    And user exports the '<Report5>' report
    Then the exported report should match with expected '<ExpectedReport5>'

    Examples:
      | Quarter Start       | Quarter End         | Ownership | Currency | PercentageType    | Report1                    | Report2                    | Report3           | Report4            | Report5       | ExpectedReport1                             | ExpectedReport2                            | ExpectedReport3                    | ExpectedReport4                    | ExpectedReport5               |
      | 2010-2014, 2012, Q1 | 2010-2014, 2012, Q1 | INVNAME51 | AUD      | Percentage, Value | Investment Diversification | Investment Valuation Trend | Commitments Trend | Top 10 Investments | Top 10 Owners | Investment_InvestmentDiversificationCV.xlsx | Investment_InvestmentValuationTrendCV.xlsx | Investment_CommitmentsTrendCV.xlsx | Investment_Top10InvestmentsCV.xlsx | Investment_Top10OwnersCV.xlsx |


  @ConsolidatedValue'Y'
   Scenario Outline: Checking Consolidated value
    Testing Valuation Trend with consolidated value 'Y'
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
    And user exports the '<Report Name>' report
    Then the exported report should match with expected '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Ownership | Currency | Report Name                | ExpectedReport                    |
      | 2010-2014, 2010, Q1 | 2010-2014, 2010, Q1 | INVNAME75 | GBP      | Investment Valuation Trend | Investment_ConsolidatedValue.xlsx |

  @ScenarioName
  Scenario Outline: Checking Valuation trend report
  #Asset transaction has a scenario name
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
    And user exports the '<Report Name>' report
    Then the exported report should match with expected '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Ownership | Currency | Report Name                | ExpectedReport                    |
      | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 | Test8     | USD      | Investment Valuation Trend | Investment_ScenarioName.xlsx      |
