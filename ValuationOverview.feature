@ValuationOverviewReport
Feature: As a Voyanta User, I want to use Valuation Overview Report so that i can Filter the Report based on requirements.

  Background:
    Given 'admin@reportstesting.com' logs into the system with 'password1!'

  @ValuationOverview
  Scenario Outline: Checking NOI value without any filters
    Given user navigates to Valuation Overview Page
    And the user clears existing filters
    When the user selects the filters as below:
      | Quarter Start | <Quarter Start> |
      | Quarter End   | <Quarter End>   |
    And the user selects apply filters
    And the user exports the report
    Then the exported report should match with expected report '<ExpectedReport>'

    Examples:
      | Quarter Start | Quarter End | ExpectedReport                            |
      | 2015, Q1      | 2015, Q4    | ValuationOverview_NOI_withoutfilters.xlsx |
