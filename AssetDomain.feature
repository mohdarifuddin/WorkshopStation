@AssetDomainReport
  Feature: As a Voyanta User, I want to create my own reports from a pre-defined selection of facts and dimensions, and a variety of report types

#    Account used : admin@adhoc2.com / password1!

    Scenario Outline: checking the reports for diferent facts and dimensions
      Given the user is on the self service organisational Report page
      When the user clears the existing filters
      And selects the filters as belows
        | Month Start           | <Month Start>           |
        | Month End             | <Month End>             |
        | Currency              | <Currency>              |
        | Area Measurement Unit | <Area Measurement Unit> |
      And the user applies the selected filter
      When the user selects '<FolderName>' from Folders
      And the user selects '<Report>' from Repository section
      Then the user can see the report '<Report>'
      When user selects to Export the report
      Then the exported report should match with expected Report '<ExpectedFile>'

  @percentageMeasure
    Examples: Leased Percentage and Market core loss percentage
      | Report             | ExpectedFile            | FolderName           | Month Start  | Month End    | Currency | Area Measurement Unit |
      | Percentage Measure | Percentage Measure.xlsx | Organisation Reports | 2014, August | 2014, August | EUR      | SM                    |

  @percentageMeasure_WithScenario
    Examples: Leased Percentage and Market core loss percentage
      | Report                          | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                         | FolderName           |
      | percentageMeasure_WithScenario | 2014, August | 2014, August | EUR      | SM                    | Percentage Measure_WithScenario.xlsx | Organisation Reports |

  @occupancyMeasure
    Examples: Occupancy by Leased area , Lease Unit area and Unit area
      | Report            | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile           | FolderName           |
      | Occupancy Measure | 2013, August | 2014, August | USD      | SF                    | Occupancy Measure.xlsx | Organisation Reports |

  @occupancyMeasure_WithScenario
    Examples: Occupancy by Leased area , Lease Unit area and Unit area
      | Report                         | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                        | FolderName           |
      | occupancyMeasure_WithScenario | 2013, August | 2014, August | USD      | SF                    | Occupancy Measure_WithScenario.xlsx | Organisation Reports |


  @rentalIncomeMeasure
    Examples: Gross Rental Income and Net Rental Income
      | Report                  | Month Start | Month End   | Currency | Area Measurement Unit | ExpectedFile               | FolderName           |
      | Gross/Net Rental Income | 2014, April | 2014, April | EUR      | SF                    | Rental Income Measure.xlsx | Organisation Reports |

  @rentalIncomeMeasure_WithScenario
    Examples: Gross Rental Income and Net Rental Income
      | Report                               | Month Start | Month End   | Currency | Area Measurement Unit | ExpectedFile                            | FolderName           |
      | rentalIncomeMeasure_WithScenario | 2014, April | 2014, April | EUR      | SF                    | Rental Income Measure_WithScenario.xlsx | Organisation Reports |


  @areaMeasure
    Examples: Gross Building area,Buildable area,Gross leasable area, Net leasable area and Parking area
      | Report        | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile      | FolderName           |
      | Area Measures | 2014, August | 2014, August | EUR      | SM                    | Area Measure.xlsx | Organisation Reports |

  @areaMeasure_WithScenario
    Examples: Gross Building area,Buildable area,Gross leasable area, Net leasable area and Parking area
      | Report                     | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                   | FolderName           |
      | areaMeasure_WithScenario | 2014, August | 2014, August | EUR      | SM                    | Area Measure_WithScenario.xlsx | Organisation Reports |


  @numberMeasure
    Examples: Number of Floors, Number of Units, Number of active units and Number of parking spaces
      | Report         | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile        | FolderName           |
      | Number Measure | 2013, August | 2013, August | AUD      | SM                    | Number Measure.xlsx | Organisation Reports |

  @numberMeasure_WithScenario
    Examples: Number of Floors, Number of Units, Number of active units and Number of parking spaces
      | Report                      | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                     | FolderName           |
      | numberMeasure_WithScenario | 2013, August | 2013, August | AUD      | SM                    | Number Measure_WithScenario.xlsx | Organisation Reports |


  @insuranceMeasure
    Examples: Insurance coverage amount and Insurance Policy Premium
      | Report             | Month Start | Month End  | Currency | Area Measurement Unit | ExpectedFile           | FolderName           |
      | Insurance Measures | 2013, June  | 2013, June | GBP      | SM                    | Insurance Measure.xlsx | Organisation Reports |

  @insuranceMeasure_WithScenario
    Examples: Insurance coverage amount and Insurance Policy Premium
      | Report                          | Month Start | Month End  | Currency | Area Measurement Unit | ExpectedFile                        | FolderName           |
      | insuranceMeasure_WithScenario | 2013, June  | 2013, June | GBP      | SM                    | Insurance Measure_WithScenario.xlsx | Organisation Reports |


  @arrearsMeasure
    Examples: Accounts Receivable arrears 0 to 30 , 30 to 60 , 60 to 90, 90 to 120 , over 120
      | Report           | Month Start  | Month End     | Currency | Area Measurement Unit | ExpectedFile         | FolderName           |
      | Arrears Measures | 2013, August | 2015, January | AUD      | SM                    | Arrears Measure.xlsx | Organisation Reports |

  @arrearsMeasure_WithScenario
    Examples: Accounts Receivable arrears 0 to 30 , 30 to 60 , 60 to 90, 90 to 120 , over 120
      | Report                        | Month Start  | Month End     | Currency | Area Measurement Unit | ExpectedFile                      | FolderName           |
      | arrearsMeasure_WithScenario | 2013, August | 2015, January | AUD      | SM                    | Arrears Measure_WithScenario.xlsx | Organisation Reports |


  @recurringBillingMeasure
    Examples: Recurring Billing Billable Rent , Billable Expense Recovery
      | Report                     | Month Start     | Month End    | Currency | Area Measurement Unit | ExpectedFile                   | FolderName           |
      | Recurring Billing Measures | 2013, September | 2014, August | GBP      | SF                    | Recurring Billing Measure.xlsx | Organisation Reports |

  @recurringBillingMeasure_WithScenario
    Examples: Recurring Billing Billable Rent , Billable Expense Recovery
      | Report                                  | Month Start     | Month End    | Currency | Area Measurement Unit | ExpectedFile                                | FolderName           |
      | recurringBillingMeasure_WithScenario | 2013, September | 2014, August | GBP      | SF                    | Recurring Billing Measure_WithScenario.xlsx | Organisation Reports |

  @assetValueMeasure
    Examples: Asset Valuation Value
      | Report              | Month Start   | Month End     | Currency | Area Measurement Unit | ExpectedFile             | FolderName           |
      | Asset Value Measure | 2012, January | 2012, January | AUD      | HA                    | Asset Value Measure.xlsx | Organisation Reports |

  @assetValueMeasure_WithScenario
    Examples: Asset Valuation Value
      | Report                           | Month Start   | Month End     | Currency | Area Measurement Unit | ExpectedFile                          | FolderName           |
      | assetValueMeasure_WithScenario | 2012, January | 2012, January | AUD      | HA                    | Asset Value Measure_WithScenario.xlsx | Organisation Reports |

  @NOIMeasure
    Examples: Account activity Net operating income
      | Report                               | Month Start | Month End       | Currency | Area Measurement Unit | ExpectedFile             | FolderName           |
      | Net Operating Income 3months Measure | 2013, July  | 2013, September | USD      | SF                    | NOI 3months Measure.xlsx | Organisation Reports |
      | Net Operating Income 2months Measure | 2013, July  | 2013, August    | USD      | SF                    | NOI 2months Measure.xlsx | Organisation Reports |

  @NOIMeasure_WithScenario
    Examples: Account activity Net operating income
      | Report                                            | Month Start | Month End       | Currency | Area Measurement Unit | ExpectedFile                          | FolderName           |
      | NOI 3Measure_WithScenario | 2013, July  | 2013, September | USD      | SF                    | NOI 3months Measure_WithScenario.xlsx | Organisation Reports |
      | NOI 2Measure_WithScenario | 2013, July  | 2013, August    | USD      | SF                    | NOI 2months Measure_WithScenario.xlsx | Organisation Reports |

  @UnitMeasure
    Examples: Leased area by Unit Area  ,Unit leasable area and Expiring area by unit area
      | Report        | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile      | FolderName           |
      | Unit Measures | 2014, April | 2014, April | EUR      | SM                    | Unit Measure.xlsx | Organisation Reports |

  @UnitMeasure_WithScenario
    Examples: Leased area by Unit Area  ,Unit leasable area and Expiring area by unit area
      | Report                     | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                   | FolderName           |
      | UnitMeasure_WithScenario | 2014, April | 2014, April | EUR      | SM                    | Unit Measure_WithScenario.xlsx | Organisation Reports |


  @LeaseMeasure
    Examples: Expiring area by lease area, Leased area by lease area, Lease area by lease unit area, Expiring area by lease unit area, Headline Gross rent and  Headline Net rent
      | Report        | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile       | FolderName           |
      | Lease Measure | 2014, April | 2014, April | EUR      | SM                    | Lease Measure.xlsx | Organisation Reports |

  @LeaseMeasure_WithScenario
    Examples: Expiring area by lease area, Leased area by lease area, Lease area by lease unit area, Expiring area by lease unit area, Headline Gross rent and  Headline Net rent
      | Report                     | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                    | FolderName           |
      | LeaseMeasure_WithScenario | 2014, April | 2014, April | EUR      | SM                    | Lease Measure_WithScenario.xlsx | Organisation Reports |

  @InternalMeasure
    Examples: Internal Measures Leased Percentage and Market core loss percentage
      | Report            | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile          | FolderName           |
      | Internal Measures | 2014, August | 2014, August | EUR      | SM                    | Internal Measure.xlsx | Organisation Reports |

  @InternalMeasure_WithScenario
    Examples: Internal Measures Leased Percentage and Market core loss percentage
      | Report                         | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                       | FolderName           |
      | InternalMeasure_WithScenario | 2014, August | 2014, August | EUR      | SM                    | Internal Measure_WithScenario.xlsx | Organisation Reports |

  @currencyConversion
    Examples: Currency Coversion for Asset valuation.value (GBP-USD)
      | Report              | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile             | FolderName           |
      | Currency Conversion | 2015, November | 2015, November | USD      | SF                    | Currency Conversion.xlsx | Organisation Reports |

  @currencyConversion_WithScenario
    Examples: Currency Coversion for Asset valuation.value (GBP-USD)
      | Report                           | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                          | FolderName           |
      | currencyConversion_WithScenario | 2015, November | 2015, November | USD      | SF                    | Currency Conversion_WithScenario.xlsx | Organisation Reports |


  @NOICurrencyConversion
    Examples: Currency Coversion for Net Operating Income (USD-CAD)
      | Report                  | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                 | FolderName           |
      | NOI Currency Conversion | 2013, August | 2013, August | CAD      | SF                    | NOI Currency Conversion.xlsx | Organisation Reports |


  @NOICurrencyConversion_WithScenario
    Examples: Currency Coversion for Net Operating Income (USD-CAD)
      | Report                               | Month Start  | Month End    | Currency | Area Measurement Unit | ExpectedFile                              | FolderName           |
      | NOICurrencyConversion_WithScenario | 2013, August | 2013, August | CAD      | SF                    | NOI Currency Conversion_WithScenario.xlsx | Organisation Reports |

