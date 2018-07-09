@AssetLevelAttribution-Performance
Feature: Checking the delta of reports and filters
#  Account used : automation@aew.com / password1!

  @AssetLevelAttribution-Performance
  Scenario Outline: Checking the reports with different filters, verifying the delta and totals
    Given the user is on the Asset Attribution Report Page
#   And the users language preference is set to "US English"
    When the user selects the filters as below

      | Quarter   | <Quarter>   |
      | Period    | <Period>    |
      | Fund(s)   | <Fund>      |
      | Breakdown | <Breakdown> |

    When the user exports Property Information
    #Then saved report should match with the expected report with name '<ExportFileName>'
    Then the exported report should match with expected report with name '<ExportFileName>'


  @PerformanceReportData
    Examples: Data
      | Quarter | Period   | Fund                    | Breakdown     | ExportFileName                            |
      | 2014 Q4 | One Year | AEW Core Property Trust | Property Type | 2014Q4-PerformanceInformationOneYear.xlsx |

  @YearToDate
    Examples: Year to Date
      | Quarter | Period       | Fund                    | Breakdown        | ExportFileName                               |
      | 2014 Q1 | Year to Date | AEW Core Property Trust | Property Type    | 2014Q1-PerformanceInformationYearToDate.xlsx |
      | 2014 Q2 | Year to Date | AEW Core Property Trust | Division         | 2014Q2-PerformanceInformationYearToDate.xlsx |
      | 2014 Q3 | Year to Date | AEW Core Property Trust | Property Subtype | 2014Q3-PerformanceInformationYearToDate.xlsx |
      | 2014 Q4 | Year to Date | AEW Core Property Trust | Region           | 2014Q4-PerformanceInformationYearToDate.xlsx |
      | 2015 Q1 | Year to Date | AEW Core Property Trust | Property Subtype | 2015Q1-PerformanceInformationYearToDate.xlsx |

  @SelectedDimensionPerformance
  Scenario Outline: Check the performance for the Selected Dimension
    Given the user is on the Asset Attribution Report Page
    When the user selects the filters as below

      | Quarter   | <Quarter>   |
      | Period    | <Period>    |
      | Fund(s)   | <Fund>      |
      | Breakdown | <Breakdown> |

    When the user clicks on the '<Dimension>'
    And the user exports Property Information
    Then the exported report should match with expected report with name '<ExportFileName>'

    Examples:
      | Quarter | Period   | Fund                    | Breakdown        | Dimension                 | ExportFileName                                       |
      | 2014 Q4 | One Year | AEW Core Property Trust | Property Type    | Apartments                | 2014Q4-PerformanceSubInformationPropertyType.xlsx    |
      | 2014 Q4 | One Year | AEW Core Property Trust | Division         | SE - Southeast            | 2014Q4-PerformanceSubInformationDivision.xlsx        |
      | 2014 Q4 | One Year | AEW Core Property Trust | Property SubType | AG - Garden-type Projects | 2014Q4-PerformanceSubInformationPropertySubType.xlsx |
      | 2014 Q4 | One Year | AEW Core Property Trust | Region           | S -South                  | 2014Q4-PerformanceSubInformationRegion.xlsx          |