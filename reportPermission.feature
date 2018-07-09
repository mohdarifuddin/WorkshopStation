@reportPermission
Feature: Checking the permission for the reports

  Background:
    Given user navigates to Assets page
    Given user is in reports page

  @dashboard
  Scenario Outline: Checking the reports on dashboard page with different filters
    Given user can see reports
    And clears the existing filters
    When selects the filters as below
      | Year        | <Year>        |
      | Asset       | <Asset>       |
      | Investment  | <Investment>  |
      | Toggle      | <Toggle>      |
      | report name | <report name> |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @assetDiversification
    Examples: Asset Diversification
      | Year | Area Measure | Asset | Investment         | report name           | Toggle      | export file name           |
      | 2013 |              |       | Capital Mall Trust | Asset Diversification | Asset Value | insufficientPermission.xls |
      | 2013 |              |       | Investor Two Ltd.  | Asset Diversification | Asset Value | sufficientPermission.xls   |

  @top10
    Examples: For Top 10 Assets
      | Year | Area Measure | Asset                               | Investment | report name   | Toggle        | export file name           |
      | 2013 |              | Amsterdam Paulus Potterstraat Store |            | Top 10 Assets | Leasable Area | sufficientPermission.xls   |
      | 2013 |              | Munich Sankt-Jakobs-Platz Store     |            | Top 10 Assets | Total Arrears | insufficientPermission.xls |

  @valuationNOI
    Examples: For Valuation / NOI Trend
      | Year | Area Measure | Asset                      | Investment | report name     | export file name           |
      | 2013 |              | London Cromwell Road Store |            | Valuation Trend | insufficientPermission.xls |

  @OperatingPerformance
  Scenario Outline: Checking the reports on operating performance page with different filters
    Given user navigates to Leasing Overview page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Year        | <Year>        |
      | Asset       | <Asset>       |
      | Investment  | <Investment>  |
      | Toggle      | <Toggle>      |
      | report name | <report name> |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @occupencyTrend
    Examples:  Occupancy Trend
      | Year | Asset | Investment        | report name     | Toggle       | export file name           |
      | 2013 |       | Camden REIT       | Occupancy Trend | Leased Area  | sufficientPermission.xls   |
      | 2013 |       | Investor Two Ltd. | Occupancy Trend | by Unit Area | insufficientPermission.xls |

  @rentalArrears
    Examples: Rental Arrears As Of - 31/12/2013
      | Year | Asset                       | Investment | report name                | Toggle | export file name           |
      | 2013 | Boston 99 High Street Store |            | Arrears As Of - 2013/12/31 |        | insufficientPermission.xls |

  @operatingStatistics
    Examples: Operating Statistics
      | Year | Asset | Investment  | report name                             | Toggle | export file name           |
      | 2013 |       | Camden REIT | Operating Statistics As Of - 2013/12/31 |        | insufficientPermission.xls |

  @leasingActivity
    Examples: Leasing Activity
      | Year | Asset | Investment                      | report name      | Toggle | export file name         |
      | 2013 |       | North America and Europe Fund 2 | Leasing Activity |        | sufficientPermission.xls |

  @leaseExpiry
    Examples: Lease Expiry Schedule
      | Year | Asset | Investment                      | report name           | Toggle                       | export file name           |
      | 2013 |       | Camden REIT                     | Lease Expiry Schedule | Expiring % of Total          | sufficientPermission.xls   |
      | 2013 |       | North America and Europe Fund 1 | Lease Expiry Schedule | Expiring && Termination Area | insufficientPermission.xls |

  @financialPerformance
  Scenario Outline: Checking the Dashbaord on Financial performance page with different filters
    Given user navigates to Financial Overview page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Year        | <Year>        |
      | Asset       | <Asset>       |
      | Investment  | <Investment>  |
      | report name | <report name> |
      | Tab         | <Tab>         |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @budgetVsActual
    Examples: Budget vs Actual
      | Year | Asset | Investment                      | report name      | Tab     | export file name           |

      | 2013 |       | North America and Europe Fund 1 | Budget vs Actual | Revenue | insufficientPermission.xls |
      | 2013 |       | Abit REIT                       | Budget vs Actual | Revenue | sufficientPermission.xls   |

  @revenueAndExpense
    Examples: Revenue and Expense Breakdown
      | Year | Asset | Investment                      | report name                   | Tab     | export file name           |

      | 2013 |       | North America and Europe Fund 1 | Revenue and Expense Breakdown | Revenue | insufficientPermission.xls |
      | 2013 |       | Abit REIT                       | Revenue and Expense Breakdown | Expense | sufficientPermission.xls   |