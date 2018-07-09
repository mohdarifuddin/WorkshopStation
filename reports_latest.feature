@ReportsAndDashboards
Feature: Checking the reports and its data

  Background:
    Given 'qvxml@testaccount.com' logs into the system with 'password1!QA'
    Given user is in reports page

  @OverviewDashboard @OpsModel @Assets
  Scenario Outline: Checking the reports on dashboard page with different filters
    Given user can see reports
    And clears the existing filters
    When selects the filters as below

      | Currency      | <Currency>      |
      | Year          | <Year>          |
      | Sector        | <Sector>        |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
      | Asset         | <Asset>         |
      | Asset Tags    | <Asset Tags>    |
      | Investment    | <Investment>    |
      | Quarter       | <Quarter>       |
      | Month         | <Month>         |
      | Area Measure  | <Area Measure>  |
      | Toggle        | <Toggle>        |
      | report name   | <report name>   |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @AssetDiversification
    Examples: Asset Diversification

      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment      | Quarter | Month | report name           | Toggle              | export file name           |
      | GBP      | 2013 | SF           | Office |        |               |       |            |                 |         |       | Asset Diversification | Asset Value         | Asset_Value.xls            |
      | GBP      | 2013 | SF           | Office |        |               |       |            |                 |         |       | Asset Diversification | Asset Value         | Asset_Value.xls            |
      | GBP      | 2013 | SF           | Office |        |               |       |            |                 |         |       | Asset Diversification | Gross Rental Income | Gross_Rental_Income.xls    |
      | GBP      | 2013 | SF           | Office |        |               |       |            |                 |         |       | Asset Diversification | Net Rental Income   | Net_Rental_Income.xls      |
      | GBP      | 2013 | SF           | Office |        |               |       |            |                 |         |       | Asset Diversification | Leasable Area       | Leasable_Area.xls          |
      | GBP      | 2013 | SF           | Office |        |               |       |            |                 |         |       | Asset Diversification | Number of Assets    | Number_of_Assets.xls       |
      #ownership percentage test case
      | EUR      | 2013 | SF           |        |        |               |       |            | UB Realty Trust |         |       | Asset Diversification | Gross Rental Income | OP_Gross_Rental_Income.xls |

  @Top10Assets
    Examples: For Top 10 Assets
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment      | Quarter | Month | report name   | Toggle              | export file name               |
      | AUD      | 2013 | SF           | Retail |        |               |       |            |                 |         |       | Top 10 Assets | Asset Value         | Top10_Asset_Value.xls          |
      | AUD      | 2013 | SF           | Retail |        |               |       |            |                 |         |       | Top 10 Assets | Net Rental Income   | Top10_Net_Rental_Income.xls    |
      | AUD      | 2013 | SF           | Retail |        |               |       |            |                 |         |       | Top 10 Assets | Gross Rental Income | Top10_Gross_Rental_Income.xls  |
      #Top 10 Total Arrears - Find calculation numbers in database, 27124 = Lease units under building (only 1 here): SELECT * FROM dbo748.Receivables where LeaseKey = 27124 AND AsOfDate < '2013-12-31';
      | AUD      | 2013 | SF           | Retail |        |               |       |            |                 |         |       | Top 10 Assets | Total Arrears       | Top10_Rental_Arrears.xls       |
      | AUD      | 2013 | SF           | Retail |        |               |       |            |                 |         |       | Top 10 Assets | Leasable Area       | Top10_Leasable_Area.xls        |
      #ownership percentage test case
      | EUR      | 2013 | SF           |        |        |               |       |            | UB Realty Trust |         |       | Top 10 Assets | Net Rental Income   | OP_Top10_Net_Rental_Income.xls |

  @ValuationTrend
    Examples: Valuation Trend
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment      | Quarter | Month | report name     | export file name                        |
      #ownership Percentage testcase
      | EUR      | 2013 | SF           |        |        |               |       |            | UB Realty Trust |         |       | Valuation Trend | UBRealtyTrust_Valuation NOI Trend.xls   |
      | USD      | 2013 | SF           |        |        |               |       |            |                 |         |       | Valuation Trend | Valuation NOI Trend_Asset_Valuation.xls |

