@InvestmentReports
Feature: As a Voyanta user, I want to see Investmentreport

  Background:
    Given 'admin@Invreports.com' logs into the system with 'password1!'
    And user navigate to Reports 'Investments' page

#  @ValuationTrendTestCase1 @Investment1
#  Scenario Outline: Checking Valuation trend report
#    # Testing with all valuations data (asset, Debt, Equity)
#    When user clears the existing filters
#    And user selects the investment filters as belows
#      | Quarter Start         | <Quarter Start>         |
#      | Quarter End           | <Quarter End>           |
#    And user applies the filter
#    When user selects the existing filters
#    And user selects the investment filters as belows
#      | Ownership             | <Ownership>             |
#      | Currency              | <Currency>              |
#      | Valuation Type(Asset) | <Valuation Type(Asset)> |
#
#    And user applies the filter
#    And user exports the '<Report Name>' report
#    Then the exported report should match with expected '<ExpectedReport>'
#
#    Examples:
#      | Quarter Start       | Quarter End         | Ownership | Currency | Valuation Type(Asset) | Report Name                | ExpectedReport                           |
#      | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 | Test1     | USD      |                       | Investment Valuation Trend | Investment_ValuationsTrendTestCase1.xlsx |
#      | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 | Test2     | USD      |                       | Investment Valuation Trend | Investment_ValuationsTrendTestCase2.xlsx |
#      | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 | Test3     | USD      | no valid data         | Investment Valuation Trend | Investment_ValuationsTrendTestCase3.xlsx |
#      | 2015-2019, 2015, Q1 | 2015-2019, 2015, Q1 | INV026    | AUD      |                       | Investment Valuation Trend | Investment_ValuationsTrendTestCase4.xlsx |

#  @CommitmentsTrendTestCase1
#  Scenario Outline: Checking Commitments trend report
#    #Testing with single investment
#    When user clears the existing filters
#    And user selects the investment filters as belows
#      | Quarter Start | <Quarter Start> |
#      | Quarter End   | <Quarter End>   |
#    And user applies the filter
#    When user selects the existing filters
#    And user selects the investment filters as belows
#      | Ownership     | <Ownership>     |
#      | Currency      | <Currency>      |
#    And user applies the filter
#    And user exports the '<Report Name>' report
#    Then the exported report should match with expected '<ExpectedReport>'
#
#    Examples:
#      | Quarter Start      | Quarter End        | Ownership | Currency | Report Name       | ExpectedReport                            |
#      | 2015-2019, 2015,Q1 | 2015-2019, 2015,Q1 | INV-003   | GBP      | Commitments Trend | Investment_CommitmentsTrendTestCase1.xlsx |
#      | 2015-2019, 2016,Q1 | 2015-2019, 2016,Q1 | INV-004   | GBP      | Commitments Trend | Investment_CommitmentsTrendTestCase2.xlsx |
#
#  @CommitmentsTrendTestCase3
#  Scenario Outline: Checking Commitments trend report
#    #Error message when investment is not selected
#    When user clears the existing filters
#    And user selects the investment filters as belows
#      | Quarter Start | <Quarter Start> |
#      | Quarter End   | <Quarter End>   |
#      | Ownership     | <Ownership>     |
#    And user applies the filter
#    Then user sees the <Error Message> on <Report Name> report
#
#    Examples:
#      | Quarter Start       | Quarter End         | Ownership        | Report Name       | Error Message                                                                |
#      | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 |                  | Commitments Trend | NO REPORT AVAILABLE Please select a single Investment to display commitments |
#      | 2015-2019, 2015, Q1 | 2015-2019, 2016, Q1 | INV-003, INV-004 | Commitments Trend | NO REPORT AVAILABLE Please select a single Investment to display commitments |
#
#
#  @Top10InvestmentsTestCase1
#  Scenario Outline: Checking Top 10 Investments report
#    #Covers all meausres Value at Ownership% , Value , Total Commitments , Unfunded commitments
#    When user clears the existing filters
#    And user selects the investment filters as belows
#      | Quarter Start | <Quarter Start> |
#      | Quarter End   | <Quarter End>   |
#    And user applies the filter
#    When user selects the existing filters
#    And user selects the investment filters as belows
#      | Ownership     | <Ownership>     |
#      | Currency      | <Currency>      |
#    And user applies the filter
#    And user goes to report '<Report Name>' and select '<Measure Type>'
#    And user exports the '<Report Name>' report
#    Then the exported report should match with expected '<ExpectedReport>'
#
#    Examples:
#      | Quarter Start      | Quarter End        | Ownership | Currency | Measure Type                  | Report Name        | ExpectedReport                                      |
#      | 2015-2019, 2018,Q1 | 2015-2019, 2018,Q1 | INV-011   | EUR      | Measure, Value at Ownership%  | Top 10 Investments | Investment_Top10InvestmentsOwnership%.xlsx          |
#      | 2015-2019, 2018,Q1 | 2015-2019, 2018,Q1 | INV-011   | EUR      | Measure, Value                | Top 10 Investments | Investment_Top10InvestmentsValue.xlsx               |
#      | 2015-2019, 2018,Q1 | 2015-2019, 2018,Q1 | INV-011   | EUR      | Measure, Total Commitments    | Top 10 Investments | Investment_Top10InvestmentsTotalCommitments.xlsx    |
#      | 2015-2019, 2018,Q1 | 2015-2019, 2018,Q1 | INV-011   | EUR      | Measure, Unfunded Commitments | Top 10 Investments | Investment_Top10InvestmentsUnfundedCommitments.xlsx |
#
#  @Top10InvestmentsTestCase2
#  Scenario Outline: Checking Top 10 Investments report
#    #error message when Investment Owns nothing
#    When user clears the existing filters
#    And user selects the investment filters as belows
#      | Quarter Start | <Quarter Start> |
#      | Quarter End   | <Quarter End>   |
#    And user applies the filter
#    When user selects the existing filters
#    And user selects the investment filters as belows
#      | Ownership     | <Ownership>     |
#    And user applies the filter
#    Then user sees the <Error Message> on <Report Name> report
#
#    Examples:
#      | Quarter Start       | Quarter End         | Ownership | Report Name        | Error Message                                                                                  |
#      | 2015-2019, 2015, Q1 | 2015-2019, 2015, Q1 | INV-024   | Top 10 Investments | NO REPORT AVAILABLE  Either no report data is available or you do not have permissions to view |

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
      | 2020-2024, 2024, Q1 | 2020-2024, 2024, Q1 | INVNAME60 | GBP      | Top 10 Owners | Investment_Top10Owners.xlsx |

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
