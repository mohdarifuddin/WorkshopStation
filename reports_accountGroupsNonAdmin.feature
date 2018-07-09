@AccountGroupsNonAdmin
Feature: As a Voyanta User, I want to see Account Groups Report

  @NonAdmin
  Scenario Outline: Testing Non Admin Account Groups Scenario
    Given 'nonadmin@accountgroups.com' logs in the system with 'password1!'
    Given a user navigates to 'Account Groups' page
    When user select the filters dropdown
    And user selects the filters as below
      | Quarter Start        | <Quarter Start>      |
      | Quarter End          | <Quarter End>        |
      | Currency             | <Currency>           |
      | Account Group        | <AccountGroup>       |
      | Accounting Book Name | <AccountingBookName> |
      | Reference Date       | <ReferenceDate>      |
    And the user applies the Selected Filters
    When user deselect the default columns
    Then user selects the below Columns
      | Column1 | <Column1> |
      | Column2 | <Column2> |
      | Column3 | <Column3> |
    And user export the '<reportname>' report
    And saved report should match with expected report with name '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup  | AccountingBookName | ReferenceDate    | Column1           | Column2               | Column3                 | reportname     | ExpectedReport |
      | 2015-2019, 2019, Q1 | 2015-2019, 2019, Q1 | EUR      | Balance Sheet | Actual             | Transaction Date | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Account Groups | NonAdmin.xlsx  |