#    Commented This Example Because Asset Map is removed from Diversification
#  @AssetMap
#  Examples: For Asset Map - Values
#    | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset  | Asset Tags | Investment | Quarter | Month | report name        | Toggle | export file name       |
#    | USD      | 2013 | SF           |        |        |               |        |            |            |         |       | Asset Map - Values |        | Asset Map - Values.xls |

  @OperatingPerformanceDashboard @OpsModel @Leasing
  Scenario Outline: Checking the reports on operating performance page with different filters
    Given user navigates to Leasing Overview page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency      | <Currency>      |
      | Year          | <Year>          |
      | Sector        | <Sector>        |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
      | Asset         | <Asset>         |
      | Asset Tags    | <Asset Tags>    |
      | Investment    | <Investment>    |
      | Quarter       | <Quarter>       |
      | Month         | <Month>         |
      | Area Measure  | <Area Measure>  |
      | Toggle        | <Toggle>        |
      | report name   | <report name>   |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @OccupancyTrend
    Examples:  Occupancy Trend
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month | report name     | Toggle       | export file name          |
      | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT |         |       | Occupancy Trend | Leased Area  | Occupancy_Leased_Area.xls |
      | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT |         |       | Occupancy Trend | by Unit Area | Occupancy_Unit_Area.xls   |

  @RentalArrearsOP
    Examples: Rental Arrears As Of - 2013/12/31
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment      | Quarter | Month | report name                | Toggle | export file name              |
      | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT     |         |       | Arrears As Of - 2013/12/31 |        | Rental Arrears As Of_1.xls    |
     #ownership percentage test case
      | EUR      | 2013 | SF           |        |        |               |       |            | UB Realty Trust |         |       | Arrears As Of - 2013/12/31 |        | OP_Rental Arrears As Of_1.xls |

  @OperatingStatistics
    Examples: Operating Statistics
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month | report name                             | Toggle | export file name                         |
      | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT |         |       | Operating Statistics As Of - 2013/12/31 |        | DASH_Operating Statistics_CamdenReit.xls |

  @LeasingActivity
    Examples: Leasing Activity
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month | report name      | Toggle | export file name                     |
      | EUR      | 2013 | SF           |        |        |               |       |            |             |         |       | Leasing Activity |        | DASH_Leasing Activity.xls            |
      | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT |         |       | Leasing Activity |        | DASH_Leasing Activity_camdenReit.xls |

  @LeaseExpirySchedule
    Examples: Lease Expiry Schedule
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month | report name           | Toggle                       | export file name                |
      | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT |         |       | Lease Expiry Schedule | Expiring % of Total          | Expiring_%_of_Total.xls         |
      | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT |         |       | Lease Expiry Schedule | Expiring && Termination Area | Expiring_&_Termination_Area.xls |

  @FinancialPerformanceDashboard @FinancialModel
  Scenario Outline: Checking the Dashbaord on Financial performance page with different filters
    Given user navigates to Financial Overview page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency            | <Currency>            |
      | Year                | <Year>                |
      | Sector              | <Sector>              |
      | Region              | <Region>              |
      | Asset Manager       | <Asset Manager>       |
      | Asset               | <Asset>               |
      | Asset Tags          | <Asset Tags>          |
      | Investment          | <Investment>          |
      | Quarter             | <Quarter>             |
      | Month               | <Month>               |
      | Area Measure        | <Area Measure>        |
      | Assumption Scenario | <Assumption Scenario> |
      | Acct Book Name      | <Acct Book Name>      |
      | Tab                 | <Tab>                 |
      | report name         | <report name>         |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @BudgetVsActual
    Examples: Budget vs Actual
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset                                | Asset Tags | Investment  | Quarter | Month    | Assumption Scenario | Acct Book Name | report name      | Tab     | export file name                            |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | Camden REIT |         |          |                     |                | Budget vs Actual | Revenue | BudgetActual_Revenue.xls                    |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | Camden REIT | Q2-2013 |          |                     |                | Budget vs Actual | Expense | BudgetActual_Expense.xls                    |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | Camden REIT |         | May/2013 |                     |                | Budget vs Actual | NOI     | BudgetActual_NOI.xls                        |
      | EUR      | 2014 | SM           |        |        |               | Paris Avenue Winston Churchill Store |            |             | Q2-2014 | Jun/2014 | End Year Forecast   |                | Budget vs Actual | Revenue | BudgetActual_Revenue_AssumptionScenario.xls |
      | EUR      | 2014 | SM           |        |        |               | Paris Avenue Winston Churchill Store |            |             | Q2-2014 | Jun/2014 | End Year Forecast   |                | Budget vs Actual | NOI     | BudgetActual_NOI_AssumptionScenario.xls     |

  @RevenueAndExpenseBreakdown
    Examples: Revenue and Expense Breakdown
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset                                | Asset Tags | Investment  | Quarter | Month    | Assumption Scenario | Acct Book Name | report name                   | Tab     | export file name                         |
      | AUD      | 2013 | SM           |        |        |               |                                      |            | Camden REIT |         |          |                     |                | Revenue and Expense Breakdown | Revenue | BreakDown_Revenue.xls                    |
      | AUD      | 2013 | SM           |        |        |               |                                      |            | Camden REIT | Q2-2013 |          |                     |                | Revenue and Expense Breakdown | Expense | BreakDown_Expense.xls                    |
      | EUR      | 2014 | SM           |        |        |               | Paris Avenue Winston Churchill Store |            |             | Q2-2014 | Jun/2014 | End Year Forecast   |                | Revenue and Expense Breakdown | Revenue | BreakDown_Revenue_AssumptionScenario.xls |

  @TenantDashboard @OpsModel @Leasing
  Scenario Outline: Checking the Dashboard on Tenant page with different filters
    Given user navigates to Tenants Dashboard page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency      | <Currency>      |
      | Year          | <Year>          |
      | Sector        | <Sector>        |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
      | Asset         | <Asset>         |
      | Asset Tags    | <Asset Tags>    |
      | Investment    | <Investment>    |
      | Quarter       | <Quarter>       |
      | Month         | <Month>         |
      | Area Measure  | <Area Measure>  |
      | Toggle        | <Toggle>        |
      | report name   | <report name>   |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @Top10Tenants
    Examples: Top 10 Tenants
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment | Quarter | Month    | report name    | Toggle        | export file name            |
      | EUR      | 2013 | SF           |        |        |               |       |            |            |         |          | Top 10 Tenants | Net Rent      | Net_Rental_Income_pcm.xls   |
      | EUR      | 2013 | SF           |        |        |               |       |            |            |         | May/2013 | Top 10 Tenants | Gross Rent    | Gross_Rental_Income_pcm.xls |
      | EUR      | 2013 | SF           |        |        |               |       |            |            | Q2-2013 |          | Top 10 Tenants | Total Arrears | Rental_Arrears_Tenant.xls   |
      | EUR      | 2013 | SF           |        |        |               |       |            |            | Q2-2013 |          | Top 10 Tenants | Leased Area   | Leased_Area_Tenant.xls      |
      | EUR      | 2013 | SF           |        |        |               |       |            |            |         |          | Top 10 Tenants | Retail Sales  | Retail_Sales.xls            |

  @RentalArrearsT
    Examples: Rental Arrears as Of
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment        | Quarter | Month | report name                | Toggle | export file name                |

      | EUR      | 2013 | SF           |        |        |               |       |            | Investor Two Ltd. | Q3-2013 |       | Arrears As Of - 2013/09/30 |        | Rental_Arrears_As_Of_Tenant.xls |

  @TenantMix
    Examples: Tenant Mix
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment        | Quarter | Month | report name | Toggle         | export file name            |
      | EUR      | 2013 | SF           |        |        |               |       |            | Investor Two Ltd. | Q3-2013 |       | Tenant Mix  | Leased Area    | Leased_Area_TenantMix.xls   |
      | EUR      | 2013 | SF           |        |        |               |       |            | Investor Two Ltd. | Q3-2013 |       | Tenant Mix  | No. of Tenants | No_of_Tenants_TenantMix.xls |
      | EUR      | 2013 | SF           |        |        |               |       |            |                   | Q3-2013 |       | Tenant Mix  | Sales Volume   | Sales_Volume_TenantMix.xls  |


 # @NOTTrend
 # Scenario Outline: Checking the Dashbaord on Financial performance page with different filters
  #  Given user navigates to Financial performance page
 #   And user can see reports
 #   And clears the existing filters
 #   When selects the filters as below
 #     | Currency      | <Currency>      |
 #     | Year          | <Year>          |
 #     | Sector        | <Sector>        |
  #    | Region        | <Region>        |
  #    | Asset Manager | <Asset Manager> |
   #   | Asset         | <Asset>         |
  #    | Asset Tags    | <Asset Tags>    |
  #    | Investment    | <Investment>    |
  #    | Quarter       | <Quarter>       |
   #   | Month         | <Month>         |
   #   | Area Measure  | <Area Measure>  |
   #   | report name   | <report name>   |
  #  And export the '<report name>' report
  #  Then the report is exported successfully
 #   And the saved report should match with expected report with name '<export file name>'


  #Examples: NOT Trend
   # | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment | Quarter | Month | report name |  export file name                        |
   # | USD     | 2013 | SF          |        |        |               |       |            |            |         |       |  NOI Trend |  NOI Trend.xls                 |

  @FinancialPerformanceReports @FinancialModel @Jen
  Scenario Outline: Checking the Operating Statement Report on Financial performance page with different filters
    Given user navigates to NOI Statement page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency            | <Currency>            |
      | Year                | <Year>                |
      | Sector              | <Sector>              |
      | Region              | <Region>              |
      | Asset Manager       | <Asset Manager>       |
      | Asset               | <Asset>               |
      | Asset Tags          | <Asset Tags>          |
      | Investment          | <Investment>          |
      | Quarter             | <Quarter>             |
      | Month               | <Month>               |
      | Area Measure        | <Area Measure>        |
      | Assumption Scenario | <Assumption Scenario> |
      | Acct Book Name      | <Acct Book Name>      |
      | Tab                 | <Tab>                 |
      | CheckBox            | <CheckBox>            |
      | report name         | <report name>         |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @OperatingStatement
    Examples: Operating Statement
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset                                | Asset Tags | Investment      | Quarter | Month    | Assumption Scenario | Acct Book Name | report name         | Tab                  | CheckBox       | export file name                              |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | Camden REIT     |         |          |                     |                | Operating Statement | Net Operating Income | Activity       | NOI_Activity.xls                              |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | Camden REIT     | Q2-2013 |          |                     |                | Operating Statement | Net Operating Income | YTD Reported   | NOI_YTD_Reported.xls                          |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | Camden REIT     |         | May/2013 |                     |                | Operating Statement | Net Operating Income | YTD Calculated | NOI_YTD_Calculated.xls                        |
      | USD      | 2013 | SM           |        |        |               |                                      |            | Camden REIT     |         |          |                     |                | Operating Statement | Net Income           | Activity       | NI_Activity.xls                               |
      | USD      | 2013 | SM           |        |        |               |                                      |            | Camden REIT     | Q2-2013 |          |                     |                | Operating Statement | Net Income           | YTD Reported   | NI_YTD_Reported.xls                           |
      | USD      | 2013 | SM           |        |        |               |                                      |            | Camden REIT     |         | May/2013 |                     |                | Operating Statement | Net Income           | YTD Calculated | NI_YTD_Calculated.xls                         |
      | EUR      | 2014 | SM           |        |        |               |                                      |            | UB Realty Trust | Q2-2014 | Jun/2014 |                     |                | Operating Statement | Net Operating Income | Activity       | NOI_Report_Activity_OPercentage.xls           |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | UB Realty Trust |         |          |                     |                | Operating Statement | Net Income           | Activity       | NI_Report_Activity_OP.xls                     |
     # VOY-7498 #VOY-7540 The actual file is not deleting the old one and then resaving the new one. The new file saves with the name that it gets when QlikView downloads it.  Manually checked and this is passing - 2015-01-21
     # | EUR      | 2013 |  SM          |        |        |               |                                      |            | UB Realty Trust | Q2-2013 |          |                     |                | Operating Statement | Net Operating Income |YTD Calculated | OP_NOI_YTD_Calculated.xls                     |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | UB Realty Trust |         |          |                     |                | Operating Statement | Net Income           | YTD Reported   | OP_NI_YTD_Reported.xls                        |
      | EUR      | 2014 | SM           |        |        |               | Paris Avenue Winston Churchill Store |            |                 | Q2-2014 | Jun/2014 | End Year Forecast   |                | Operating Statement | Net Operating Income | Activity       | OS_NOI_Report_Activity_AssumptionScenario.xls |

  @FinancialPerformanceReports @FinancialModel @run
  Scenario Outline: Checking the Balance Sheet Report on Financial performance page with different filters
    Given user navigates to Balance Sheet page
    #And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency            | <Currency>            |
      | Year                | <Year>                |
      | Sector              | <Sector>              |
      | Region              | <Region>              |
      | Asset Manager       | <Asset Manager>       |
      | Asset               | <Asset>               |
      | Asset Tags          | <Asset Tags>          |
      | Investment          | <Investment>          |
      | Quarter             | <Quarter>             |
      | Month               | <Month>               |
      | Area Measure        | <Area Measure>        |
      | Assumption Scenario | <Assumption Scenario> |
      | Acct Book Name      | <Acct Book Name>      |
      | Tab                 | <Tab>                 |
      | report name         | <report name>         |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @BalanceSheet
    Examples: Balance Sheet
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset                                | Asset Tags | Investment      | Quarter | Month    | Assumption Scenario | Acct Book Name | report name   | Tab                    | export file name                                 |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | Camden REIT     | Q2-2013 |          |                     |                | Balance Sheet | Previous Year End      | PreviousYearEnd.xls                              |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | Camden REIT     |         | May/2013 |                     |                | Balance Sheet | Same Period Prior Year | Same_Period_Prior_Year.xls                       |
      | EUR      | 2013 | SM           |        |        |               |                                      |            | UB Realty Trust |         |          |                     |                | Balance Sheet | Previous Year End      | OP_PreviousYearEnd.xls                           |
      | EUR      | 2014 | SM           |        |        |               | Paris Avenue Winston Churchill Store |            |                 | Q2-2014 | Jun/2014 | End Year Forecast   |                | Balance Sheet | Previous Year End      | BS_Same_Period_Prior_Year_AssumptionScenario.xls |

  @OperatingPerformanceReports @OpsModel @run @Leasing
  Scenario Outline: Checking the Repport on Operating Performance page with different filters
