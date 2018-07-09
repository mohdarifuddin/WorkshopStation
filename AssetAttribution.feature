@AssetLevelAttribution
Feature: Checking the Delta of Reports and Filters

#  Account used : automation@aew.com / password1!

  @AssetLevelAttribution

  Scenario Outline: Checking the reports with different filters, verifying the delta and totals for Investment Information

    Given the user is on the Asset Attribution Report Page
#    And the users language preference is set to "UK English"
    When the user selects the filters as below
      | Quarter       | <Quarter>    |
      | Period        | <Period>     |
      | Investment(s) | <Investment> |
      | Breakdown     | <Breakdown>  |
    When the user exports Investment Information
    Then the exported report should match with expected report with name '<ExportFileName>'

  @Delta
    Examples: Delta
      | Quarter | Period   | Investment              | Breakdown     | ExportFileName                     |
      | 2015 Q4 | One Year | AEW Core Property Trust | Property Type | 2015Q4-FundInformationOneYear.xlsx |
      | 2014 Q4 | One Year | AEW Core Property Trust | Property Type | 2014Q4-FundInformationOneYear.xlsx |

  @YearToDate
    Examples: Year to Date
      | Quarter | Period       | Investment              | Breakdown        | ExportFileName                                |
      | 2015 Q1 | Year to Date | AEW Core Property Trust | Property Type    | 2015Q1-FundInfoYearToDatePropertyType.xlsx    |
  #    | 2014 Q2 | Year to Date | AEW Core Property Trust | Division         | 2014Q2-FundInfoYearToDateDivision.xlsx        |
      | 2015 Q3 | Year to Date | AEW Core Property Trust | Property Subtype | 2015Q3-FundInfoYearToDatePropertySubType.xlsx |
  #    | 2014 Q4 | Year to Date | AEW Core Property Trust | Region           | 2014Q4-FundInfoYearToDateRegion.xlsx          |
      | 2015 Q2 | Year to Date | AEW Core Property Trust | Property Subtype | 2015Q2-FundInfoYearToDatePropertySubType.xlsx |
      | 2014 Q3 | Year to Date | AEW Core Property Trust | Property Subtype | 2014Q3-FundInfoYearToDatePropertySubType.xlsx |
      | 2014 Q1 | Year to Date | AEW Core Property Trust | Property Type    | 2014Q1-FundInfoYearToDatePropertyType.xlsx    |


  @ExpandingDimensions
  Scenario Outline: Expanding dimensions and viewing the sub dimensions and the title of the bubble chart
    Given the user is on the Asset Attribution Report Page
#    And the users language preference is set to "<Language Preference>"
    When the user selects the filters as below

      | Quarter       | <Quarter>    |
      | Period        | <Period>     |
      | Investment(s) | <Investment> |
      | Breakdown     | <Breakdown>  |

#    And the user clicks on the arrow next to Attribution from Fund Information Table
#    And the user is able to view the columns Selection, Interaction, Allocation and Total Effect
    And the title of the bubble chart as '<Dimension - Bubble Chart Title>'
    When the user clicks on the '<Dimension>'
    Then the user can sees '<SubDimensions>' in the report table
    And the title of the bubble chart as '<SubDimension - Bubble Chart Title>'

    Examples: Expanding Dimensions
      | Quarter | Period         | Investment              | Breakdown        | Dimension          | SubDimensions                                                                                                                                                 | Dimension - Bubble Chart Title                  | SubDimension - Bubble Chart Title            |
      | 2014 Q4 | One Year       | AEW Core Property Trust | Property Type    | A - Apartments     | NE - Northeast,WP - Pacific,EN - East North Central,ME - Mideast,WM - Mountain,SW - Southwest,WN - West North Central,SE - Southeast,Geographic Totals        | All NCREIF Property Types (Sector Breakdown)    | Sector: Apartments (Geographic Breakdown)    |
      | 2014 Q3 | Three Quarters | AEW Core Property Trust | Division         | WM - Mountain      | I - Industrial,O - Office,L - Land,R - Retail,H - Hotel*,X - Other*,A - Apartments,Sector Totals                                                              | All Divisions (Geographic Breakdown)            | Geographic: Mountain (Sector Breakdown)      |
      | 2014 Q2 | Two Quarters   | AEW Core Property Trust | Property Subtype | IM - Manufacturing | WP - Pacific,WM - Mountain*,ME - Mideast*,NE - Northeast*,EN - East North Central*,WN - West North Central*,SE - Southeast*,SW - Southwest*,Geographic Totals | All NCREIF Property Subtypes (Sector Breakdown) | Sector: Manufacturing (Geographic Breakdown) |
      | 2014 Q1 | One Quarter    | AEW Core Property Trust | Region           | W - West           | A - Apartments,H - Hotel,X - Other,L - Land,I - Industrial,O - Office,R - Retail,Sector Totals                                                                | All Regions (Geographic Breakdown)              | Geographic: West (Sector Breakdown)          |
