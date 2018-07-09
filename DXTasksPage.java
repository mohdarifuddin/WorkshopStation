package voyanta.ui.pageobjects;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.DXTasksPageContainer;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class DXTasksPage extends AbstractVoyantaPage {

    final private WebDriverWait wait;
    final private WebDriver driver = VoyantaDriver.getCurrentDriver();
    final private String pageLoading = "//*[@id='loader']";
    final private WebElement tableElement;
    final private DXTasksPageContainer dxTasksPageContainer = DXTasksPage.getDataContainer(DXTasksPageContainer.class);
    final private String backToMyTasks = "« Back to My Tasks";
    private List<HashMap> listViewElements;
    private List<HashMap> listTableFilters;
    private Map<String, String> dataTable;
    private int rowCount;
    private WebDriver mDriver;
    AbstractPageWithList abstractPageWithList;
    Logger LOGGER = Logger.getLogger(DXTasksPage.class);

    public DXTasksPage() {
        this.wait = new WebDriverWait(driver, 60);
        if (VUtils.isElementPresent(By.xpath(pageLoading))) {
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(pageLoading)));
        }
        this.tableElement = dxTasksPageContainer.tableElement;
    }

    public List<HashMap> getRowsFromTable() {
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
                            String header = tableElement.findElement(By.xpath("//thead/tr[@id='sorters']")).findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, element1);
//							LOGGER.info("Key: " + header + " Value: " + ((WebElement) hashMap.get(header)).getText());
                        } else {
                            String header = tableElement.findElement(By.xpath("//thead/tr[@id='sorters']")).findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, element1);
//							LOGGER.info("Key: " + header + " Value: " + ((WebElement) hashMap.get(header)).getText());
                        }
                        i++;
                    }
