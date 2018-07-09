package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.ReportsPageContainer;
import voyanta.ui.utils.*;
import voyanta.ui.utils.unused.VoyantaElement;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReportsPage extends AbstractVoyantaPage {

    private static final String url = PropertiesLoader.getProperty("ui_url") + "report/";
    public static Logger LOGGER = org.apache.log4j.Logger.getLogger(ReportsPage.class);
    static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 20);

    ReportsPageContainer pageContainer;

    public ReportsPage() {
        pageContainer = ReportsPage.getDataContainer(ReportsPageContainer.class);
        VoyantaDriver.getCurrentDriver().switchTo().defaultContent();

        if (!VUtils.elementExists(By.id("report-page"))) {
            if (VUtils.elementExists(By.id("qlikviewIframe"))) {
                VoyantaDriver.getCurrentDriver().switchTo().frame("qlikviewIframe");
                VUtils.waitForElement(pageContainer.currentSelectionsHeader);
            }

        } else {
            if (VUtils.elementExists(By.id("report-page"))) {
                VoyantaDriver.getCurrentDriver().switchTo().frame("report-page");
                VUtils.waitForElement(pageContainer.currentSelectionsHeader);
            }

        }

        LOGGER.info("user goes to report page");
    }

    public void setReportsPage() {
        pageContainer = ReportsPage.getDataContainer(ReportsPageContainer.class);
        VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
        VUtils.waitForElement(pageContainer.currentSelectionsHeader);
        LOGGER.info("user goes to report page");
    }

    public static ReportsPage openPage() {
        VoyantaDriver.getCurrentDriver().get(url);
        return new ReportsPage();
    }

    public boolean isReportDisplayed() {
        try {
            //PhantomJS
            VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
            if (!VUtils.elementExists(By.id("report-page"))) {
                try {
                    VoyantaDriver.getCurrentDriver().switchTo().frame(pageContainer.frameQlikViewReports);
                    //VoyantaDriver.getDriver("phantomjs").switchTo().frame(pageContainer.frameQlikViewReports);
                } catch (NoSuchFrameException e) {
                    VoyantaDriver.getCurrentDriver().switchTo().frame(pageContainer.frameReportsPage);
                    //VoyantaDriver.getDriver("phantomjs").switchTo().frame(pageContainer.frameReportsPage);
                }
            } else {
                VoyantaDriver.getCurrentDriver().switchTo().frame(pageContainer.frameReportsPage);
                //VoyantaDriver.getDriver("phantomjs").switchTo().frame(pageContainer.frameReportsPage);
            }
//            VoyantaDriver.getCurrentDriver().switchTo().frame("qlikviewIframe");
            VUtils.waitForElement(pageContainer.currentSelectionsHeader);
            return pageContainer.currentSelectionsHeader.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public void exportToExcel(String reportName) {


        //  else if((VUtils.elementExists(By.xpath("//div[text()='Send to Excel']"))))
        //      VoyantaDriver.getDivByText("Send to Excel").click();
        VUtils.waitFor(5);
        if (reportName.equalsIgnoreCase("Operating Statistics")) {
            generateReportFromBody("Document\\CT_OperatingStatistics");
        } else if (reportName.contains("Valuation / NOI Trend")) {

            VUtils.waitFor(5);
            VoyantaDriver.findElement(By.xpath("//div[@id='Document\\CH_ValuationNOI']//div[@title='Send to Excel']")).click();
//                (new VoyantaElementImpl(VoyantaDriver.findElement(By.id("Document\\CH_ValuationNOI")).findElement(By.className("QvGraph")))).rightclick();
            VUtils.waitFor(5);
        } else if (reportName.contains("Budget vs Actual")) {

            generateReportFromBody("Document\\CT_ActVsBud");
        } else if (reportName.contains("Revenue and Expense Breakdown")) {

            generateReportFromBody("Document\\CT_RevExp");
        } else if (reportName.contains("Operating Statement")) {
            generateReportFromGrid("Document\\CT_OperatingStatement");
        } else if (reportName.contains("Balance Sheet")) {
            generateReportFromGrid("Document\\CT_BalanceSheet");
        } else if (reportName.contains("Lease Expiry Profile Report")) {
            generateReportFromGrid("Document\\CT_LeaseExpiryProfile");
        } else if (reportName.contains("Rental Arrears Report")) {
            generateReportFromGrid("Document\\CT_RentalArrearsReport");
        } else if (reportName.equalsIgnoreCase("Operating Statistics Overview") || reportName.equalsIgnoreCase("Operating Statistics As Of - 2013/12/31")) {
            generateReportFromGrid("Document\\CT_OperatingStatistics");
        } else if (reportName.contains("Building Unit Inventory")) {
            generateReportFromGrid("Document\\CT_BuildingUnitInventory");
        } else if (reportName.contains("Tenancy Schedule")) {
            generateReportFromGrid("Document\\CT_TenancySchedule");
        } else if (reportName.contains("Rental Analysis Report")) {
            generateReportFromGrid("Document\\CT_RentalAnalysis");
        } else if (reportName.contains("Loan Schedule Report")) {
            generateReportFromGrid("Document\\CT_LoanSchedule");
        }
            /*
            else if(reportName.contains("Arrears As Of"))
            {
                WebElement elem = VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".QvCaptionIcon.caption-icon-16x16.caption-XL-dark-icon"));
                elem.click();
                //generateReportFromImage("Document\\CH_RentArrears");
            }*/
        else {
            WebElement element = VoyantaDriver.getDivByText(reportName);
            wait.until(ExpectedConditions.visibilityOf(element));
            VoyantaElement voyantaElement = new VoyantaElementImpl(element);
            wait.until(ExpectedConditions.visibilityOf(voyantaElement));
            try {
                ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);",  voyantaElement);
                VUtils.waitFor(1);
                voyantaElement.rightclick();
            } catch (Exception ex) {
                VUtils.waitFor(5);
                ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);",  voyantaElement);
                VUtils.waitFor(1);
                voyantaElement.rightclick();
            }
            VUtils.waitFor(5);
            //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Send to Excel']")));
            if ((VUtils.isElementPresent(By.xpath("//span[text()='Send to Excel']")))) {
                ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);",  VoyantaDriver.getSpanByText("Send to Excel"));
                VUtils.waitFor(1);
                VoyantaDriver.getSpanByText("Send to Excel").click();
            } else
                throw new RuntimeException("Unable to generate report for " + reportName);
        }
    }

