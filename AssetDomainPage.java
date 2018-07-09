package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.css.sac.ElementSelector;
import voyanta.ui.pagecontainers.AssetDomainPageContainer;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hiten.Parma on 26/01/2016.
 */
public class AssetDomainPage extends AbstractVoyantaPage {

    private WebDriver mDriver = VoyantaDriver.getCurrentDriver();
    private Logger LOGGER = Logger.getLogger(AssetDomainPage.class);
    private WebDriverWait wait;
    final int Max_Wait_Limit = 120;
    public AssetDomainPageContainer assetDomainPageContainer = AssetDomainPage.getDataContainer(AssetDomainPageContainer.class);


    //    @FindBy(how = How.XPATH, using = "//*[@id='canvas']//*[@class='title']")
    public WebElement reportTitle;

    public AssetDomainPage() {
        this.wait = new WebDriverWait(mDriver, 120);

        if (VUtils.isElementPresent(By.xpath("//*[@id='loader']"))) {
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        }
//        PageFactory.initElements(mDriver,AssetDomainPage.class);
    }

    public void selectFolder(String folderName) {
        WebElement element = mDriver.findElement(By.xpath("//*[@src='/reporting/jasperload']"));
        mDriver.switchTo().frame(element);
        WebElement expandFolder = mDriver.findElement(By.xpath("//*[@id='handler2']"));
        expandFolder.click();
        VUtils.waitFor(3);
        List<WebElement> list = mDriver.findElement(By.xpath("//*[@id='foldersTree']//*[@id='node2']")).findElement(By.xpath("//ul[@id='node2sub']")).findElements(By.xpath("//li[@class='folders node closed']"));

        for (WebElement e : list) {
            if (e.getText().equals(folderName)) {
                LOGGER.info("Selecting Folder : " + e.getText());
                e.findElement(By.tagName("p")).click();
                this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='nothingToDisplay']/div/div/p")));
                VUtils.waitFor(2);
                break;
            }
        }
    }

    public void selectReportFromList(String reportName) {
        WebElement reportsElement = mDriver.findElement(By.xpath("//*[@id='resultsContainer']"));
        List<WebElement> list = reportsElement.findElement(By.xpath("//ul[@id='resultsList']")).findElements(By.xpath("//*[@class='column two']"));
        for (WebElement e : list)
        {
            VUtils.waitFor(1);
            if (e.getText().equals(reportName))
            {
                LOGGER.info("Selecting Report : " + e.getText());
                wait.until(ExpectedConditions.visibilityOf(e.findElement(By.tagName("a"))));
                while(e.findElement(By.linkText(e.getText())).isDisplayed())
                {
                    try {
                        Coordinates coordinate=((Locatable)e).getCoordinates();
                        coordinate.inViewPort();
                        e.findElement(By.tagName("a")).click();
                        break;
                    } catch (WebDriverException w) {
                        e.findElement(By.tagName("a")).click();
                    }
                }
                VUtils.waitFor(3);
                return;
            }
        }
        VerifyUtils.fail("Given Report is Not Present : " + reportName);
    }

    public void waitForReportToGetLoad(String name) {
        LOGGER.info("Waiting for Report to be shown...");
        for (int i = 0; i < Max_Wait_Limit; i++) {
            try {
                if (mDriver.findElement(By.xpath("//*[@id='canvas']//*[@class='title']")).isDisplayed()) {
                    reportTitle = mDriver.findElement(By.xpath("//*[@id='canvas']//*[@class='title']"));

                    if (reportTitle.getText().equals(name)) {
                        VUtils.waitFor(3);
                        return;
                    }
                }
                VUtils.waitFor(1);
                LOGGER.info("Loading Report...");
            } catch (NoSuchElementException e) {
                LOGGER.info("waiting for 1 sec...");
                VUtils.waitFor(1);
            }
        }
    }

    public String getReportName() {
        return reportTitle.getText();
    }

    public void selectExport() {

        /*Waiting for Report Table to Get display*/
        if (!VUtils.isElementPresent(assetDomainPageContainer.reportTable))
            this.wait.until(ExpectedConditions.visibilityOf(assetDomainPageContainer.reportTable));

        /*Mouse Hover Over to Export Link and Wait for Drop down List to show*/
        WebElement element = assetDomainPageContainer.exportIcon;
        String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript(mouseOverScript,
                element);
        this.wait.until(ExpectedConditions.visibilityOf(assetDomainPageContainer.exportDropDownList));

        /*Selecting AS XLSX from Export DropDown List */
        List<WebElement> list = assetDomainPageContainer.exportDropDownList.findElements(By.xpath("//li[@class='leaf']"));
        for (WebElement e : list) {
            if (e.getText().equals("As XLSX")) {
                LOGGER.info("Exporting Report As XLSX ");
                String mainTab = mDriver.getWindowHandle();
                e.findElement(By.tagName("p")).click();
                int i = 0;

                /*Below code to Verify the Reports are downloading */
                Set s = mDriver.getWindowHandles();
                Iterator ite = s.iterator();
                while (ite.hasNext()) {
                    String newTab = ite.next().toString();
                    if (!newTab.contains(mainTab)) {
                        mDriver.switchTo().window(newTab);
                        LOGGER.info("Reports Are Downloading !!!");
                        mDriver.switchTo().window(mainTab);
                    }
                }

                /*WAITING FOR 40 SECONDS FOR REPORT TO GET DOWNLOAD*/
                do {
                    i++;
                    VUtils.waitFor(1);
                } while (i < 45);
                break;
            }
        }
    }

    public void clearFilters() {
        assetDomainPageContainer.filterToggle.click();
        if (!VUtils.elementExists(assetDomainPageContainer.filterContainerBox))
            wait.until(ExpectedConditions.visibilityOf(assetDomainPageContainer.filterContainerBox));
        assetDomainPageContainer.filterClearButton.click();
        VUtils.waitFor(2);
    }

    public void applyFilters() {
        VUtils.waitFor(2);
        VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());
        if (VUtils.isElementPresent(By.cssSelector("a.button.actionApply")))
            wait.until(ExpectedConditions.elementToBeClickable(assetDomainPageContainer.filterApplyButton));
        try {
            // code is jumping the gun !!!
            VUtils.waitFor(5);
            VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());
            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);", assetDomainPageContainer.filterApplyButton);
            assetDomainPageContainer.filterApplyButton.click();
        }catch(Exception e){
            VUtils.waitFor(5);
            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].click();", assetDomainPageContainer.filterApplyButton);
        }
         VUtils.waitFor(2);
         mDriver.navigate().refresh();
         LOGGER.info("Apply Filters");
    }

    public void selectFilterValues(final Map<String, String> data) {
        String value;

        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                if (key.equals("Month Start")) {
                    value = data.get(key);
                    selectMonthFilter();
                    selectMonthQuarterYearStartFilter(value);
                } else if (key.equals("Month End")) {
                    value = data.get(key);
                    selectMonthQuarterYearEndFilter(value);
                } else if (key.equals("Currency")) {
                    value = data.get(key);
                    selectCurrencyFilter(value);
                } else if (key.equals("Area Measurement Unit")) {
                    value = data.get(key);
                    selectAMUFilter(value);
                } else if (key.equals("Accounting Book Name")) {
                    value = data.get(key);
                    selectAccountBookNameFilter(value);
                }

            } else
                LOGGER.info("Ignore the filter as the value is not given in Scenario : " + key);
        }
    }

    private void selectAMUFilter(final String value) {
        List<WebElement> elements = assetDomainPageContainer.amuFilter.findElement(By.cssSelector("div.filter-content"))
                .findElements(By.xpath("ul/li"));

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting AMU : " + e.getText());
                e.click();
                VUtils.waitFor(1);
                break;
            }
        }
    }

    private void selectAccountBookNameFilter(final String value) {
        List<WebElement> elements = assetDomainPageContainer.abnFilter.findElement(By.cssSelector("div.filter-content"))
                .findElements(By.xpath("ul/li"));

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting ABN : " + e.getText());
                e.click();
                VUtils.waitFor(1);
                break;
            }
        }
    }

    private void selectCurrencyFilter(final String value) {
        List<WebElement> elements = assetDomainPageContainer.currencyFilter.findElement(By.cssSelector("div.filter-content"))
                .findElements(By.xpath("ul/li"));

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting Currency : " + e.getText());
                e.click();
                VUtils.waitFor(1);
                break;
            }
        }
    }

    private void selectMonthQuarterYearEndFilter(final String values) {
        String[] value = values.split(",");
        WebElement end = assetDomainPageContainer.monthQuarterYearFilter.findElement(By.cssSelector("div.filter-content")).
                findElement(By.xpath("//div[2][@class='calendar-field']")).findElement(By.cssSelector("div.input-field")).findElement(By.cssSelector("span.value"));

        end.click();
        WebElement year = assetDomainPageContainer.dateSelector.findElement(By.cssSelector("div.range-selector"));
        year.click();

        List<WebElement> elements = year.findElement(By.cssSelector("div.options")).findElements(By.xpath("ul/li"));
        for (WebElement e : elements) {
            if (e.getText().equals(value[0])) {
                LOGGER.info("Selecting End Year : " + e.getText());
                e.click();
                VUtils.waitFor(1);
                break;
            }
        }

        WebElement month = assetDomainPageContainer.dateSelector.findElement(By.cssSelector("div.drs-list"));
        for (WebElement e : month.findElements(By.xpath("ul/li"))) {
            if (e.getText().equals(value[1].trim())) {
                LOGGER.info("Selecting End Month : " + e.getText());
                e.click();
                VUtils.waitFor(1);
                break;
            }
        }
    }

    private void selectMonthQuarterYearStartFilter(final String values) {
        String[] value = values.split(",");
        WebElement start = assetDomainPageContainer.monthQuarterYearFilter.findElement(By.cssSelector("div.filter-content")).
                findElement(By.cssSelector("div.calendar-field")).findElement(By.cssSelector("div.input-field")).findElement(By.cssSelector("span.value"));
        start.click();
        WebElement year = assetDomainPageContainer.dateSelector.findElement(By.cssSelector("div.range-selector"));
        year.click();
        List<WebElement> elements = year.findElement(By.cssSelector("div.options")).findElements(By.xpath("ul/li"));
        for (WebElement e : elements)
        {
            if (e.getText().equals(value[0])) {
               ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);",e);
                LOGGER.info("Selecting Start Year : " + e.getText());
                e.click();
                VUtils.waitFor(1);
                break;
            }
        }

        WebElement month = assetDomainPageContainer.dateSelector.findElement(By.cssSelector("div.drs-list"));
        for (WebElement e : month.findElements(By.xpath("ul/li"))) {
            if (e.getText().equals(value[1].trim())) {
                LOGGER.info("Selecting Start Month : " + e.getText());
                e.click();
                VUtils.waitFor(1);
                break;
            }
        }
    }

    private void selectMonthFilter() {
        WebElement monthQuarterYearHeaderFilter = assetDomainPageContainer.monthQuarterYearFilter.findElement(By.cssSelector("div.mode-selector"));

        monthQuarterYearHeaderFilter.click();
        List<WebElement> elements = monthQuarterYearHeaderFilter.findElements(By.xpath("div/ul/li"));

        for (WebElement e : elements) {
            if (e.getText().equals("Month")) {
                LOGGER.info("Selecting Month ");
                e.click();
                break;
            }
        }
    }

}
