package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.AuditPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.*;

/**
 * Created by Hiten.Parma on 08/12/2015.
 */
public class AuditPage extends AbstractPageWithList {

    static Logger LOGGER = Logger.getLogger(AuditPage.class);
    private static final String url = PropertiesLoader.getProperty("ui_url") + "data-management/audit";
    AuditPageContainer auditPageContainer = AuditPage.getDataContainer(AuditPageContainer.class);
    public static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 30);
    public WebDriver mDriver = VoyantaDriver.getCurrentDriver();
    public List<HashMap> listViewElements;
    private String pageName = "Audit";
    public WebElement tableElement;

    public AuditPage() {
        this.tableElement = auditPageContainer.tableElement;
    }

    public AuditPage openPage() {
        mDriver.get(url);
        LOGGER.info("* Arriving on Audit Page *");
        wait.until(ExpectedConditions.textToBePresentInElement(mDriver.findElement(By.cssSelector("header > h2")), "Filters"));
        return new AuditPage();
    }

    public void selectFilters(Map<String, String> data) {
        String value;
        String[] multiValue;
        for (String key : data.keySet()) {
            if (!data.get(key).equals("")) {
                if (key.equals("StartDate")) {
                    value = data.get(key);
//                    VoyantaDriver.findElement(By.xpath("//input[@id='minDate']/following-sibling::img")).click();
                    WebElement startDate = VoyantaDriver.findElement(By.xpath("//*[@class='voyantaForm']//input[@id='minDate']"));
                    startDate.click();
                    startDate.sendKeys(value);
//                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].setAttribute('value','" +value  + "')", startDate);
//                    VoyantaDriver.findElement(By.xpath("//input[@id='minDate']/following-sibling::img")).click();
                    VUtils.waitFor(2);
                    LOGGER.info("Selecting Filter : " + key + " with Value :" + value);
                } else if (key.equals("EndDate")) {
                    value = data.get(key);
//                    VoyantaDriver.findElement(By.xpath("//input[@id='maxDate']/following-sibling::img")).click();
                    WebElement endDate = VoyantaDriver.findElement(By.xpath("//*[@class='voyantaForm']//input[@id='maxDate']"));
                    endDate.click();
                    endDate.sendKeys(value);
//                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].setAttribute('value','" +value  + "')", endDate);
//                    VoyantaDriver.findElement(By.xpath("//input[@id='maxDate']/following-sibling::img")).click();
                    VUtils.waitFor(2);
                    LOGGER.info("Selecting Filter : " + key + " with Value :" + value);
                } else if (key.equals("Provider")) {
                    value = data.get(key);
                    multiValue = value.split(",");
                    WebElement providerElement = VoyantaDriver.findElement(By.xpath("//div[@id='providers-component']"));
                    List<WebElement> providerList = providerElement.findElements(By.xpath("//select/option"));
                    providerElement.sendKeys(Keys.CONTROL);
                    for (String s : multiValue) {
                        for (WebElement e : providerList) {
                            if (e.getText().equals(s)) {
                                LOGGER.info("Selecting Filter : " + key + " with Value : " + s);
                                e.click();
                                VUtils.waitFor(2);
                                break;
                            }
                        }
                    }
                } else if (key.equals("Tag")) {
                    value = data.get(key);
                    multiValue = value.split(",");
                    WebElement tagElement = VoyantaDriver.findElement(By.xpath("//div[@id='tags-component']"));
                    List<WebElement> tagList = tagElement.findElements(By.xpath("//select/option"));
                    tagElement.sendKeys(Keys.CONTROL);
                    for (String s : multiValue) {
                        for (WebElement e : tagList) {
                            if (e.getText().equals(s)) {
                                LOGGER.info("Selecting Filter : " + key + " with Value :" + s);
                                e.click();
                                VUtils.waitFor(2);
                                break;
                            }
                        }
                    }
                }
            } else {
                LOGGER.info("Ignore the Filter as the Value is Not Given in Scenario : " + key);
            }
            VUtils.waitFor(2);
        }

    }

    public List<HashMap> getRowsFromReportTable() {
        if (!VUtils.isElementPresent(By.cssSelector("span.close"))) {
            auditPageContainer.openArrowToggle.click();
            wait.until(ExpectedConditions.visibilityOf(auditPageContainer.closeArrowToggle));
        }

        List<HashMap> listOfElement = new LinkedList<HashMap>();
        for (WebElement element : this.tableElement.findElements(By.xpath("//tbody/tr"))) {
            if (!element.getText().trim().equals("")) {
                if (element.getAttribute("class").contains("expandedView")) {
                    continue;
                } else {
                    int i = 0;
                    HashMap hashMap = new HashMap();
                    for (WebElement element1 : element.findElements(By.tagName("td"))) {
                        if (!element1.getText().equals("")) {
                            String header = tableElement.findElement(By.xpath("//thead/tr[@class='labels']")).findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, element1);
                            LOGGER.debug("Key: " + header + " Value: " + ((WebElement) hashMap.get(header)).getText());
                        } else {
                            String header = tableElement.findElement(By.xpath("//thead/tr[@class='labels']")).findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, element1);
                            LOGGER.debug("Key: " + header + " Value: " + ((WebElement) hashMap.get(header)).getText());
                        }
                        i++;
                    }
                    LOGGER.debug("------------------------------------");
                    listOfElement.add(hashMap);
                }
            }
        }
        this.listViewElements = listOfElement;
        System.out.println("size : " + listViewElements.size());
        return listOfElement;
    }

    public List<HashMap> getRowsBasedOnIndexFromTable(int rowNumber) {
        if (!VUtils.isElementPresent(By.cssSelector("span.close"))) {
            auditPageContainer.openArrowToggle.click();
            wait.until(ExpectedConditions.visibilityOf(auditPageContainer.closeArrowToggle));
        }

        List<HashMap> listOfElement = new LinkedList<>();
        List<WebElement> webElementList = this.tableElement.findElements(By.xpath("//tbody/tr"));

        if (rowNumber == 0 || rowNumber > webElementList.size()) {
            rowNumber = webElementList.size();
        }

        for (int i = 0; i < rowNumber; i++) {
            WebElement element = webElementList.get(i);
            if (!element.getAttribute("class").equals("expandedView")) {
                int j = 0;
                HashMap hashMap = new HashMap();

                for (WebElement e : element.findElements(By.tagName("td"))) {
                    if (!e.getText().equals("")) {
                        String header = tableElement.findElement(By.xpath("//thead/tr[@class='labels']")).findElements(By.xpath("//th")).get(j).getText().trim();
                        hashMap.put(header, e);
                        LOGGER.debug("Key : " + header + "  Value : " + e.getText());
                    } else {
                        String header = tableElement.findElement(By.xpath("//thead/tr[@class='labels']")).findElements(By.xpath("//th")).get(j).getText().trim();
                        hashMap.put(header, e);
                        LOGGER.debug("Key : " + header + "  Value : " + e.getText());
                    }
                    j++;
                }
                LOGGER.debug("------------");
                listOfElement.add(hashMap);
            }

        }
        this.listViewElements = listOfElement;
        rowCount = listViewElements.size();
        System.out.println("There are in total " + rowCount + " lines");
        return listViewElements;
    }

    public List<HashMap> getReportFilters() {
        if (!VUtils.isElementPresent(By.cssSelector("span.close"))) {
            auditPageContainer.openArrowToggle.click();
            wait.until(ExpectedConditions.visibilityOf(auditPageContainer.closeArrowToggle));
        }

        List<HashMap> listOfElement = new LinkedList<HashMap>();
        for (WebElement element : this.tableElement.findElements(By.xpath("//thead/tr[@class='report-filters']"))) {
            if (!element.getText().trim().equals("")) {
                if (element.getAttribute("class").contains("expandedView")) {
                    continue;
                } else {
                    int i = 0;
                    HashMap hashMap = new HashMap();
                    for (WebElement e : element.findElements(By.tagName("th"))) {
                        if (!e.getText().equals("")) {
                            String header = tableElement.findElement(By.xpath("//thead/tr[@class='labels']")).findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, e);
                            LOGGER.debug("Key: " + header + " Value: " + ((WebElement) hashMap.get(header)).getText());
                        } else {
                            String header = tableElement.findElement(By.xpath("//thead/tr[@class='labels']")).findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, e);
                            LOGGER.debug("Key: " + header + " Value: " + ((WebElement) hashMap.get(header)).getText());
                        }
                        i++;
                    }
                    listOfElement.add(hashMap);
                }
            }
        }
        this.listViewElements = listOfElement;
        System.out.println("size : " + listViewElements.size());
        return listOfElement;
    }

    /*public void selectReportFilters(String columnName, String value) {
        String [] multiValue =  value.split(",");
        String [] multiColumn = columnName.split(",");
        String newColumn;
       listViewElements = getReportFilters();

        for(HashMap map : listViewElements){
            for(int i =0; i< multiColumn.length; i++) {
                String stringValue = ((WebElement) map.get(multiColumn[i])).getText();
                WebElement element = ((WebElement) map.get(multiColumn[i]));

                if (stringValue != null) {
                    if(multiColumn[i].equals("Parent 1")){
                        if(stringValue.equals("All")) {
                            String parent1 = "firstParent";
                            element.findElement(By.cssSelector("th." + parent1 + " > a.filter.selected")).click();
//                            wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.id("report-filter-element"))));
                            VUtils.waitFor(3);
                        }
                    }
                    else if(multiColumn[i].equals("Parent 2")){
                        if(stringValue.equals("All")) {
                            String parent2 = "secondParent";
                            element.findElement(By.cssSelector("th." + parent2 + " > a.filter.selected")).click();
//                            wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.id("report-filter-element"))));
                            VUtils.waitFor(3);
                        }
                    }
                    else if(multiColumn[i].equals("Change")){
                        if(stringValue.equals("All")){
                            String type = "changeType";
                            element.findElement(By.cssSelector("th."+ type + " > a.filter.selected")).click();
                            VUtils.waitFor(3);
                        }
                    }
                    else {
                        if (stringValue.equals("All")) {
                            String newColumn1 = multiColumn[i].replaceAll("\\s+", "");
                            newColumn = String.valueOf(Character.toLowerCase(newColumn1.charAt(0))) + newColumn1.substring(1);
                            element.findElement(By.cssSelector("th." + newColumn + " > a.filter.selected")).click();
//                            wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.id("report-filter-element"))));
                            VUtils.waitFor(3);
                        }
                    }
                } selectOptionsFromFilters(value);
            }
        }
    }*/

    public void selectOptionsFromFilters(String value) {
        String[] listValue = value.split(",");
        List<WebElement> options;
        WebElement list = VoyantaDriver.findElement(By.xpath("//*[@id='report-filter-element']")).findElement(By.xpath("//*[@id='list-region']"));
        for (int i = 0; i < listValue.length; i++) {
            options = list.findElements(By.xpath("//div/ul/li/label"));
            for (WebElement e : options) {
                if (e.getText().equals(listValue[i])) {
                    LOGGER.info("Selecting CheckBox : " + e.getText());
                    e.click();
                    VUtils.waitFor(3);
                    return;
                }
            }
            /*When looking object not found in List then Use Search*/
            WebElement serchBox = VoyantaDriver.findElement(By.name("filter-search"));
            serchBox.sendKeys(listValue[i]);
            VUtils.waitFor(2);
            options = list.findElements(By.xpath("//div/ul/li/label"));
            for (WebElement e : options) {
                if (e.getText().equals(listValue[i])) {
                    LOGGER.info("Selecting CheckBox : " + e.getText());
                    e.click();
                    VUtils.waitFor(2);
                    break;
                }
            }
        }
    }

    public void selectReportFilters(Map<String, String> data) {
        String value;
        String newColumn;
        listViewElements = getReportFilters();
        for (String key : data.keySet()) {
            for (HashMap map : listViewElements) {
                String stringValue = ((WebElement) map.get(key)).getText();
                WebElement element = ((WebElement) map.get(key));
                if (!data.get(key).equals("")) {
                    if (key.equals("Parent 1")) {
                        if (stringValue.equals("All")) {
                            value = data.get(key);
                            String parent1 = "firstParent";
                            LOGGER.info("Selecting Filter For Column : " + key);
                            element.findElement(By.cssSelector("th." + parent1 + " > a.filter.selected")).click();
                            VUtils.waitFor(2);
                            selectOptionsFromFilters(value);
                        }
                    } else if (key.equals("Parent 2")) {
                        if (stringValue.equals("All")) {
                            value = data.get(key);
                            String parent2 = "secondParent";
                            LOGGER.info("Selecting Filter For Column : " + key);
                            element.findElement(By.cssSelector("th." + parent2 + " > a.filter.selected")).click();
                            VUtils.waitFor(2);
                            selectOptionsFromFilters(value);
                        }
                    } else if (key.equals("Change")) {
                        if (stringValue.equals("All")) {
                            value = data.get(key);
                            String type = "changeType";
                            LOGGER.info("Selecting Filter For Column : " + key);
                            element.findElement(By.cssSelector("th." + type + " > a.filter.selected")).click();
                            VUtils.waitFor(2);
                            selectOptionsFromFilters(value);
                        }
                    } else if (key.equals("Approved Date")) {
                        if (stringValue.equals("All")) {
                            value = data.get(key);
                            String date = "approvalDate";
                            LOGGER.info("Selecting Filter For Column : " + key);
                            element.findElement(By.cssSelector("th." + date + " > a.filter.selected")).click();
                            VUtils.waitFor(2);
                            selectOptionsFromFilters(value);
                        }
                    } else {
                        if (stringValue.equals("All")) {
                            value = data.get(key);
                            String newColumn1 = key.replaceAll("\\s+", "");
                            newColumn = String.valueOf(Character.toLowerCase(newColumn1.charAt(0))) + newColumn1.substring(1);
                            LOGGER.info("Selecting Filter For Column : " + key);
                            element.findElement(By.cssSelector("th." + newColumn + " > a.filter.selected")).click();
                            VUtils.waitFor(2);
                            selectOptionsFromFilters(value);
                        }
                    }

                } else {
                    LOGGER.info("Ignore The Report Filter as the value is Not Given In Scenario : " + key);
                }
            }
        }
    }

    public int getRowNumberFromReportTable(String columnName, String value) {
        listViewElements = getRowsFromReportTable();
        int i = 0;
        for (HashMap map : listViewElements) {
            String columnValue = ((WebElement) map.get(columnName)).getText().trim();
            if (columnValue.equals(value)) {
                LOGGER.info("Found the value " + ((WebElement) map.get(columnName)).getText().trim() + " at Row : " + i);
                return i;
            }
            i++;
        }
        VerifyUtils.fail("Given Value :" + value + " Not Found in Column : " + columnName);
        return i;
    }

    public int getNoOfRowsFromReportTable() {
        listViewElements = getRowsFromReportTable();
        return listViewElements.size();
    }

    public List<String> getObjectNameFromColumn(String columnName) {
        super.listViewElements = getRowsFromReportTable();
        return getColumnValueAsList(columnName);
    }

    public WebElement getCellElementFromRow(int i, String columnName) {
        i = i - 1;
        listViewElements = null;
        try {
            if (listViewElements == null) {
                this.getRowsFromReportTable();
            }
            if (listViewElements.get(i).containsKey(columnName)) {
                WebElement element = (WebElement) listViewElements.get(i).get(columnName);
                wait.until(ExpectedConditions.visibilityOf(element));
                return element.findElement(By.tagName("a"));
            } else {
                throw new RuntimeException("The given header is not valid: " + columnName);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Row number not valid :" + i);
        }
    }

    public String getAlertMessages() {
        String parentWindow = mDriver.getWindowHandle();
        String subWindow = null;
        String alertMessage;
        Set<String> handles = mDriver.getWindowHandles();

        for (String handle : handles) {
            subWindow = handle;
        }

        mDriver.switchTo().window(subWindow);
        WebElement message = mDriver.findElement(By.id("dialog-region"));
        alertMessage = message.getText();
        mDriver.findElement(By.linkText("Close")).click();
        mDriver.switchTo().window(parentWindow);
        VUtils.waitFor(1);
        return alertMessage;
    }

    public void closeAlert() {
        mDriver.findElement(By.linkText("Close")).click();
    }

    public List<String> getAlertField() {
        List<String> s = new LinkedList<>();
        WebElement fieldName = mDriver.findElement(By.xpath("//*[@id='dialog-region']/div"));
        List<WebElement> list = fieldName.findElements(By.xpath("//p/strong"));
        for (WebElement e : list) {
            s.add(e.getText());
        }
        return s;
    }

    public List<String> getAlert() {
        List<String> s = new LinkedList<>();
        WebElement alert = mDriver.findElement(By.xpath("//*[@id='dialog-region']/div"));
        List<WebElement> list = alert.findElements(By.xpath("//p[@class='alert']"));
        for (WebElement e : list) {
            s.add(e.getText());
        }
        return s;
    }

    public void selectFilterFromTable(String columnName, String objectName) {
        String value;
        String newColumn;
        listViewElements = getReportFilters();
        for (HashMap map : listViewElements) {
            String stringValue = ((WebElement) map.get(columnName)).getText();
            WebElement element = ((WebElement) map.get(columnName));
            if (!objectName.equals("")) {
                if (columnName.equals("Parent 1")) {
                    if (stringValue.equals("All")) {
                        value = objectName;
                        String parent1 = "firstParent";
                        LOGGER.info("Selecting Filter For Column : " + columnName);
                        element.findElement(By.cssSelector("th." + parent1 + " > a.filter.selected")).click();
                        VUtils.waitFor(2);
                        selectOptionsFromFilters(value);
                    }
                } else if (columnName.equals("Parent 2")) {
                    if (stringValue.equals("All")) {
                        value = objectName;
                        String parent2 = "secondParent";
                        LOGGER.info("Selecting Filter For Column : " + columnName);
                        element.findElement(By.cssSelector("th." + parent2 + " > a.filter.selected")).click();
                        VUtils.waitFor(2);
                        selectOptionsFromFilters(value);
                    }
                } else if (columnName.equals("Change")) {
                    if (stringValue.equals("All")) {
                        value = objectName;
                        String type = "changeType";
                        LOGGER.info("Selecting Filter For Column : " + columnName);
                        element.findElement(By.cssSelector("th." + type + " > a.filter.selected")).click();
                        VUtils.waitFor(2);
                        selectOptionsFromFilters(value);
                    }
                } else if (columnName.equals("Name")) {
                    if (stringValue.equals("All")) {
                        value = objectName;
                        String column = "object" + columnName;
                        LOGGER.info("Selecting Filter For Column : " + columnName);
                        element.findElement(By.cssSelector("th." + column + " > a.filter.selected")).click();
                        VUtils.waitFor(2);
                        selectOptionsFromFilters(value);
                    }
                } else {
                    if (stringValue.equals("All")) {
                        value = objectName;
                        String newColumn1 = columnName.replaceAll("\\s+", "");
                        newColumn = String.valueOf(Character.toLowerCase(newColumn1.charAt(0))) + newColumn1.substring(1);
                        LOGGER.info("Selecting Filter For Column : " + columnName);
                        element.findElement(By.cssSelector("th." + newColumn + " > a.filter.selected")).click();
                        VUtils.waitFor(2);
                        selectOptionsFromFilters(value);
                    }
                }

            } else {
                LOGGER.info("Ignore The Report Filter as the value is Not Given In Scenario : " + columnName);
            }
        }
    }

    public String getPageName() {
        String pageName = null;
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        for (WebElement e : mDriver.findElements(By.cssSelector("ul.tabs-menu > li > a"))) {
            if (e.getAttribute("class").equals("selected")) {
                pageName = e.getText();
                return pageName;
            }
        }
        return pageName;
    }

    public boolean isProviderInFilter(String value) {
        boolean b = false;
        WebElement providerElement = VoyantaDriver.findElement(By.xpath("//div[@id='providers-component']"));
        List<WebElement> providerList = providerElement.findElements(By.xpath("//select/option"));
        for (WebElement e : providerList) {
            if (e.getText().equals(value)) {
                LOGGER.info("Found The Provider In Filter : " + e.getText());
                VUtils.waitFor(1);
                b = true;
                return b;
            }
        }
        LOGGER.info("Given Provider " + value + " is Not Present In Filter");
        return b;
    }

    public boolean isTagInFilter(String value) {
        boolean b = false;
        WebElement tagElement = VoyantaDriver.findElement(By.xpath("//div[@id='tags-component']"));
        List<WebElement> tagList = tagElement.findElements(By.xpath("//select/option"));
        for (WebElement e : tagList) {
            if (e.getText().equals(value)) {
                LOGGER.info("Found the Tag In Filter : " + e.getText());
                VUtils.waitFor(1);
                b = true;
                return b;
            }
        }
        LOGGER.info("Given Tag " + value + " is Not Present In Filter");
        return b;
    }

    public void enterPageNumber(String pageNumber) {
        auditPageContainer.goPageBox.sendKeys(pageNumber);
        VUtils.waitFor(1);
    }

    public void selectGoButton() {
        auditPageContainer.goButton.click();
        VUtils.waitFor(2);
    }

    public String canSeeSelectedPageNumber() {
        WebElement element = mDriver.findElement(By.cssSelector("div.pages"));
        return element.getText();
    }

    public void selectNoOfRows(String noOfRows) {
        WebElement dropDown = mDriver.findElement(By.cssSelector("button.voyantaButton.dropdown-toggle"));
        dropDown.click();
        WebElement list = mDriver.findElement(By.cssSelector("ul.dropdown-menu.list-unstyled"));
        wait.until(ExpectedConditions.visibilityOf(list));
        List<WebElement> pageList = list.findElements(By.xpath("//li/a"));
        for (WebElement e : pageList) {
            if (e.getText().equals(noOfRows)) {
                LOGGER.info("Selecting No Of Rows : " + e.getText());
                e.click();
                VUtils.waitFor(2);
                return;
            }
        }
        VerifyUtils.fail("Given No of Rows is Invalid : " + noOfRows);
    }

}
