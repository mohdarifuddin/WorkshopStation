package voyanta.ui.pageobjects;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.TenancySchedulePageContainer;
import voyanta.ui.utils.FiltersObject;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class TenancySchedulePage extends AbstractVoyantaPage {

    private static final String url = PropertiesLoader.getProperty("ui_url") + "report/";
    public static Logger LOGGER = Logger.getLogger(ReportsPage.class);
    static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(),10);

    TenancySchedulePageContainer pageContainer;

    public TenancySchedulePage() {
        pageContainer = TenancySchedulePage.getDataContainer(TenancySchedulePageContainer.class);
        LOGGER.info("REPORT 2.0 -> User arrives on Report 2.0 page");
    }

    public void applyMeasures(Map<String, String> data) {
        VUtils.waitFor(2);
        String value;
        pageContainer.editDisplay.click();
        Map<String, WebElement> result =
                pageContainer.measuresList.stream().collect(Collectors.toMap(WebElement::getText, c -> c));
        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                value = data.get(key);
                try {
                    if(value.toLowerCase().equals("select all")) {
                        result.get(key.toUpperCase()).click();
                        VUtils.waitFor(5);
                        pageContainer.measures.findElement(By.xpath("//label[contains(text(),'"+ key + "')]")).findElement(By.linkText(value)).click();
                        VUtils.waitFor(3);
                    } else {
                        pageContainer.editDisplaySeachEditBox.sendKeys(value);
                        VUtils.waitFor(2);
                        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.measures.findElement(By.linkText(value))));
                        if (!pageContainer.measures.findElement(By.linkText(value)).getAttribute("class").equals("selected")) {
                            pageContainer.measures.findElement(By.linkText(value)).click();
                        }
                        pageContainer.editDisplaySeachEditBox.clear();
                    }
                }catch (Exception e) {
                    LOGGER.error(value + " for : "+ key + " not found in measures list");
                }
            } else
                LOGGER.info("Ignore the Measures as the value is not given in scenario:" + key);
        }
    }

    public void selectFilter(Map<String, String> data) {
        final String[] value = new String[1];
        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                value[0] = data.get(key);
                VUtils.setReportFilter(key.replaceAll("\\s+", "").toUpperCase(), value[0]);
            }
        }
    }

    public void applyFilter(){
        pageContainer.applyFilter.click();
    }

    public void selectView(Map<String, String> data){
        VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());
        wait.until(ExpectedConditions.visibilityOf(pageContainer.filters));
        wait.until(ExpectedConditions.visibilityOf(pageContainer.viewAs));
        pageContainer.viewAs.click();
        switch (data.get("View as")) {
            case "FlatView":
                pageContainer.flatView.click();
                LOGGER.info("Flat View selected.");
                LOGGER.info("REPORT 2.0 -> Flat View selected.");
                break;
            case "GroupedWithSubtotals":
                pageContainer.groupedWithSubtotals.click();
                LOGGER.info("REPORT 2.0 -> Grouped View with Subtotals selected.");
                break;
            case "GroupedWithoutSubtotals":
                pageContainer.groupedWithoutSubtotals.click();
                LOGGER.info("REPORT 2.0 -> Grouped View without Subtotals selected.");
                break;
            default:
                pageContainer.flatView.click();
        }
    }

    public void selectView(String view){
        VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.defaultView));
        if(!pageContainer.defaultView.getText().equals(view)){
            pageContainer.defaultView.click();
            VUtils.waitFor(2);
            Map<String, WebElement> views =
                    pageContainer.defaultViewItemsList.findElements(By.tagName("li")).stream().collect(Collectors.toMap(WebElement::getText, c -> c));
            if(views.keySet().contains(view))
                views.get(view).click();
            else
                LOGGER.error(view + " not found in view list");
            VUtils.waitFor(3);
        }
    }

    public void downloadReport(){
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.reportDownloadLink));
        VUtils.waitFor(5);
        pageContainer.reportDownloadLink.click();
        LOGGER.info("REPORT 2.0 -> User clicks Download link.");
    }

    public void selectFilter(){
        VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());
        (new WebDriverWait(VoyantaDriver.getCurrentDriver(),30)).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(pageContainer.filters));
        pageContainer.filters.click();
    }

    public void resetFilters(){
        if (!pageContainer.resetAll.getAttribute("class").contains("reset-button disabled"))
            pageContainer.resetAll.click();
    }

    public void goToTenancySchedule2(){
        VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());
        pageContainer.reportingLink.click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.tenancySchedule2Link));
        pageContainer.tenancySchedule2Link.click();
        LOGGER.info("REPORT 2.0 -> User arrives on Tenancy Schedule 2.0 report.");
        VUtils.waitFor(1);
    }

    public void validateNoReportsErrorMessage(String message){
        if(pageContainer.noReportAvailableMessage.getText().contains(message)){
            LOGGER.info("Error message validated");
        }else{
            LOGGER.error("Invalid message: "+ pageContainer.noReportAvailableMessage.getText());
        }
    }

    public void DeselectAll() {
        VUtils.waitFor(2);
        pageContainer.editDisplay.click();
        Map<String, WebElement> result1 =
                pageContainer.measuresList.stream().collect(Collectors.toMap(WebElement::getText, c -> c));
        result1.forEach((k,v) -> {
            if (!result1.get(k).equals("")) { try {
                    result1.get(k.toUpperCase()).click(); VUtils.waitFor(5);
                    pageContainer.measures.findElement(By.xpath("//label[contains(text(),'"+ WordUtils.capitalizeFully(k) + "')]")).findElement(By.linkText("Select All")).click(); VUtils.waitFor(3);
                    pageContainer.measures.findElement(By.xpath("//label[contains(text(),'"+ WordUtils.capitalizeFully(k) + "')]")).findElement(By.linkText("Deselect All")).click();
                LOGGER.info("REPORT 2.0 -> User selects 'Select All' for measure group :: " + WordUtils.capitalizeFully(k));
                }catch (Exception e) { LOGGER.error("Error while Deselecting item(s) for "+ k ); }
            } else { LOGGER.info("Ignore the Measures as the value is not given in scenario:" + k);} });
        pageContainer.editDisplay.click();
    }

    public void ValidateFilterAssetCount(String filterValue, String filterName, String expectedCount) {
        String s = "not found";
        FiltersObject filter = new FiltersObject();
        filter.setSelectValue(false);
        switch (filterName) {
            case "Asset":
                filter.setAsset(filterValue);
                break;

            case "Sector":
                filter.setSector(filterValue);
                break;

            case "Asset Manager":
                filter.setAssetManager(filterValue);
                break;

            case "Asset Tag":
                filter.setAssetTag(filterValue);
                break;

            case "Tenant":
                filter.setTenant(filterValue);
                break;

            case "Benchmark Region":
                filter.setBenchmarkRegion(filterValue);
                break;
        }

        s = filter.getFieldCount();
        LOGGER.info("The filter asset count value for : " + filterName + " is: " + s);
        Assert.assertTrue("Expected Asset Filter count was: " + expectedCount + " but was found to be: " + s, s.equalsIgnoreCase(expectedCount));
        Actions actions = new Actions(VoyantaDriver.getCurrentDriver());
        actions.click().perform();
    }

    public void ValidateNoAssetIsPresent(String filterValue, String filterName) {
        boolean isDropDownPresent = true;
        FiltersObject filter = new FiltersObject();
        filter.setSelectValue(false);
        switch (filterName) {
            case "Asset":
                filter.setAsset(filterValue);
                isDropDownPresent = VUtils.isElementPresent(pageContainer.assetDropdownList);
                break;

            case "Sector":
                filter.setSector(filterValue);
                isDropDownPresent = VUtils.isElementPresent(pageContainer.sectorDropdownList);
                break;

            case "Asset Manager":
                filter.setAssetManager(filterValue);
                isDropDownPresent = VUtils.isElementPresent(pageContainer.assetManagersDropdownList);
                break;

            case "Asset Tag":
                filter.setAssetTag(filterValue);
                isDropDownPresent = VUtils.isElementPresent(pageContainer.assetTagDropdownList);
                break;

            case "Tenant":
                filter.setTenant(filterValue);
                isDropDownPresent = VUtils.isElementPresent(pageContainer.tenantDropdownList);
                break;

            case "Benchmark Region":
                filter.setBenchmarkRegion(filterValue);
                isDropDownPresent = VUtils.isElementPresent(pageContainer.benchmarkDropdownList);
                break;
        }
        Actions actions = new Actions(VoyantaDriver.getCurrentDriver());
        actions.click().perform();


        LOGGER.info("The filter asset count value for : " + filterName + " is: " + filterValue);
        Assert.assertFalse("Unexpected Asset Filter dropdown was fund for this fitler: " + filterName + " and value: " + filterValue, isDropDownPresent );

    }
}