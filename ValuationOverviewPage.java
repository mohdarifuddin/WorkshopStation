package voyanta.ui.pageobjects;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.ValuationOverviewContainers;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.List;
import java.util.Map;

public class ValuationOverviewPage extends AbstractVoyantaPage {
    private WebDriverWait wait;
    private Logger LOGGER = Logger.getLogger(ValuationOverviewPage.class);
    private ValuationOverviewContainers valuationOverviewContainers;

    public ValuationOverviewPage() {
        this.wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 60);
        valuationOverviewContainers = ValuationOverviewPage.getDataContainer(ValuationOverviewContainers.class);
    }

    public void clearFilters() {
        valuationOverviewContainers.filterToggle.click();

        if (!VUtils.elementExists(valuationOverviewContainers.filterContainerBox))
            wait.until(ExpectedConditions.visibilityOf(valuationOverviewContainers.filterContainerBox));

        valuationOverviewContainers.filterClearButton.click();
        VUtils.waitFor(2);
    }

    public void selectFiltersData(final Map<String, String> data) {
        String value;

        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                if (key.equals("Quarter Start")) {
                    value = data.get(key);
                    selectQuarterStart(value);
                } else if (key.equals("Quarter End")) {
                    value = data.get(key);
                    selectQuarterEnd(value);
                }

            } else {
                LOGGER.info("Ignore the filter as the data is not given in Scenario : " + key);
            }
        }
    }

    private void selectQuarterEnd(final String value) {
        WebElement end = valuationOverviewContainers.quarterFilter.findElement(By.cssSelector("div.filter-content")).
                findElement(By.xpath("//div[2][@class='calendar-field']")).findElement(By.cssSelector("div.input-field")).findElement(By.cssSelector("span.value"));

        end.click();

        List<WebElement> endQuarter = valuationOverviewContainers.quarterFilter.findElement(By.xpath("//*[@class = 'drs-list']")).
                findElements(By.xpath("//ul[@class='clearfix']/li"));

        for (WebElement eq : endQuarter) {
            if (eq.getText().equals(value)) {
                LOGGER.info("Selecting Quarter End : " + eq.getText());
                eq.click();
                VUtils.waitFor(1);
                break;
            }
        }

    }

    private void selectQuarterStart(final String value) {
        WebElement start = valuationOverviewContainers.quarterFilter.findElement(By.cssSelector("div.filter-content")).
                findElement(By.cssSelector("div.calendar-field")).findElement(By.cssSelector("div.input-field")).findElement(By.cssSelector("span.value"));

        start.click();

        List<WebElement> quarter = valuationOverviewContainers.quarterSelector.findElement(By.xpath("//*[@class = 'drs-list']")).
                findElements(By.xpath("//ul[@class='clearfix']/li"));


        for (WebElement e : quarter) {
            if (e.getText().equals(value)) {
                LOGGER.info("Selecting Quarter Start : " + e.getText());
                e.click();
                VUtils.waitFor(1);
                break;
            }
        }
    }

    public void selectApplyFilters() {
        valuationOverviewContainers.applyFilterButton.click();
        VUtils.waitFor(2);
    }

    public void selectExportToExcel() {
        if (VUtils.elementExists(valuationOverviewContainers.loadingTable)) {
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading-message")));

            LOGGER.info("Exporting Report...");
            valuationOverviewContainers.exportToExcelLink.click();
            VUtils.waitFor(5);

            if (!VUtils.isElementPresent(valuationOverviewContainers.reportTable))
                this.wait.until(ExpectedConditions.visibilityOf(valuationOverviewContainers.reportTable));
        }
    }
}
