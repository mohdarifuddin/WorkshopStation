package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.AccountGroupsPageContainer;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.List;
import java.util.Map;

/**
 * Created by arifuddin.mohd on 11/7/2017.
 */
public class AccountGroupsPage extends AbstractVoyantaPage {

    public static Logger LOGGER = org.apache.log4j.Logger.getLogger(AccountGroupsPage.class);
    static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 20);
    WebDriver driver = VoyantaDriver.getCurrentDriver();
    AccountGroupsPageContainer accountGroupsPageContainer;

    public AccountGroupsPage() {
        accountGroupsPageContainer = AccountGroupsPage.getDataContainer(AccountGroupsPageContainer.class);
        VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
    }

    public void selectAccountGroupsReport() {

        (new WebDriverWait(driver, 120)).until(ExpectedConditions.elementToBeClickable(accountGroupsPageContainer.portFolioDropdown)).click();
        List<WebElement> moduleList = accountGroupsPageContainer.reportDropdown.findElement(By.tagName("ul")).findElements(By.tagName("li"));
        for (WebElement e : moduleList) {
            if (e.getText().equals("Financial")) {
                e.click();
                break;
            }
        }
        (new WebDriverWait(driver, 120)).until(ExpectedConditions.elementToBeClickable(accountGroupsPageContainer.accountGroups));
        List<WebElement> sliderList = accountGroupsPageContainer.ReportTabs.findElement(By.className("tabs-menu")).findElements(By.tagName("li"));
        for (WebElement e : sliderList) {
            if (e.getText().contains("Account Groups")) {
                e.click();
                break;
            }
        }
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#loader")));

    }

    public void clearFiltersTab() {
        try {
            //(new WebDriverWait(driver, 120)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.filterToggle));
            accountGroupsPageContainer.filterToggle.click();
            LOGGER.info("Clicking on the filter dropdown");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            e.getMessage();
        }
    }

    private void selectQuarterFilter() {
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.elementToBeClickable(accountGroupsPageContainer.monthQuarterYearSelectionFilter));
        accountGroupsPageContainer.monthQuarterYearSelectionFilter.click();

        List<WebElement> elements = accountGroupsPageContainer.monthQuarterYearSelectionListFilter;
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfAllElements(elements));
        for (WebElement e : elements) {
            if (e.getText().equals("Quarter")) {
                LOGGER.info("Selecting Quarter");
                e.click();
                break;
            }
        }
    }

    private void selectMonthQuarterYearEndFilter(final String values) {
        String[] value = values.split(",");
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectMonthQuarterYearEndFilter));
        WebElement end = accountGroupsPageContainer.selectMonthQuarterYearEndFilter;
        end.click();

        WebElement year = accountGroupsPageContainer.dateSelector;
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(accountGroupsPageContainer.dateSelector));
        year.click();
        List<WebElement> elements = accountGroupsPageContainer.dateSelectorlist;
        for (WebElement e : elements) {
            if (e.getText().equals(value[0])) {
                LOGGER.info("Selecting End Year : " + e.getText());
                e.click();
                break;
            }
        }

        WebElement month = accountGroupsPageContainer.dateQuarterSelector;
        for (WebElement e : month.findElements(By.xpath("ul/li"))) {
            LOGGER.info("values:" + value[1].trim() + ", " + value[2].trim());
            if (e.getText().equals(value[1].trim() + ", " + value[2].trim())) {
                LOGGER.info("Selecting End Month/Quarter : " + e.getText());
                e.click();
                break;
            }
        }
    }

    private void selectCurrencyFilter(String value) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectCurrency));
        List<WebElement> elements = accountGroupsPageContainer.selectCurrencyList;

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting Currency : " + e.getText());
                e.click();
                break;
            }
        }
    }

    private void selectMonthQuarterYearStartFilter(final String values) {
        String[] value = values.split(",");
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectMonthQuarterYearStartFilter));
        WebElement start = accountGroupsPageContainer.selectMonthQuarterYearStartFilter;

        start.click();
        WebElement year = accountGroupsPageContainer.dateSelector;
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(accountGroupsPageContainer.dateSelector));
        year.click();
        List<WebElement> elements = accountGroupsPageContainer.dateSelectorlist;
        for (WebElement e : elements) {
            if (e.getText().equals(value[0])) {
                LOGGER.info("Selecting Start Year : " + e.getText());
                e.click();
                break;
            }
        }

        WebElement month = accountGroupsPageContainer.dateQuarterSelector;
        for (WebElement e : month.findElements(By.xpath("ul/li"))) {
            LOGGER.info("values:" + value[1].trim() + ", " + value[2].trim());
            if (e.getText().equals(value[1].trim() + ", " + value[2].trim())) {
                LOGGER.info("Selecting Start Month/Quarter : " + e.getText());
                e.click();
                break;
            }
        }
    }

    private void selectAccountGroupFilter(String value) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectAccountGroups));
        List<WebElement> elements = accountGroupsPageContainer.selectAccountGroupsList;

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting Account Group : " + e.getText());
                e.click();
                break;
            }
        }
    }

    private void selectAccountBookNameFilter(String value) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectAccountGroupsBook));
        List<WebElement> elements = accountGroupsPageContainer.selectAccountGroupsBookList;

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting Account Book Name : " + e.getText());
                e.click();
                break;
            }
        }
    }

    private void selectReferenceDateFilter(String value) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectReferenaceDate));
        List<WebElement> elements = accountGroupsPageContainer.selectReferenaceDateList;

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting Reference Date : " + e.getText());
                e.click();
                break;
            }
        }
    }

    private void selectAssetTagsFilter(String value) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectAssetTags));
        List<WebElement> elements = accountGroupsPageContainer.selectAssetTagsList;

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting Asset Tags : " + e.getText());
                e.click();
                break;
            }
        }
    }

    private void selectDebtFacilityFilter(String value) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectDebtFacility));
        List<WebElement> elements = accountGroupsPageContainer.selectDebtFacilityList;

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting Asset Tags : " + e.getText());
                e.click();
                break;
            }
        }
    }

    private void selectAssetFilter(String value) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectAssetFilter));
        List<WebElement> elements = accountGroupsPageContainer.selectAssetFilterList;

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting Asset : " + e.getText());
                e.click();
                break;
            }
        }
    }
    private void selectChartOfAccountFilter(String value) {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.selectChartOfAccountsFilter));
        List<WebElement> elements = accountGroupsPageContainer.selectChartOfAccountsFilterList;

        for (WebElement e : elements) {
            if (e.getText().equalsIgnoreCase(value)) {
                LOGGER.info("Selecting Chart Of Accounts : " + e.getText());
                e.click();
                break;
            }
        }
    }

    private void selectOwnershipFilter(final String value) {
        String label[] = value.split(",");

        for(int i=0; i<label.length; i++) {
            WebElement search = accountGroupsPageContainer.ownershipSearch;
            search.click();
            WebElement searchBox = accountGroupsPageContainer.ownershipSearchInput;
            searchBox.sendKeys(label[i].trim());
            VUtils.waitFor(2);
            List<WebElement> labels = accountGroupsPageContainer.hierarchylists;
            for (WebElement e : labels) {
                if (e.getText().equalsIgnoreCase(label[i].trim())) {
                    LOGGER.info("Selecting the Ownership: " + e.getText());
                    e.click();
                    VUtils.waitFor(1);
                    break;
                }
            }
            searchBox.clear();
        }
    }


    public void selectApplyFilters() {
        try {
            (new WebDriverWait(driver, 60)).until(ExpectedConditions.elementToBeClickable(accountGroupsPageContainer.applyFilterButton));
            accountGroupsPageContainer.applyFilterButton.click();
            LOGGER.info("Clicking on filter dropdown");
        } catch (Exception e) {
            VUtils.waitFor(60);
            accountGroupsPageContainer.applyFilterButton.click();
        }
    }

    public static void waitForAccountGroupsJQuery(WebDriver driver) {
        (new WebDriverWait(driver, 120)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                JavascriptExecutor js = (JavascriptExecutor) d;
                LOGGER.info("The value for jquery.active is : " + js.executeScript("return jQuery.active"));
                return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
            }
        });
    }


    public void selectFiltersData(final Map<String, String> data) {
        String value;
        String Qstar = null;

        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                switch (key) {
                    case "Quarter Start":
                        value = data.get(key);
                        Qstar = value;
                        selectQuarterFilter();
                        selectMonthQuarterYearStartFilter(value);
                        break;
                    case "Quarter End":
                        value = data.get(key);
                        selectMonthQuarterYearEndFilter(value);
                        if (driver.findElement(By.cssSelector("span.error-sign")).isDisplayed())
                            selectMonthQuarterYearStartFilter(Qstar);
                        break;
                    case "Currency":
                        value = data.get(key);
                        selectCurrencyFilter(value);
                        break;
                    case "Account Group":
                        value = data.get(key);
                        selectAccountGroupFilter(value);
                        break;
                    case "Accounting Book Name":
                        value = data.get(key);
                        selectAccountBookNameFilter(value);
                        break;
                    case "Reference Date":
                        value = data.get(key);
                        selectReferenceDateFilter(value);
                        break;
                    case "Asset Tags":
                        value = data.get(key);
                        selectAssetTagsFilter(value);
                        break;
                    case "Debt Facility":
                        value = data.get(key);
                        selectDebtFacilityFilter(value);
                        break;
                    case "Asset":
                        value = data.get(key);
                        selectAssetFilter(value);
                        break;
                    case "Chart Of Accounts":
                        value = data.get(key);
                        selectChartOfAccountFilter(value);
                        break;
                    case "Ownership":
                        value = data.get(key);
                        selectOwnershipFilter(value);
                        break;
                }
                VUtils.waitFor(1);
            } else {
                LOGGER.info("Ignore the filter as the data is not given in Scenario : " + key);
            }
        }

    }

    public void waitForClearButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(accountGroupsPageContainer.clearButton));
    }

    public void waitReportGrid() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.reportGrid));
    }

    public void selectAccountGroupsShowColumns() {
        int i = 1;
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.showColumns));
        accountGroupsPageContainer.showColumns.click();
        LOGGER.info("clicking on the showcolumns dropdown");
        List<WebElement> elements = accountGroupsPageContainer.showColumnsList;
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.visibilityOfAllElements(elements));
        LOGGER.info("Total elements: " + elements.size());
        for (WebElement e : elements) {
            if (!e.findElement(By.tagName("a")).getAttribute("class").equals("selected")) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", e.findElement(By.tagName("a")));
                LOGGER.info("Selecting Columns no: " + i + "..." + e.getText());
                e.findElement(By.tagName("a")).click();
            } else {
                LOGGER.info("Default 2 Columns are already selected " + e.getText());
            }
            i++;
        }
    }

    public void applyFilterAfterSelection() {
        VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());
        selectApplyFilters();
        driver.navigate().refresh();

        LOGGER.info("The Account Groups grid enabled status is  : " + accountGroupsPageContainer.reportGrid.isEnabled());
        Assert.assertEquals(true, accountGroupsPageContainer.reportGrid.isEnabled());
        waitForAccountGroupsJQuery(driver);
    }

    public void exportToExcel(String report) {

        (new WebDriverWait(driver, 120)).until(ExpectedConditions.elementToBeClickable(accountGroupsPageContainer.sendtoExcel));
        accountGroupsPageContainer.sendtoExcel.click();
        VUtils.waitFor(5);
    }

    public void checkExcelReportGenerated() {
        VerifyUtils.True(isExcelReportGenerated());
    }

    public boolean isExcelReportGenerated() {
        VUtils.waitForElement(accountGroupsPageContainer.downloadConfirmBox);
        return accountGroupsPageContainer.downloadConfirmBox.isDisplayed();
    }

    public void deselectShowColumns() {
        String value;
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.visibilityOf(accountGroupsPageContainer.showColumns));
        accountGroupsPageContainer.showColumns.click();
        LOGGER.info("clicking on the showcolumns dropdown");
        List<WebElement> elements = accountGroupsPageContainer.showColumnsList;
        for (WebElement e : elements) {
            if (e.findElement(By.tagName("a")).getAttribute("class").equals("selected")) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", e.findElement(By.tagName("a")));
                e.findElement(By.tagName("a")).click();
            }
        }

    }

    public void showAccountGroupsColumns(final Map<String, String> data) {

        String value;
        List<WebElement> elements = accountGroupsPageContainer.showColumnsList;
        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                value = data.get(key);
                LOGGER.info("Value is: " + value);
                (new WebDriverWait(driver, 60)).until(ExpectedConditions.visibilityOfAllElements(elements));
                LOGGER.info("Total elements: " + elements.size());

                for (WebElement e : elements)
                    if (e.getText().equals(value)) {
                        e.findElement(By.tagName("a")).click();
                        LOGGER.info("clicking on element: " + e.getText());
                    }
            }
        }
    }

    public void validateErrorMessage(String expErrMessage) {
        String actErrMessage = driver.findElement(By.xpath("//div[@class='exception-message']//div")).getText();
        VUtils.waitFor(2);
        LOGGER.info("ActErrMessage: " + actErrMessage.replaceAll("\\s+", ""));
        LOGGER.info("ExpErrMessage: " + expErrMessage.replaceAll("\\s+", ""));

        if (actErrMessage.replaceAll("\\s+", "").equals(expErrMessage.replaceAll("\\s+", ""))) {
            Assert.assertTrue("Successfully Validate Actual Text " + actErrMessage.replaceAll("\\s+", "") + "With Expected " + expErrMessage.replaceAll("\\s+", ""), true);
            LOGGER.info("Successfully Validating the Error Message " + actErrMessage.replaceAll("\\s+", ""));
        } else {
            Assert.assertFalse("Fail to Validate Actual Text " + actErrMessage.replaceAll("\\s+", "")+"With Expected " + expErrMessage.replaceAll("\\s+", ""), true);
            LOGGER.info("Fail to Validate Actual text" + actErrMessage.replaceAll("\\s+", "")+"with expected " + expErrMessage.replaceAll("\\s+", ""));
        }
    }
}