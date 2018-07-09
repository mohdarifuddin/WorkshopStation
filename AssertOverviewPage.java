package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.AssertOverviewPageContainer;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

/**
 * Created by reshma on 28/10/2015.
 */
public class AssertOverviewPage extends AbstractVoyantaPage {
    static Logger LOGGER = Logger.getLogger(BuildingPage.class);
    static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 30);

    AssertOverviewPageContainer pageContainer = AssertOverviewPage.getDataContainer(AssertOverviewPageContainer.class);

    public String getAssertOverviewPageTitle() {
        return VoyantaDriver.getCurrentDriver().getTitle();
    }

    public String getBuildingNameFromHeaderElement() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.h1OnAsserOverviewPage));
        return pageContainer.h1OnAsserOverviewPage.getText();
    }

    public void clickOnValueHistoryTableLink() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.valueHistoryTableLinkElement));
        pageContainer.valueHistoryTableLinkElement.click();

        //VUtils.waitFor(5);
    }

    public void exportToExcel() {

        LOGGER.info("***** Exporting Value History Report *****");
        //VUtils.waitFor(2);
        wait.until(ExpectedConditions.visibilityOf(pageContainer.iframe));
        VoyantaDriver.getCurrentDriver().switchTo().frame(pageContainer.iframe);
        //VUtils.waitFor(2);
        //verify that report is displayed on the page
        // VUtils.waitForElement(VoyantaDriver.findElement(By.xpath("")));
        isValueHistoryReportDisplayed();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.exportToExcelCSS));
        WebElement exportElement = pageContainer.exportToExcelCSS;
        exportElement.click();
        LOGGER.info("***** Exported Value history Report *****");
        //wait.until(ExpectedConditions.)
        //VUtils.waitFor(5);
    }

    public void isValueHistoryReportDisplayed() {

        Assert.assertFalse("No Reports available message on the value history table page", VUtils.isElementPresent(By.cssSelector("#nodata>div")));
    }
}