#    Given user navigates to Report Operating Performance page
#    And user goes to '<report name>'
    Given user navigates to Rental Arrears page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency      | <Currency>      |
      | Year          | <Year>          |
      | Sector        | <Sector>        |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
      | Asset         | <Asset>         |
      | Asset Tags    | <Asset Tags>    |
      | Investment    | <Investment>    |
      | Quarter       | <Quarter>       |
      | Month         | <Month>         |
      | Area Measure  | <Area Measure>  |
      | Checkbox      | <Checkbox>      |
      | report name   | <report name>   |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @RentalArrearsReport
    Examples: Rental Arrears Report
      | report name           | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month | Checkbox | export file name     |
      | Rental Arrears Report | USD      | 2013 | SM           |        |        |               |       |            | Camden REIT | Q2-2013 |       |          | OPR_RentalArrear.xls |
    #Expired Rental Arrears no longer needs to be a test case as this does not look at expired lease but instead whether the Lease.Active column in the database is equal to Y
   # | Rental Arrears Report      | GBP      | 2015 |  SM          |        |        |               |       |            | Camden REIT|         | May/2015           |               | Expired_RentalArrear.xls |

  @OperatingPerformanceReports @OpsModel @run @Leasing
  Scenario Outline: Checking the Repport on Operating Performance page with different filters