#  @occupancyMeasure
#  Examples: Occupancy by Leased area , Lease Unit area and Unit area
#    | Report            | ExpectedFile           | FolderName           |
#    | Occupancy Measure | Occupancy Measure.xlsx | Organisation Reports |
#
#  @rentalIncomeMeasure
#  Examples: Gross Rental Income and Net Rental Income
#    | Report                  | ExpectedFile               | FolderName           |
#    | Gross/Net Rental Income | Rental Income Measure.xlsx | Organisation Reports |
#
#  @areaMeasure
#  Examples: Gross Building area,Buildable area,Gross leasable area, Net leasable area and Parking area
#    | Report        | ExpectedFile      | FolderName           |
#    | Area Measures | Area Measure.xlsx | Organisation Reports |
#
#  @numberMeasure
#  Examples: Number of Floors, Number of Units, Number of active units and Number of parking spaces
#    | Report         | ExpectedFile        | FolderName           |
#    | Number Measure | Number Measure.xlsx | Organisation Reports |
#
#  @insuranceMeasure
#  Examples: Insurance coverage amount and Insurance Policy Premium
#    | Report             | ExpectedFile           | FolderName           |
#    | Insurance Measures | Insurance Measure.xlsx | Organisation Reports |
#
#  @arrearsMeasure
#  Examples: Accounts Receivable arrears 0 to 30 , 30 to 60 , 60 to 90, 90 to 120 , over 120
#    | Report           | ExpectedFile         | FolderName           |
#    | Arrears Measures | Arrears Measure.xlsx | Organisation Reports |
#
#  @recurringBillingMeasure
#  Examples: Recurring Billing Billable Rent , Billable Expense Recovery
#    | Report                     | ExpectedFile                   | FolderName           |
#    | Recurring Billing Measures | Recurring Billing Measure.xlsx | Organisation Reports |
#
#  @assetValueMeasure
#  Examples: Asset Valuation Value
#    | Report              | ExpectedFile             | FolderName           |
#    | Asset Value Measure | Asset Value Measure.xlsx | Organisation Reports |
#
#  @NOIMeasure
#  Examples: Account activity Net operating income
#    | Report                               | ExpectedFile             | FolderName           |
#    | Net Operating Income 3months Measure | NOI 3months Measure.xlsx | Organisation Reports |
#    | Net Operating Income 2months Measure | NOI 2months Measure.xlsx | Organisation Reports |
#
#  @UnitMeasure
#  Examples: Leased area by Unit Area  ,Unit leasable area and Expiring area by unit area
#    | Report        | ExpectedFile      | FolderName           |
#    | Unit Measures | Unit Measure.xlsx | Organisation Reports |
#
#  @LeaseMeasure
#  Examples: Expiring area by lease area, Leased area by lease area, Lease area by lease unit area, Expiring area by lease unit area, Headline Gross rent and  Headline Net rent
#    | Report        | ExpectedFile       | FolderName           |
#    | Lease Measure | Lease Measure.xlsx | Organisation Reports |
#
#  @InternalMeasure
#  Examples: Internal Measures Leased Percentage and Market core loss percentage
#    | Report            | ExpectedFile          | FolderName           |
#    | Internal Measures | Internal Measure.xlsx | Organisation Reports |
#
#  @currencyConversion
#  Examples: Currency Coversion for Asset valuation.value (GBP-USD)
#    | Report              | ExpectedFile             | FolderName           |
#    | Currency Conversion | Currency Conversion.xlsx | Organisation Reports |
#
#  @NOICurrencyConversion
#  Examples: Currency Coversion for Net Operating Income (USD-CAD)
#    | Report                  | ExpectedFile                 | FolderName           |
#    | NOI Currency Conversion | NOI Currency Conversion.xlsx | Organisation Reports |

    Scenario Outline: checking the reports for diferent facts and dimensions on org adhoc3
      Given the user is on the self service organisational Report page with org adhoc3
      When the user clears the existing filters
      And selects the filters as belows
        | Month Start           | <Month Start>           |
        | Month End             | <Month End>             |
        | Currency              | <Currency>              |
        | Area Measurement Unit | <Area Measurement Unit> |
        | Accounting Book Name  | <Accounting Book Name>  |
      And the user applies the selected filter
      When the user selects '<FolderName>' from Folders
      And the user selects '<Report>' from Repository section
      Then the user can see the report '<Report>'
      When user selects to Export the report
      Then the exported report should match with expected Report '<ExpectedFile>'

    @NOI_AccountingBookActual
      Examples: Currency Coversion for Net Operating Income (USD-CAD)
        | Report                       | Month Start  | Month End    | Currency | Area Measurement Unit | Accounting Book Name | ExpectedFile                    | FolderName           |
        | NOI- Accounting Book- Actual | 2013, August | 2013, August | CAD      | SF                    | Actual               | NOI Accounting Book Actual.xlsx | Organisation Reports |

    @NOI_AccountingBookBudget
      Examples: Currency Coversion for Net Operating Income (USD-CAD)
        | Report                       | Month Start  | Month End    | Currency | Area Measurement Unit | Accounting Book Name | ExpectedFile                    | FolderName           |
        | NOI- Accounting Book- Budget | 2013, August | 2013, August | CAD      | SF                    | Budget               | NOI Accounting Book Budget.xlsx | Organisation Reports |

    @NOI_AccountingBookForecast
      Examples: Currency Coversion for Net Operating Income (USD-CAD)
        | Report                         | Month Start  | Month End    | Currency | Area Measurement Unit | Accounting Book Name | ExpectedFile                      | FolderName           |
        | NOI- Accounting Book- Forecast | 2013, August | 2013, August | CAD      | SF                    | Forecast             | NOI Accounting Book Forecast.xlsx | Organisation Reports |
