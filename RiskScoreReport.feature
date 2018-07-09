@RiskScoreReport
  Feature: As a Voyanta User, I want to see Risk Score Report

#  Account used : automation@aew.com / password1!

  Background:
  Given the user is on the Risk Score reports page

  Scenario Outline: Checking the report with different Combination of filters
    Given the user can see risk score reports
    When selects filters as below
      | Quarter | <Quarter> |
      | Fund(s) | <Fund>    |
    And export the risk score report
    Then the saved report should match with expected risk report with name '<ExportFileName>'

  Examples:
    | Quarter | Fund                    | ExportFileName         |
    | 2014 Q4 | AEW Core Property Trust | 2014Q4-Risk Score.xlsx |
    | 2014 Q3 | AEW Core Property Trust | 2014Q3-Risk Score.xlsx |
    | 2014 Q2 | AEW Core Property Trust | 2014Q2-Risk Score.xlsx |
    | 2014 Q1 | AEW Core Property Trust | 2014Q1-Risk Score.xlsx |
    | 2015 Q1 | AEW Core Property Trust | 2015Q1-Risk Score.xlsx |
    | 2015 Q2 | AEW Core Property Trust | 2015Q2-Risk Score.xlsx |