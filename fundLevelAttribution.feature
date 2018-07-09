@FundLevelAttribution
Feature: Checking the delta of reports and filters

#  Account Used for This Report - automation@aew.com / password1!
#  Current Account used for this Report  - aew@adminaew.com,invesco@admininvesco.com / password1!

  @Attribution
  Scenario Outline: Checking the reports on dashboard page with different filters and verifying the delta with AEW Org
    Given '<Username>' logs into the system with '<Password>'
    And the user is on the fund level attribution reports page
    Given user can see fund level reports
    When the user selects the filters as below
      | Quarter | <Quarter> |
      | Period  | <Period>  |
      | Fund(s) | <Fund>    |
    And export the fund level report
    Then the saved report should match with the expected report with name '<Export file name>'

  @fundDelta
    Examples:
      | Username         | Password   | Quarter | Period       | Fund                    | Export file name        |
      | aew@adminaew.com | password1! | 2014 Q1 | One Quarter  | AEW Core Property Trust | 2014Q1-OneQuarter.xlsx  |
      | aew@adminaew.com | password1! | 2014 Q4 | One Year     | AEW Core Property Trust | 2014Q4-OneYear.xlsx     |
      | aew@adminaew.com | password1! | 2014 Q2 | Two Quarters | AEW Core Property Trust | 2014Q2-TwoQuarters.xlsx |
      | aew@adminaew.com | password1! | 2015 Q4 | One Year     | AEW Core Property Trust | 2015Q4-OneYear.xlsx     |
      | aew@adminaew.com | password1! | 2015 Q1 | Two Quarters | AEW Core Property Trust | 2015Q1-TwoQuarters.xlsx |

  @fundYearToDate
    Examples:
      | Username         | Password   | Quarter | Period       | Fund                    | Export file name       |
      | aew@adminaew.com | password1! | 2014 Q1 | Year to Date | AEW Core Property Trust | 2014Q1-YearToDate.xlsx |
      | aew@adminaew.com | password1! | 2014 Q2 | Year to Date | AEW Core Property Trust | 2014Q2-YearToDate.xlsx |
      | aew@adminaew.com | password1! | 2014 Q3 | Year to Date | AEW Core Property Trust | 2014Q3-YearToDate.xlsx |
      | aew@adminaew.com | password1! | 2014 Q4 | Year to Date | AEW Core Property Trust | 2014Q4-YearToDate.xlsx |
      | aew@adminaew.com | password1! | 2015 Q1 | Year to Date | AEW Core Property Trust | 2015Q1-YearToDate.xlsx |
      | aew@adminaew.com | password1! | 2015 Q2 | Year to Date | AEW Core Property Trust | 2015Q2-YearToDate.xlsx |
      | aew@adminaew.com | password1! | 2015 Q3 | Year to Date | AEW Core Property Trust | 2015Q3-YearToDate.xlsx |
      | aew@adminaew.com | password1! | 2015 Q4 | Year to Date | AEW Core Property Trust | 2015Q4-YearToDate.xlsx |

  @checkFootNotes
  Scenario Outline: Checking the UI of the report
    Given '<Username>' logs into the system with '<Password>'
    And the user is on the fund level attribution reports page
    Given user can see fund level reports
    When  selects filters as below
      | Quarter | <Quarter> |
      | Period  | <Period>  |
      | Fund(s) | <Fund>    |
    Then the user can sees the foot notes on the Attribution tab as 'Note: NFI-ODCE Performance Attribution is a work in progress and NCREIF continues to work with the ODCE managers to revise and formalize the process and production of the product.'
    When the user selects the Income/Appreciation tab
    Then the user see foot notes on the Income/Appreciation tab as '* The sum of income and appreciation returns may not equal total return due to rounding and/or the compounding of individual component returns to each other.'

    Examples: UI
      | Username         | Password   | Quarter | Period   | Fund                    |
      | aew@adminaew.com | password1! | 2014 Q4 | One Year | AEW Core Property Trust |


  @AttributionWithInvesco
  Scenario Outline: Checking the reports on dashboard page with different filters and verifying the delta with Invesco Org
    Given '<Username>' logs into the system with '<Password>'
    And the user is on the fund level attribution reports page
    Given user can see fund level reports
    When the user selects the filters as below
      | Quarter | <Quarter> |
      | Period  | <Period>  |
      | Fund(s) | <Fund>    |
    And export the fund level report
    Then the saved report should match with the expected report with name '<Export file name>'

  @fundDelta
    Examples:
      | Username                 | Password   | Quarter | Period       | Fund                           | Export file name                |
      | invesco@admininvesco.com | password1! | 2014 Q1 | One Quarter  | INVESCO Core Real Estate - USA | 2014Q1-OneQuarter_invesco.xlsx  |
      | invesco@admininvesco.com | password1! | 2014 Q4 | One Year     | INVESCO Core Real Estate - USA | 2014Q4-OneYear_invesco.xlsx     |
      | invesco@admininvesco.com | password1! | 2014 Q2 | Two Quarters | INVESCO Core Real Estate - USA | 2014Q2-TwoQuarters_invesco.xlsx |
      | invesco@admininvesco.com | password1! | 2015 Q4 | One Year     | INVESCO Core Real Estate - USA | 2015Q4-OneYear_invesco.xlsx     |
      | invesco@admininvesco.com | password1! | 2015 Q1 | Two Quarters | INVESCO Core Real Estate - USA | 2015Q1-TwoQuarters_invesco.xlsx |

  @fundYearToDate
    Examples:
      | Username                 | Password   | Quarter | Period       | Fund                           | Export file name               |
      | invesco@admininvesco.com | password1! | 2014 Q1 | Year to Date | INVESCO Core Real Estate - USA | 2014Q1-YearToDate_invesco.xlsx |
      | invesco@admininvesco.com | password1! | 2014 Q2 | Year to Date | INVESCO Core Real Estate - USA | 2014Q2-YearToDate_invesco.xlsx |
      | invesco@admininvesco.com | password1! | 2014 Q3 | Year to Date | INVESCO Core Real Estate - USA | 2014Q3-YearToDate_invesco.xlsx |
      | invesco@admininvesco.com | password1! | 2014 Q4 | Year to Date | INVESCO Core Real Estate - USA | 2014Q4-YearToDate_invesco.xlsx |
      | invesco@admininvesco.com | password1! | 2015 Q1 | Year to Date | INVESCO Core Real Estate - USA | 2015Q1-YearToDate_invesco.xlsx |
      | invesco@admininvesco.com | password1! | 2015 Q2 | Year to Date | INVESCO Core Real Estate - USA | 2015Q2-YearToDate_invesco.xlsx |
      | invesco@admininvesco.com | password1! | 2015 Q3 | Year to Date | INVESCO Core Real Estate - USA | 2015Q3-YearToDate_invesco.xlsx |
      | invesco@admininvesco.com | password1! | 2015 Q4 | Year to Date | INVESCO Core Real Estate - USA | 2015Q4-YearToDate_invesco.xlsx |

  @checkFootNotes
  Scenario Outline: Checking the UI of the report
    Given '<Username>' logs into the system with '<Password>'
    And the user is on the fund level attribution reports page
    Given user can see fund level reports
    When  selects filters as below
      | Quarter | <Quarter> |
      | Period  | <Period>  |
      | Fund(s) | <Fund>    |
    Then the user can sees the foot notes on the Attribution tab as 'Note: NFI-ODCE Performance Attribution is a work in progress and NCREIF continues to work with the ODCE managers to revise and formalize the process and production of the product.'
    When the user selects the Income/Appreciation tab
    Then the user see foot notes on the Income/Appreciation tab as '* The sum of income and appreciation returns may not equal total return due to rounding and/or the compounding of individual component returns to each other.'

    Examples: UI
      | Username                 | Password   | Quarter | Period   | Fund                    |
      | invesco@admininvesco.com | password1! | 2014 Q4 | One Year | AEW Core Property Trust |