//        if(reportName.equals("Asset Diversification")) {
//
//            WebElement element = VoyantaDriver.findElement(By.xpath("//div[@title='Send to Excel']"));
//            element.click();
//        }
//        else if(reportName.equals("Top 10 Assets")) {
//
//            WebElement element = VoyantaDriver.findElements(By.xpath("//div[@title='Send to Excel']")).get(1);
//            element.click();
//        }


    public void generateReportFromBody(String reportLocator) {
        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.findElement(By.id(reportLocator)).findElement(By.className("QvGraph")));
        VUtils.waitFor(1);
        (new VoyantaElementImpl(VoyantaDriver.findElement(By.id(reportLocator)).findElement(By.className("QvGraph")))).rightclick();
        //xpath=//div[@id='Document\CT_RevExp']//div[@class='QvGraph']
        VUtils.waitFor(5);
        if (VUtils.isElementWithTextPresentBySpan("Send to Excel")){
            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getSpanByText("Send to Excel"));
            VUtils.waitFor(1);
            VoyantaDriver.getSpanByText("Send to Excel").click();
        }

        else
            throw new RuntimeException("Unable to generate report for " + reportLocator);
    }

    public void generateReportFromGrid(String reportLocator) {
        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.findElement(By.id(reportLocator)).findElement(By.className("QvGrid")));
        VUtils.waitFor(1);
        (new VoyantaElementImpl(VoyantaDriver.findElement(By.id(reportLocator)).findElement(By.className("QvGrid")))).rightclick();
        //xpath=//div[@id='Document\CT_RevExp']//div[@class='QvGraph']
        VUtils.waitFor(5);
        if (VUtils.isElementWithTextPresentBySpan("Send to Excel")){
            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getSpanByText("Send to Excel"));
            VUtils.waitFor(1);
            VoyantaDriver.getSpanByText("Send to Excel").click();
        }

        //  else
        //    throw new RuntimeException("Unable to generate report for "+reportLocator);
    }

    public void generateReportFromImage(String reportLocator) {
        (new VoyantaElementImpl(VoyantaDriver.findElement(By.id(reportLocator)).findElement(By.className("QvGraph")))).rightclick();
        //xpath=//div[@id='Document\CT_RevExp']//div[@class='QvGraph']
        VUtils.waitFor(5);
        if (VUtils.isElementWithTextPresentBySpan("Send to Excel"))
            VoyantaDriver.getSpanByText("Send to Excel").click();
        else
            throw new RuntimeException("Unable to generate report for " + reportLocator);
    }


    public boolean isExcelReportGenerated() {
        VUtils.waitForElement(pageContainer.downloadConfirmBox);
        return pageContainer.downloadConfirmBox.isDisplayed();
    }

    public void applyFilters(Map<String, String> data) {
        String value;
        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                if (key.equals("Year")) {
                    value = data.get(key);
                    if (value.equals("All")) {
                        VoyantaDriver.getDivByText("Year").click();
                        LOGGER.info("Clicking on the Section: " + key);
                        VUtils.waitFor(5);
                        value = "2014";//Document\F_DATE_SELECTString.valueOf((new Date()).getTime());
                        LOGGER.info("Clicking on the Section: " + key + " and value: " + value);

                        VoyantaDriver.getDivByTextElements(value).get(1).click();
                        VUtils.waitFor(5);
                    } else if (!value.equals(String.valueOf((new Date()).getYear()))) {
                        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByText("Year"));
//                        javascript("scrollIntoView", VoyantaDriver.getDivByText("Year"));
                        VoyantaDriver.getDivByText("Year").click();
                        VUtils.waitFor(5);
                        VUtils.waitForAttribute(VoyantaDriver.getDivByText("Year"), "class", "QvTab QvFloatLeft QvTabSelected");
                        LOGGER.info("Clicking on the Section: " + key + " and value: " + value);
                        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByText("Year"));
//                        javascript("scrollIntoView", VoyantaDriver.getDivByText("Year"));
                        LOGGER.info("scroll up the page to year");
                        //Only works on Firefox 34.0.5
                        //VoyantaDriver.getDivByText(value).click();
                        WebElement element = VoyantaDriver.getDivByText(value);
                        Point locationPoint;
                        locationPoint = element.getLocation();
                        LOGGER.info(key + " " + element.getText() + " Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                        //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
//                        javascript("click", element);
                        builder.click(element).build().perform();
                        VUtils.waitFor(5);
                       /* if(VUtils.isTextSelected(value)==false){
                           LOGGER.info("Clicking on the Section agains: " + key + " and value: " + value);
                        	 VoyantaDriver.getDivByText(value).click();
                             VUtils.waitFor(2);
                        };*/
                        VUtils.confirmSelected(key, value);
                        VUtils.waitFor(5);
                    }
                } else if (key.equals("Quarter")) {
                    VUtils.waitFor(5);
                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByText("Quarter"));