#    Given user navigates to Report Operating Performance page
#    And user goes to '<report name>'
    Given user navigates to Asset Statistics page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency      | <Currency>      |
      | Year          | <Year>          |
      | Sector        | <Sector>        |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
      | Asset         | <Asset>         |
      | Asset Tags    | <Asset Tags>    |
      | Investment    | <Investment>    |
      | Quarter       | <Quarter>       |
      | Month         | <Month>         |
      | Area Measure  | <Area Measure>  |
      | Checkbox      | <Checkbox>      |
      | report name   | <report name>   |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @OperatingStatisticsOverviewReport
    Examples: Operating Statistics Report
      | report name                   | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month    | Checkbox | export file name        |
      | Operating Statistics Overview | AUD      | 2013 | SM           |        |        |               |       |            | Camden REIT | Q2-2013 | May/2013 |          | OperatingStatistics.xls |

  @OperatingPerformanceReports @OpsModel @run @Leasing
  Scenario Outline: Checking the Repport on Building inventory report with different filters
#    Given user navigates to Report Operating Performance page
#    And user goes to '<report name>'
    Given user navigates to Unit Inventory page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency      | <Currency>      |
      | Year          | <Year>          |
      | Sector        | <Sector>        |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
      | Asset         | <Asset>         |
      | Asset Tags    | <Asset Tags>    |
      | Investment    | <Investment>    |
      | Quarter       | <Quarter>       |
      | Month         | <Month>         |
      | Area Measure  | <Area Measure>  |
      | Tab           | <Tab>           |
      | report name   | <report name>   |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @BuildingUnitInventoryReport
    Examples: OSO
      | report name             | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month    | Tab         | export file name                  |
      | Building Unit Inventory | GBP      | 2013 | SF           |        |        |               |       |            | Camden REIT | Q2-2013 | May/2013 | Rent per SF | Building_Unit_Inventory.xls       |
      | Building Unit Inventory | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT |         |          | Total Rent  | Building_Unit_Inventory_Total.xls |


  @TenantReports @OpsModel @run @Leasing
  Scenario Outline: Checking the Report on Tenant page with different filters
