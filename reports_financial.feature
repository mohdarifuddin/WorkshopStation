@FinancialReportsAndDashboards
Feature: As a Voyanta user, I want to see Financial report

  Background:
    Given 'qvxml@testaccount.com' logs into the system with 'password1!QA'
#    Given user is in reports page

  @FinancialOverview
  Scenario Outline: Checking the Dashboard on Financial performance page with different filters
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
      | Assumption Scenario | <Assumption Scenario> |
      | Acct Book Name      | <Acct Book Name>      |
      | Tab                 | <Tab>                 |
      | report name         | <report name>         |
    And the user deselect '<Acct Book Name>'
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

  @NOIStatement
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
      | Assumption Scenario | <Assumption Scenario> |
      | Acct Book Name      | <Acct Book Name>      |
      | Tab                 | <Tab>                 |
      | CheckBox            | <CheckBox>            |
      | report name         | <report name>         |
    And the user deselect '<Acct Book Name>'
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

  @BalanceSheet
  Scenario Outline: Checking the Balance Sheet Report on Financial performance page with different filters
    Given user navigates to Balance Sheet page
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
      | Assumption Scenario | <Assumption Scenario> |
      | Acct Book Name      | <Acct Book Name>      |
      | Tab                 | <Tab>                 |
      | report name         | <report name>         |
    And the user deselect '<Acct Book Name>'
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