//                    javascript("scrollIntoView", VoyantaDriver.getDivByText("Quarter"));
                    LOGGER.info("scroll up the page to quarter");
                    VoyantaDriver.getDivByText("Quarter").click();

                    LOGGER.info("Clicking on the Section: " + key);
                    VUtils.waitFor(5);
                    value = data.get(key);
                    VUtils.waitForAttribute(VoyantaDriver.getDivByText("Quarter"), "class", "QvTab QvFloatLeft QvTabSelected");
                    if (VUtils.isElementPresent(By.xpath("//div[text()='" + value + "']"))) {
                        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByText("Quarter"));
//                        javascript("scrollIntoView", VoyantaDriver.getDivByText("Quarter"));
                        LOGGER.info("Clicking on the Section: " + key + " and value: " + value);
                        //Chrome latest version
                        WebElement element = VoyantaDriver.getDivByText(value);
                        Point locationPoint;
                        locationPoint = element.getLocation();
                        LOGGER.info(key + " " + element.getText() + " Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                        //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
//                        javascript("click", element);
                        builder.click(element).build().perform();
                        //Firefox 34.0.5 only
                        //VoyantaDriver.getDivByText(value).click();
                    } else {
                        WebElement e = VoyantaDriver.findElement(By.xpath("//*[@id='Document\\F_DATE_SELECT']/div[2]/div[2]/div[2]//div/*[@class='QvSelected']"));
                        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", e);
                        (new VoyantaElementImpl(e)).rightclick();
                        VUtils.waitFor(1);
                        if (VUtils.isElementWithTextPresentBySpan("Clear"))
                            VoyantaDriver.getSpanByText("Clear").click();

                        LOGGER.info("Clearing Quarter Section");
                        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByText("Quarter"));
//                        javascript("scrollIntoView", VoyantaDriver.getDivByText("Quarter"));
                        LOGGER.info("Clicking on the Section: " + key + " and value: " + value);
                        //Chrome latest version
                        WebElement element = VoyantaDriver.getDivByText(value);
                        Point locationPoint;
                        locationPoint = element.getLocation();
                        LOGGER.info(key + " " + element.getText() + " Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                        //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
//                        javascript("click", element);
                        builder.click(element).build().perform();
                        //Firefox 34.0.5 only
                        //VoyantaDriver.getDivByText(value).click();
                    }

                    VUtils.waitFor(5);

                } else if (key.equals("Month")) {
                    // VUtils.waitFor(2);
                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByText("Month"));