#    Given user navigates to Report Tenancy page
#    And user goes to '<report name>'
    Given user navigates to Rental Analysis page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency      | <Currency>      |
      | Year          | <Year>          |
      | Sector        | <Sector>        |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
      | Asset         | <Asset>         |
      | Asset Tags    | <Asset Tags>    |
      | Investment    | <Investment>    |
      | Quarter       | <Quarter>       |
      | Month         | <Month>         |
      | Area Measure  | <Area Measure>  |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @RentalAnalysisReport
    Examples: Rental Analysis Report
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month | report name            | export file name           |
      # Ting has this commented out when she committed her final code.  When I run this it fails, however other tenancy schedule tests are passing
      # | EUR      | 2013 |              |        |        |               |       |            |            |         |          | Tenancy Schedule       | Tenancy_Schedule.xls |
      # VOY-7279
      | EUR      | 2013 |              |        |        |               |       |            | Camden REIT | Q3-2013 |       | Rental Analysis Report | Rental_Analysis_Report.xls |


  @TenantReports @OpsModel @run @Leasing
  Scenario Outline: Checking the Report on Tenant page with different filters after selecting all values
#    Given user navigates to Report Tenancy page
#    And user goes to '<report name>'
    Given user navigates to Tenancy Schedule page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency      | <Currency>      |
      | Year          | <Year>          |
      | Sector        | <Sector>        |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
      | Asset         | <Asset>         |
      | Asset Tags    | <Asset Tags>    |
      | Investment    | <Investment>    |
      | Quarter       | <Quarter>       |
      | Month         | <Month>         |
      | Area Measure  | <Area Measure>  |
      | Tab           | <Tab>           |
      | report name   | <report name>   |
    And select option '<option>'
    And select all the values in '<report name>'
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @TenancyScheduleReport
    Examples: Tenancy Schedule
      | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month    | report name      | export file name            | option                     | Tab     |
      | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT |         |          | Tenancy Schedule | Tenancy_Schedule_Lease.xls  | Lease                      | Annual  |
      | EUR      | 2013 | SF           |        |        |               |       |            | Camden REIT | Q2-2013 |          | Tenancy Schedule | Tenancy_Schedule_Asset.xls  | Asset, Unit & Tenant       | Monthly |
      | EUR      | 2013 | SM           |        |        |               |       |            | Camden REIT |         | May/2013 | Tenancy Schedule | Tenancy_Schedule_Review.xls | Reviews, Options & Billing | Annual  |

  @OperatingPerformanceReports @OpsModel @run @Leasing
  Scenario Outline: Checking the Lease expiring profil report