//					LOGGER.info("=======================================");
                    listOfElement.add(hashMap);
                }
            }
        }
        this.listViewElements = listOfElement;
        return listOfElement;
    }

    public List<HashMap> getRowsFromTableUsingRowNumber(int rowNumber) {

        List<HashMap> listOfElement = new LinkedList<>();
        List<WebElement> webElementList = this.tableElement.findElements(By.xpath("//tbody/tr"));

        if (rowNumber == 0 || rowNumber > webElementList.size()) {
            rowNumber = webElementList.size();
        }

        WebElement element = webElementList.get(rowNumber - 1);
        if (!element.getAttribute("class").equals("expandedView")) {
            int j = 0;
            HashMap hashMap = new HashMap();

            for (WebElement e : element.findElements(By.tagName("td"))) {
                if (!e.getText().equals("")) {
                    String header = tableElement.findElement(By.xpath("//thead/tr[@id='sorters']")).findElements(By.xpath("//th")).get(j).getText().trim();
                    hashMap.put(header, e);
                    LOGGER.info("Key : " + header + "  Value : " + e.getText());
                } else {
                    String header = tableElement.findElement(By.xpath("//thead/tr[@id='sorters']")).findElements(By.xpath("//th")).get(j).getText().trim();
                    hashMap.put(header, e);
                    LOGGER.info("Key : " + header + "  Value : " + e.getText());
                }
                j++;
            }
            listOfElement.add(hashMap);
        }

        this.listViewElements = listOfElement;
        rowCount = listViewElements.size();
        return listViewElements;
    }

    public void selectTableFilter(final Map<String, String> data) {
        this.listViewElements = getTableFilters();
        this.dataTable = data;

        data.forEach((Key, Value) -> {
            listViewElements.forEach(map -> {
                if (Objects.nonNull(map.get(Key))) {
                    String string_Value = ((WebElement) map.get(Key)).getText();
                    WebElement element = ((WebElement) map.get(Key));

                    if (!Value.equals("")) {
                        switch (Key) {
                            case "ID":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Organization":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Name":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Status":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Created":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Related To":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "C. Asset Ref.":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Asset Ref.":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Date of Value":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Initial V. Deadline":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Final V. Deadline":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                            case "Appraiser":
                                if (string_Value.equals("All")) {
                                    LOGGER.info("Selecting Filter From Column : " + Key);
                                    element.click();
                                    selectOptionsFromFilterDropDown(Value);
                                    break;
                                }
                        }
                    } else {
                        LOGGER.info("Ignore the Table Filter As Value is not Provided in Scenario : " + Key);
                    }
                }
            });
        });
    }

    private void selectOptionsFromFilterDropDown(final String value) {
        String[] listValue = value.split(",");
        Stream<String> stream1 = Arrays.stream(listValue);
        WebElement e1 = VoyantaDriver.findElement(By.xpath("//*[@id='report-filter-element']")).findElement(By.xpath("//*[@id='list-region']"));

        stream1.forEach(value1 -> {
            VUtils.waitFor(2);
            List<WebElement> options = e1.findElements(By.xpath("//div/ul/li/label"));
            for (WebElement e : options) {
                if (e.getText().equals(value1)) {
                    LOGGER.info("Selecting CheckBox : " + e.getText());
                    VUtils.waitFor(2);
                    e.click();
                    VUtils.waitFor(3);
                    return;
                }
            }
            /*When looking object not found in List DropDown then Use Search*/
            dxTasksPageContainer.filterSearchBox.sendKeys(value1);
            VUtils.waitFor(2);
            List<WebElement> options1 = e1.findElements(By.xpath("//div/ul/li/label"));
            for (WebElement e : options1) {
                if (e.getText().equals(value1)) {
                    LOGGER.info("Selecting CheckBox : " + e.getText());
                    VUtils.waitFor(2);
                    e.click();
                    VUtils.waitFor(2);
                    break;
                }
            }
        });

    }

    public void selectClearFromFilterDropDown() {
        try {
            WebElement e1 = VoyantaDriver.findElement(By.xpath("//*[@id='report-filter-element']")).findElement(By.xpath("//div[@id='report-filter-element']/a"));
            e1.click();
            VUtils.waitFor(2);
        } catch (Exception e) {
            WebElement e1 = VoyantaDriver.findElement(By.xpath("//div[@id='report-filter-element']//a"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='report-filter-element']//a")));
            e1.click();
            VUtils.waitFor(2);
        }
    }

    public void selectCurrentTab() {
        wait.until(ExpectedConditions.visibilityOf(dxTasksPageContainer.CurrentTab));
        VUtils.waitFor(2);
        dxTasksPageContainer.CurrentTab.click();
    }


    public List<HashMap> getTableFilters() {
        List<HashMap> listOfElement = new LinkedList<HashMap>();
        VUtils.waitFor(2);
        for (WebElement element : this.tableElement.findElements(By.xpath("//thead/tr[@id='filters']"))) {
            if (!element.getText().trim().equals("")) {
                if (element.getAttribute("class").contains("expandedView")) {
                    continue;
                } else {
                    int i = 0;
                    HashMap hashMap = new HashMap();
                    for (WebElement e : element.findElements(By.tagName("th"))) {
                        if (!e.getText().equals("")) {
                            VUtils.waitFor(2);
                            String header = tableElement.findElement(By.xpath("//thead/tr[@id='sorters']")).findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, e);
                            LOGGER.info("Key: " + header + " Value: " + ((WebElement) hashMap.get(header)).getText());
                        } else {
                            VUtils.waitFor(2);
                            String header = tableElement.findElement(By.xpath("//thead/tr[@id='sorters']")).findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, e);
                            LOGGER.info("Key: " + header + " Value: " + ((WebElement) hashMap.get(header)).getText());
                        }
                        i++;
                    }
                    listOfElement.add(hashMap);
                }
            }
        }
        this.listTableFilters = listOfElement;
        this.listViewElements = listOfElement;
        return listOfElement;
    }

    public void clearSelectedFilters() {
        this.listTableFilters = getTableFilters();
        VUtils.waitFor(2);
        dataTable.forEach((Key, Value) -> {
            listTableFilters.forEach(map -> {
                if (Objects.nonNull(map.get(Key))) {
                    VUtils.waitFor(1);
                    String string_Value = ((WebElement) map.get(Key)).getText();
                    WebElement element = ((WebElement) map.get(Key));

                    if (!string_Value.equals("All")) {
                        if (!string_Value.equals("Multiple selected")) {
                            element.click();
                            VUtils.waitFor(1);
                            selectClearFromFilterDropDown();
                        }
                    }
                }
            });
        });
    }


    public String getValueForColumn(final String columnName, final List<HashMap> listViewElements) {
        String actualValue = null;
        this.listViewElements = listViewElements;

        for (HashMap map : listViewElements) {
            actualValue = ((WebElement) map.get(columnName)).getText();
        }

        return actualValue;
    }

    public void openFilteredInstance() {
        List<WebElement> webElementList = this.tableElement.findElements(By.xpath("//tbody/tr"));

        WebElement element = webElementList.get(0);
        for (WebElement e : element.findElements(By.tagName("td"))) {
            if (e.getText().equals("") || e.findElement(By.tagName("a")).getAttribute("class").equals("button-icon")) {
                LOGGER.info("Opening the Instance...");
                e.click();
                VUtils.waitFor(2);
                wait.until(ExpectedConditions.textToBePresentInElement(dxTasksPageContainer.backToMyTasksLink, backToMyTasks));
                break;
            }
        }
    }

    public void selectBackToMyTasks() {
        wait.until(ExpectedConditions.visibilityOf(dxTasksPageContainer.backToMyTasksLink));
        dxTasksPageContainer.backToMyTasksLink.click();
        VUtils.waitFor(2);
    }

    public void selectUploadFileFromAppraisal() {
        wait.until(ExpectedConditions.visibilityOf(dxTasksPageContainer.uploadFileBtn));

        if (dxTasksPageContainer.uploadFileBtn.isDisplayed()) {
            LOGGER.info("Selecting Upload File Button...");
            dxTasksPageContainer.uploadFileBtn.click();
            return;
        }
    }


    public void uploadFileToAppraisalWizard(final String fileName, final String folder, final String categoryName) {
        mDriver = VoyantaDriver.getCurrentDriver();
        List<WebElement> options;
        Robot robot = null;

        try {
            String parentWindow = mDriver.getWindowHandle();
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                subWindow = iterator.next();
            }
            mDriver.switchTo().window(subWindow);
            WebElement category = dxTasksPageContainer.categoryDropDown;
            category.click();
            VUtils.waitFor(2);

            options = category.findElements(By.xpath("//option"));

            options.stream().filter(op -> op.getText().equals(categoryName))
                    .forEach(op -> {
                        LOGGER.info("Selecting Category : " + op.getText());
                        op.click();
                        return;
                    });

            abstractPageWithList = new AbstractPageWithList();
            final String filePath = abstractPageWithList.checkFileExist(fileName, folder);

            abstractPageWithList.setClipboardData(filePath);
            LOGGER.info("Uploading the file from location : " + filePath);
            /*
			String js = "arguments[0].click();";
			((JavascriptExecutor) mDriver).executeScript(js, dxTasksPageContainer.selectFileBtn);*/
            javascript("click", dxTasksPageContainer.selectFileBtn);
            abstractPageWithList.setClipboardData(filePath);

            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }

            if (robot != null) {
                robot.setAutoWaitForIdle(true);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.delay(2000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.delay(2000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            }

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='File Upload in Progress']")));
            if (!VUtils.isElementPresent(dxTasksPageContainer.uploadedStatus)) {
                wait.until(ExpectedConditions.visibilityOf(dxTasksPageContainer.uploadedStatus));
            }

            dxTasksPageContainer.saveFromUploadFile.click();
            VUtils.waitFor(2);

            mDriver.switchTo().window(parentWindow);

        } catch (NoSuchElementException e) {
            LOGGER.info("Upload File Wizard Not Present ");
            return;
        }
    }

    public List<HashMap> getRowsFromFilesTabTable() {
        List<HashMap> listOfElement = new LinkedList<HashMap>();

        for (WebElement element : dxTasksPageContainer.filesTabsTableElement.findElements(By.xpath("//tbody/tr"))) {
            if (!element.getText().trim().equals("")) {
                if (element.getAttribute("class").contains("expandedView")) {
                    continue;
                } else {
                    int i = 0;
                    HashMap hashMap = new HashMap();
                    for (WebElement element1 : element.findElements(By.tagName("td"))) {
                        if (!element1.getText().equals("")) {
                            String header = dxTasksPageContainer.filesTabsTableElement
                                    .findElement(By.xpath("//thead/tr[@id='sorters']"))
                                    .findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, element1);
                        } else {
                            String header = dxTasksPageContainer.filesTabsTableElement
                                    .findElement(By.xpath("//thead/tr[@id='sorters']"))
                                    .findElements(By.xpath("//th")).get(i).getText().trim();
                            hashMap.put(header, element1);
                        }
                        i++;
                    }
                    listOfElement.add(hashMap);
                }
            }
        }
        this.listViewElements = listOfElement;
        return listOfElement;
    }

    public void selectFileCheckBoxFromTable() {
        List<WebElement> webElementList = dxTasksPageContainer.filesTabsTableElement.findElements(By.xpath("//tbody/tr"));
        WebElement element = webElementList.get(0);

        element.findElements(By.tagName("td")).stream()
                .filter(e -> e.getAttribute("class").equals("checkbox"))
                .forEach(e -> {
                    LOGGER.info("Selecting File Checkbox ");
                    e.click();
                    VUtils.waitFor(2);
                    return;
                });
    }

    public boolean canSeeFileInList(final String fileName) {
        this.listViewElements = getRowsFromFilesTabTable();

        return listViewElements.stream().anyMatch(map -> ((WebElement) map.get("Name")).getText().equals(fileName));
    }

    public void downloadSelectedFile(final String fileName) {
        selectFileCheckBoxFromTable();
        LOGGER.info("Downloading File...");
        dxTasksPageContainer.downloadSelectedBtn.click();
        VUtils.waitFor(50);
    }

    public void deleteSelectedFile(final String fileName) {
        LOGGER.info("Deleting Selected File from Appraiser : " + fileName);
        dxTasksPageContainer.deleteSeletedBtn.click();
        try {
            String parentWindow = mDriver.getWindowHandle();
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                subWindow = iterator.next();
            }
            mDriver.switchTo().window(subWindow);
            WebElement deleteBtnFromPopUp = mDriver.findElement(By.linkText("Delete Selected Files"));
            deleteBtnFromPopUp.click();
            VUtils.waitFor(5);

            mDriver.switchTo().window(parentWindow);
        } catch (NoSuchElementException e) {
            LOGGER.info("No Popup to Delete ");
        }
    }


    public boolean canSeeColumnFilterInAllRow(final String columnName, final String expValue) {
        this.listViewElements = getRowsFromTable();
        int i = 0;
        boolean b = false;

        for (HashMap map : listViewElements) {
            String value = ((WebElement) map.get(columnName)).getText();
            if (value.equals(expValue)) {
                LOGGER.info("Found the Value in Column '" + columnName + "' in Row : " + i);
                b = true;
                i++;
            }
        }
        return b;
    }
}
