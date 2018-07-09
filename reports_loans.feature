@LoansReportsAndDashboards
Feature: As a Voyanta User, I want to see the Loan Reports and its Data

  Background:
    Given 'qvxml@testaccount.com' logs into the system with 'password1!QA'
#    Given user is in reports page

  @LoanSchedule
  Scenario Outline: Checking the loan schedule report with different filters after selecting all values
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
