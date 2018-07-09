@AccountGroups
Feature: As a Voyanta User, I want to see AccountGroups Report

  Background:
    Given 'automation@accountgroups.com' logs into the system with 'password1!'

  @NoTransactionDate
  Scenario Outline: Checking the data when there is no Transaction date in the data
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
      | Quarter Start       | Quarter End         | Currency | AccountGroup  | AccountingBookName | ReferenceDate    | Column1           | Column2               | Column3                 | reportname     | ExpectedReport        |
      | 2015-2019, 2015, Q1 | 2015-2019, 2016, Q3 | EUR      | Balance Sheet | Actual             | Transaction Date | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Account Groups | NoTrasactionDate.xlsx |

  @PeriodDate
  Scenario Outline: Checking the data for the period date
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
      | Quarter Start       | Quarter End         | Currency | AccountGroup | AccountingBookName | ReferenceDate | Column1           | Column2               | Column3                 | reportname     | ExpectedReport  |
      | 2015-2019, 2015, Q1 | 2015-2019, 2016, Q2 | EUR      | Liabilities  | Budget             | Period        | Activity (Budget) | YTD Reported (Budget) | YTD Calculated (Budget) | Account Groups | PeriodDate.xlsx |


  @TransactionDate
  Scenario Outline: Checking the data for the Transaction date
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
      | Quarter Start       | Quarter End         | Currency | AccountGroup  | AccountingBookName | ReferenceDate    | Column1           | Column2               | Column3                 | reportname     | ExpectedReport       |
      | 2015-2019, 2017, Q1 | 2015-2019, 2017, Q2 | EUR      | Balance Sheet | Budget             | Transaction Date | Activity (Budget) | YTD Reported (Budget) | YTD Calculated (Budget) | Account Groups | TransactionDate.xlsx |


  @AllBookTypes
  Scenario Outline: Checking the data for all Book Types
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
      | Column4 | <Column4> |
      | Column5 | <Column5> |
      | Column6 | <Column6> |
      | Column7 | <Column7> |
      | Column8 | <Column8> |
      | Column9 | <Column9> |
    And user export the '<reportname>' report
    And saved report should match with expected report with name '<ExpectedReport>'


    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup | AccountingBookName | ReferenceDate    | Column1           | Column2               | Column3                 | Column4           | Column5               | Column6                 | Column7             | Column8                 | Column9                   | reportname     | ExpectedReport    |
      | 2010-2014, 2013, Q1 | 2010-2014, 2013, Q1 | AUD      | new8         | Actual             | Transaction Date | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Activity (Budget) | YTD Reported (Budget) | YTD Calculated (Budget) | Activity (Forecast) | YTD Reported (Forecast) | YTD Calculated (Forecast) | Account Groups | AllBookTypes.xlsx |

  @AllAccountCategories
  Scenario Outline: Checking the data for all different account categories
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
      | Quarter Start       | Quarter End         | Currency | AccountGroup | AccountingBookName | ReferenceDate    | Column1           | Column2               | Column3                 | reportname     | ExpectedReport            |
      | 2025-2029, 2027, Q1 | 2025-2029, 2027, Q2 | AUD      | new6         | Actual             | Transaction Date | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Account Groups | AllAccountCategories.xlsx |

  @2DifferentSubAccountNumbers
  Scenario Outline: Checking the data for 2 account activities with same Account number but 2 different sub Account numbers.
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
      | Quarter Start       | Quarter End         | Currency | AccountGroup | AccountingBookName | ReferenceDate    | Column1           | Column2               | Column3                 | reportname     | ExpectedReport                   |
      | 2020-2024, 2024, Q1 | 2020-2024, 2024, Q2 | AUD      | new2         | Actual             | Transaction Date | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Account Groups | 2DifferentSubAccountNumbers.xlsx |