#    Given user navigates to Report Operating Performance page
#    And user goes to '<report name>'
    Given user navigates to Lease Expiry page
    And user can see reports
    And clears the existing filters
    When selects the filters as below
      | Currency      | <Currency>      |
      | Year          | <Year>          |
      | Sector        | <Sector>        |
      | Region        | <Region>        |
      | Asset Manager | <Asset Manager> |
      | Asset         | <Asset>         |
      | Asset Tags    | <Asset Tags>    |
      | Investment    | <Investment>    |
      | Quarter       | <Quarter>       |
      | Month         | <Month>         |
      | Area Measure  | <Area Measure>  |
      | Checkbox      | <Checkbox>      |
      | report name   | <report name>   |
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @LeaseExpiryProfileReport
    Examples: Lease expiring profile report
      | report name                 | Currency | Year | Area Measure | Sector | Region | Asset Manager | Asset | Asset Tags | Investment  | Quarter | Month | Checkbox               | export file name                |
      | Lease Expiry Profile Report | EUR      | 2013 | SM           |        |        |               |       |            | Camden REIT | Q2-2013 |       | Expiring               | ExpiringProfile_Expiring.xls    |
      | Lease Expiry Profile Report | GBP      | 2014 | SM           |        |        |               |       |            | Camden REIT |         |       | Expiring & Terminating | ExpiringProfile_Terminating.xls |

  @LoansReports @run
  Scenario Outline: Checking the loan schedule report with different filters after selecting all values
