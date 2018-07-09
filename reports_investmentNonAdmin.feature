@InvestmentReportsNonAdmin
Feature: As a Non Admin Voyanta user, I want to see Investment report

  @PermissionTestCase1
  Scenario Outline: Checking Valuation trend report
  #Shared only asset and legalentity
    Given '<Username>' logs into the system with '<Password>'
    And user navigate to Reports 'Investments' page
    When user clears the existing filters
    And user selects the investment filters as belows
      | Quarter Start | <Quarter Start> |
      | Quarter End   | <Quarter End>   |
      | Ownership     | <Ownership>     |
      | Currency      | <Currency>      |

    And user applies the filter
    And user exports the '<Report Name>' report
    Then the exported report should match with expected '<ExpectedReport>'

    Examples:
      | Username                | Password   | Quarter Start       | Quarter End         | Ownership | Currency | Report Name                | ExpectedReport                       |
      | nonadmin@invreports.com | password1! | 2015-2019, 2016, Q1 | 2015-2019, 2016, Q1 | Test1     | USD      | Investment Valuation Trend | Investment_PermissionsTestCase1.xlsx |

  @PermissionsTestCase2
  Scenario Outline: Checking all reports data
  # covers all 5 reports
    Given '<Username>' logs into the system with '<Password>'
    And user navigate to Reports 'Investments' page
    When user clears the existing filters
    And user selects the investment filters as belows
      | Quarter Start | <Quarter Start> |
      | Quarter End   | <Quarter End>   |
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
      | Username                | Password   | Quarter Start       | Quarter End         | PercentageType    | Ownership | Currency | Report1                    | Report2                    | Report3           | Report4            | Report5       | ExpectedReport1                           | ExpectedReport2                          | ExpectedReport3                  | ExpectedReport4                  | ExpectedReport5                     |
      | nonadmin@invreports.com | password1! | 2010-2014, 2012, Q1 | 2010-2014, 2012, Q1 | Percentage, Value | INVNAME51 | GBP      | Investment Diversification | Investment Valuation Trend | Commitments Trend | Top 10 Investments | Top 10 Owners | Investment_InvestmentDiversification.xlsx | Investment_InvestmentValuationTrend.xlsx | Investment_CommitmentsTrend.xlsx | Investment_Top10Investments.xlsx | Investment_Top10OwnersNonAdmin.xlsx |