//                    javascript("scrollIntoView", VoyantaDriver.getDivByText("Month"));
                    LOGGER.info("scroll up the page to month");
                    VoyantaDriver.getDivByText("Month").click();

                    VUtils.waitFor(5);
                    value = data.get(key);
                    //    VUtils.waitForAttribute(VoyantaDriver.getDivByText("Month"),"class","QvTab QvFloatLeft QvTabSelected");
                    LOGGER.info("Clicking on the Section: " + key + " and value: " + value);
                    //Chrome latest version
                    WebElement element = VoyantaDriver.getDivByText(value);
                    Point locationPoint;
                    locationPoint = element.getLocation();
                    LOGGER.info(key + " " + element.getText() + " Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                    Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                    //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
//                    javascript("click", element);
                    builder.click(element).build().perform();
                    //Firefox 34.0.5 only
                    //VoyantaDriver.getDivByText(value).click();
                    VUtils.waitFor(5);

                } else if (key.equals("Toggle") || key.equals("report name")) {
                    clickAsPerReportAndToggleValue(data.get("report name"), data.get(key));
                    VUtils.waitFor(5);
                } else if (key.equals("Tab")) {
                    value = data.get(key);
                    LOGGER.info("Clicking on the Section: " + key + " and value: " + value);
                    selectTab(data.get("report name"), value);
                    VUtils.waitFor(5);

                } else if (key.equalsIgnoreCase("Checkbox")) {
                    value = data.get(key);
                    LOGGER.info("Clicking on the Section: " + key + " and value: " + value);

                    if (value.equalsIgnoreCase("Activity")) {
                        LOGGER.info("Ignoring as is default value on the Section: " + key + " and value: " + value);
                    } else if (value.equalsIgnoreCase("Expiring")) {
                        LOGGER.info("Ignoring as is default value on the Section: " + key + " and value: " + value);
                    } else {
                        //Chrome latest version
                        WebElement element = VoyantaDriver.getDivByText(value);
                        Point locationPoint;
                        locationPoint = element.getLocation();
                        LOGGER.info(key + " " + element.getText() + " Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                        //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
//                        javascript("click", element);
                        builder.click(element).build().perform();
                        //Firefox 34.0.5 only
                        //VoyantaDriver.getDivByText(value).click();
                    }


                } else if (key.equalsIgnoreCase("Area Measure")) {
                    value = data.get(key);
                    LOGGER.info("Clicking on the Section: " + key + " and value: " + value);
                    VUtils.waitFor(5);
                    Point locationPoint;
//                    javascript("scrollIntoView", VoyantaDriver.getDivByTitleAndText(value));
                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByTitleAndText(value));
                    WebElement element = VoyantaDriver.getDivByTitleAndText(value);
                    if (VoyantaDriver.getDivByTitleAndText(value).isDisplayed()) {
                        locationPoint = element.getLocation();
                        LOGGER.info("Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                        //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
//                        javascript("click", element);
                        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", element);
                        builder.click(element).build().perform();
                        //Only works on Firefox Version 34.0.5
                        //VoyantaDriver.getDivByTitleAndText(value).click();
                    } else
                        VoyantaDriver.getDivElementsByTitleAndText(value).get(1).click();
                    VUtils.waitFor(5);
                } else if (key.equalsIgnoreCase("Acct Book Name")) {
                    value = data.get(key);
                    LOGGER.info("Clicking on the Section: " + key + " and value: " + value);
                    Point locationPoint;

                    VUtils.waitFor(5);
                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByTitleAndText(value));
//                    javascript("scrollIntoView", VoyantaDriver.getDivByTitleAndText(value));
                    WebElement element = VoyantaDriver.getDivByTitleAndText(value);
                    if (VoyantaDriver.getDivByTitleAndText(value).isDisplayed()) {
//                      VoyantaDriver.getDivByTitleAndText(value).click();
                        locationPoint = element.getLocation();
                        LOGGER.info("Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
//                        javascript("click", element);
                        builder.click(element).build().perform();
                    } else {
//                        VoyantaDriver.getDivElementsByTitleAndText(value).get(1).click();
                        element = VoyantaDriver.getDivElementsByTitleAndText(value).get(1);
                        locationPoint = element.getLocation();
                        LOGGER.info("Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                        javascript("click", element);
                        //builder.click(element).build().perform();
                    }
                    VUtils.waitFor(5);
                } else if (key.equalsIgnoreCase("Assumption Scenario")) {
                    value = data.get(key);
                    LOGGER.info("Clicking on the Section: " + key + " and value: " + value);
                    Point locationPoint;

                    VUtils.waitFor(5);
                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByTitleAndText(value));
//                    javascript("scrollIntoView", VoyantaDriver.getDivByTitleAndText(value));
                    WebElement element = VoyantaDriver.getDivByTitleAndText(value);
                    if (VoyantaDriver.getDivByTitleAndText(value).isDisplayed()) {
//                        VoyantaDriver.getDivByTitleAndText(value).click();
                        locationPoint = element.getLocation();
                        LOGGER.info("Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
//                        javascript("click", element);
                        builder.click(element).build().perform();
                    } else {
//                        VoyantaDriver.getDivElementsByTitleAndText(value).get(1).click();
                        element = VoyantaDriver.getDivElementsByTitleAndText(value).get(1);
                        locationPoint = element.getLocation();
                        LOGGER.info("Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
//                        javascript("click", element);
                        builder.click(element).build().perform();
                    }
                    VUtils.waitFor(5);
                } else if (key.equals("Investment")) {

                    LOGGER.info("AJAX:" + (Long) ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("return jQuery.active"));
                    value = data.get(key);
                    VUtils.waitFor(5);
                    // VoyantaElement element = new VoyantaElementImpl(VoyantaDriver.findElement(By.cssSelector("div[title='"+value+"']")));
                    VoyantaElement element = new VoyantaElementImpl(VoyantaDriver.findElement(By.id("Document\\LB_F_Investment")));
                    LOGGER.info("user scroll to investment");
                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", element);
//                    javascript("scrollIntoView", element);
                    if (!value.equalsIgnoreCase("UB Realty Trust")) {
                        wait.until(ExpectedConditions.visibilityOf(element));
                        VoyantaElement voyantaElement = new VoyantaElementImpl(element);
                        wait.until(ExpectedConditions.visibilityOf(voyantaElement));
                        voyantaElement.rightclick();
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                        /*builder.click(element).build().perform();
                        element.rightclick();*/
                        wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.getSpanByText("Expand all")));
                        //Firefox 34.0.5?
                        //VoyantaDriver.getSpanByText("Expand all").click();
                        if (VUtils.isElementPresent(VoyantaDriver.getSpanByText("Expand all"))) {
                            WebElement elem = VoyantaDriver.getSpanByText("Expand all");
                            Point locationPoint = elem.getLocation();
                            LOGGER.info("Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                            //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
//                            javascript("click", elem);
                            builder.click(elem).build().perform();
                        }
                        //Firefox 34.0.5 only
                        //VoyantaDriver.getSpanByText("Expand all").click();

                        VUtils.waitFor(5);
                        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.getDivByText(value));
                    }
//                        javascript("scrollIntoView", VoyantaDriver.getDivByText(value));
                    //   VoyantaDriver.findElement(By.cssSelector("div[title='"+value+"']  div.cell-icon.cell-EXC-icon")).click();
                    // VUtils.waitFor(3);
                    WebElement elem = VoyantaDriver.getDivByText(value);
                    Point locationPoint = elem.getLocation();
                    LOGGER.info("Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                    Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                    //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
//                        javascript("click", elem);
                    builder.click(elem).build().perform();
                    //Firefox 34.0.5 only
                    //VoyantaDriver.getDivByText(value).click();
                    VUtils.waitFor(5);
                    //     VoyantaDriver.findElement(By.cssSelector("div[title='"+value+"']")).click();
                    LOGGER.info("Clicking on the Section: " + key + " and value: " + value);
                } else if (key.equals("DebtFacility")) {
                    value = data.get(key);
                    //Chrome latest version
                    WebElement element = VoyantaDriver.getDivByText(value);
                    Point locationPoint;
                    locationPoint = element.getLocation();
                    LOGGER.info(key + " " + element.getText() + " Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                    Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                    //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
                    builder.click(element).build().perform();
//                        javascript("click", element);
                    // /Firefox 34.0.5 only
                    //VoyantaDriver.getDivByText(value).click();
                } else if (key.equals("Asset")) {
                    value = data.get(key);
                    WebElement assetFilter = VoyantaDriver.findElement(By.id("Document\\LB_F_Asset"));
                    javascript("scrollIntoView", assetFilter);
                    //((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", assetFilter);
                    WebElement assetSearch = VoyantaDriver.findElement(By.xpath("//div[@id='Document\\LB_F_Asset']/div[1]/div[1]/div"));
                    assetSearch.click();
                    WebElement search = VoyantaDriver.findElement(By.xpath("//div[@class='PopupSearch']/input"));
                    search.click();
                    search.sendKeys(value);
                    VUtils.waitFor(5);
                    VoyantaDriver.findElement(By.xpath("//*[@id='Document\\LB_F_Asset']//div[@class='QvOptional'][1]")).click();
                    VUtils.waitFor(5);
                } else if (key.equals("Currency")) {
                    value = data.get(key);
                    WebElement currencyFilter = VoyantaDriver.findElement(By.id("Document\\LB_F_Currency"));
                    //((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", currencyFilter);
                    javascript("scrollIntoView", currencyFilter);
                    WebElement assetSearch = VoyantaDriver.findElement(By.xpath("//div[@id='Document\\LB_F_Currency']/div[1]/div[1]/div"));
                    assetSearch.click();
                    WebElement search = VoyantaDriver.findElement(By.xpath("//div[@class='PopupSearch']/input"));
                    search.click();
                    search.sendKeys(value);
                    VUtils.waitFor(5);
                    VoyantaDriver.findElement(By.xpath("//*[@id='Document\\LB_F_Currency']/div[2]/div/div[1]/div[1]")).click();
                    VUtils.waitFor(5);
                } else {
                    value = data.get(key);
                    LOGGER.info("Clicking on the Section: " + key + " with value: " + value);
                    System.out.println("Clicking on the Section: " + key + " with value: " + value);
                    VUtils.waitFor(5);
                    if (VoyantaDriver.getDivByText(value) != null && VoyantaDriver.getDivByText(value).isDisplayed()) {
                        //Chrome latest version
                        WebElement element = VoyantaDriver.getDivByText(value);
                        Point locationPoint;
                        locationPoint = element.getLocation();
                        LOGGER.info(key + " " + element.getText() + " Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
                        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
                        //builder.moveToElement(element, locationPoint.x, locationPoint.y).click().build().perform();
                        builder.click(element).build().perform();
                        //Firefox 34.0.5 only
                        //VoyantaDriver.getDivByText(value).click();
                    } else {
                        VoyantaDriver.getDivByTitle(value).click();
                    }
                    VUtils.waitFor(5);
                }
            } else
                LOGGER.info("Ignore the filter as the value is not given in scenario:" + key);
            VUtils.waitFor(5);
        }
    }


    public void deSelectAcctBookName(final String value) {
        if (value.equals("")) {
            WebElement ele = VoyantaDriver.findElement(By.xpath("//*[@id='Document\\LB_F_AccountingBookName']/div[2]/div/div[1]/div[1]/div[1]"));
            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);", ele);
            VUtils.waitFor(1);
            (new VoyantaElementImpl(ele)).rightclick();
            VUtils.waitFor(1);
            if (VUtils.isElementWithTextPresentBySpan("Clear")){
                ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);", VoyantaDriver.getSpanByText("Clear"));
                VUtils.waitFor(1);
                VoyantaDriver.getSpanByText("Clear").click();
                VUtils.waitFor(2);
            }
            
            LOGGER.info("Clearing Acct Book Name Filter");
        }
    }

    private void clickAsPerReportAndToggleValue(String reportName, String toggle) {
        String togglePath;
        if (reportName.equalsIgnoreCase("Asset Diversification")) {
            togglePath = "//div[@id='Document\\CH_AssetDiv']//div[@class='Qv_Hotspot']";
            if (toggle.equals("Asset Value")) {
                LOGGER.info("Not clicking on any toggle as this is default toggle");
            } else if (toggle.equals("Gross Rental Income") || toggle.equals("Net Rental Income") || toggle.equals("Leasable Area") || toggle.equals("Number of Assets")) {
                LOGGER.info("Clicking on toggle as to generate " + toggle + " report");
                clickOnToggle(togglePath, toggle);
            } else {

                LOGGER.info("Invalid toogle " + toggle);
                //   throw new RuntimeException("Invalid toggle "+toggle);
            }
        } else if (reportName.contains("Top 10 Assets")) {
            togglePath = "//div[@id='Document\\CH_TopAssets']//div[@class='Qv_Hotspot']";
            if (toggle.equalsIgnoreCase("Net Rental Income")) {
                LOGGER.info("Not clicking on any toggle as this is default toggle");
            } else if (toggle.equalsIgnoreCase("Gross Rental Income") || toggle.equalsIgnoreCase("Total Arrears") || toggle.equalsIgnoreCase("Leasable Area") || toggle.equalsIgnoreCase("Asset Value")) {
                LOGGER.info("Clicking on toggle as to generate " + toggle + " report");
                // clickOnToggleFor("//div[@id='Document\\CH_TopAssets']//div[@class='Qv_Hotspot']", 3);
                clickOnToggle(togglePath, toggle);
            } else {
                LOGGER.info("Invalid toogle " + toggle);
            }
        }
        //   else if(reportName.contains("Valuation / NOI Trend"))
        else if (reportName.contains("Valuation Trend")) {  // togglePath="//div[@id='Document\\CH_ValuationNOI']//div[@class='Qv_Hotspot']";
     /*       VUtils.waitFor(3);
            if(toggle.contains("NOI Trend"))
            {
                LOGGER.info("Not clicking on any toggle as this is default toggle");
            }
            else if(toggle.contains("Asset Value")) {
              clickOnToggle(togglePath,toggle);
            }
            else{

                LOGGER.info("Invalid toogle "+toggle);
            }*/
        } else if (reportName.contains("Lease Expiry Schedule")) {
            togglePath = "//div[@id='Document\\CH_LeaseExpiry']//div[@class='Qv_Hotspot']";
            if (toggle.equalsIgnoreCase("Expiring & Termination Area")) {
                LOGGER.info("Not clicking on any toggle as this is default toggle");

            } else if (toggle.contains("Expiring && Terminating % of Total") || toggle.contains("Expiring % of Total") || toggle.contains("Expiring % of Total") || toggle.contains("Expiring Area")) {


                LOGGER.info("Clicking on toggle as to generate " + toggle + " report");
                clickOnToggle(togglePath, toggle);

            } else {
                LOGGER.info("Invalid toggle " + toggle);
//                throw new RuntimeException("Invalid toggle "+toggle);
            }
        } else if (reportName.equalsIgnoreCase("Occupancy Trend")) {
            togglePath = "//div[@id='Document\\CH_OccupTrend']//div[@class='Qv_Hotspot']";

            if (toggle.equalsIgnoreCase("Leased Area")) {
                LOGGER.info("Not clicking on any toggle as " + toggle + " is default toggle");
            } else if (toggle.equalsIgnoreCase("by Unit Area")) {
                LOGGER.info("Clicking on toggle as to generate " + toggle + " report");
                clickOnToggle(togglePath, toggle);
            }
        } else if (reportName.equalsIgnoreCase("Top 10 Tenants")) {
            togglePath = "//div[@id='Document\\CH_TopTenants']//div[@class='Qv_Hotspot']";
            if (toggle.equalsIgnoreCase("Net Rent")) {
                LOGGER.info("Not clicking on any toggle as " + toggle + " is default toggle");
            } else if (toggle.equalsIgnoreCase("Retail Sales") || toggle.equalsIgnoreCase("Leased Area") || toggle.equalsIgnoreCase("Gross Rent") || toggle.equalsIgnoreCase("Total Arrears")) {
                LOGGER.info("Clicking on toggle as to generate " + toggle + " report");
                clickOnToggle(togglePath, toggle);
            } else {
                LOGGER.info("Given report doesn't have toggles :" + reportName);
//            throw new RuntimeException("Given report doesn't have toggles :"+reportName);
            }
        } else if (reportName.equalsIgnoreCase("Tenant Mix")) {
            togglePath = "//div[@id='Document\\CH_TenantMix']//div[@class='Qv_Hotspot']";
            if (toggle.equalsIgnoreCase("Leased Area")) {
                LOGGER.info("Not clicking on any toggle as " + toggle + " is default toggle");
            } else if (toggle.equalsIgnoreCase("No. of Tenants") || toggle.equalsIgnoreCase("Sales Volume")) {
                LOGGER.info("Clicking on toggle as to generate " + toggle + " report");
                clickOnToggle(togglePath, toggle);
            } else {
                LOGGER.info("Given report doesn't have toggles :" + reportName);
//            throw new RuntimeException("Given report doesn't have toggles :"+reportName);
            }
        } else {
            LOGGER.info("Given report doesn't have toggles :" + reportName);
//            throw new RuntimeException("Given report doesn't have toggles :"+reportName);
        }


    }

    public void clickOnToggleFor(String xpathToggle, int count) {

        WebElement element = VoyantaDriver.findElement(By.xpath(xpathToggle));
        VoyantaElement voyantaElement = new VoyantaElementImpl(element);
        voyantaElement.rightclick();
        VoyantaDriver.getCurrentDriver().findElement(By.linkText("Number of Assets")).click();
        VUtils.waitFor(2);
    }

    public void clickOnToggle(String xpathToggle, String toggleName) {
        WebElement element = VoyantaDriver.findElement(By.xpath(xpathToggle));
        VoyantaElement voyantaElement = new VoyantaElementImpl(element);
        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);",  voyantaElement);
        VUtils.waitFor(1);
        voyantaElement.rightclick();
        // VoyantaDriver.getCurrentDriver().findElement(By.linkText(toggleName)).click();
        try {
            VoyantaDriver.getCurrentDriver().findElement(By.partialLinkText(toggleName)).click();
        } catch (WebDriverException e) {
            VoyantaDriver.getCurrentDriver().findElements(By.tagName("td")).stream().filter(x -> x.getText().equalsIgnoreCase(toggleName)).findFirst().get().click();
        }

        VUtils.waitFor(5);
    }

    public void clearFilters() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.clearFiltersButton));
        pageContainer.clearFiltersButton.click();
        //Moved to next method to wait for clear to occur
        VUtils.waitFor(5);
    }

    public void selectTab(String reportName, String value) {
        LOGGER.info("report name is " + reportName);
        if (reportName.equalsIgnoreCase("Revenue and Expense Breakdown")) {
            WebElement e = VoyantaDriver.findElement(By.xpath("//div[@id='Document\\CT_RevExp']//td[text()='" + value + "']"));
            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", e);
            VUtils.waitFor(1);
            e.click();

            VUtils.waitFor(5);
        } else if (reportName.equalsIgnoreCase("Budget vs Actual")) {

            WebElement element = VoyantaDriver.findElement(By.xpath("//div[@id='Document\\CT_ActVsBud']//td[text()='" + value + "']"));

            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", element);
//            javascript("scrollIntoView", element);
            element.click();
            VUtils.waitFor(5);
            LOGGER.info("the tab" + value + "is clicked " + element.getText());

            //VoyantaDriver.getTableCellByText(value).click();
        } else {
            VoyantaDriver.getTableCellByText(value).click();
        }
    }


    public void closeExportDialogBox() {
        if (VUtils.isElementPresentWithLocator(pageContainer.getDialogBox().getLocator(), pageContainer.getDialogBox().getValue()))
            VUtils.getElement(pageContainer.getDialogBox().getLocator(), pageContainer.getDialogBox().getValue()).click();
    }

    public void selectAllTenancyFilters(String reportName) {
        if (reportName.equalsIgnoreCase("Tenancy Schedule")) {

            List<WebElement> list = VoyantaDriver.findElements(By.xpath("//div[@id='Document\\CT_TenancySchedule']//div[@class='QvListbox']"));
            LOGGER.info("there are " + list.size() + " elements");

            if (list.size() == 2)
                try {
                    new VoyantaElementImpl(list.get(1)).rightclick();
                } catch (MoveTargetOutOfBoundsException e) {
                    new VoyantaElementImpl(list.get(0)).rightclick();
                }
            else {
                try {
                    new VoyantaElementImpl(list.get(0)).rightclick();
                } catch (MoveTargetOutOfBoundsException e) {
                    new VoyantaElementImpl(list.get(1)).rightclick();
                }
            }
        } else if (reportName.equalsIgnoreCase("Loan Schedule Report")) {

            List<WebElement> list = VoyantaDriver.findElements(By.xpath("//div[@id='Document\\CT_LoanSchedule']//div[@class='QvListbox']"));
            LOGGER.info("there are " + list.size() + " elements");
            int index;
            if (list.size() == 2) {
                index = 1;
            } else {
                index = 2;
            }
            // new VoyantaElementImpl(VoyantaDriver.findElement(By.xpath("//div[@id='Document\\CT_LoanSchedule']/div[@class='QvContent']//div[@class='QvFrame'][2]//div[@class='QvContent']//div[@class='QvListbox']"))).rightclick();
            new VoyantaElementImpl(list.get(index)).rightclick();
            //new VoyantaElementImpl(VoyantaDriver.findElement(By.xpath("//div[@id='34']/div[@class='QvContent']/div[@class='QvListbox']")))).rightclick();
            //  new VoyantaElementImpl(VoyantaDriver.findElement(By.xpath("//div[@id='34']"))).rightclick();
        }
        if ((VUtils.isElementPresent(By.xpath("//span[text()='Select All']")))) {
            VoyantaDriver.getSpanByText("Select All").click();
        }
        VUtils.waitFor(5);
        // VoyantaDriver.getCurrentDriver().findElement(By.xpath("//div[@id='Document\\CT_TenancySchedule']//div[@class='QvListbox']"))
    }

    public void expandLoanScheduleReport() {
        int attempt = 0;
        while (attempt < 2) {
            try {
                wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.getDivByTitleAndText("Debt Facility")));
                (new VoyantaElementImpl(VoyantaDriver.getDivByTitleAndText("Debt Facility"))).rightclick();
                if ((VUtils.isElementPresent(By.xpath("//span[text()='Expand all']")))) {
                    VoyantaDriver.getSpanByText("Expand all").click();
                }
                break;
            } catch (StaleElementReferenceException e) {
                LOGGER.info("Unable to expand Debt Facility, trying again...");
                VUtils.waitFor(5);
                attempt++;
            }
        }
        VUtils.waitFor(3);
    }

    public void selectOption(String option) {
        //  pageContainer.optionSelector.click();
        VoyantaDriver.select(pageContainer.optionSelector, option);
        VUtils.waitFor(5);
    }

    public void selectBasis(String basis) {
        Point locationPoint;
        WebElement element = VoyantaDriver.getDivByTitleAndText(basis);
        locationPoint = element.getLocation();
        LOGGER.info("Location Point X: " + locationPoint.x + " Location Point Y: " + locationPoint.y);
        Actions builder = new Actions(VoyantaDriver.getCurrentDriver());
        builder.click(element).build().perform();
//            VoyantaDriver.getDivByTitleAndText(basis).click();
        VUtils.waitFor(5);
    }

    public void isRowInCurrentSelections(String field, String value) {
        switch (field) {
            case "Currency":
                VerifyUtils.True(pageContainer.currentSelectionsCurrency.getText().equals(value));
                break;
            case "Area Measure":
                VerifyUtils.True(pageContainer.currentSelectionsMeasurementUnit.getText().equals(value));
                break;
        }
    }
}