#    Given user navigates to Loans page
#    And user goes to '<report name>'
    Given user navigates to Loan Schedule page
    And user can see reports
    And clears the existing filters
    And select basis '<basis>'
    When selects the filters as below
      | DebtFacility | <Debt Facility> |
      | Year         | <Year>          |
      | Quarter      | <Quarter>       |
      | Month        | <Month>         |
      | Currency     | <Currency>      |
      | Sector       | <Sector>        |
      | Investment   | <Investment>    |
      | Area Measure | <Area Measure>  |

    And select option '<option>'
    And select all the values in '<report name>'
    And select expand all to expand the loan schedule report
    And export the '<report name>' report
    Then the report is exported successfully
    And the saved report should match with expected report with name '<export file name>'

  @LoanScheduleReport
    Examples: Loan Schedule report
      | Year | Currency | Area Measure | Sector | Investment | Quarter | Month    | basis             | option           | report name          | export file name           | Debt Facility |
      | 2013 | EUR      | SF           |        |            |         | Aug/2013 | Account Activity  | Loan & Asset     | Loan Schedule Report | LoanScheduleReport.xls     | DF-004        |
      | 2013 | EUR      | SF           |        |            |         | Aug/2013 | Recurring Billing | DSCR             | Loan Schedule Report | DSCR.xls                   | DF-004        |
      | 2013 | EUR      | SF           |        |            |         |          | Account Activity  | ICR              | Loan Schedule Report | ICR.xls                    | DF-004        |
      | 2013 | EUR      | SF           |        |            | Q3-2013 |          | Recurring Billing | Debt Yield Ratio | Loan Schedule Report | DebtYield.xls              | DF-004        |
      | 2013 | EUR      | SF           |        |            |         | Aug/2013 | Recurring Billing | Loan & Asset     | Loan Schedule Report | LoanScheduleReport_002.xls | DF-002        |
      | 2013 | EUR      | SF           |        |            | Q4-2013 |          | Account Activity  | DSCR             | Loan Schedule Report | DSCR_002.xls               | DF-002        |
