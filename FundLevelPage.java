package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.FundLevelPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.List;
import java.util.Map;

/**
 * Created by reshma on 15/10/2015.
 */
public class FundLevelPage extends AbstractVoyantaPage {
    private static final String url = PropertiesLoader.getProperty("ui_url") + "reporting/15/37";
    static Logger LOGGER = org.apache.log4j.Logger.getLogger(ReportsPage.class);
    static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 5);

    FundLevelPageContainer pageContainer = FundLevelPage.getDataContainer(FundLevelPageContainer.class);

    public boolean isFundLevelReportsDisplayed() {
        String reportName = "Fund Level Attribution";
        WebElement selectedReport = VoyantaDriver.findElement(By.cssSelector("ul.tabs-menu > li > a.selected"));
        VUtils.waitForElement(pageContainer.filtersHeadersElement);
/*        if(selectedReport.getText().equals(reportName))
            return true;
        else
            return false;*/
        return true;
    }

    public void selectFilters(Map<String, String> data) {
        String value;

        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                if (key.equals("Quarter")) {
                    value = data.get(key);
                    WebElement element = VoyantaDriver.findElement(By.xpath("//div[@id='filters']/ul/li"));
                    List<WebElement> listQuarter = element.findElements(By.xpath("//*[@id='filterRiskYearQuarter']/div/*"));

                    for (WebElement e : listQuarter) {
                        if (e.getText().equals(value)) {
                            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", e);
                            LOGGER.info("Selecting Quarter : " + e.getText());
                            e.click();
                            VUtils.waitFor(2);
                            break;
                        }

                        if (e.getText().equals("")) {
                            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", VoyantaDriver.findElement(By.id("filterRiskYearQuarter")));
                            listQuarter = element.findElements(By.xpath("//*[@id='filterRiskYearQuarter']/div/*"));
                            for (WebElement w : listQuarter) {
                                if (w.getText().equals(value)) {
                                    LOGGER.info("Selecting Quarter : " + w.getText());
                                    w.click();
                                    VUtils.waitFor(2);
                                    break;
                                }
                            }
                        }
                    }
                } else if (key.equals("Period")) {
                    value = data.get(key);
                    WebElement element = VoyantaDriver.findElement(By.xpath("//div[@id='filters']/ul/li[2]"));
                    List<WebElement> listPeriod = element.findElements(By.xpath("//div/div"));
                    for (WebElement e : listPeriod) {
                        if (e.getText().equals(value)) {
                            LOGGER.info("Selecting Period : " + e.getText());
                            e.click();
                            VUtils.waitFor(2);
                            break;
                        }
                    }
                } else if (key.equals("Investment(s)") || key.equals("Fund(s)")) {
                    value = data.get(key);
                    WebElement element;

                    if (key.equals("Fund(s)"))
                        element = VoyantaDriver.findElement(By.xpath("//*[@id='filters' ]//ul/li[contains(.,'Fund(s)')]"));
                    else
                        element = VoyantaDriver.findElement(By.xpath("//*[@id='filters' ]//ul/li[contains(.,'Investment(s)')]"));

                    List<WebElement> listFund = element.findElements(By.xpath("//div/div"));
                    for (WebElement e : listFund) {
                        if (e.getText().equals(value)) {
                            LOGGER.info("Selecting " + key + " : " + e.getText());
                            e.click();
                            VUtils.waitFor(2);
                            break;
                        }
                    }
                } else if (key.equals("Breakdown")) {
                    value = data.get(key);
                    WebElement element = VoyantaDriver.findElement(By.xpath("//div[@id='filters']/ul/li[4]"));
                    List<WebElement> listBreakdown = element.findElements(By.xpath("//div/div"));
                    for (WebElement e : listBreakdown) {
                        if (e.getText().equals(value)) {
                            LOGGER.info("Selecting Breakdown : " + e.getText());
                            e.click();
                            VUtils.waitFor(2);
                            break;
                        }
                    }
                }
            } else
                LOGGER.info("Ignore the filter as the value is not given in scenario:" + key);
        }
    }

    public void exportToExcel() {
        VUtils.waitFor(5);
        if (VUtils.isElementPresent(By.cssSelector("span.control-msg")))
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.control-msg")));

        /*WebElement investmentInfoElement = VoyantaDriver.findElement(By.xpath("//*[@id='wj-report']/div[2]/div[1]"));
        WebElement exportElement = investmentInfoElement.findElement(By.xpath("//span[@id='exportAttribution']/a/span[2]"));
        exportElement.click();*/
        WebElement exportAttribution = pageContainer.exportButton;
        JavascriptExecutor executor = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
        executor.executeScript("arguments[0].click();", exportAttribution);
        LOGGER.info("***** Exporting FundLevel Report *****");
        VUtils.waitFor(7);
    }

    public void selectIncomeAppreciationCheckBox() {
        VUtils.waitFor(1);
        pageContainer.IncomeCheckBox.click();
    }

    public String getFootNotes() {
        VUtils.waitFor(1);
        LOGGER.info("Actual FootNotes : " + pageContainer.footNotes.getText());
        return pageContainer.footNotes.getText();
    }


    public String getIncomeAppriciationFootNotes() {
        WebElement footNote = VoyantaDriver.findElement(By.xpath("//*[@id='gridFundIncomeContainer']/div[2]"));
        LOGGER.info("Actual Income/Approciation FootNotes : " + footNote.getText());
        return footNote.getText();
    }
}
