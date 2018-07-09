package voyanta.ui.pageobjects;


import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import voyanta.ui.pagecontainers.ListOfValueContainer;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;
import voyanta.ui.utils.unused.StringUtils;

import java.util.*;

/**
 * Created by Hiten.Parma on 26/01/2015.
 */
public class ListOfValuePage extends AbstractPageWithList {
    static Logger LOGGER = Logger.getLogger(ListOfValuePage.class);
    ListOfValueContainer listOfValueContainer;
    WebDriver mDriver = VoyantaDriver.getCurrentDriver();
    public WebElement tableElement;

    public ListOfValuePage() {
        listOfValueContainer = ListOfValuePage.getDataContainer(ListOfValueContainer.class);
    }

    public void selectFromFilter(String objectType, String list) {
        selectObjectType(objectType);
        selectListName(list);
    }

    public UploadPage selectUploadLoV() {
        listOfValueContainer.uploadListOfValuesButton.click();
        return new UploadPage();
    }

    public UploadPage selectUploadLoVButtonLink() {
        listOfValueContainer.uploadLoVLink.click();
        LOGGER.info("Goes to Upload List of Values Page");
        return new UploadPage();
    }

    public void selectObjectType(String objectType) {
        LOGGER.info("Selecting ObjectType from the Filter: " + objectType);
        mDriver.findElement(By.id("typeSelection_chzn")).findElement(By.tagName("span")).click();
        VUtils.waitFor(2);
        WebElement rowElement = mDriver.findElement(By.id("typeSelection_chzn")).findElement(By.className("chzn-drop"));
        List<WebElement> options = rowElement.findElement(By.className("chzn-results")).findElements(By.tagName("li"));

        for (WebElement e : options) {
                if (e.getText().equals(objectType)) {
                    LOGGER.info("Found the ObjectType :" + e.getText());
                    LOGGER.info("---------------------------------------");
                    wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(e), ExpectedConditions.elementToBeClickable(e)));
                    e.click();
                    VUtils.waitFor(2);
                    Assert.assertTrue("Object type " + objectType + " is not selected", VoyantaDriver.getCurrentDriver().findElement(By.xpath("//div[@id='typeSelection_chzn']/a/span")).getText().equalsIgnoreCase(objectType));
                    return;
                } else {
                    LOGGER.info("Not Found Object Name :" + e.getText());
                }
        }
    }

    public void selectListName(String list) {
        LOGGER.info("Selecting ListName from the List: " + list);
        mDriver.findElement(By.id("listSelection_chzn")).findElement(By.tagName("span")).click();
        VUtils.waitFor(2);
        WebElement rowElement = mDriver.findElement(By.id("listSelection_chzn")).findElement(By.className("chzn-drop"));
        List<WebElement> options = rowElement.findElement(By.className("chzn-results")).findElements(By.tagName("li"));

        for (WebElement e : options) {
            if (e.getText().equals(list)) {
                LOGGER.info("Found the ListName : " + e.getText());
                LOGGER.info("----------------------------------------");
                e.click();
                VUtils.waitFor(2);
                Assert.assertTrue("List "+list+" is not selected", VoyantaDriver.getCurrentDriver().findElement(By.xpath("//div[@id='listSelection_chzn']/a/span")).getText().equalsIgnoreCase(list));
                return;
            } else {
                LOGGER.info("ListName is not Present in List");
            }
        }
    }

    public void searchListName(String listName) {
        LOGGER.info("Searching the ListName Using Search :" + listName);
        WebElement name = mDriver.findElement(By.id("searchbox-region")).findElement(By.id("listNameSearch"));
        name.sendKeys(listName);
        WebElement element = mDriver.findElement(By.id("searchbox-region")); //.findElement(By.id("listNameSearch"));
        List<WebElement> options = element.findElement(By.id("searchResultsContainer")).findElements(By.tagName("a"));

        for (WebElement e : options) {
            if (e.getText().equals(listName)) {
                LOGGER.info("Found the ListName: " + e.getText());
                e.click();
                VUtils.waitFor(1);
                return;
            } else {
                LOGGER.info("ListName is not Present ");
            }
        }
    }

    public void canSeeObjectTypeLink(String listName, String typeOfList, String linkWith) {
        LOGGER.info("verifying the ListName, Type, and Associate with ");
        WebElement listSection = mDriver.findElement(By.id("list-region"));

        LOGGER.info("Verifying the ListName :" + listName);
        VerifyUtils.contains(listName, listSection.findElement(By.tagName("h3")).getText());

        LOGGER.info("verifying the Type: " + typeOfList);
        VerifyUtils.contains(typeOfList, listSection.findElement(By.cssSelector("div.info-row")).getText());

        LOGGER.info("Verifying the Associated with: " + linkWith);
        WebElement associateWith = listSection.findElement(By.xpath("//div[@id='list-region']/div/div[2]"));

        String[] items = linkWith.split(",");

        for (String text : items) {
            VerifyUtils.contains(associateWith.getText(), text);
        }

    }

    public void canSeeObjectName(String listName) {
        WebElement listSection = mDriver.findElement(By.id("list-region"));
        LOGGER.info("Verifying the ListName :" + listName);
        VerifyUtils.contains(listName, listSection.findElement(By.tagName("h3")).getText());
    }

    public void canSeeListDetailSection(String defaultValue, String type, String active, String desc, String uk_locale, String us_locale) {
        getRowsFromListDetailSection();
        compareList(defaultValue, type, active, desc, uk_locale, us_locale);
    }

    public void canSeeDocumentList(String name) {
        getRowsFromDocumentLOV();
        compareDocumentList(name);
    }

    private void compareDocumentList(String name) {
        listViewElements = getListViewElements();
        String[] listName = name.split(",");
        int j = 0;
        LOGGER.info("Comparing Document List");

        for (HashMap map : listViewElements) {
            String nameList = ((WebElement) map.get("Name")).getText();
            if (nameList.equals(listName[j])) {
                LOGGER.info(nameList + " = Name = " + listName[j]);
            } else {
                VerifyUtils.fail("Extra value Found -:" + nameList);
            }
            j++;
        }
        LOGGER.info("******************************************");
    }

    private void compareList(String value, String type, String active, String desc, String uk_locale, String us_locale) {
        LOGGER.info("Comparing The List");
        listViewElements = getListViewElements();
        String[] listName = value.split(",");
        String[] typeList = type.split(",");
        int j = 0;

        for (HashMap map : listViewElements) {
            String dv = ((WebElement) map.get("Default Value")).getText();
            String Type;

            if (dv.equals("")) {
                dv = ((WebElement) map.get("Default Value")).getAttribute("value");
                Type = ((WebElement) map.get("Type")).getAttribute("value");

                if (dv.equals(listName[j]) || Type.equals(typeList[j])) {
                    LOGGER.info(dv + " = Default Value = " + listName[j]);
                    LOGGER.info(Type + " = Type = " + typeList[j]);
                } else {
                    VerifyUtils.fail("Extra Value Found --:" + dv);

                }

                j++;
            } else {
                dv = ((WebElement) map.get("Default Value")).getText();
                Type = ((WebElement) map.get("Type")).getText();

                if (dv.equals(listName[j]) || Type.equals(typeList[j])) {
                    LOGGER.info(dv + " = Default Value = " + listName[j]);
                    LOGGER.info(Type + " = Type = " + typeList[j]);
                } else {
                    VerifyUtils.fail("Extra Value Found :" + dv);
                }

                j++;
            }

            LOGGER.info("******************************************");
        }
    }

    private WebElement findValue(String value) {
        listViewElements = getListViewElements();
        LOGGER.info("Comparing The List");
        for (HashMap map : listViewElements) {
            WebElement element = (WebElement) map.get("Default Value");
            String dv = element.getText();
            if (dv.equals("")) {
                dv = element.getAttribute("value");
                if (dv.equals(value)) {
                    return element;
                }
            } else if (dv.equals(value)) {
                return element;
            }
        }
        return null;
    }

    public void canSeePermissionList(String defaultValue) {
        getRowsFromListDetailSection();
        comparePermissionList(defaultValue);
    }

    private void comparePermissionList(String value) {
        LOGGER.info("Comparing The List");
        listViewElements = getListViewElements();
        String[] listName = value.split(",");
        int j = 0;

        for (HashMap map : listViewElements) {
            String dv = ((WebElement) map.get("Default Value")).getText();

            if (dv.equals("")) {
                dv = ((WebElement) map.get("Default Value")).getAttribute("value");

                if (dv.equals(listName[j])) {
                    LOGGER.info(dv + " = Default Value = " + listName[j]);
                } else {
                    VerifyUtils.fail("Extra Value Found --:" + dv);
                }

                j++;
            } else {
                dv = ((WebElement) map.get("Default Value")).getText();

                if (dv.equals(listName[j])) {
                    LOGGER.info(dv + " = Default Value = " + listName[j]);
                } else {
                    VerifyUtils.fail("Extra Value Found :" + dv);
                }

                j++;
            }

            LOGGER.info("******************************************");
        }
    }

    public List<HashMap> getRowsFromDocumentLOV() {
        List<HashMap> listOfElement = new LinkedList<>();
        tableElement = mDriver.findElement(By.id("pagination-main"));

        for (WebElement element : tableElement.findElements(By.xpath("//tbody/tr"))) {
            if (!element.getText().trim().equals("")) {
                if (!element.getAttribute("class").contains("expandedView")) {
                    int i = 0;
                    HashMap hashMap = new HashMap();

                    for (WebElement element1 : element.findElements(By.tagName("td"))) {
                        if (!element1.getText().equals("")) {
                            String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();

                            if (StringUtils.isBlank(header)) {
                                header = "" + i;
                            }

                            LOGGER.info("Key:" + header + " Value =:" + element1.getText());
                            hashMap.put(header, element1);
                        }

                        i++;
                    }

                    LOGGER.info("---------------------------------------");
                    listOfElement.add(hashMap);
                }
            }
        }

        this.listViewElements = listOfElement;
        System.out.println(listViewElements.size());
        return listOfElement;
    }

    public List<HashMap> getRowsFromListDetailSection() {
        List<HashMap> listOfElement = new LinkedList<HashMap>();

        tableElement = mDriver.findElement(By.className("list-table-holder"));
        for (WebElement element : tableElement.findElements(By.xpath("//tbody/tr"))) {
            if (!element.getText().trim().equals("")) {
                if (element.getAttribute("class").contains("expandedView")) {
                    continue;
                } else {
                    int i = 0;
                    HashMap hashMap = new HashMap();

                    for (WebElement element1 : element.findElements(By.tagName("td"))) {
                        if (!element1.getText().equals("")) {
                            String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                            hashMap.put(header, element1);
                            LOGGER.debug("Key:" + header + " Value :" + ((WebElement) hashMap.get(header)).getAttribute("value"));
                        } else if (element1.getAttribute("class").equals("s")) {
                            if (element1.findElement(By.className("text")).getAttribute("name").equals("defaultValue")) {
                                String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                                hashMap.put(header, element1.findElement(By.cssSelector("tr.item-row > td.s > input[name='defaultValue']")));
                                LOGGER.debug("Key:" + header + " Value :" + ((WebElement) hashMap.get(header)).getAttribute("value"));
                            } else if (element1.findElement(By.className("text")).getAttribute("name").equals("localizations")) {
                                String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                                hashMap.put(header, element1.findElement(By.cssSelector("tr.item-row > td.s > input[name='localizations']")));
                                LOGGER.debug("Key:" + header + " Value :" + ((WebElement) hashMap.get(header)).getAttribute("value"));
                            }
                        } else if (element1.getAttribute("class").equals("m")) {
                            if (!element1.getText().equals("")) {
                                String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                                hashMap.put(header, element1.findElement(By.xpath("//td/input[@name='description']")));
                                LOGGER.debug("Key:" + header + " Value ->:" + ((WebElement) hashMap.get(header)).getAttribute("value"));
                            } else {
                                String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                                hashMap.put(header, element1);
                                LOGGER.debug("Key:" + header + " Value <=:" + element1.getText());
                            }
                        } else {
                            if (VUtils.isElementPresent(By.xpath("//input[@name='active']"))) {
                                if (!element1.findElement(By.xpath("//input[@name='active']")).getAttribute("name").equals("active")) {
                                    LOGGER.debug("Key:" + tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim() + " Value -:" + element1.getText());
                                }
                                String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                                hashMap.put(header, element1.findElement(By.xpath("//td/input[@name='active']")));
                                LOGGER.debug("Key:" + header + " Value ~:" + ((WebElement) hashMap.get(header)).getAttribute("value"));
                            }
                        }

                        i++;
                    }

                    LOGGER.debug("---------------------------------------");
                    listOfElement.add(hashMap);
                }
            }
        }

        this.listViewElements = listOfElement;
        System.out.println(listViewElements.size());
        return listOfElement;
    }

    public void addButtonPresent(boolean b, String buttonText) {
        VerifyUtils.equals(b, VUtils.isElementPresent(By.linkText(buttonText)));
    }

    public void isRevertPresent() {
        VerifyUtils.False(VUtils.isElementPresent(By.linkText("Revert")));
    }

    public void editCustomValue(String column, String oldValue, String newValue, String returnColumn) {
        mDriver = VoyantaDriver.getCurrentDriver();

        try {
            getRowsFromListDetailSection();
            WebElement element = getRowElementFromTextBox(column, oldValue, returnColumn);
            LOGGER.info("Column Name :" + column + " & Custom Value :" + element.getAttribute("value"));
            List<WebElement> element1;

            if (column.equals("English (UK)") || column.equals("English (US)")) {
                element1 = element.findElements(By.xpath("//td/input[@name='localizations']"));
            } else {
                element1 = element.findElements(By.xpath("//td/input"));
            }

            for (WebElement e : element1) {
                if (e.getAttribute("value").equals(oldValue)) {
                    e.clear();
                    VUtils.waitFor(1);
                    LOGGER.info("Editing Custom Value : " + oldValue + " - to New Value :" + newValue);
                    e.sendKeys(newValue);
                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].setAttribute('value','" + newValue + "')", e);
                    wait.until(ExpectedConditions.textToBePresentInElementValue(e, newValue));
                    mDriver.findElement(By.linkText("Save")).click();
                    String parentWindow = mDriver.getWindowHandle();
                    String subWindow = null;
                    Set<String> handles = mDriver.getWindowHandles();
                    Iterator<String> iterator = handles.iterator();

                    while (iterator.hasNext()) {
                        subWindow = iterator.next();
                    }

                    mDriver.switchTo().window(subWindow);
                    mDriver.findElement(By.linkText("Continue")).click();
                    VUtils.waitFor(5);
                    mDriver.switchTo().window(parentWindow);
                    return;
                }
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            LOGGER.info("LoV is not Present in List");
        }
    }

    public WebElement getRowElementFromTextBox(String column, String text, String returnColumn) {
        int i = getRowNumberFromTable(column, text);
        return i >= 0 ? getCellElementWithRow(i + 1, returnColumn) : null;
    }

    public int getRowNumberFromTable(String column, String value) {
        listViewElements = getListViewElements();
        int i = 0;

        for (HashMap map : listViewElements) {
            String stringValue = ((WebElement) map.get(column)).getAttribute("value");

            if (stringValue != null) {
                if (stringValue.equals(value)) {
                    LOGGER.info("Found record with text :" + ((WebElement) map.get(column)).getAttribute("value"));
                    return i;
                }

                i++;
            } else {
                stringValue = ((WebElement) map.get(column)).getText();

                if (stringValue.equals(value)) {
                    LOGGER.info("Found record with text :" + ((WebElement) map.get(column)).getText());
                    return i;
                }

                i++;
            }
        }

        return -1;
    }

    public void editedValueInList(String column, String value) {
        getRowsFromListDetailSection();
        getRowNumberFromTable(column, value);
    }

    public void selectAddValue() {
        listOfValueContainer.addValue.click();
        VUtils.waitFor(2);
    }

    public void selectDocumentAddValue() {
//		VoyantaDriver.findElement(By.cssSelector("button.voyantaButton")).click();
        listOfValueContainer.documentLoVAddValueLink.click();
        VUtils.waitFor(2);
    }

    public void fillAddValueForm(String newValue, String desc, String uk_eng, String us_eng) {
        String updateMessage = "Lookup value has been added.";
        String popTitle = "Add Value";
        String parentWindow = mDriver.getWindowHandle();
        String subWindow = null;
        Set<String> handles = mDriver.getWindowHandles();

        for (String handle : handles) {
            subWindow = handle;
        }

        mDriver.switchTo().window(subWindow);
        VerifyUtils.equals(mDriver.findElement(By.id("ui-id-1")).getText(), popTitle);
        LOGGER.info("Adding New Value to List...");
        listOfValueContainer.newDefaultValue.sendKeys(newValue);
        VUtils.waitFor(1);
        listOfValueContainer.newDescription.sendKeys(desc);
        VUtils.waitFor(1);

        if (!listOfValueContainer.newActive.isEnabled()) {
            listOfValueContainer.newActive.click();
        }

        listOfValueContainer.newEn_GB.sendKeys(uk_eng);
        VUtils.waitFor(1);
        listOfValueContainer.newEn_US.sendKeys(us_eng);
        VUtils.waitFor(1);
        listOfValueContainer.newSave.click();
        VUtils.waitFor(5);
        mDriver.switchTo().window(parentWindow);
        WebElement msg = mDriver.findElement(By.xpath("//div[@id='list-region']/div/div[4]"));
        VerifyUtils.equals(updateMessage, msg.getText());
    }

    public void fillDocumentAddValueForm(String name, String expectedMessage) {
        String parentWindow = mDriver.getWindowHandle();
        String subWindow = null;
        Set<String> handles = mDriver.getWindowHandles();

        for (String handle : handles) {
            subWindow = handle;
        }

        mDriver.switchTo().window(subWindow);
        listOfValueContainer.newNameDocument.sendKeys(name);
        listOfValueContainer.newSaveDocument.click();
        mDriver.switchTo().window(parentWindow);
        VUtils.waitFor(5);
//		WebElement actualMessage = VoyantaDriver.findElement(By.xpath("//*[@id='dialog-region']/div/div"));
//		VerifyUtils.equals(expectedMessage, actualMessage.getText());
    }

    public void checkValuePresent(String value, boolean b) {
        VerifyUtils.equals(b, isValuePresentInList(value));
    }

    public boolean isValuePresentInList(String value) {
        getRowsFromListDetailSection();
        int rowNumber = getRowNumberFromTable("Default Value", value);
        LOGGER.info("Found the Value in the List " + rowNumber);
        return rowNumber >= 0;
    }

    public void deleteValue(String column, String returnColumn, String value) {
        String updateMessage = "Lookup value deleted.";
        mDriver = VoyantaDriver.getCurrentDriver();

        try {
            WebElement delete = mDriver.findElement(By.xpath("//a[contains(text(),'X')]"));
            getRowsFromListDetailSection();
            WebElement element = getRowElementFromTextBox(column, value, returnColumn);
            if (element != null) {
                LOGGER.info("Column Name :" + column + " & Custom Value :" + element.getAttribute("value"));
                List<WebElement> element1 = element.findElements(By.xpath("//td/input"));
                for (WebElement e : element1) {
                    if (e.getAttribute("value").equals(value)) {
                        LOGGER.info("Deleting the Value : " + e.getAttribute("value"));
                        if (delete.isEnabled()) {
                            delete.click();
                            String parentWindow = mDriver.getWindowHandle();
                            String subWindow = null;
                            Set<String> handles = mDriver.getWindowHandles();
                            Iterator<String> iterator = handles.iterator();
                            while (iterator.hasNext()) {
                                subWindow = iterator.next();
                            }

                            mDriver.switchTo().window(subWindow);
                            mDriver.findElement(By.linkText("Delete")).click();
                            VUtils.waitFor(3);
                            mDriver.switchTo().window(parentWindow);
                            WebElement msg = mDriver.findElement(By.xpath("//div[@id='list-region']/div/div[4]"));
                            VerifyUtils.equals(updateMessage, msg.getText());
                            return;

                        } else {
                            LOGGER.error("The Value Cannot be Deleted");
                        }
                    }
                }
            } else {
                LOGGER.info("The Value doesn't exist");
            }
        } catch (NoSuchElementException e) {
            LOGGER.info("The Value is in use");
        }
    }

    public void isActive(String flag, String value) {
        mDriver = VoyantaDriver.getCurrentDriver();
        String column = "Default Value";
        String returnColumn = "Active";
        getRowsFromListDetailSection();
        WebElement active = getRowElementFromTextBox(column, value, returnColumn);
        LOGGER.info("Column Name : " + returnColumn + " Value : " + active.isEnabled());

        if (flag.equals("disable")) {
            LOGGER.info("The Value is in Use and Not able make Inactive");
            VerifyUtils.False(active.isEnabled());
        } else {
            LOGGER.info("The Value is Not in Use and able to Make Inactive");
            active.click();
            VUtils.waitFor(1);
            mDriver.findElement(By.linkText("Save")).click();
            VUtils.waitFor(2);
            getRowsFromListDetailSection();
            WebElement inActive = getRowElementFromTextBox(column, value, returnColumn);
            VerifyUtils.True(inActive.isEnabled());
        }
    }

    public void addExisting(String existingValue) {
        String popTitle = "Add Value";
        String subWindow = null;
        Set<String> handles = mDriver.getWindowHandles();

        for (String handle : handles) {
            subWindow = handle;
        }

        mDriver.switchTo().window(subWindow);
        VerifyUtils.equals(mDriver.findElement(By.id("ui-id-1")).getText(), popTitle);
        LOGGER.info("Adding New Value to List...");
        listOfValueContainer.newDefaultValue.sendKeys(existingValue);
        VUtils.waitFor(1);
        listOfValueContainer.newSave.click();
        VUtils.waitFor(2);
    }

    public void errorMessage(String errorMsg) {
        WebElement message;

        if (errorMsg.equals("List of Values updated.")) {
            message = mDriver.findElement(By.xpath("//div[@id='list-region']/div/div[4]"));
        } else {
            message = mDriver.findElement(By.cssSelector("div.message-list.failure"));
        }

        VerifyUtils.equals(errorMsg, message.getText());
    }

    public void canSeeRevert(String flag) {
        WebElement button = mDriver.findElement(By.linkText("Revert"));

        if (flag.equals("disable")) {
            LOGGER.info("The Revert Button is disable :" + button.isEnabled());
            VerifyUtils.True(button.isEnabled());
        } else {
            LOGGER.info("The Revert Button is Enable ");
        }
    }

    public void selectRevert() {
        String revertMsg = "Lookup value has been reverted to default settings.";
        mDriver = VoyantaDriver.getCurrentDriver();

        try {
            LOGGER.info("Reverting The List");
            mDriver.findElement(By.linkText("Revert")).click();
            VUtils.waitFor(2);
            String parentWindow = mDriver.getWindowHandle();
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();

            for (String handle : handles) {
                subWindow = handle;
            }

            mDriver.switchTo().window(subWindow);
            mDriver.findElement(By.xpath("//div[@id='dialog-region']/div/a")).click();
            VUtils.waitFor(2);
            mDriver.switchTo().window(parentWindow);
            WebElement msg = mDriver.findElement(By.xpath("//div[@id='list-region']/div/div[4]"));
            VerifyUtils.equals(revertMsg, msg.getText());
        } catch (NoSuchElementException e) {
            VerifyUtils.fail("Revert Button is not Enable ");
        }
    }

    public void selectDocumentLOVs() {
        LOGGER.info("Opening Document LOVs");
//		listOfValueContainer.documentTab.click();
        VUtils.waitFor(2);
        listOfValueContainer.documentLoVs.click();
//		wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//div[2]/h2")),"Field Values for Document Type"));
        VUtils.waitFor(2);
    }

    public void selectDataLOVs() {
        LOGGER.info("Opening Data LOVs");
//		listOfValueContainer.dataTab.click();
        listOfValueContainer.dataLoVs.click();
        VUtils.waitFor(2);
    }

    public void canSeeDocumentFields() {
        VerifyUtils.equals("Field Values for Document Type", VoyantaDriver.findElement(By.xpath("//div[2]/h2")).getText());
    }

    public void isAddValueBtnOnDocumentLoV(boolean b) {
        LOGGER.info("Verifying the Add Value Present on Document LOV : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.cssSelector("span.icon.add")));
    }

    public void cannotSeeValue(String value) {
        getRowsFromListDetailSection();
        Assert.assertNull(findValue(value));
    }

    public void canSeeConfirmationMessage(String message) {
        VUtils.waitForElement(listOfValueContainer.confirmationMessage);
        VerifyUtils.equals(listOfValueContainer.confirmationMessage.getText(), message);
    }

    public void saveLovValue() {
        listOfValueContainer.saveUpdates.click();
    }

    public void deleteDocumentValue(String nameToDelete) {
        List<HashMap> rows = getRowsFromDocumentLOV();

        for (HashMap row : rows) {
            if (((WebElement) row.get("Name")).getText().equals(nameToDelete)) {
                for (WebElement e : (Collection<WebElement>) row.values()) {
                    if (e.getText().equals("Delete")) {
                        e.findElement(By.tagName("a")).click();
                        VUtils.waitFor(2);
                        return;
                    }
                }
            }
        }
    }

    public void checkDeleteConfirmationMessage(String expectedMessage) {
        String actualMessage = VoyantaDriver.findElement(By.xpath("//*[@id='dialog-region']/div/p")).getText();
        VerifyUtils.equals(expectedMessage, actualMessage);
    }

    public void confirmDocumentValueDeletion(String expectedMessage) {
        VoyantaDriver.findElement(By.xpath("//*[@id='dialog-region']/div/a[1]")).click();
        VUtils.waitFor(2);
        String actualMessage = VoyantaDriver.findElement(By.xpath("//*[@id=\"dialog-region\"]/div/div")).getText();
        VerifyUtils.equals(expectedMessage, actualMessage);
    }

    public void localizeDocumentValue(String lovName, String localization, String expectedMessage) {
        List<HashMap> rows = getRowsFromDocumentLOV();

        for (HashMap row : rows) {
            if (((WebElement) row.get("Name")).getText().equals(lovName)) {
                for (WebElement e : (Collection<WebElement>) row.values()) {
                    if (e.getText().equals("Localize")) {
                        e.findElement(By.tagName("a")).click();
                        VUtils.waitFor(2);
                        break;
                    }
                }
            }
        }

        String parentWindow = mDriver.getWindowHandle();
        String subWindow = null;
        Set<String> handles = mDriver.getWindowHandles();

        for (String handle : handles) {
            subWindow = handle;
        }

        mDriver.switchTo().window(subWindow);
        listOfValueContainer.newNameDocument.sendKeys(localization);
        listOfValueContainer.newSaveDocument.click();
        mDriver.switchTo().window(parentWindow);

//		WebElement actualMessage = VoyantaDriver.findElement(By.xpath("//*[@id='dialog-region']/div/div"));
        WebElement actualMessage = VoyantaDriver.findElement(By.cssSelector("div.message-list.success"));
        VerifyUtils.equals(expectedMessage, actualMessage.getText());
    }

    public boolean isDownloadDSTButtonPresent() {
        return VUtils.isElementPresent(By.linkText("Download DSTs"));
    }

    public boolean isUploadDSTButtonPresent() {
        return VUtils.isElementPresent(By.linkText("Upload List of Values"));
    }

    public void clickOnDownloadDSTsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(listOfValueContainer.downloadLoVDSTLink));
        listOfValueContainer.downloadLoVDSTLink.click();
    }

    public void editedLocalisedValue(String value, String localizedValue) {
        getRowsFromListDetailSection();
        int rowNumber = getRowNumberFromTable("Default Value", value);
        VerifyUtils.equals(localizedValue, getLocalisedValue(rowNumber, localizedValue));
    }

    public String getLocalisedValue(int rowNumber, String value) {
        listViewElements = getListViewElements();
        String loc_UK = "English (UK)";
        String loc_US = "English (US)";
        WebElement element_UK = getRowElementFromTextBox(loc_UK, value, loc_UK);
        WebElement element_US = getRowElementFromTextBox(loc_US, value, loc_US);
        WebElement element;
        if (element_US.getAttribute("value").equals(value)) {
//			element = (WebElement)listViewElements.get(rowNumber).get(loc_US);
            return element_US.getAttribute("value");
        } else {
//			element = (WebElement) listViewElements.get(rowNumber).get(loc_UK);
            return element_UK.getAttribute("value");
        }

    }

    public void canSeeDataLoV(boolean b, String result) {
        if (result.equalsIgnoreCase("can")) {
            LOGGER.info("Goes to Data LOVs ");
            listOfValueContainer.dataTab.click();
            VUtils.waitFor(2);
        } else {
            LOGGER.info("Checking Data LOVs is Present : " + b);
            VerifyUtils.equals(b, isDataLOVPresent());
        }
    }

    public boolean isDataLOVPresent() {
        try {
            return listOfValueContainer.dataLoVs.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("The User have No Access To Data LOVs");
        }
        return false;
    }

    public void canSeeDownloadDST(boolean b) {
        LOGGER.info("Verifying Download DSTs Link Present : " + b);
        VerifyUtils.equals(b, isDownloadDSTButtonPresent());
    }

    public void canSeeDocumentLOV(boolean b, String result) {
        if (result.equalsIgnoreCase("can")) {
            LOGGER.info("Goes to Document LOVs ");
            listOfValueContainer.documentLoVs.click();
            VUtils.waitFor(2);
        } else {
            LOGGER.info("Checking Document LOV Present :" + b);
            VerifyUtils.equals(b, isDocumentLOVPresent());
        }
    }

    public boolean isDocumentLOVPresent() {
        try {
            return listOfValueContainer.documentLoVs.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("The User have No Access To Document LOVs");
        }
        return false;
    }

    public void selectFromFilter_Dynamic(String objectType, String list) {
        selectObjectType_Dyamic(objectType);
        selectListName_Dyamic(list);
    }

    public void selectObjectType_Dyamic(String objectType) {
        LOGGER.info("Selecting ObjectType from the Filter: " + objectType);
        listOfValueContainer.typeSelectionspan.click();
        List<WebElement> options = listOfValueContainer.getObjectTypes();

        for (WebElement e : options) {
            if (e.getText().equals(objectType)) {
                e.click();
                VUtils.waitFor(2);
                return;
            } else {
                LOGGER.info("Not Found Object Name :" + e.getText());
            }
        }
    }

    public void selectListName_Dyamic(String list) {
        LOGGER.info("Selecting ListName from the List: " + list);
        listOfValueContainer.listSelectionspan.click();
        List<WebElement> options = listOfValueContainer.getlistTypes();

        for (WebElement e : options) {
            if (e.getText().equals(list)) {
                LOGGER.info("Found the ListName : " + e.getText());
                LOGGER.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                e.click();
                VUtils.waitFor(2);
                return;
            } else {
                LOGGER.info("ListName is not Present in List");
            }
        }
    }

    public boolean addListOfValueAndValidate(String Value) {
        boolean addValue = false;
        try {
            String popTitle = "Add Value";
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();
            for (String handle : handles) {
                subWindow = handle;
            }
            mDriver.switchTo().window(subWindow);
            VerifyUtils.equals(listOfValueContainer.popupTitle.getText(), popTitle);
            LOGGER.info("Adding New Value to List...");
            listOfValueContainer.newDefaultValue.sendKeys(Value);
            listOfValueContainer.newSave.click();
            wait.until(ExpectedConditions.visibilityOf(listOfValueContainer.valueSaved));
            if (mDriver.getPageSource().contains("Lookup value has been added.")) {
                addValue = true;
                LOGGER.info("Lookup value " + Value + " has been added successfully. " + addValue);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return addValue;
    }

    public boolean ValidateExistingListOfValues(String Value) {
        boolean alreadyExist = false;
        try {
            String popTitle = "Add Value";
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();
            for (String handle : handles) {
                subWindow = handle;
            }
            mDriver.switchTo().window(subWindow);
            VerifyUtils.equals(listOfValueContainer.popupTitle.getText(), popTitle);
            listOfValueContainer.newDefaultValue.sendKeys(Value);
            listOfValueContainer.newSave.click();
            wait.until(ExpectedConditions.visibilityOf(listOfValueContainer.valueAlreadyExist));
            if (mDriver.getPageSource().contains("A lookup value with this name already exists.")) {
                alreadyExist = true;
                LOGGER.info("A lookup value with name " + Value + " already exists. " + alreadyExist);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return alreadyExist;
    }

}
