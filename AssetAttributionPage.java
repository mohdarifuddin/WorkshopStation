package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.AssetAttributionPageContainer;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hiten.Parma on 19/10/2015.
 */
public class AssetAttributionPage extends AbstractVoyantaPage {

    static Logger LOGGER = org.apache.log4j.Logger.getLogger(AssetAttributionPage.class);
    static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 60);
    AssetAttributionPageContainer assetPageContainer;
    public WebElement tableElement;
    public List<HashMap> listViewElements;
    WebDriver mDriver = VoyantaDriver.getCurrentDriver();

    public AssetAttributionPage() {
        assetPageContainer = AssetAttributionPage.getDataContainer(AssetAttributionPageContainer.class);
        VUtils.waitForElement(assetPageContainer.filtersHeadersElement);
        LOGGER.info("User Goes To Asset Attribution Report Page");
    }

    public void filterMethod(List<WebElement> el, String Key, String value) {
        for (WebElement e : el) {
            System.out.println("List : " + e.getText());
            int attempts = 0;
            int maxAttempts = 10;
            try {
                if (e.getText().equals(value)) {
                    LOGGER.info("Selecting " + Key + " : " + e.getText());
                    wait.until(ExpectedConditions.elementToBeClickable(e));
                    e.click();
                    List<WebElement> elSel = VoyantaDriver.getCurrentDriver().findElements(By.cssSelector(".wj-listbox-item.wj-state-selected"));
                    try {
                        Assert.assertTrue(elSel.get(0).getText().contains(value));
                    } catch (AssertionError assertError) {
                        if (attempts++ == maxAttempts) throw assertError;
                    }
                }
            } catch (Exception sefe) {
                System.out.println("Error on Filter" + Key + " : " + sefe);
                if (attempts++ == maxAttempts) {
                    throw sefe;
                }
            }
        }
    }

    public void selectFilters(Map<String, String> data) throws InterruptedException {
        String value;
        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                if (key.equals("Quarter")) {
                    value = data.get(key);
                    LOGGER.info("Key : " + value);
                    boolean result = false;
                    int i = 0;
                    int maxAttempts = 50;
                    int noAttempts = 0;
                    while (!result) {
                        List<WebElement> el = VoyantaDriver.getCurrentDriver().findElements(By.className("wj-listbox-item"));
                        List<WebElement> elSelected = VoyantaDriver.getCurrentDriver().findElements(By.cssSelector(".wj-listbox-item.wj-state-selected"));
                        LOGGER.info("elSelected: " + elSelected.get(0).getText());
                        if (elSelected.get(0).getText().contains(value)) {
                            break;
                        }
                        for (WebElement e : el) {
                            if (e.getText().equals(value)) {
                                LOGGER.info("Selecting Quarter : " + e.getText());
                                int attempts = 0;
                                int maxAttempt = 3;
                                try {
                                    e.click();
                                    VUtils.waitFor(2);
                                    List<WebElement> elSel = VoyantaDriver.getCurrentDriver().findElements(By.cssSelector(".wj-listbox-item.wj-state-selected"));
                                    Assert.assertTrue(elSel.get(0).getText().contains(value));
                                    break;
                                } catch (WebDriverException we) {
                                    if (attempts++ == maxAttempt) throw we;
                                }
                                result = true;
                                break;
                            } else {
                                try {
                                    i = i++;
                                    el.get(i).click();
                                    wait.until(ExpectedConditions.elementToBeClickable(el.get(i)));
                                    VUtils.waitFor(2);
                                    el.get(i).sendKeys(Keys.DOWN);
                                    wait.until(ExpectedConditions.elementToBeClickable(el.get(i)));
                                    LOGGER.info("Selecting Quarter Unselected : " + el.get(i).getText());
                                    if (el.get(i).getText().contains(value)) {
                                        result = true;
                                        break;
                                    }
                                } catch (Exception z) {
                                    LOGGER.info("Exception z: " + z);
                                    try {
                                        elSelected.get(0).click();
                                        VUtils.waitFor(2);
                                        elSelected.get(0).sendKeys(Keys.DOWN);
                                        wait.until(ExpectedConditions.elementToBeSelected(elSelected.get(0)));
                                        LOGGER.info("Selecting Quarter selected : " + elSelected.get(0).getText());
                                        if (elSelected.get(0).getText().contains(value)) {
                                            result = true;
                                            break;
                                        }
                                    } catch (WebDriverException v) {
                                        LOGGER.info("Element not visible");
                                        result = false;
                                    }

                                }
                                i++;
                                noAttempts++;
                                if (noAttempts == maxAttempts) {
                                    result = true;
                                    break;
                                }
                            }
                        }
                    }

                } else if (key.equals("Period")) {
                    mDriver.navigate().refresh();
                    VUtils.waitFor(2);
                    VUtils.waitForElement(assetPageContainer.filtersHeadersElement);
                    value = data.get(key);
                    LOGGER.info("Key : " + value);
                    List<WebElement> listPeriod = VoyantaDriver.getCurrentDriver().findElements(By.className("wj-listbox-item"));
                    List<WebElement> elSelected = VoyantaDriver.getCurrentDriver().findElements(By.cssSelector(".wj-listbox-item.wj-state-selected"));
                    if (listPeriod.get(0).getText().contains(value) || elSelected.get(0).getText().contains(value)) {
                        System.out.println("Period already selected");
                        break;
                    } else {
                        filterMethod(listPeriod, key, value);
                        break;
                    }
                }

            } else if (key.equals("Investment(s)") || key.equals("Fund(s)")) {
                mDriver.navigate().refresh();
                VUtils.waitForElement(assetPageContainer.filtersHeadersElement);
                value = data.get(key);
                LOGGER.info("Key : " + value);
                List<WebElement> listFund = VoyantaDriver.getCurrentDriver().findElements(By.className("wj-listbox-item"));
                filterMethod(listFund, key, value);

            } else if (key.equals("Breakdown")) {
                value = data.get(key);
                LOGGER.info("Key : " + value);
                List<WebElement> listBreakdown = VoyantaDriver.getCurrentDriver().findElements(By.className("wj-listbox-item"));
                List<WebElement> elSelected = VoyantaDriver.getCurrentDriver().findElements(By.cssSelector(".wj-listbox-item.wj-state-selected"));
                if (listBreakdown.get(0).getText().contains(value) || elSelected.get(0).getText().contains(value)) {
                    System.out.println("Breakdown already selected");
                    break;
                } else {
                    filterMethod(listBreakdown, key, value);
                    break;
                }
            } else
                LOGGER.info("Ignore the filter as the value is not given in scenario:" + key);
        }
    }

    public void exportInvestmentInfo() {
        int attempts = 0;
        int maxAttempts = 3;
        mDriver.navigate().refresh();
        VUtils.waitForElement(assetPageContainer.filtersHeadersElement);
        try {
            if (VUtils.isElementPresent(By.cssSelector("span.control-msg")))
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.control-msg")));
        } catch (Exception e) {
            if (attempts++ == maxAttempts) throw e;
        }

        WebElement exportToExcel = assetPageContainer.exportToExcel;
        int attempts1 = 0;
        int maxAttempts1 = 10;
        try {
            JavascriptExecutor executor = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
            executor.executeScript("arguments[0].click();", exportToExcel);
        } catch (WebDriverException e) {
            if (attempts1++ == maxAttempts1) throw e;
        }
        LOGGER.info("***** Exporting Investment Information *****");
        VUtils.waitFor(7);
    }

    public void exportPropertyInfo() {
        if (VUtils.isElementPresent(By.xpath("//div[@id='gridPropPerformance']/div/span[2]")))
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='gridPropPerformance']/div/span[2]")));

        WebElement propertyInfoElement = VoyantaDriver.findElement(By.xpath("//*[@id='wj-report']/div[3]/div[1]"));
        WebElement export = propertyInfoElement.findElement(By.xpath("//*[@id='exportPerformance']/a/span[2]"));
        export.click();
        LOGGER.info("***** Exporting Property Information *****");
        VUtils.waitFor(5);
    }

    public void selectAttributionFromTable() {
        List<WebElement> listElement = getTableHeaders();
        for (int i = 0; i < listElement.size(); i++) {
            for (WebElement e : listElement) {
                if (e.getText().equals("Attribution")) {
                    LOGGER.info("Found : " + e.getText());
                    e.click();
                    VUtils.waitFor(2);
                    return;
                }
            }
        }
    }

    public List<WebElement> getTableHeaders() {
        List<WebElement> listOfElement = new LinkedList<>();
        tableElement = VoyantaDriver.findElement(By.id("gridPropAttribution"));
        int i = 0;
        List<WebElement> e = tableElement.findElement(By.xpath("//div")).findElements(By.xpath("//*[@class='wj-colheaders']/*"));
        for (WebElement element : e) {
//            String headers = element.findElements(By.xpath("//*[@class='wj-colheaders']/div")).get(i).getText().trim();
            i++;
            listOfElement.add(element);
        }
        LOGGER.info("Number of Columns : " + listOfElement.size());
        return listOfElement;
    }

    public void canViewTheSubColumn(String[] subColumn) {
        List<WebElement> listElement = getTableHeaders();
        for (WebElement e : listElement) {
            for (int i = 0; i < subColumn.length; i++) {
                if (e.getText().equals(subColumn[i])) {
                    LOGGER.info("Found SubColumn: " + e.getText());
                    VUtils.waitFor(2);
                    break;
                }
            }
        }
    }

    public String getTitleofBubbleChart() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.control-msg")));
        LOGGER.info("Title Of Bubble Chart : " + assetPageContainer.titleOfBubbleChart.getText());
        return assetPageContainer.titleOfBubbleChart.getText();
    }

    public void selectDimensionsFromTable(String dimensions) {
        Actions actions = new Actions(VoyantaDriver.getCurrentDriver());
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.control-msg")));
//        VUtils.waitFor(10);
        List<WebElement> list = VoyantaDriver.findElements(By.xpath("//*[@id='gridPropAttribution']/div/div/div/div/*"));
        for (WebElement e : list) {
            if (e.getAttribute("class").contains("wj-cell wj-group wj-frozen wj-frozen-col")) {
                List<WebElement> listDimension1 = VoyantaDriver.findElements(By.xpath("//*[contains(@class,'wj-cell wj-group wj-frozen wj-frozen-col')]"));
                for (WebElement e1 : listDimension1) {
                    if (e1.getText().equals(dimensions)) {
                        LOGGER.info("Selecting Dimensions : " + e1.getText());
                        //js.executeScript(doubleClickJS, e1);
                        javascript("click", e1);
                        //actions.doubleClick(e1).build().perform();
                        VUtils.waitFor(2);
                        return;
                    }
                }
            } else if (e.getAttribute("class").contains("wj-cell wj-group wj-alt wj-frozen wj-frozen-col")) {
                List<WebElement> listDimension2 = VoyantaDriver.findElements(By.xpath("//*[contains(@class,'wj-cell wj-group wj-alt wj-frozen wj-frozen-col')]"));
                for (WebElement e2 : listDimension2) {
                    if (e2.getText().equals(dimensions)) {
                        LOGGER.info("Selecting Dimensions : " + e2.getText());
                        js.executeScript("click", e2);
                        //actions.doubleClick(e2).build().perform();
                        VUtils.waitFor(2);
                        return;
                    }
                }
            }
        }
        VerifyUtils.fail("The Dimensions is Not Present : " + dimensions);
    }


    public void canSeeSubDimensions(String subDimensionList) {
        VoyantaDriver.getCurrentDriver().navigate().refresh();
        String[] subDim = subDimensionList.split(",");
        VUtils.waitFor(5);
        if (!VUtils.isElementPresent(By.xpath("//*[@id='gridPropAttribution']/div/div/div/div/*"))) {
            wait.until(ExpectedConditions.visibilityOfAllElements(VoyantaDriver.findElements(By.xpath("//*[@id='gridPropAttribution']/div/div/div/div/*"))));
        }
        List<WebElement> list = VoyantaDriver.findElements(By.xpath("//*[@id='gridPropAttribution']/div/div/div/div/*"));

        for (String name : subDim) {
            for (WebElement e : list) {
                if (e.getAttribute("class").contains("wj-cell wj-group wj-alt wj-frozen wj-frozen-col secondary")) {
                    List<WebElement> listSubDimension1 = VoyantaDriver.findElements(By.xpath("//*[contains(@class,'wj-cell wj-group wj-alt wj-frozen wj-frozen-col secondary')]"));
                    for (WebElement e1 : listSubDimension1) {
                        if (e1.getText().equals(name)) {
                            LOGGER.info("Found SubDimensions : " + e1.getText());
                            return;
                        }
                    }
                } else if (e.getAttribute("class").contains("wj-cell wj-group wj-frozen wj-frozen-col secondary")) {
                    List<WebElement> listSubDimension2 = VoyantaDriver.findElements(By.xpath("//*[contains(@class,'wj-cell wj-group wj-frozen wj-frozen-col secondary')]"));
                    for (WebElement e2 : listSubDimension2) {
                        if (e2.getText().equals(name)) {
                            LOGGER.info("Found SubDimensions : " + e2.getText());
                            return;
                        }
                    }
                } else if (e.getAttribute("class").contains("wj-cell wj-group wj-frozen suppressed wj-frozen-col secondary")) {
                    List<WebElement> listSubDimension3 = VoyantaDriver.findElements(By.xpath("//*[contains(@class,'wj-cell wj-group wj-frozen suppressed wj-frozen-col secondary')]"));
                    for (WebElement e3 : listSubDimension3) {
                        if (e3.getText().equals(name)) {
                            LOGGER.info("Found SubDimensions : " + e3.getText());
                            return;
                        }
                    }
                } else if (e.getAttribute("class").contains("wj-cell wj-group wj-alt wj-frozen suppressed wj-frozen-col secondary")) {
                    List<WebElement> listSubDimension4 = VoyantaDriver.findElements(By.xpath("//*[contains(@class,'wj-cell wj-group wj-alt wj-frozen suppressed wj-frozen-col secondary')]"));
                    for (WebElement e4 : listSubDimension4) {
                        if (e4.getText().equals(name)) {
                            LOGGER.info("Found SubDimensions : " + e4.getText());
                            return;
                        }
                    }
                } else if (e.getAttribute("class").contains("wj-cell wj-group wj-alt wj-frozen wj-frozen-col total secondary")) {
                    List<WebElement> dimensionTotal = VoyantaDriver.findElements(By.xpath("//*[contains(@class,'wj-cell wj-group wj-alt wj-frozen wj-frozen-col total secondary')]"));
                    for (WebElement dt : dimensionTotal) {
                        if (dt.getText().equals(name)) {
                            LOGGER.info("Found Total : " + dt.getText());
                            return;
                        }
                    }
                }
            }
            VerifyUtils.fail("Given SubDimensions not Present : " + name);
        }

    }

}
