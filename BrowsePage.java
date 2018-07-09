package voyanta.ui.pageobjects;

import com.google.common.collect.Ordering;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.BrowsePageContainer;
import voyanta.ui.utils.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by sriramangajala on 22/08/2014.
 */
public class BrowsePage extends AbstractPageWithList {

    static Logger LOGGER = Logger.getLogger(BrowsePage.class);

    public BrowsePageContainer pageContainer = BrowsePage.getDataContainer(BrowsePageContainer.class);
    VoyantaDriver alert = new VoyantaDriver();
    public WebDriver mDriver;
    public WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 60);

    public String save = "//*[@class='dialog change-owner-dialog']//*[@id='dialog-button-yes']";
    public String cancel = "//*[@id='change-owner-form']//input[@value='Cancel']";

    public WebElement tableElement;
    public List<HashMap> listViewElements;

    public void BrowsePage() {
        VUtils.waitForElement(pageContainer.content);
        if (!pageContainer.content.getText().contains("Order by"))
            throw new RuntimeException("Browse page not loaded");
    }

    public void checkPermissionRecordExists(String type, String objectName, String objectReference, String levelOfAccess) {
        getRowsAsHashIgnoreBlankHeaders();
        int rowNumber = getRowNumber("Object", objectName + " - " + objectReference);
        LOGGER.info("Found the object in row number " + rowNumber);
        VerifyUtils.True(rowNumber > 0);
    }

    public void openObjectWithName(String objectName) {
        VoyantaDriver.getDivByText(objectName).click();
        //VUtils.waitFor(5);

    }

    public void openObject(String objectName) {
        boolean objectFound = false;
        //WebDriverWait shortWait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10);
        while (!objectFound) {
            try {
                wait.until(ExpectedConditions.visibilityOfAllElements(pageContainer.objectNameList));
                List<WebElement> elements = pageContainer.objectNameList;
                for (int i = 0; i < elements.size(); i++) {
                    WebElement el;
                    if (elements.get(i).getText().contains(objectName)) {
                        objectFound = true;
                        el = elements.get(i);
                        LOGGER.info("Element got: " + el.getText());
                        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", el);
                        el.click();
                        if (VUtils.isElementPresent(By.id("modal-progress")))
                            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modal-progress")));
                        return;
                    } else if (i == elements.size() - 1 && pageContainer.moreResults.isDisplayed()) {
                        pageContainer.moreResults.click();
                        elements = pageContainer.objectNameList;
                        i = 0;
                    } else if (i == elements.size() - 1 && !pageContainer.moreResults.isDisplayed()) {
                        fail("Object Not Found !");
                    }
                    /**
                     * *This will Only work when there is always more results link is present otherwise it will Throw an Error
                     else {
                     pageContainer.moreResults.click();
                     elements = pageContainer.objectNameList;
                     for (i = 0; i < elements.size(); i++) {
                     if (elements.get(i).getText().contains(objectName)) {
                     objectFound = true;
                     el = elements.get(i);
                     LOGGER.info("More Elements got:" + el.getText());
                     ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", el);
                     el.click();
                     return;
                     }
                     }
                     }*/
                }
            } catch (NoSuchElementException | TimeoutException | ElementNotVisibleException e) {
                fail("Object not Found");
            }
        }
    }

    public boolean canSeeObjectInList(String objectName) {
        boolean objectFound = false;
        WebDriverWait shortWait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10);
        while (!objectFound) {
            try {
                shortWait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.xpath("//div[contains(text(),'" + objectName + "')]"))));
                WebElement element = VoyantaDriver.findElement(By.xpath("//div[contains(text(),'" + objectName + "')]"));
                ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", element);
                LOGGER.info("Object Found : " + objectName);
                objectFound = true;
                return objectFound;
            } catch (NoSuchElementException | TimeoutException e) {
                if (VUtils.isElementPresent(By.xpath("//a[@class='get-more-results'][@style='display: block;']"))) {
                    VoyantaDriver.findElement(By.xpath("//a[@class='get-more-results'][@style='display: block;']")).click();
                } else {
                    LOGGER.info("Object Not Present : " + objectName);
                    return objectFound;
                }
            }
        }
        return objectFound;
    }

    public void openObjectContainingName(String objectName) {
        VoyantaDriver.getDivContainingText(objectName).click();
        VUtils.waitFor(5);
    }

    public void checkChildLevelObjectCanbeOpened(String objectName, boolean b) {
        // VerifyUtils.equals(b,checkChildLevelObjectCanbeOpened(objectName));
    }

    public void checkChildLevelObjectCanbeOpened(String name, String childObjectType, String childObjectName, String fieldName, String objectName, boolean b) {

        VerifyUtils.equals(b, checkChildLevelObjectCanbeOpened(name, childObjectType, childObjectName, fieldName, objectName));
    }

    public boolean checkChildLevelObjectCanbeOpened(String name, String childObjectType, String childObjectName, String fieldName, String fieldValue) {

        WebElement parentElement;
        try {

            //  VoyantaDriver.getDivByText(name).click();
            VUtils.waitFor(2);
            parentElement = VoyantaDriver.findElement(By.linkText(name)).findElement(By.xpath("parent::*"));
            if (!parentElement.getText().contains(childObjectType)) {
                LOGGER.info("The child object type " + childObjectType + " is not visible so returning false....");
                return false;
            } else {

                LOGGER.info("The child object type " + childObjectType + " visible ");
                VUtils.waitFor(2);

                LOGGER.info("The child object type " + childObjectType + " is visible ");

                VoyantaDriver.getLinkByText(childObjectType).click();
                VUtils.waitFor(5);
                parentElement = VoyantaDriver.findElement(By.linkText(name)).findElement(By.xpath("parent::*"));

                if (!parentElement.getText().contains(childObjectName)) {
                    LOGGER.info("The child object name " + childObjectName + " is not visible so returning false....");
                    return false;
                } else {
                    LOGGER.info("The child object name " + childObjectName + "is visible ");
                    VoyantaDriver.getLinkByText(childObjectName).click();
                    VUtils.waitFor(2);
                    checkFieldVisible(fieldName, fieldValue);
                    return true;
                }


            }
        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to load the data for object data in time  : " + name);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Unable to load the data for object data : " + name);
        }
        // return true;
    }


    public void downloadDST() {
        pageContainer.linkDownload.click();
        VUtils.waitFor(5);
    }

    private boolean canUserAttachFiles() {
        try {
            return pageContainer.btnAttachFiles.isDisplayed();
        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to find the attach button");
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Unable to find the attach button");
        }
    }


    public void checkFieldVisibleInSection(String section, String fieldName, String fieldValue) {
        HashMap data = getSectionData(section);
        if (data.containsKey(fieldName)) {
            LOGGER.info("Found field " + fieldName);

            if (data.get(fieldName).equals(fieldValue)) {
                LOGGER.info("Found field value " + fieldValue);
            } else {
                VerifyUtils.fail("Field value not found " + fieldValue);
            }
        } else {
            VerifyUtils.fail("Field not found " + fieldName);
        }
    }

    public void checkFieldVisible(String fieldName, String fieldValue) {
        checkFieldVisibleInSection("References", fieldName, fieldValue);
    }

    public HashMap getSectionData(String section) {
        HashMap hashMap = null;
        LOGGER.info("Getting the section data " + section);

        try {
            if (VoyantaDriver.findElement(By.xpath("//h1[text()='" + section + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::*")).findElement(By.tagName("table")).isDisplayed()) {
                WebElement table = VoyantaDriver.findElement(By.xpath("//h1[text()='" + section + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::*")).findElement(By.tagName("table"));
                hashMap = getRowsAsHashFromSection(table);
            }
        } catch (TimeoutException | NoSuchElementException e) {
            throw new RuntimeException("Unable to load the data for section data " + section);
        }

        return hashMap;
    }

    public HashMap getRowsAsHashFromSection(WebElement table) {
        HashMap hashMap = new LinkedHashMap();
        String key, value;

        try {
            for (WebElement row : table.findElements(By.tagName("tr"))) {
                key = row.findElements(By.tagName("td")).get(0).getText();
                value = row.findElements(By.tagName("td")).get(1).getText().replace("\n", "").replace("Information", "");
                hashMap.put(key, value);
                LOGGER.info("Loading the section data with key: '" + key + "' value: '" + value + "'");
            }
        } catch (java.util.NoSuchElementException e) {
            throw new RuntimeException("Unable to load the elements from table " + table.getText());
        }

        return hashMap;
    }

    public void selectEditButton() {
        pageContainer.linkEdit.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='modal-progress'][contains(@style,'display: none')]")));
        VUtils.waitFor(2);
    }


    public void selectSendButton() {
        pageContainer.sendEditButton.click();
        VUtils.waitFor(5);
    }

    public void submitAllValidation() {
        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);", pageContainer.submitAllValidation);
        wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(pageContainer.submitAllValidation), ExpectedConditions.elementToBeClickable(pageContainer.submitAllValidationBy)));
        VUtils.waitFor(1); //to move to this element from bottom as this element appears at the top, if wait is removed, you will get an exception
        //try catch has been implemented since still WebDriverException is coming up
        try {
            pageContainer.submitAllValidation.click();
        } catch (Exception e) {
            VoyantaDriver.getCurrentDriver().navigate().refresh();
            VoyantaDriver.getCurrentDriver().findElement(By.linkText("Submit All for validation"));
        }
        VUtils.waitFor(3);
//       alert.isAlertPresent();
    }

    public void selectSubmitFromPopup() {
        alert.isAlertPresent();
    }

    public void submitAllForValidationClearance() {
        boolean check = VUtils.isElementPresent(pageContainer.submitAllValidationBy);
        if (check) {
                wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(pageContainer.submitAllValidation), ExpectedConditions.elementToBeClickable(pageContainer.submitAllValidation)));
                pageContainer.submitAllValidation.click();
                VUtils.waitFor(3);

                if (VUtils.isElementPresent(By.id("submitAll"))) {
                    alert.isAlertPresent();
                }
                VUtils.waitForElement(pageContainer.confirmMsg);
                String confirmationMsg = pageContainer.confirmMsg.getText();
                VerifyUtils.equals(confirmationMsg.trim(), "Changes were committed");
        }
        logout();
    }

    public void editTagsLink() {
        pageContainer.editTagLink.click();

    }

    public void editTagPopUp(String tagName, String box) {
        mDriver = VoyantaDriver.getCurrentDriver();
        try {
            String parentWindow = mDriver.getWindowHandle();
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                subWindow = iterator.next();
            }
            if (box.equals("Private Tags")) {
                mDriver.switchTo().window(subWindow);
                LOGGER.info("Editing PrivateTag to Object from PopUp");

                WebElement privateBox = mDriver.findElement(By.cssSelector("#tag_edit_chzn > ul.chzn-choices > li.search-field > input"));
                privateBox.click();
                privateBox.sendKeys(tagName);
                VUtils.waitFor(2);
                LOGGER.info("------Editing------" + tagName);
                WebElement tag = mDriver.findElement(By.className("VoyantaSelectList")).findElement(By.xpath("//div[@id='tag_edit_chzn']")).findElement(By.className("chzn-drop"));
                List<WebElement> options = tag.findElement(By.className("chzn-results")).findElements(By.tagName("li"));
                for (WebElement element : options) {
                    if (element.getText().equalsIgnoreCase(tagName)) {
                        LOGGER.info("Found the Tag with text " + tagName);
                        element.click();
                        VUtils.waitFor(2);
                        LOGGER.info("Saving Tag to an Object");
//                        WebElement save = mDriver.findElement(By.xpath("(//input[@id=''])[5]"));
//                        save.click();
                        return;
                    } else
//                        VerifyUtils.fail("Tag is not Exist");
                        LOGGER.info("Tag with text " + tagName + " Not Found");
                }
            } else if (box.equals("Public Tags")) {
                mDriver.switchTo().window(subWindow);
                LOGGER.info("Editing PublicTag to Object from PopUp");
                WebElement publicBox = mDriver.findElement(By.cssSelector("#tag_edit_public_chzn > ul.chzn-choices > li.search-field > input"));
                publicBox.click();
                publicBox.sendKeys(tagName);
                VUtils.waitFor(2);
                LOGGER.info("------Editing------" + tagName);
                WebElement tag = mDriver.findElement(By.className("VoyantaSelectList")).findElement(By.xpath("//div[@id='tag_edit_public_chzn']")).findElement(By.className("chzn-drop"));
                List<WebElement> options = tag.findElement(By.className("chzn-results")).findElements(By.tagName("li"));
                for (WebElement element : options) {
                    if (element.getText().equalsIgnoreCase(tagName)) {
                        LOGGER.info("Found the Tag with text " + tagName);
                        element.click();
                        VUtils.waitFor(2);
                        LOGGER.info("Saving Tag to an Object");
//                        WebElement save = mDriver.findElement(By.xpath("(//input[@id=''])[5]"));
//                        save.click();
                        return;
                    } else
//                        VerifyUtils.fail("Tag is not Exist");
                        LOGGER.info("Tag with text " + tagName + " Not Found");
                }
            }
            VUtils.waitFor(2);
            mDriver.switchTo().window(parentWindow);
        } catch (NoAlertPresentException n) {
            System.out.println("Tag Popup is Not Present : --");
            return;
        }
    }

    public void tagPresentToObject(String tagName, boolean b) {
        VerifyUtils.equals(b, isTagVisibleToTagsLabel(tagName));
    }

    public boolean isTagVisibleToTagsLabel(String tagName) {
        mDriver = VoyantaDriver.getCurrentDriver();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("object-top-actions")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tags")));
        WebElement tagElement = mDriver.findElement(By.className("object-top-actions")).findElement(By.className("tags"));
        List<WebElement> options = tagElement.findElements(By.className("tag"));
        for (WebElement element : options) {
            if (element.getText().contains(tagName)) {
                LOGGER.info("Tag is Added To the Object");
                return true;
            }
        }
        LOGGER.info("Given Tag :" + tagName + " is not Present ");
        return false;
    }

    public void selectAddNew() {
        if (!pageContainer.addNewObjectButton.isDisplayed()) {
            VerifyUtils.fail("User doesn't have permission to Create Object");
        } else {
            LOGGER.info("creating New Object");
            pageContainer.addNewObjectButton.click();
            VUtils.waitFor(2);
        }

    }

    public void checkIfFormPresent(String text) {
        mDriver = VoyantaDriver.getCurrentDriver();
        WebElement name = mDriver.findElement(By.xpath("//form/h1"));
        LOGGER.info("Form Available for New Object");
        VerifyUtils.True(name.getText().equals(text));
    }

    public void isAddnewButtonPresent(boolean b) {
        mDriver = VoyantaDriver.getCurrentDriver();
        VerifyUtils.equals(b, pageContainer.addNewObjectButton.isDisplayed());

    }

    public void removeTagFromObject(String tagName, String box) {
        mDriver = VoyantaDriver.getCurrentDriver();
        try {
            String parentWindow = mDriver.getWindowHandle();
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                subWindow = iterator.next();
            }
            if (box.equals("Private Tags")) {
                mDriver.switchTo().window(subWindow);
                LOGGER.info("Removing PrivateTag From an Object");
                WebElement tag = mDriver.findElement(By.cssSelector("#tag_edit_chzn > ul.chzn-choices "));
                List<WebElement> options = tag.findElements(By.tagName("li"));
                for (WebElement element : options) {
                    if (element.getText().equalsIgnoreCase(tagName)) {
                        LOGGER.info("Found the PrivateTag with text " + tagName);
                        element.findElement(By.className("search-choice-close")).click();
                        VUtils.waitFor(1);
                        LOGGER.info("Tag Removed From an Object");
//                        WebElement save = mDriver.findElement(By.xpath("(//input[@id=''])[5]"));
//                        save.click();
                        return;
                    } else
//                        VerifyUtils.fail("Tag is not Exist");
                        LOGGER.info("Tag with text " + tagName + " Not Found");
                }
            } else if (box.equals("Public Tags")) {
                mDriver.switchTo().window(subWindow);
                LOGGER.info("Removing PublicTag From an Object");
                WebElement tag = mDriver.findElement(By.cssSelector("#tag_edit_public_chzn > ul.chzn-choices"));
                List<WebElement> options = tag.findElements(By.tagName("li"));
                for (WebElement element : options) {
                    if (element.getText().equalsIgnoreCase(tagName)) {
                        LOGGER.info("Found the Tag with text " + tagName);
                        element.findElement(By.className("search-choice-close")).click();
                        VUtils.waitFor(1);
                        LOGGER.info("Tag Removed From an Object");
//                        WebElement save = mDriver.findElement(By.xpath("(//input[@id=''])[5]"));
//                        save.click();
                        return;
                    } else
//                        VerifyUtils.fail("Tag is not Exist");
                        LOGGER.info("Tag with text " + tagName + " Not Found");
                }
            }
            VUtils.waitFor(2);
            mDriver.switchTo().window(parentWindow);
        } catch (NoAlertPresentException n) {
            System.out.println("Tag Popup is Not Present : --");
            return;
        }
    }

    public void saveObjectTag() {
        VUtils.waitFor(2);
        mDriver.findElement(By.xpath("(//input[@id=''])[5]")).click();
        WaitUtils.loaderWait();
    }

    public void canSeeObjectBrowsePage(boolean b, String name) {
        LOGGER.info(name + " is Displaying on Browse Page ");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1/div[text()=\"" + name + "\"]")));
    }

    public void createObjectButton(boolean b) {
        LOGGER.info("Verifying Create Object Button Present");
        VerifyUtils.equals(b, pageContainer.addNewObjectButton.isDisplayed());
    }


    public void canDownload(boolean b) {
        LOGGER.info("Verifying Download Button is Enable");
        VerifyUtils.equals(b, pageContainer.linkDownload.isEnabled());
    }

    public void canAttachFile(boolean b) {
        LOGGER.info("Verifying Attach File Button is Enable");
        VerifyUtils.equals(b, !pageContainer.btnAttachFiles.findElement(By.xpath("parent::div")).getAttribute("class").contains("disabled"));
    }

    public void canShareButton(boolean b) {
        LOGGER.info("Verifying Share Button is Displayed");
        VerifyUtils.equals(b, VUtils.isElementPresent(By.linkText("Share")));
    }

    public void canEdit(boolean b) {
        LOGGER.info("Verifying Edit Button is Displayed");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Edit")));
        VerifyUtils.equals(b, !pageContainer.linkEdit.findElement(By.xpath("parent::div")).getAttribute("class").contains("disabled"));
    }

    public void canDelete(boolean b) {
        LOGGER.info("Verifying Delete Button is Enable");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='buttons-bar']/div")));
        VerifyUtils.equals(b, VUtils.isElementPresent(By.xpath("//div[@class='buttons-bar']/div[@title='Delete']")));
    }

    public void canCreateChild(boolean b) {
        LOGGER.info("Verifying Create Child Object Button is displayed ");
        VerifyUtils.equals(b, VUtils.isElementPresent(By.xpath("//a[@id='childObjectSelectorButton']/span/strong")));
    }

    public void canOpenChildObjectType(String objectName, String childObject, boolean b) {
        VerifyUtils.equals(b, isChildObjectPresent(objectName, childObject));
    }

    public boolean isChildObjectPresent(String objectName, String childObjectType) {
        WebElement parentElement;
        try {

            //  VoyantaDriver.getDivByText(name).click();
            VUtils.waitFor(2);
            parentElement = VoyantaDriver.findElement(By.linkText(objectName)).findElement(By.xpath("parent::*"));
            if (!parentElement.getText().contains(childObjectType)) {
                LOGGER.info("The child object type " + childObjectType + " is not visible so returning false....");
                return false;
            } else {
                LOGGER.info("The child object type " + childObjectType + " visible ");
                VUtils.waitFor(2);
                List<WebElement> links = VoyantaDriver.findElements(By.xpath("//div[contains(text(),'" + childObjectType + "')]"));

                if (links.size() > 1) {
                    VoyantaDriver.findElement(By.xpath("//div[contains(text(),'" + childObjectType + "')][starts-with(text(),'" + childObjectType + "')]")).click();
                } else {
                    VoyantaDriver.getLinkByText(childObjectType).click();
                }
                VUtils.waitFor(3);
                return true;

            }
        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to load the data for object data in time  : " + objectName);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Unable to load the data for object data : " + objectName);
        }
    }

    public void canOpenSpecificChildObjectType(String objectName, String childObject, String childObjectName, boolean b) {
        VerifyUtils.equals(b, isSpecificChildObjectPresent(objectName, childObject, childObjectName));
    }

    public boolean isSpecificChildObjectPresent(String objectName, String childObject, String childObjectName) {
        WebElement parentElement;
        try {
            VUtils.waitFor(2);
            parentElement = VoyantaDriver.findElement(By.linkText(objectName)).findElement(By.xpath("parent::*"));
            if (!parentElement.getText().contains(childObject)) {
                LOGGER.info("The child object Type  " + childObject + " is not visible so returning false....");
                return false;
            } else {
//                LOGGER.info("The child object Name "+childObject+" visible ");
//                VUtils.waitFor(2);
//                VoyantaDriver.getLinkByText(childObjectName).click();
//                VUtils.waitFor(5);
                parentElement = VoyantaDriver.findElement(By.linkText(objectName)).findElement(By.xpath("parent::*"));

                if (!parentElement.getText().contains(childObjectName)) {
                    LOGGER.info("The child object name " + childObjectName + " is not visible so returning false....");
                    return false;
                } else {
                    LOGGER.info("The child object name " + childObjectName + " is visible ");
                    LOGGER.info("Opening " + childObjectName);
                    VoyantaDriver.getLinkByText(childObjectName).click();
                    VUtils.waitFor(2);
                }
                return true;

            }
        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to load the data for object data in time - : " + childObject);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Unable to load the data for object data - : " + childObject);
        }
    }

    public void selectCreateObjectButtonFrmBrowse() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.addNewObjectButton));
        pageContainer.addNewObjectButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='newObject']/h1[text()='Add New']")));
    }

    public void clickOnEditOwner() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.editOwner));
        pageContainer.editOwner.click();
    }

    public void editedMessage(String message) {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.editMessage));
        WebElement editMessage = pageContainer.editMessage;
        VerifyUtils.equals(editMessage.getText(), message);
    }

    public void enterChangeOwnerEmail(String email) {
        pageContainer.changeOwnerEmail.click();
        VUtils.waitFor(5);
        pageContainer.changeOwnerEmail.sendKeys(email);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='emailAddress_chzn']/a/span[text()='" + email + "']")));
        VoyantaDriver.getCurrentDriver().findElement(By.xpath("//*[@id='emailAddress_chzn']/a/span[text()='" + email + "']")).click();
    }

    public void clickChangeOwnerSave() {
        VUtils.waitFor(5);
        wait.until(ExpectedConditions.elementToBeClickable(VoyantaDriver.getCurrentDriver().findElement(By.xpath(save))));
        VoyantaDriver.getCurrentDriver().findElement(By.xpath(save)).click();
        VUtils.waitFor(2);
    }

    public boolean isObjectOwner(String name) {
        return pageContainer.owner.getText().contains(name);
    }

    public void clickChangeOwnerCancel() {
        VUtils.waitFor(5);
        wait.until(ExpectedConditions.elementToBeClickable(VoyantaDriver.getCurrentDriver().findElement(By.xpath(cancel))));
        VoyantaDriver.getCurrentDriver().findElement(By.xpath(cancel)).click();
        VUtils.waitFor(2);
    }

    public void addChildObject(String object) {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.childObjectDropDown));
        pageContainer.childObjectDropDown.click();
        for (WebElement option : pageContainer.childObjectDropDownOptions) {
            if (option.getText().equals(object)) {
                wait.until(ExpectedConditions.elementToBeClickable(option));
                option.click();
                break;
            }
        }
        VUtils.waitFor(3);
        pageContainer.createChildObjectButton.click();
        VUtils.waitFor(3);
    }

    public boolean isAutoGeneratedReferencePresent(String referenceType, String reference) {
        String[] referenceTypeAsArray = referenceType.split(",");
        String[] referenceAsArray = reference.split(",");
        for (int i = 0; i < referenceTypeAsArray.length; i++) {
            if (!VoyantaDriver.findElement(By.xpath("//td[@id='" + referenceTypeAsArray[i] + "']/input")).getAttribute("value").equals(referenceAsArray[i])) {
                return false;
            }
        }
        return true;
    }

    public void clickOnAutoGenerateButton() {
        pageContainer.autoGenerateButton.click();
        VUtils.waitFor(1);
    }

    public void fillInMandatoryFields(String[] fields, String[] values) {
        for (int i = 0; i < fields.length; i++) {
            try {
                VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + fields[i] + "']/input[not(contains(@readonly,'readonly'))]")).sendKeys(values[i]);
            } catch (NoSuchElementException e) {
                try {
                    WebElement dateField = VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + fields[i] + "']/input[contains(@readonly,'readonly')]"));
                    ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].setAttribute('value','" + values[i] + "')", dateField);
                } catch (NoSuchElementException f) {
                    try {
                        VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + fields[i] + "']/div[2]/div/a")).click();
                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='chzn-results']/li[text()='" + values[i] + "']")));
                        VoyantaDriver.getCurrentDriver().findElement(By.xpath("//ul[@class='chzn-results']/li[text()='" + values[i] + "']")).click();
                        VUtils.waitFor(2);
                    } catch (NoSuchElementException g) {
                        fail("Failed to enter " + fields[i] + " with value " + values[i]);
                    }
                }

            }
        }
    }

    public void updateField(String field, String newValue) {

        if(VUtils.isElementPresent(By.xpath("//td[@id='" + field + "']/input[not(contains(@readonly,'readonly'))]"))) {
            VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + field + "']/input[not(contains(@readonly,'readonly'))]")).clear();
            VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + field + "']/input[not(contains(@readonly,'readonly'))]")).sendKeys(newValue);
        } else if (VUtils.isElementPresent(By.xpath("//td[@id='" + field + "']/input[contains(@readonly,'readonly')]"))) {
            WebElement dateField = VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + field + "']/input[contains(@readonly,'readonly')]"));
            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].setAttribute('value','" + newValue + "')", dateField);
        } else if (VUtils.isElementPresent(By.xpath("//td[@id='" + field + "']/div/div/a"))) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id='" + field + "']/div/div/a")));
            try {
                ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);", VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + field + "']/div/div/a")));
                VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + field + "']/div/div/a")).click();
            } catch (Exception e) {
                ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
                Actions actions = new Actions(VoyantaDriver.getCurrentDriver());
                actions.moveToElement(VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + field + "']/div/div/a"))).build().perform();
                actions.click(VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + field + "']/div/div/a"))).build().perform();
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id='" + field + "']/div/div/div/ul[@class='chzn-results']/li[text()='" + newValue + "']")));
            //((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);", VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + field + "']/div/div/div/ul[@class='chzn-results']/li[text()='" + newValue + "']")));
            VoyantaDriver.getCurrentDriver().findElement(By.xpath("//td[@id='" + field + "']/div/div/div/ul[@class='chzn-results']/li[text()='" + newValue + "']")).click();
        } else {
            fail("Failed to enter " + field + " with value " + newValue);
        }
    }

    public void saveCreateChildObject() {
        pageContainer.saveChildObject.click();
        //Waiting here for Submission to get approve on Submission page
        VoyantaDriver.waitFor(10000);
    }

    public void clickDownloadButton() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.linkDownload));
        pageContainer.linkDownload.click();
    }

    public void selectDeleteButton() {
        pageContainer.deleteButton.click();
        VUtils.waitFor(2);
    }

    public void selectYesToDelete() {
        try {
            mDriver = VoyantaDriver.getCurrentDriver();
            String parentWindow = mDriver.getWindowHandle();
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                subWindow = iterator.next();
            }
            mDriver.switchTo().window(subWindow);
            System.out.println("Popup is Present : --");
            VUtils.waitFor(1);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@id='dialog-button-yes'])[2]")));
            mDriver.findElement(By.xpath("(//input[@id='dialog-button-yes'])[2]")).click();
            VUtils.waitFor(2);
            mDriver.switchTo().window(parentWindow);

        } catch (NoAlertPresentException n) {
            System.out.println("Popup is Not Present : --");
            return;
        }
    }

    public void selectShareButton() {
        pageContainer.linkShare.click();
        LOGGER.info("Selecting Share the Object");
        VUtils.waitFor(2);
    }

    public void selectLevelOfAccess(String levelOfAccess) {
        try {
            mDriver = VoyantaDriver.getCurrentDriver();
            String parentWindow = mDriver.getWindowHandle();
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                subWindow = iterator.next();
            }
            mDriver.switchTo().window(subWindow);
            LOGGER.info("Select the Level Of Access from the Pop Up ");
            if (levelOfAccess.equals("Approver")) {
                mDriver.findElement(By.id("permission-radio-3")).click();
                LOGGER.info("Sharing the Object as : " + levelOfAccess);
            } else if (levelOfAccess.equals("Submitter")) {
                mDriver.findElement(By.id("permission-radio-2")).click();
                LOGGER.info("Sharing the Object as : " + levelOfAccess);
            } else {
                mDriver.findElement(By.id("permission-radio-1")).click();
                LOGGER.info("Sharing the Object as : " + levelOfAccess);
            }
            VUtils.waitFor(2);
//            mDriver.switchTo().window(parentWindow);

        } catch (NoAlertPresentException n) {
            System.out.println("Level Of Access Pop up is not Present  : --");
            return;
        }
    }

    public void selectContinueForSharing() {
        mDriver = VoyantaDriver.getCurrentDriver();
        mDriver.findElement(By.cssSelector("a.continue-link > span")).click();
        VUtils.waitFor(2);
    }

    public void selectToAddChildObject(String childObject) {
        VoyantaDriver.findElement(By.xpath("//div[@id='childObjectSelector_chzn']/a")).click();
        WebElement childList = VoyantaDriver.findElement(By.id("childObjectSelector_chzn")).findElement(By.className("chzn-drop"));
        List<WebElement> options = childList.findElement(By.className("chzn-results")).findElements(By.className("active-result"));
        for (WebElement e : options) {
            if (e.getText().equals(childObject)) {
                e.click();
                LOGGER.info("Adding the Child Object : " + e.getText());
                VUtils.waitFor(1);
            }
        }
    }

    public void selectAddNewChildButton() {
        VoyantaDriver.findElement(By.xpath("//*[@id='childObjectSelectorButton']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='modal-progress'][contains(@style,'display: none')]")));
        VUtils.waitFor(2);
    }

    public void confirmChildAddNewPageOpen() {
        LOGGER.info("The user will see the Add New Page for Child Object");
        VerifyUtils.equals(VoyantaDriver.findElement(By.xpath("//*[@id='newObject']/h1")).getText(), "Add New");
    }

    public void selectChildType(String objectName, String childType) {
        WebElement parentElement;
        try {
            VUtils.waitFor(2);
            parentElement = VoyantaDriver.findElement(By.linkText(objectName)).findElement(By.xpath("parent::*"));
            if (!parentElement.getText().contains(childType)) {
                LOGGER.info("The child object Type  " + childType + " is not visible ...");
            } else {
                LOGGER.info("The child object Type " + childType + " is visible ");
                LOGGER.info("Opening " + childType);
                VoyantaDriver.getLinkByText(childType).click();
                VUtils.waitFor(2);
            }
        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to load the data for object data in time - : " + childType);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Unable to load the data for object data - : " + childType);
        }

    }

    public void selectChildObject(String objectName, String childType, String childObjectName) {
        WebElement parentElement;
        try {
            VUtils.waitFor(2);
            parentElement = VoyantaDriver.findElement(By.linkText(objectName)).findElement(By.xpath("parent::*"));
            if (!parentElement.getText().contains(childType)) {
                LOGGER.info("The child object Type  " + childType + " is not visible so returning false....");
            } else {
                parentElement = VoyantaDriver.findElement(By.linkText(objectName)).findElement(By.xpath("parent::*"));

                if (!parentElement.getText().contains(childObjectName)) {
                    LOGGER.info("The child object name " + childObjectName + " is not visible so returning false....");
                    VerifyUtils.fail("The Child Object is not Present !!! ");
                } else {
                    LOGGER.info("The child object name " + childObjectName + " is visible ");
                    LOGGER.info("Opening " + childObjectName);
                    VoyantaDriver.getLinkByText(childObjectName).click();
                    VUtils.waitFor(2);
                }
            }
        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to load the data for object data in time - : " + childType);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Unable to load the data for object data - : " + childType);
        }
    }

    public String getValueForField(String labelName) {
        String value;
        if (Objects.equals(labelName, "Comments")) {
            value = VoyantaDriver.getCurrentDriver().findElement(By.xpath("//*[text()='" + labelName + "']/parent::*/following-sibling::div[1]")).getText();
        } else if (Objects.equals(labelName, "Credit Rating") || Objects.equals(labelName, "Credit Rating Source")) {
            value = VoyantaDriver.getCurrentDriver().findElement(By.xpath("//*[text()='" + labelName + "']/parent::*/following-sibling::*/div[1]")).getText();
        } else {
            value = VoyantaDriver.getCurrentDriver().findElement(By.xpath("//*[text()='" + labelName + "']/parent::*/following-sibling::*[1]")).getText();
        }
        if (value.contains("\nInformation"))
            value = value.replaceAll("\nInformation", "");
        else
            value = value.replaceAll("Information", "");
        return value;
    }

    public void checkSpellingChange(String section, String spellingChange) {
        HashMap data = getSectionData(section);
        if (data.containsKey(spellingChange)) {
            LOGGER.info("Found field " + spellingChange);
        } else {
            VerifyUtils.fail("Field not found " + spellingChange);
        }
    }

    public void searchOnBrowse(String halfName) {
        WebElement searchBox = VoyantaDriver.findElement(By.cssSelector("input.search"));
        searchBox.click();
        LOGGER.info("Search for " + halfName + " On BrowseTree");
        searchBox.sendKeys(halfName);
        VoyantaDriver.findElement(By.cssSelector("input.submit")).click();
        VUtils.waitFor(2);
    }

    public void firstBrowseResult(String objectName) {
        WebElement list;
        list = VoyantaDriver.findElement(By.id("sidebar"));
        List<WebElement> noOfList = list.findElement(By.className("results")).findElements(By.tagName("li"));
        for (WebElement e : noOfList) {
            if (e.getText().equals(objectName)) {
                LOGGER.info("Found the Object in BrowseTree : " + objectName);
                return;
            } else {
                VerifyUtils.fail("This is not the Latest Object : " + e.getText());
            }
        }
    }

    public void selectOrder(String orderBy) {
        WebDriver driver = VoyantaDriver.getCurrentDriver();
        driver.findElement(By.cssSelector("a.chzn-single.chzn-default > span")).click();
        WebElement options = VoyantaDriver.findElement(By.id("orderby_chzn")).findElement(By.className("chzn-drop"));
        List<WebElement> listOptions = options.findElement(By.className("chzn-results")).findElements(By.tagName("li"));
        for (WebElement e : listOptions) {
            if (e.getText().equalsIgnoreCase(orderBy)) {
                e.click();
                VUtils.waitFor(2);
                LOGGER.info("Selecting an Order By : " + e.getText());
            }
        }

    }

    public void enterTagInDisplay(String tagName) {
        VoyantaDriver.findElement(By.xpath("//div[@id='tag_name_chzn']/a/span")).click();
        VUtils.waitFor(1);
        WebElement name = VoyantaDriver.findElement(By.cssSelector("div.chzn-search > input.ui-autocomplete-input"));
        name.sendKeys(tagName);
        VUtils.waitFor(1);
        WebElement results = VoyantaDriver.findElement(By.id("tag_name_chzn")).findElement(By.className("chzn-drop")).findElement(By.cssSelector("ul.chzn-results"));
        WebElement tagResult = results.findElement(By.tagName("li"));
        if (tagResult.getText().equalsIgnoreCase(tagName)) {
            LOGGER.info("Found the Tag in Display : " + tagResult.getText());
            tagResult.click();
            return;
        }

    }

    public void browseTagSearchResult(String results) {
        String[] resultList = results.split(",");
        WebElement list;
        list = VoyantaDriver.findElement(By.id("sidebar"));
        List<WebElement> noOfList = list.findElement(By.className("results")).findElements(By.tagName("li"));
        for (int i = 0; i < resultList.length; i++) {
            for (WebElement e : noOfList) {
                if (e.getText().equals(resultList[i])) {
                    LOGGER.info("Found the Tag Object in BrowseTree : " + resultList[i]);
                }
            }
//            VerifyUtils.fail("The Tag Object is not Presnt in the Browse Tree Result");
        }
    }

    public void saveEditedObject() {
        ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);", pageContainer.saveEditObject);
        wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(pageContainer.saveEditObject), ExpectedConditions.elementToBeClickable(pageContainer.saveEditObject)));
        pageContainer.saveEditObject.click();
    }

    public void canSeeSubmitAllForValidationMessage(String message) {
        if (VUtils.isElementPresent(By.xpath("//*[@id='content']/div[2]/ul/li")))
            VerifyUtils.equals(VoyantaDriver.findElement(By.xpath("//*[@id='content']/div[2]/ul/li")).getText(), message);
    }

    public void canSeeConfirmationMessage(String message) {
        try {
            wait.ignoring(java.util.NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.or(ExpectedConditions.visibilityOf(pageContainer.confirmMsg), ExpectedConditions.textToBePresentInElement(pageContainer.confirmMsg, message)));
            String confirmationMsg = pageContainer.confirmMsg.getText();
            VerifyUtils.equals(confirmationMsg.trim(), message);
        } catch (Exception e) {
            e.getMessage();
            LOGGER.info("Couldn't find the confirmation message "+message+" as it would have been disappeared which is not an issue unless next step is passing, instead an exception is thrown : "+e.getMessage());
        }
    }

    /**
     * @param type dst type must be in singular form!
     */
    public void openSubType(String type) {
        LOGGER.info("Looking for subtype: " + type);
        List<WebElement> subTypes = VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".object-list.main")).findElement(By.cssSelector(".object-list")).findElements(By.tagName("a"));
        for (WebElement subType : subTypes) {
            LOGGER.info("Subtype: " + subType.getAttribute("data-dst-type-name"));
            if (subType.getAttribute("data-dst-type-name").equals(type)) {
                subType.click();
            }
        }
    }

    public void clickAttachFiles() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.btnAttachFiles));
        pageContainer.btnAttachFiles.click();
        /*wait for page to get load*/
        //VUtils.waitFor(3);
    }

    public void waitAttachFiles() {
        //Wait for Upload Done element
        wait.until(ExpectedConditions.visibilityOf(pageContainer.UploadDone));
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.btnAttachFiles));
        /*wait for page to get load*/
    }

    //file upload - tested on Firefox browser
    public void fileUpload(String filePath, String folder) throws AWTException {
        //wait for file name input field as upload button appears invisible
        wait.until(ExpectedConditions.visibilityOf(pageContainer.fileNameInput));
        mDriver = VoyantaDriver.getCurrentDriver();
        //Javascript executor to make element visible to send filepath name
        WebElement elem = pageContainer.uploadButton;
        String js = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";
        ((JavascriptExecutor) mDriver).executeScript(js, elem);
        //Check file exists in folder
        final String filePath1 = checkFileExist(filePath, folder);
        //Opens windows explorer dialog at filepath1 location
        elem.sendKeys(filePath1);
        //Adds filepath to clipboard
        StringSelection ss = new StringSelection(filePath1);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        //Pastes clipboard contents to windows explorer
        Robot robot = new Robot();
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

    public void selectFile(String fileName, String folder) {
        final String filePath = checkFileExist(fileName, folder);

        setClipboardData(filePath);
        LOGGER.info("Uploading the file from location : " + filePath);

        pageContainer.uploadButton.sendKeys(filePath);
        VoyantaDriver.waitFor(10);

        if (pageContainer.sendButton.getAttribute("disabled").equals("true")) {
            VoyantaDriver.waitFor(10);
            pageContainer.uploadButton.click();
            VoyantaDriver.waitFor(2);
            setClipboardData(filePath);

            Robot robot = null;

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
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectFileDocumentsPage(String fileName, String folder)  {
        int size = pageContainer.documentNames.size();
        final String filePath = checkFileExist(fileName, folder);
        setClipboardData(filePath);
        LOGGER.info("Uploading the file from location : " + filePath);

        pageContainer.uploadFile.sendKeys(filePath);

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".first-column.doc-name"), size));

        for (int i = 0; i < pageContainer.documentNames.size(); i++) {
            if (pageContainer.documentNames.get(i).getText().trim().contains(fileName.split("\\.")[0])) { //need to split to remove the extension
                Assert.assertTrue("Document is not uploaded...", pageContainer.documentNames.get(i).getText().trim().contains(fileName.split("\\.")[0]));
                break;
            }
        }
    }

    public void selectFileToAttach(String fileName, String folder) {
        final String filePath = checkFileExist(fileName, folder);

        setClipboardData(filePath);
        LOGGER.info("Uploading the file from location : " + filePath);

        if (pageContainer.sendButton.getAttribute("disabled").equals("true")) {
            VoyantaDriver.waitFor(10);
            //wait.until(ExpectedConditions.elementToBeClickable(pageContainer.uploadButton));
            pageContainer.uploadButton.click();
            VoyantaDriver.waitFor(2);

            StringSelection ss = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

            Robot robot = null;

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
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enterFileName(String fileName) {
        //pageContainer.fileNameInput.clear();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.fileNameInput));
        pageContainer.fileNameInput.sendKeys(fileName);
    }

    public void confirmAttachFile() {
        //Waits for send button to become available to click after file uploaded
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.sendButton));
        pageContainer.sendButton.click();
        VUtils.waitFor(3);
    }

    public void checkDocumentVisible(String fileName, boolean expectedVisible) {
        if (expectedVisible) {
            try {
                final HashMap data = getSectionData("Documents");
                if (!data.containsKey(fileName)) {
                    VerifyUtils.fail("Document " + fileName + " is not visible.");
                }
            } catch (RuntimeException e) {
                VerifyUtils.fail("No documents section. Document " + fileName + " is not visible.");
            }
        } else {
            try {
                final HashMap data = getSectionData("Documents");
                if (data.containsKey(fileName)) {
                    VerifyUtils.fail("Document " + fileName + " is visible.");
                }
            } catch (RuntimeException e) {
                LOGGER.info("No Documents section.");
            }
        }
    }

    public boolean getBool(String canOrNot) {
        boolean bool = false;
        if (canOrNot == "can") {
            bool = false;
        } else if (canOrNot == "cannot") {
            bool = true;
        }
        return bool;
    }

    public boolean checkDocumentVisible(String fileName, String canOrNot) {
        List<WebElement> docName = pageContainer.deleteDocumentLink;
        boolean docObj = docName.stream().anyMatch(doc -> doc.getText().equalsIgnoreCase(fileName));
        boolean bool = getBool(canOrNot);

        switch (canOrNot) {
            case "can":
                if (docObj == true) {
                    bool = true;
                    break;
                }
            case "cannot":
                if (docObj == false) {
                    bool = false;
                    break;
                }
            default:
                getBool(canOrNot);
        }

        return bool;

        /*
        if(expectedVisible) {
            try {
                final HashMap data = getSectionData("Documents");
                if(!data.containsKey(fileName)) {
                    VerifyUtils.fail("Document " + fileName + " is not visible.");
                }
            } catch (RuntimeException e) {
                VerifyUtils.fail("No documents section. Document " + fileName + " is not visible.");
            }
        } else {
            try {
                final HashMap data = getSectionData("Documents");
                if(data.containsKey(fileName)) {
                    VerifyUtils.fail("Document " + fileName + " is visible.");
                }
            } catch (RuntimeException e) {
                LOGGER.info("No Documents section.");
            }
        }*/
    }

    public void deleteAttachedFile(String fileName) {
        JavascriptExecutor js = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", pageContainer.documentsNameLink.get(0));
        /*
        actions.moveToElement(deleteDocumentLink.get(0));
        actions.build().perform();*/
        //pageContainer.deleteDocumentLink.get(0).click();
        //List<WebElement> rows = table.findElements(By.tagName("tr"));

        try {
            pageContainer.deleteDocumentLink.get(0).click();
            LOGGER.info("Delete document " + fileName);
            /*
            for (WebElement row : table.findElements(By.tagName("tr"))) {
                if (row.findElements(By.tagName("td")).get(0).getText().equals(fileName)) {
                    row.findElement(By.linkText("Delete")).click();
                    VoyantaDriver.findElement(By.id("dialog-button-yes")).click();
                    LOGGER.info("Delete document " + fileName);
                }
            }*/

        } catch (Exception e) {
            throw e;
        }
    }

    public void selectLovForFileToAttach(String lovName) {
        WebElement dropdown = VoyantaDriver.findElement(By.id("documentType_chzn"));
        dropdown.findElement(By.tagName("a")).click();

        for (WebElement lov : dropdown.findElements(By.tagName("li"))) {
            if (lov.getAttribute("innerHTML").equals(lovName)) {
                lov.click();
                break;
            }
        }
    }

    public void selectAttachedFile(String fileName) {
        WebElement table = VoyantaDriver.findElement(By.xpath("//h1[text()='Documents']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::*")).findElement(By.tagName("table"));

        for (WebElement row : table.findElements(By.tagName("tr"))) {
            if (row.findElements(By.tagName("td")).get(0).getText().equals(fileName)) {
                row.findElements(By.tagName("td")).get(0).findElement(By.tagName("a")).click();
                LOGGER.info("Select document " + fileName);
            }
        }
    }

    public void checkAttachedFileType(String type) {
        String dropdownSelectedType = VoyantaDriver.findElement(By.id("documentType_chzn")).findElement(By.tagName("a")).getText();
        VerifyUtils.equals(dropdownSelectedType, type);
    }

    public void ableToSeeChildObject(String objectName, String objectType, boolean b) {
        VerifyUtils.equals(b, isAbleToSeeChildObject(objectName, objectType));
    }

    public boolean isAbleToSeeChildObject(String objectName, String objectType) {
        WebElement element = VoyantaDriver.findElement(By.linkText(objectName)).findElement(By.xpath("parent::*"));
        if (!element.getText().contains(objectType)) {
            LOGGER.info("The Given Child Object " + objectType + "  not Visible ");
            return false;
        } else {
            LOGGER.info("The Given Child Object " + objectType + "  is Visible ");
            return true;
        }
    }

    public boolean isEditOwnerLinkPresent() {
        try {
            return pageContainer.editOwner.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Edit Owner link is not Visible");
        }
        return false;
    }

    public boolean isObjectCommitMessageDisplayed(String message) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='message-list']/li[text()='" + message + "']")));
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isBrowsePageLoaded(String object) {
        WebDriverWait shortwait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 5);
        try {
            shortwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='object-widget']//h1/div[text()='" + object + "']")));
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public void ableToSeeObjectFiled(String objectType, boolean b) {
        VerifyUtils.equals(b, isAbleToSeeObjectFields(objectType));
    }

    public boolean isAbleToSeeObjectFields(String objectType) {
        boolean value = false;
        int Count = 0;
        int maxCount = 10;
        try {
            List<WebElement> element = VoyantaDriver.findElements(By.xpath("//td/div[contains(text(),'" + objectType.trim() + "')]"));
            for (WebElement obj : element) {
                LOGGER.info("obj element value: " + obj.getText());
                if (obj.getText().trim().equals(objectType.trim())) {
                    LOGGER.info("The Given Object " + objectType + "  Visible ");
                    value = true;
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            if (++Count == maxCount)
                throw e;
        }
        return value;
    }

    public void clickBuildingDocumentsIcon(String building) {
        LOGGER.info("***About to click the "+building+" building's documents icon***");
        pageContainer.buildingDocuments(building).click();
        WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 30);
        wait.until(ExpectedConditions.urlMatches("/ng/documents/"));
        wait.until(ExpectedConditions.titleContains("- Documents Page -"));
        if (VoyantaDriver.getCurrentDriver().getCurrentUrl().contains("/ng/documents/") && VoyantaDriver.getCurrentDriver().getTitle().contains("- Documents Page -")) {
            LOGGER.info("+++Navigated to Documents Page of the "+building+" building+++");
        } else {
            LOGGER.info("---Incorrect Documents Page URL "+VoyantaDriver.getCurrentDriver().getCurrentUrl()+ "---");
        }

        Assert.assertTrue("Not on the correct building's documents page", pageContainer.buildingNameOnDocumentsPage.isDisplayed() && pageContainer.buildingNameOnDocumentsPage.getText().equalsIgnoreCase(building));
    }

    public void documentsTable() {
        super.tableElement = pageContainer.documentsTableElement;
    }

    public String getValueForDSTColumn(int row, String columnName) {
        return this.getCellElementWithRow(row, columnName).getText().replace(",", "");
    }

    public void checkBoxToSelectAllDocuments() {
        LOGGER.info("***Verification of check box being present to select all the documents attached***");
        Assert.assertTrue("Check box to select all documents is missing", pageContainer.checkBoxToSelectAllDocuments.isEnabled());
    }

    public void clickCheckBoxToSelectAllDocuments() {
        LOGGER.info("***To select the check box being present to select all the documents attached***");
        pageContainer.clickCheckBoxToSelectAllDocuments.click();
        Assert.assertTrue("Check box to select all documents is not selected", pageContainer.clickCheckBoxToSelectAllDocuments.getAttribute("class").contains("checked"));
    }

    public void documentActions(String filename, String actions) {
        LOGGER.info("***To click on the ... of the document "+filename+" to view the list of actions***");
        pageContainer.documentsActionsClick(filename).click();

        LOGGER.info("***To verify the list of actions of document "+filename+" ***");
        List<WebElement> list = pageContainer.documentsActions(filename);
        String[] expectedActionList = actions.split(",");
        List<String> expectedActionsList = new ArrayList<>();
        expectedActionsList.addAll(Arrays.asList(expectedActionList));

        List<String> actualActionsList = new ArrayList<>();
        for (WebElement action : list) {
            actualActionsList.add(action.getText());
        }
        Assert.assertTrue("Actual action list " + actualActionsList + " is not same as the expected one " + expectedActionsList + " for this document " + filename, expectedActionsList.equals(actualActionsList));
        pageContainer.documentsActionsClick(filename).click();
    }

    public void clickPreview(String filename) {
        LOGGER.info("***To click on Preview link for the document "+filename+" to be opened***");
        pageContainer.previewDocument(filename).click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.previewDownloadFile));
    }

    public void previewDocument(String filename) {
        LOGGER.info("***Verifying the Preview functionality***");
        Assert.assertTrue("Preview filename is incorrect", pageContainer.previewFile.getText().contains(filename));
        Assert.assertTrue("Preview download icon is missing", pageContainer.previewDownloadFile.isDisplayed());
        Assert.assertTrue("Preview close icon is missing", pageContainer.previewCloseFile.isDisplayed());

        LOGGER.info("*** To close the Preview of document "+filename+" ***");
        pageContainer.previewCloseFile.click();
//        wait.until(ExpectedConditions.visibilityOf(pageContainer.addDocumentsButton));
//        Assert.assertTrue("Preview window is still open even after closing it", pageContainer.addDocumentsButton.isDisplayed());

        boolean status = false;

        try {
            if (pageContainer.previewDownloadFile.isDisplayed())
                status = false;
        } catch (NoSuchElementException e) {
            status = true;
        }
        Assert.assertTrue("Preview window is still open even after closing it", status);
    }

    public void clickActions(String filename) {
        LOGGER.info("***To click on Actions ... link for the document "+filename+" ***");
        if(pageContainer.documentsActionsClick(filename).isDisplayed())
            pageContainer.documentsActionsClick(filename).click();
    }

    public void clickRename(String filename) {
        LOGGER.info("***To click on Rename link for the document "+filename+" to be renamed***");
        pageContainer.renameDocument(filename).click();
    }

    public void renameDocument(String rename) {
        LOGGER.info("***Verifying the Rename functionality***");
        pageContainer.renameDocument().sendKeys(rename);
        pageContainer.renameDocument().sendKeys(Keys.TAB);
        pageContainer.renameDocument().sendKeys(Keys.ENTER);
        VUtils.waitFor(1); //Please don't remove this wait for 1 sec as its really required to rename the file and click on actions --> Download (or any other actions) otherwise 'org.openqa.selenium.TimeoutException: Expected condition failed: Could not find Element (tried for 30 second(s) with 1 SECONDS interval)' error
        pageContainer.buildingName.click();
    }

    public void assertRename(String originalName, String rename) {
        LOGGER.info("***To verify that the document "+originalName+" has been renamed to "+rename+" successfully***");
        Assert.assertTrue("Document has not been renamed", pageContainer.documentName.getText().equals(rename) || !pageContainer.documentName.getText().equals(originalName));
    }

    public void downloadDocument(String filename) throws InterruptedException {
        Actions actions = new Actions(VoyantaDriver.getCurrentDriver());
        LOGGER.info("***To click on Download link for the document "+filename+" to be downloaded***");
        VUtils.waitFor(1); // wait to avoid StaleElementReferenceException
        wait.until(ExpectedConditions.visibilityOf(pageContainer.documentsActionsClick(filename)));
        if(pageContainer.documentsActionsClick(filename).isDisplayed()) {
            actions.moveToElement(pageContainer.documentsActionsClick(filename)).build().perform();
            actions.click(pageContainer.documentsActionsClick(filename)).build().perform();
        }
        try {
            wait.until(ExpectedConditions.visibilityOf(pageContainer.downloadDocument(filename)));
            VUtils.waitFor(1); //this 1 second wait is required to open up the ... actions menu otherwise staleElementReferenceException
            pageContainer.downloadDocument(filename).click();
        } catch (Exception e) {
            pageContainer.documentsActionsClick(filename).click();
            pageContainer.downloadDocument(filename).click();
        }
        VUtils.waitFor(1); //this 1 second wait is required to download the document
    }

    public void downloadDocumentWithPreview(String filename) {
        LOGGER.info("***To click on Download link for the document "+filename+" to be downloaded***");
        if(pageContainer.documentsActionsClick(filename).isDisplayed())
            pageContainer.documentsActionsClick(filename).click();
        pageContainer.downloadDocument(filename).click();
        VUtils.waitFor(1);
        if (pageContainer.previewDownloadFile.isDisplayed())
            pageContainer.previewDownloadFile.click();
    }

    public void downloadCheckForExcel(String expectedFileName, String actualFileName) {
        LOGGER.info("***To verify that the document "+expectedFileName+" has been downloaded successfully***");
        Assert.assertTrue("Downloaded file/document is not the expected one as Expected File/Document is "+expectedFileName+" but the Actual File/Document is "+actualFileName, expectedFileName.trim().contains(actualFileName.trim()));
    }

    public void downloadCheck(String expectedFileName, String actualFileName) {
        LOGGER.info("***To verify that the document "+expectedFileName+" has been downloaded successfully***");
        Assert.assertTrue("Downloaded file/document is not the expected one as Expected File/Document is "+expectedFileName+" but the Actual File/Document is "+actualFileName, expectedFileName.trim().contains(actualFileName.trim()));
    }

    public void deleteDocument(String filename, int number) {
        LOGGER.info("***To click on Delete link for the document "+filename+" to be deleted***");
        VUtils.waitFor(1); // this wait is required else org.openqa.selenium.WebDriverException: unknown error: Element <i _ngcontent-c17="" aria-haspopup="true" aria-hidden="true" class="menu-column-icon icon-ellipsis-h dropdown-toggle" ngbdropdowntoggle="" aria-expanded="false"></i> is not clickable at point (1478, 352). Other element would receive the click: <div _ngcontent-c20="" class="preview-overlay">...</div>
        if(pageContainer.documentsActionsClick(filename).isDisplayed())
            pageContainer.documentsActionsClick(filename).click();
        pageContainer.deleteDocument(filename).click();
        VoyantaDriver.getCurrentDriver().switchTo().activeElement();
        deleteDialog(number);
        pageContainer.deleteDialogDeleteButton.click();
        VUtils.waitFor(1); // this wait is required to delete the document
        VoyantaDriver.getCurrentDriver().navigate().refresh();
        wait.until((ExpectedCondition<Boolean>) afterDeleteWait -> {
            boolean status = false;
            if (!pageContainer.documentNames.isEmpty()) {
                for (int i = 0; i < pageContainer.documentNames.size(); i++) {
                    if (pageContainer.documentNames.get(i).getText().trim().contains(filename)) {
                        status = false;
                        break;
                    } else {
                        status = true;
                    }
                }
                return status;
            } else {
                try {
                    pageContainer.documentName.getText().trim().contains(filename);
                    return false;
                } catch (NoSuchElementException e) {
                    return true;
                }
            }
        });
    }

    public void deleteMultipleDocuments(int number) {
        LOGGER.info("***To click on Delete button for the multiple documents to be deleted***");
        if(pageContainer.deleteMultipleDocs.isDisplayed())
            pageContainer.deleteMultipleDocs.click();
        VoyantaDriver.getCurrentDriver().switchTo().activeElement();
        deleteDialog(number);
        pageContainer.deleteDialogDeleteButton.click();
        VUtils.waitFor(1);
        VoyantaDriver.getCurrentDriver().navigate().refresh();
    }

    private void deleteDialog(int number) {
        LOGGER.info("***To check the Delete dialog box***");
        String expectedPrimaryMessage = null;
        String expectedSecondaryMessage = null;
        if (number == 1) {
//            expectedPrimaryMessage = "Are you sure you want to delete the selected file?";
            expectedPrimaryMessage = "Are you sure you want to delete the selected folder?"; //these error messages have tendancy to change in future sprints
            expectedSecondaryMessage = "This action cannot be undone.";
        }
        else {
            expectedPrimaryMessage = "Are you sure you want to delete the selected files and folder contents ?";
            expectedSecondaryMessage = "Standard Folders will remain, but their contents will be deleted. This action cannot be undone.";
        }
        Assert.assertTrue("Actual primary message "+pageContainer.deleteDialogPrimaryMsg.getText()+" is not same as the expected one."+expectedPrimaryMessage, expectedPrimaryMessage.equals(pageContainer.deleteDialogPrimaryMsg.getText()));
        Assert.assertTrue("Actual secondary message "+pageContainer.deleteDialogSecondaryMsg.getText()+" is not same as the expected one."+expectedSecondaryMessage, expectedSecondaryMessage.equals(pageContainer.deleteDialogSecondaryMsg.getText()));
        Assert.assertTrue("Cancel button is not displayed.", pageContainer.deleteDialogCancelButton.isDisplayed());
        Assert.assertTrue("Delete button is not displayed.", pageContainer.deleteDialogDeleteButton.isDisplayed());
    }

    public void deleteCheck(String filename) {
        LOGGER.info("***To verify that the document "+filename+" has been deleted successfully***");
        if (!pageContainer.documentNames.isEmpty()) {
            for (int i=0; i < pageContainer.documentNames.size(); i++) {
                Assert.assertTrue("File/document " + filename + " is not deleted properly.", !pageContainer.documentNames.get(i).getText().contains(filename));
            }
        } else {
            boolean status = false;
            try {
                if (pageContainer.documentName.isDisplayed() || pageContainer.documentName.getText().equalsIgnoreCase(filename))
                    status = false;
            } catch (NoSuchElementException e) {
                status = true;
            }
            Assert.assertTrue("File/document " + filename + " is not deleted properly.", status);
            LOGGER.info ("$$$ The document "+filename+" is deleted $$$");
        }
    }

    public void checkTableHeaders(String expectedHeaders) {
        documentsTable();
        LOGGER.info("***To verify the header of the table***");
        List<String> expectedHeaderList = Arrays.asList(expectedHeaders.split(","));
        List<String> actualHeaderList = getHeaderList();
        Assert.assertTrue("Actual header "+actualHeaderList+" is not same as the expected header "+expectedHeaderList+" .", expectedHeaderList.equals(actualHeaderList));
    }

    public void checkDocumentDetails(String columnName, String expectedDetails) throws ParseException {
        documentsTable();
        getRowsInHash();
        listViewElements = getListViewElements();
        List<String> expectedList = new ArrayList<>();
        List<String> actualList = new ArrayList<>();
        LOGGER.info("***To verify the equivalence of documents uploaded and their details in "+columnName+" respectively***");
        if (columnName.equalsIgnoreCase("Last modified") && expectedDetails.equalsIgnoreCase("date")) {
            expectedDetails = new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
            List<String> expectedDates = new ArrayList<>();
            for (int i=0; i<getColumnValueAsList(columnName).size(); i++) {
                expectedDates.add(expectedDetails);
                if (!getColumnValueAsList(columnName).get(i).equalsIgnoreCase("")) {
                    VerifyUtils.dateTimeAfterOrEquals(new SimpleDateFormat("dd MMM yyyy HH:mm").parse(expectedDates.get(i)), new SimpleDateFormat("dd MMM yyyy HH:mm").parse(getColumnValueAsList(columnName).get(i)));
                    VerifyUtils.dateTimeWithinRange(new SimpleDateFormat("dd MMM yyyy HH:mm").parse(getColumnValueAsList(columnName).get(i)), -15, 15);
                }
            }
            Assert.assertTrue("Number of documents uploaded " + getColumnValueAsList(columnName).size() + " and the expected number " + expectedDates.size() + " is different", expectedDates.size() <= getColumnValueAsList(columnName).size());
        } else if (columnName.equalsIgnoreCase("Uploaded By")) {
            List<String> expectedUploader = new ArrayList<>();
            for (int i=0; i<getColumnValueAsList(columnName).size(); i++) {
                expectedUploader.add(expectedDetails);
            }
            Assert.assertTrue("Number of documents uploaded " + getColumnValueAsList(columnName).size() + " and the expected number " + expectedUploader.size() + " is different", expectedUploader.size() <= getColumnValueAsList(columnName).size());
            expectedList = expectedUploader;
            actualList = getColumnValueAsList(columnName);
            Assert.assertTrue("Actual details in " + columnName + " : " + actualList + " are not same as the expected details " + expectedList + " .", actualList.containsAll(expectedList));
        } else {
            Assert.assertTrue("Number of documents uploaded " + getColumnValueAsList(columnName).size() + " and the expected number " + expectedDetails.split(",").length + " is different", expectedDetails.split(",").length <= getColumnValueAsList(columnName).size());
            expectedList = Arrays.asList(expectedDetails.split(","));
            actualList = getColumnValueAsList(columnName);
            Assert.assertTrue("Actual details in " + columnName + " : " + actualList + " are not same as the expected details " + expectedList + " .", actualList.containsAll(expectedList));
        }
    }

    public void getSpinningMessage(String message) {
        if (pageContainer.spinningMessage.isDisplayed())
            Assert.assertTrue("Actual message "+pageContainer.spinningMessage.getText()+" is not same as the expected one "+message, pageContainer.spinningMessage.getText().contains("selected") && pageContainer.spinningMessage.getText().contains(message));
        else
            Assert.assertTrue("No message is displayed.", !pageContainer.spinningMessage.isDisplayed());
    }

    public void downloadMultipleDocuments() {
        LOGGER.info("***To click on Download link for multiple documents to be downloaded***");
        if(pageContainer.downloadMultipleDocs.isDisplayed())
            pageContainer.downloadMultipleDocs.click();
        VUtils.waitFor(5);
    }

    public void clickDocumentFolderOrFile(String fileOrFolder) {
        LOGGER.info("***To click on folder or filename "+fileOrFolder+" on the documents page***");
        for (int i = 0; i < pageContainer.documentNames.size(); i++) {
            if (pageContainer.documentNames.get(i).getText().trim().equalsIgnoreCase(fileOrFolder)) {
                pageContainer.documentNames.get(i).click();
                break;
            }
        }
        VoyantaDriver.getCurrentDriver().navigate().refresh();
        VUtils.waitForPageToLoad(pageContainer.breadcrumbMainAllDocumentsLink);
        VUtils.waitForPageToLoad(pageContainer.breadcrumbActive);
        if (fileOrFolder.matches(".*([ \t]).*")) {
            String[] fileFolder = fileOrFolder.split(" ");
            for (int i=0; i < fileFolder.length; i++) {
                wait.until(ExpectedConditions.urlContains(fileFolder[i]));
            }
        } else {
            wait.until(ExpectedConditions.urlContains(fileOrFolder));
        }
    }

    public void checkDocumentFolderOrFile(String fileOrFolder) {
        LOGGER.info("***To check on folder or filename "+fileOrFolder+" on the documents page whether we navigated correctly or not***");
        Assert.assertTrue("Main breadcrumb is not 'All Documents'.", pageContainer.breadcrumbMainAllDocumentsLink.getText().trim().equalsIgnoreCase("All Documents"));
        Assert.assertTrue("Active breadcrumb is "+pageContainer.breadcrumbActive.getText().trim()+" instead of "+fileOrFolder, pageContainer.breadcrumbActive.getText().trim().equalsIgnoreCase(fileOrFolder));
        if (fileOrFolder.matches(".*([ \t]).*")) {
            String[] fileFolder = fileOrFolder.split(" ");
            for (int i=0; i < fileFolder.length; i++) {
                Assert.assertTrue("URL doesn't contain required file or folder.", VoyantaDriver.getCurrentDriver().getCurrentUrl().contains(fileFolder[i]));
            }
        } else {
            Assert.assertTrue("URL doesn't contain required file or folder.", VoyantaDriver.getCurrentDriver().getCurrentUrl().contains(fileOrFolder));
        }
    }


    public void sortByColumn(String column, String order) {

        documentsTable();
        getRowsInHash();
        listViewElements = getListViewElements();
        List<String> actualList = new ArrayList<>();

        boolean sortedAscending = false;
        boolean sortedDescending = false;

        LOGGER.info("@@@Going to sort by "+column+" column@@@");
        pageContainer.clickSortByColumn(column).click();
        if(VUtils.isElementPresent(pageContainer.getSortByColumn(column))) {
            LOGGER.info("@@@"+column+" column is going to be sorted "+order+"ly@@@");
            if (order.contains("descend")) {
                if (pageContainer.getSortByColumn(column).getAttribute("class").contains("icon-arrow-up"))
                    pageContainer.clickSortByColumn(column).click();
            } else if (order.contains("ascend")) {
                if ((pageContainer.getSortByColumn(column).getAttribute("class").contains("icon-arrow-down")))
                    pageContainer.clickSortByColumn(column).click();
            }
        }

        LOGGER.info("***To verify that the "+column+" is sorted in the "+order+" order correctly***");

        documentsTable();
        getRowsInHash();
        listViewElements = getListViewElements();
        actualList = getColumnValueAsList(column);

        if (order.contains("ascend")) {
            sortedAscending = Ordering.natural().nullsLast().isOrdered(actualList);
            Assert.assertTrue(column+" column is not sorted properly in "+order+" order", sortedAscending);
        }
        else if (order.contains("descend")) {
            sortedDescending = Ordering.natural().nullsLast().reverse().isOrdered(actualList);
            Assert.assertTrue(column+" column is not sorted properly in "+order+" order", sortedDescending);
        }
    }
}


