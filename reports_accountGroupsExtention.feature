@AccountGroupsExtension
Feature: As a Voyanta User, I want to see Account Groups Report

  Background:
    Given 'automation@accountgroups.com' logs into the system with 'password1!'

  @NullSubAccountNumber
  Scenario Outline: Checking the data for 2 account activities with same Account number and one of them is null
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
      | 2020-2024, 2024, Q1 | 2020-2024, 2024, Q1 | AUD      | new21        | Actual             | Transaction Date | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Account Groups | NullSubAccountNumber.xlsx |

  @NullAccountingBookName
  Scenario Outline: Checking the data for Null Accounting book name
    Given a user navigates to 'Account Groups' page
    When user select the filters dropdown
    And user selects the filters as below
      | Quarter Start  | <Quarter Start> |
      | Quarter End    | <Quarter End>   |
      | Currency       | <Currency>      |
      | Account Group  | <AccountGroup>  |
      | Reference Date | <ReferenceDate> |

    And the user applies the Selected Filters
    When user deselect the default columns
    Then user selects the below Columns
      | Column1 | <Column1> |
      | Column2 | <Column2> |
      | Column3 | <Column3> |
      | Column4 | <Column4> |
      | Column5 | <Column5> |
      | Column6 | <Column6> |
    And user export the '<reportname>' report
    And saved report should match with expected report with name '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup | ReferenceDate    | Column1           | Column2               | Column3                 | Column4           | Column5               | Column6                 | reportname     | ExpectedReport              |
      | 2025-2029, 2027, Q1 | 2025-2029, 2027, Q3 | AUD      | new6         | Transaction Date | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Activity (Budget) | YTD Reported (Budget) | YTD Calculated (Budget) | Account Groups | NullAccountingBookName.xlsx |

  @CurrencyConversion
  Scenario Outline: Testing Currency Conversion AUD-GBP
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
      | Quarter Start       | Quarter End         | Currency | AccountGroup | AccountingBookName | ReferenceDate    | Column1           | Column2               | Column3                 | reportname     | ExpectedReport          |
      | 2010-2014, 2013, Q1 | 2010-2014, 2013, Q1 | GBP      | new8         | Actual             | Transaction Date | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Account Groups | CurrencyConversion.xlsx |

  @TagFilter
  Scenario Outline: Checking the data for Tag Filter
    Given a user navigates to 'Account Groups' page
    When user select the filters dropdown
    And user selects the filters as below
      | Quarter Start        | <Quarter Start>      |
      | Quarter End          | <Quarter End>        |
      | Currency             | <Currency>           |
      | Account Group        | <AccountGroup>       |
      | Accounting Book Name | <AccountingBookName> |
      | Reference Date       | <ReferenceDate>      |
      | Asset Tags           | <AssetTags>          |
    And the user applies the Selected Filters
    When user deselect the default columns
    Then user selects the below Columns
      | Column1 | <Column1> |
      | Column2 | <Column2> |
      | Column3 | <Column3> |
    And user export the '<reportname>' report
    And saved report should match with expected report with name '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup  | AccountingBookName | ReferenceDate    | AssetTags | Column1           | Column2               | Column3                 | reportname     | ExpectedReport |
      | 2015-2019, 2017, Q1 | 2015-2019, 2018, Q4 | EUR      | Balance Sheet | Actual             | Transaction Date | tag 1     | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Account Groups | TagFilter.xlsx |

  @DebtFacilityFilter
  Scenario Outline: Checking the data for Debt Facility Filter
    Given a user navigates to 'Account Groups' page
    When user select the filters dropdown
    And user selects the filters as below
      | Quarter Start        | <Quarter Start>      |
      | Quarter End          | <Quarter End>        |
      | Currency             | <Currency>           |
      | Account Group        | <AccountGroup>       |
      | Accounting Book Name | <AccountingBookName> |
      | Reference Date       | <ReferenceDate>      |
      | Debt Facility        | <DebtFacility>       |
    And the user applies the Selected Filters
    When user deselect the default columns
    Then user selects the below Columns
      | Column1 | <Column1> |
      | Column2 | <Column2> |
      | Column3 | <Column3> |
    And user export the '<reportname>' report
    And saved report should match with expected report with name '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup  | AccountingBookName | ReferenceDate    | DebtFacility | Column1             | Column2                 | Column3                   | reportname     | ExpectedReport          |
      | 2015-2019, 2017, Q1 | 2015-2019, 2019, Q1 | AUD      | Balance Sheet | Forecast           | Transaction Date | DFR457       | Activity (Forecast) | YTD Reported (Forecast) | YTD Calculated (Forecast) | Account Groups | DebtFacilityFilter.xlsx |

  @AssetFilter
  Scenario Outline: Testing Asset Filter
    Given a user navigates to 'Account Groups' page
    When user select the filters dropdown
    And user selects the filters as below
      | Quarter Start        | <Quarter Start>      |
      | Quarter End          | <Quarter End>        |
      | Currency             | <Currency>           |
      | Account Group        | <AccountGroup>       |
      | Accounting Book Name | <AccountingBookName> |
      | Reference Date       | <ReferenceDate>      |
      | Asset                | <Asset>              |
    And the user applies the Selected Filters
    When user deselect the default columns
    Then user selects the below Columns
      | Column1 | <Column1> |
      | Column2 | <Column2> |
      | Column3 | <Column3> |
    And user export the '<reportname>' report
    And saved report should match with expected report with name '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup  | AccountingBookName | ReferenceDate    | Asset     | Column1           | Column2               | Column3                 | reportname     | ExpectedReport   |
      | 2015-2019, 2017, Q1 | 2015-2019, 2018, Q4 | EUR      | Balance Sheet | Actual             | Transaction Date | BUILD-001 | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Account Groups | AssetFilter.xlsx |

  @AllFilters
  Scenario Outline: Testing most of the filters
    Given a user navigates to 'Account Groups' page
    When user select the filters dropdown
    And user selects the filters as below
      | Quarter Start        | <Quarter Start>      |
      | Quarter End          | <Quarter End>        |
      | Currency             | <Currency>           |
      | Account Group        | <AccountGroup>       |
      | Accounting Book Name | <AccountingBookName> |
      | Reference Date       | <ReferenceDate>      |
      | Asset                | <Asset>              |
      | Asset Tags           | <AssetTags>          |
      | Chart Of Accounts    | <ChartOfAccounts>    |

    And the user applies the Selected Filters
    When user deselect the default columns
    Then user selects the below Columns
      | Column1 | <Column1> |
      | Column2 | <Column2> |
      | Column3 | <Column3> |
    And user export the '<reportname>' report
    And saved report should match with expected report with name '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup | AccountingBookName | ReferenceDate    | Asset     | AssetTags | ChartOfAccounts | Column1           | Column2               | Column3                 | reportname     | ExpectedReport  |
      | 2015-2019, 2018, Q1 | 2015-2019, 2019, Q4 | EUR      | Group 1      | Actual             | Transaction Date | BUILD-001 | tag 1     | Standard Chart  | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actual) | Account Groups | AllFilters.xlsx |


  @OwnershipFilterEquityParticipation
  Scenario Outline: Testing Ownership Filter with Equity Participation data
    Given a user navigates to 'Account Groups' page
    When user select the filters dropdown
    And user selects the filters as below
      | Quarter Start        | <Quarter Start>      |
      | Quarter End          | <Quarter End>        |
      | Currency             | <Currency>           |
      | Account Group        | <AccountGroup>       |
      | Accounting Book Name | <AccountingBookName> |
      | Reference Date       | <ReferenceDate>      |
      | Ownership            | <Ownership>          |

    And the user applies the Selected Filters
    When user deselect the default columns
    Then user selects the below Columns
      | Column1 | <Column1> |
      | Column2 | <Column2> |
      | Column3 | <Column3> |
    And user export the '<reportname>' report
    And saved report should match with expected report with name '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup | AccountingBookName | ReferenceDate    | Ownership | Column1             | Column2                 | Column3                   | reportname     | ExpectedReport                          |
      | 2020-2024, 2024, Q1 | 2020-2024, 2024, Q1 | AUD      | new15        | Forecast           | Transaction Date | INV-13    | Activity (Forecast) | YTD Reported (Forecast) | YTD Calculated (Forecast) | Account Groups | OwnershipFilterEquityParticipation.xlsx |

  @OwnershipFilterAssetTransaction
  Scenario Outline: Testing Ownership Filter with Asset Transaction data
    Given a user navigates to 'Account Groups' page
    When user select the filters dropdown
    And user selects the filters as below
      | Quarter Start        | <Quarter Start>      |
      | Quarter End          | <Quarter End>        |
      | Currency             | <Currency>           |
      | Account Group        | <AccountGroup>       |
      | Accounting Book Name | <AccountingBookName> |
      | Reference Date       | <ReferenceDate>      |
      | Ownership            | <Ownership>          |

    And the user applies the Selected Filters
    When user deselect the default columns
    Then user selects the below Columns
      | Column1 | <Column1> |
      | Column2 | <Column2> |
      | Column3 | <Column3> |
    And user export the '<reportname>' report
    And saved report should match with expected report with name '<ExpectedReport>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup | AccountingBookName | ReferenceDate    | Ownership | Column1           | Column2               | Column3                 | reportname     | ExpectedReport                       |
      | 2020-2024, 2024, Q1 | 2020-2024, 2024, Q1 | AUD      | new20        | Actual             | Transaction Date | INV-14    | Activity (Actual) | YTD Reported (Actual) | YTD Calculated (Actaul) | Account Groups | OwnershipFilterAssetTransaction.xlsx |

  @AccountsWithScenarioNameMapped
  Scenario Outline: Check that accounts with scenario name should not display display in the report
    Given a user navigates to 'Account Groups' page
    When user select the filters dropdown
    And user selects the filters as below
      | Quarter Start        | <Quarter Start>      |
      | Quarter End          | <Quarter End>        |
      | Currency             | <Currency>           |
      | Account Group        | <AccountGroup>       |
      | Accounting Book Name | <AccountingBookName> |
      | Asset                | <Asset>              |
      | Reference Date       | <ReferenceDate>      |

    And the user applies the Selected Filters
    Then error message should  be displayed as '<errorMessage>'

    Examples:
      | Quarter Start       | Quarter End         | Currency | AccountGroup | AccountingBookName | Asset     | ReferenceDate   | errorMessage                                                                                  |
      | 2015-2019, 2015, Q1 | 2020-2024, 2020, Q1 | AUD      | 1            | Actual             | BUILD-002 | Tansaction Date | NO REPORT AVAILABLE Either no report data is available or you do not have permissions to view |
