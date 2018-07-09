@appraisalSummary
Feature: As a Voyanta User, I want to see Appraisal Summary Report

  Scenario Outline: User should see reports based on selected filters (NOTE : We will modify this When Export to Excel Options Available on Report)

    Given '<UserName>' logs into the system with '<Password>'
    Then the user navigates to Apparaisal Summary Report Page
    When the user selects the filters as below
      | Appraisal Tags | <Appraisal Tags> |
      | Appraisal Name | <Appraisal Name> |
      | Currency       | <Currency>       |
      | Start Quarter  | <Start>          |
      | End Quarter    | <End / As-at>    |
  #Then the user sees the

    Examples:
      | UserName                    | Password   | Appraisal Tags | Appraisal Name    | Currency | Start    | End / As-at |
      | tester@testorganization.com | password1! | Public         | ME - Reg - 004/13 | USD      | 2016, Q2 | 2016, Q2    |