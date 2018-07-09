@PortfolioReportsAndDashboards
Feature: As a Voyanta User, I want to see Portfolio for Asset

  Background:
    Given 'qvxml@testaccount.com' logs into the system with 'password1!QA'
    Given user navigates to Assets page
    Given user is in reports page

  @AssetsPortfolio
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


