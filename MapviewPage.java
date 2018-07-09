package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.rmi.runtime.Log;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by arifuddin.mohd on 1/19/2018.
 */
public class MapviewPage extends AbstractVoyantaPage {

    WebDriver driver = VoyantaDriver.getCurrentDriver();
    static Logger LOGGER = Logger.getLogger(MapviewPage.class);
    MapViewPageContainer mapViewPageContainer;
    Actions act = new Actions(driver);

    public MapviewPage() {
        mapViewPageContainer = MapviewPage.getDataContainer(MapViewPageContainer.class);
        LOGGER.info("user goes to map view page");
    }

    public void selectApplyFilters() {

        VUtils.waitForJQuery(driver);
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(mapViewPageContainer.applyFilterButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", mapViewPageContainer.applyFilterButton);

    }

    public void dropdownAndClearFiltersTab() {

        (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(mapViewPageContainer.filterToggle));
        mapViewPageContainer.filterToggle.click();
        LOGGER.info("clicking filter toggle");
        VUtils.waitForJQuery(driver);
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(mapViewPageContainer.clearButton));
        act.moveToElement(driver.findElement(By.cssSelector("a.button.actionClear"))).click().build().perform();
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(mapViewPageContainer.applyFilterButton));

    }

    public void mapViewZoomIn() {

        (new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOf(driver.findElement(By.className("map-region")).findElement(By.className("gm-style"))));
        WebElement element = mapViewPageContainer.zoomIn;
        element.click();
        LOGGER.info("clicking zoom in");
    }

    public void userApplyFilter() {

        selectApplyFilters();
        driver.navigate().refresh();
        LOGGER.info("Apply Filters");
    }

    public void mapFilters(Map<String, String> data) {
        String value;
        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                switch (key) {
                    case "Property Type":
                        value = data.get(key);
                        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(mapViewPageContainer.propertyType));
                        List<WebElement> elements = mapViewPageContainer.propertyType.findElements(By.xpath("//ul/li"));
                        for (WebElement e : elements) {
                            if (e.getText().equalsIgnoreCase(value)) {
                                LOGGER.info("Selecting Property Type : " + e.getText());
                                e.click();
                                break;
                            }
                        }
                        break;
                    case "Region":
                        value = data.get(key);
                        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(mapViewPageContainer.regionSelect));
                        List<WebElement> regionSel = mapViewPageContainer.regionSelect.findElements(By.xpath("//ul/li"));

                        for (WebElement e : regionSel) {
                            if (e.getText().equalsIgnoreCase(value)) {
                                LOGGER.info("Selecting Region : " + e.getText());
                                e.click();
                                break;
                            }
                        }
                        break;
                    case "Asset Manager":
                        value = data.get(key);
                        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(mapViewPageContainer.assetManager));
                        List<WebElement> assetManag = mapViewPageContainer.assetManager.findElements(By.xpath("//ul/li"));

                        for (WebElement e : assetManag) {
                            if (e.getText().equalsIgnoreCase(value)) {
                                LOGGER.info("Selecting Asset Manager : " + e.getText());
                                e.click();
                                break;
                            }
                        }
                        break;
                    default:
                        LOGGER.info("Ignore the filter as the value is not given in scenario:" + key);
                }
            } else
                LOGGER.info("Ignore the filter as the value is not given in scenario:" + key);
        }
    }

    public void validateTotalPropertiesInAssetPlain(String totalProperties) throws InterruptedException {
        String[] totalProp = null;

        for (int i = 0; i <= 2; ) {
            VUtils.waitFor(2);
            totalProp = mapViewPageContainer.assetRegionPlaneHeader.getText().split(":");
            LOGGER.info("totalProp:    " + Arrays.toString(totalProp));
                if (totalProp[1].trim().equals(totalProperties))
                    break;
                else
                    i++;
        }

        if (totalProp[1].trim().equals(totalProperties)) {
            LOGGER.info("total number of properties are: " + totalProp[1]);
            Assert.assertTrue("Actual Number of Properties " + totalProperties + " and Expected total Number of Properties " + totalProp[1] + " Matches, Successfully found on Assert Panel Page", true);
        } else {
            Assert.assertFalse("Actual Number of Properties " + totalProperties + " and Expected total Number of Properties " + totalProp[1] + " not Matching, Unable to Match Total Number of Properties", true);
        }
    }

    public void validateAssetPlainProperties(String totalNumbProp) {

        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(driver.findElement(By.className("assets-region"))));
        List<WebElement> elements = mapViewPageContainer.assetsContainer;
        LOGGER.info("elements: " + elements.size());

        String[] actualAssetName = totalNumbProp.split(",");
        LOGGER.info("numbofimages: " + Arrays.toString(actualAssetName));
        for (int t = 0; t < actualAssetName.length - 1; t++) {
            for (int i = 1; i <= elements.size(); i++) {
                (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(driver.findElement(By.className("asset"))));
                LOGGER.info("i value is: " + i);

                String expectedAssetName = driver.findElement(By.xpath("//div[@class='asset'][" + i + "]//descendant::figcaption//label")).getText();
                LOGGER.info("expectedAssetName: " + expectedAssetName);

                if (actualAssetName[t].trim().equals(expectedAssetName.trim())) {
                    Assert.assertTrue("Validating the Actual Asset Name " + actualAssetName[t] + " is present in Expected Plane " + expectedAssetName + "", true);
                    break;
                } else {
                    Assert.assertFalse("Validating the Actual Asset Name " + actualAssetName[t] + " is not present in Expected Plane " + expectedAssetName + "", false);
                }
            }
        }
    }

    public void validatePropertiesInViewOnAssetPlain(String viewNumbProp) {

        String[] totalProp = null;
        for (int i = 0; i <= 2; ) {
            VUtils.waitFor(1);
            totalProp = mapViewPageContainer.assetRegionPlaneHeader.getText().split(":");
            LOGGER.info("totalProp:    " + Arrays.toString(totalProp));

            if (totalProp[2].trim().equals(viewNumbProp))
                break;
            else
                i++;
        }

        (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(mapViewPageContainer.assetRegionPlaneHeader));
        totalProp = mapViewPageContainer.assetRegionPlaneHeader.getText().split(":");

        if (totalProp[2].trim().equals(viewNumbProp)) {
            LOGGER.info("total number of view properties are: " + totalProp[2]);
            Assert.assertTrue("Actual Number of Properties " + viewNumbProp + " and Expected total Number of Properties " + totalProp[1] + " Matches, Successfully found on Assert Panel Page", true);
        } else {
            Assert.assertFalse("Actual Number of Properties " + viewNumbProp + " and Expected total Number of Properties " + totalProp[1] + " not Matching, Unable to Match Total Number of Properties", true);
        }
    }
}
