@InvestmentTrend
Feature: As a Voyanta user, I want to see Investmentreport

  Background:
    Given 'admin@Invreports.com' logs into the system with 'password1!'
    And user navigate to Reports 'Investments' page

  @ValuationTrendTestCase1
  Scenario Outline: Checking Valuation trend report
    # Testing with all valuations data (asset, Debt, Equity)
    When user clears the existing filters
    And user selects the investment filters as belows
      | Quarter Start         | <Quarter Start>         |
      | Quarter End           | <Quarter End>           |
    And user applies the filter
    When user selects the existing filters
    And user selects the investment filters as belows
      | Ownership             | <Ownership>             |
      | Currency              | <Currency>              |
      | Valuation Type(Asset) | <Valuation Type(Asset)> |

    And user applies the filter
    And user exports the '<Report Name>' report
    Then the exported report should match with expected '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Ownership | Currency | Valuation Type(Asset) | Report Name                | ExpectedReport                           |
      | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 | Test1     | USD      |                       | Investment Valuation Trend | Investment_ValuationsTrendTestCase1.xlsx |
      | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 | Test2     | USD      |                       | Investment Valuation Trend | Investment_ValuationsTrendTestCase2.xlsx |
      | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 | Test3     | USD      | no valid data         | Investment Valuation Trend | Investment_ValuationsTrendTestCase3.xlsx |
      | 2015-2019, 2015, Q1 | 2015-2019, 2015, Q1 | INV026    | AUD      |                       | Investment Valuation Trend | Investment_ValuationsTrendTestCase4.xlsx |

  @CommitmentsTrendTestCase1
  Scenario Outline: Checking Commitments trend report
#Testing with single investment
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
      | Quarter Start      | Quarter End        | Ownership | Currency | Report Name       | ExpectedReport                            |
      | 2015-2019, 2015,Q1 | 2015-2019, 2015,Q1 | INV-003   | GBP      | Commitments Trend | Investment_CommitmentsTrendTestCase1.xlsx |
      | 2015-2019, 2016,Q1 | 2015-2019, 2016,Q1 | INV-004   | GBP      | Commitments Trend | Investment_CommitmentsTrendTestCase2.xlsx |

  @CommitmentsTrendTestCase3
  Scenario Outline: Checking Commitments trend report
    #Error message when investment is not selected
    When user clears the existing filters
    And user selects the investment filters as belows
      | Quarter Start | <Quarter Start> |
      | Quarter End   | <Quarter End>   |
      | Ownership     | <Ownership>     |
    And user applies the filter
    Then user sees the <Error Message> on <Report Name> report

    Examples:
      | Quarter Start       | Quarter End         | Ownership        | Report Name       | Error Message                                                                |
      | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 |                  | Commitments Trend | NO REPORT AVAILABLE Please select a single Investment to display commitments |
      | 2015-2019, 2015, Q1 | 2015-2019, 2016, Q1 | INV-003, INV-004 | Commitments Trend | NO REPORT AVAILABLE Please select a single Investment to display commitments |


  @Top10InvestmentsTestCase1
  Scenario Outline: Checking Top 10 Investments report
    #Covers all meausres Value at Ownership% , Value , Total Commitments , Unfunded commitments
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
    And user exports the '<Report Name>' report
    Then the exported report should match with expected '<ExpectedReport>'

    Examples:
      | Quarter Start      | Quarter End        | Ownership | Currency | Measure Type                  | Report Name        | ExpectedReport                                      |
      | 2015-2019, 2018,Q1 | 2015-2019, 2018,Q1 | INV-011   | EUR      | Measure, Value at Ownership%  | Top 10 Investments | Investment_Top10InvestmentsOwnership%.xlsx          |
      | 2015-2019, 2018,Q1 | 2015-2019, 2018,Q1 | INV-011   | EUR      | Measure, Value                | Top 10 Investments | Investment_Top10InvestmentsValue.xlsx               |
      | 2015-2019, 2018,Q1 | 2015-2019, 2018,Q1 | INV-011   | EUR      | Measure, Total Commitments    | Top 10 Investments | Investment_Top10InvestmentsTotalCommitments.xlsx    |
      | 2015-2019, 2018,Q1 | 2015-2019, 2018,Q1 | INV-011   | EUR      | Measure, Unfunded Commitments | Top 10 Investments | Investment_Top10InvestmentsUnfundedCommitments.xlsx |

  @Top10InvestmentsTestCase2
  Scenario Outline: Checking Top 10 Investments report
    #error message when Investment Owns nothing
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
      | Quarter Start       | Quarter End         | Ownership | Report Name        | Error Message                                                                                  |
      | 2015-2019, 2015, Q1 | 2015-2019, 2015, Q1 | INV-024   | Top 10 Investments | NO REPORT AVAILABLE  Either no report data is available or you do not have permissions to view |
