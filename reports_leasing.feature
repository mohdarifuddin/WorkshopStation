@LeasingReportsAndDashboards
Feature: As a Voyanta User, I want to see Different Leasing Report

  Background:
    Given 'qvxml@testaccount.com' logs into the system with 'password1!QA'
#    Given user is in reports page

  @LeasingOverview
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

  @TenantsDashboard
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

  @RentalArrears
  Scenario Outline: Checking the Repport on Operating Performance page with different filters
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

  @AssetStatistics
  Scenario Outline: Checking the Repport on Operating Performance page with different filters
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

  @UnitInventory
  Scenario Outline: Checking the Repport on Building inventory report with different filters
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


  @RentalAnalysis
  Scenario Outline: Checking the Report on Tenant page with different filters
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
      | EUR      | 2013 |              |        |        |               |       |            | Camden REIT | Q3-2013 |       | Rental Analysis Report | Rental_Analysis_Report.xls |


  @TenancySchedule
  Scenario Outline: Checking the Report on Tenant page with different filters after selecting all values
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

  @LeaseExpiry
  Scenario Outline: Checking the Lease expiring profile report
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
