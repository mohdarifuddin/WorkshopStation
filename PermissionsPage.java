package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.PermissionsPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.*;

public class PermissionsPage extends AbstractPageWithList {

    private static final String url = PropertiesLoader.getProperty("ui_url") + "administration/permissions/shared-objects";
    static Logger LOGGER = org.apache.log4j.Logger.getLogger(PermissionsPage.class);
    WebDriver mDriver = VoyantaDriver.getCurrentDriver();
    final String tableRows = "//table[@class='list-grid']/tbody/tr";
    WebDriverWait wait = new WebDriverWait(mDriver, 60);
    final String confirmationMessage = "//*[@class='message-list success']";
    UploadPage uploadPage;

    PermissionsPageContainer pageContainer = PermissionsPage.getDataContainer(PermissionsPageContainer.class);

    public PermissionsPage() {
        super.tableElement = pageContainer.tableElement;
        super.tableId = "sharing-table";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("list-grid")));
    }

    public PermissionsPage(String notable) {
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
    }

    public static PermissionsPage openPage() {
        VoyantaDriver.getCurrentDriver().get(url);
//        wait.until(ExpectedConditions.textToBePresentInElement(mDriver.findElement(By.xpath("//*[@id='filter-region']/form/div[1]/label")),"Filter by:"));
        return new PermissionsPage();
    }

    public void checkPermissionRecordExists(String type, String objectName, String objectReference, String levelOfAccess) {
        getRowsAsHashIgnoreBlankHeaders();
        int rowNumber = getRowNumber("Object", objectName + " - " + objectReference);
        LOGGER.info("Found the object in row number " + rowNumber);
        VerifyUtils.True(rowNumber >= 0);
    }

    public void checkPermissionSharedList(String objectType, String object, String email, String levelOfAccess) {
        getRowsAsHashIgnoreBlankHeaders();
        int rowNumber = getRowNumber("Shared with", email);
        LOGGER.info("Found the Object in List :" + rowNumber);
        VerifyUtils.True(rowNumber >= 0);
    }

    public int getCountOfSharedObjectsPermissionPresent(String objectType, String object, String reference, String email, String levelOfAccess) {
        int permissionEntries = 0;
        if (VUtils.isElementPresent(By.xpath(tableRows))) {
            int size = VoyantaDriver.findElements(By.xpath(tableRows)).size();
            for (int i = 1; i <= size; i++) {
                if (VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[3]")).getText().equals(objectType)
                        && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[4]")).getText().equals(object + " (" + reference + ")")
                        && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[6]")).getText().contains(email)
                        && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[7]")).getText().equals(levelOfAccess)
                        && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[8]/a[1]")).getText().equals("Edit")
                        && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[8]/a[2]")).getText().equals("Stop Sharing")) {
                    permissionEntries++;
                    LOGGER.info("Found The Object : " + VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[4]")).getText() + " At : " + i);
                }
            }
        }
        return permissionEntries;
    }

    public void checkPermissions(String levelOfAccess) {
        if (VUtils.isElementPresent(By.xpath(tableRows))) {
            for (int i = 1; i <= VoyantaDriver.findElements(By.xpath(tableRows)).size(); i++) {
                VerifyUtils.True(VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[6]")).getText().equals(levelOfAccess));
            }
        }
    }

    public void clickEditForUser(String object, String reference, String email) {
        int rows = VoyantaDriver.findElements(By.xpath(tableRows)).size();
        for (int i = 1; i <= rows; i++) {
            if (VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]" + "//td[4]")).getText().equals(object + " (" + reference + ")")
                    && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]" + "//td[6]")).getText().contains(email)) {
                VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]" + "//td[8]/a[1]")).click();
                VUtils.waitFor(2);
                break;
            }
        }
    }

    public void selectCreateNew() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.createNewPlusButton));
        if (!pageContainer.createNewPlusButton.isEnabled()) {
            LOGGER.info("User Doesn't have Creator Rights");
        }
        LOGGER.info("Giving Creator Rights to User");
        pageContainer.createNewPlusButton.click();
    }

    public void viewCreateNew() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.createNewPlusButton));
        pageContainer.createNewPlusButton.isDisplayed();
    }

    public void addObjectCreator(String object, String user) {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.objectType));
        pageContainer.objectType.click();
        pageContainer.objectType.sendKeys(object);
        VUtils.waitFor(2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(object)));
        VoyantaDriver.findElement(By.linkText(object)).click();
        VUtils.waitFor(2);
        pageContainer.emailTextField.click();
        pageContainer.emailTextField.sendKeys(user);
        pageContainer.emailTextField.sendKeys(Keys.RETURN);
        VUtils.waitFor(2);
    }

    public void savePermission() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.saveButton));
        pageContainer.saveButton.click();
    }

    public void clickConfirmRevoke() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']")));
        WebElement revoke = VoyantaDriver.findElement(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']"));
        revoke.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']")));
    }

    public void creatorPopUp(String objectType, String userEmail) {
        int i = 0, j = 0;
        try {
            String parentWindow = mDriver.getWindowHandle();
            String subWindow = null;
            Set<String> handles = mDriver.getWindowHandles();
            Iterator<String> iterator = handles.iterator();
            while (iterator.hasNext()) {
                subWindow = iterator.next();
            }
            mDriver.switchTo().window(subWindow);
            Keyboard key = ((HasInputDevices) mDriver).getKeyboard();
            mDriver.findElement(By.className("VoyantaSelectList")).findElement(By.id("dstTypeIds_chzn")).findElement(By.className("search-field")).click();
            WebElement element = mDriver.findElement(By.className("VoyantaSelectList")).findElement(By.id("dstTypeIds_chzn")).findElement(By.className("chzn-drop"));
            List<WebElement> options = element.findElement(By.className("chzn-results")).findElements(By.tagName("li"));
            for (WebElement e : options) {

                if (e.getText().equals(objectType)) {
                    LOGGER.info("Found the Object Type :" + e.getText());
                    e.click();
                    VUtils.waitFor(2);
                    key.pressKey(Keys.TAB);
                    WebElement t = mDriver.findElement(By.className(" VoyantaSelectList")).findElement(By.xpath("//div[@id='accountIds_chzn']")).findElement(By.className("search-field")).findElement(By.tagName("input"));
                    t.click();
                    t.sendKeys(userEmail);
                    VUtils.waitFor(3);
                    WebElement name = mDriver.findElement(By.className(" VoyantaSelectList")).findElement(By.xpath("//div[@id='accountIds_chzn']")).findElement(By.className("chzn-drop"));
                    List<WebElement> mailList = name.findElement(By.className("chzn-results")).findElements(By.tagName("li"));
                    for (WebElement m : mailList) {
                        if (m.getText().equals(userEmail)) {
                            LOGGER.info("Email Found :" + m.getText());
                            m.click();
                            VUtils.waitFor(2);
                            mDriver.findElement(By.id("dialog-button-send")).click();
                            VUtils.waitFor(1);
                            return;
                        } else {
                            LOGGER.info("Email Not Found :" + m.getText());
                        }
                        j++;
                    }
                    VUtils.waitFor(5);
                    return;
                } else {
                    LOGGER.info("Object Not Found " + e.getText());
                }
                i++;
            }
            mDriver.switchTo().window(parentWindow);

        } catch (NoAlertPresentException n) {
            System.out.println("Popup is Not Present : --");
            return;
        }
    }


    public void checkCreatorRecordExist(String objectType) {
//         getRowsInHash();
        getRowsfromCreator();
        int rowNumber = getRowNumber("Type", objectType);
        LOGGER.info("Found the Tag in row number " + rowNumber);
        VerifyUtils.True(rowNumber >= 0);
    }

    public List<HashMap> getRowsfromCreator() {

        List<HashMap> listOfElement = new LinkedList<HashMap>();

        int rowNr = 0;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("list-grid")));
//        tableElement = mDriver.findElement(By.className("list-grid"));
        tableElement = mDriver.findElement(By.id("list-region"));
        for (WebElement element : tableElement.findElements(By.xpath("//tbody/tr"))) {
            if (!element.getText().trim().equals("")) {
                if (element.getAttribute("class").contains("expandedView")) {
                    continue;
                } else {
                    int i = 0;
                    HashMap hashMap = new HashMap();
                    for (WebElement element1 : element.findElements(By.tagName("td"))) {
                        String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                        hashMap.put(header, element1);
                        //  System.out.println("this is the row" + rowNr+ " value "+ element1.getText() + " for header " + header);
                        LOGGER.info("Key:" + tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim() + " Value:" + element1.getText());
                        i++;
                    }
                    LOGGER.info("---------------------------------------");
                    listOfElement.add(hashMap);
                    rowNr++;
                }
            }
        }
        this.listViewElements = listOfElement;
        System.out.println(listViewElements.size());
        return listOfElement;
    }

    public void selectRevoke(String column, String userEmail, String returnColumn) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("list-grid")));
        List<WebElement> rows = VoyantaDriver.findElements(By.xpath(tableRows));
        for (int i = 1; i <= rows.size(); i++) {
            if (VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]/td[3]")).getText().equals(userEmail.toLowerCase())) {
                VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]/td[4]/a")).click();
            }
        }
    }

    public void selectNewSharedObject() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.createNewPlusButton));
        pageContainer.createNewPlusButton.click();
        VUtils.waitFor(2);
    }

    public void visibleNewSharedObject() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.createNewPlusButton));
        pageContainer.createNewPlusButton.isDisplayed();
    }


    public void enterObjectTypeandObject(String objectType, String object, String reference) {
        selectObjectType(objectType);
        VUtils.waitFor(2);
        selectObject(object, reference);
        VUtils.waitFor(2);
    }

    public void selectObjectType(String objectType) {
        VUtils.waitFor(2);
//        VoyantaDriver.findElement(By.xpath("//a/h6[text()='Select Object Type']")).click();
        VoyantaDriver.findElement(By.xpath("//span[contains(text(),'1.) Type:')]/..//a")).click();
        /*wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='" + objectType + "']")));
        VoyantaDriver.findElement(By.xpath("//a[text()='" + objectType + "']")).click();*/

        WebElement listObject = VoyantaDriver.findElement(By.cssSelector("ul.jui-menu.scrollable"));
        List<WebElement> elements = listObject.findElements(By.xpath("//li/a"));
        for (WebElement e : elements) {
            if (e.getText().equals(objectType)) {
                LOGGER.info("Selecting Object Type : " + e.getText());
                e.click();
                VUtils.waitFor(2);
                return;
            }
        }
    }

    public void selectObject(String object, String reference) {
        VUtils.waitFor(2);
        VoyantaDriver.findElement(By.xpath("//a/h6[text()='Select Object']")).click();
//        if (object.length() + 3 + reference.length() > 30) {
//            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@title='" + object + " - " + reference + "']/preceding-sibling::input")));
//            VoyantaDriver.findElement(By.xpath("//label[@title='" + object.toString().trim() + " - " + reference.toString().trim() + "']/preceding-sibling::input")).click();
//        } else {
//            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='" + object + " - " + reference + "']/preceding-sibling::input")));
//            VoyantaDriver.findElement(By.xpath("//label[text()='" + object.toString().trim() + " - " + reference.toString().trim() + "']/preceding-sibling::input")).click();
//        }

        String objectName = object + " - " + reference;
        WebElement dropdown = VoyantaDriver.findElement(By.cssSelector("ul.jui-menu.scrollable"));
        List<WebElement> list = dropdown.findElements(By.xpath("//li/label"));
        for (WebElement e : list) {
            if (e.getAttribute("title").equals(objectName)) {
                LOGGER.info("Selecting Object : " + e.getAttribute("title"));
                javascript("click", e);
                //((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].click();",e);
                VUtils.waitFor(2);
                return;
            } else if (e.getText().equals(objectName)) {
                LOGGER.info("Selecting Object : " + e.getText());
                javascript("click", e);
                //((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].click();", e);
                VUtils.waitFor(2);
                return;
            }
        }
        VerifyUtils.fail("Given Object Not Present In List");
    }

    public void selectAddButton() {
        pageContainer.addButton.click();
        VUtils.waitFor(5);
    }

    public void selectAssignPermissionsButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='voyantaButton medium callToAction actionAssign']")));
        VoyantaDriver.findElement(By.xpath("//a[@class='voyantaButton medium callToAction actionAssign']")).click();
    }

    public void objectPresentInList(String object) {
        getObjectRow();
        int rowNumber = getRowNumber("Object", object);
        LOGGER.info("Found the Object in row number " + rowNumber);
        VerifyUtils.True(rowNumber >= 0);
    }

    public List<HashMap> getObjectRow() {
        List<HashMap> listOfElement = new LinkedList<HashMap>();

        int rowNr = 0;
        tableElement = mDriver.findElement(By.className("block-inner"));
        for (WebElement element : tableElement.findElements(By.xpath("//tbody/tr"))) {
            if (element.getAttribute("class").contains("expandedView")) {
                continue;
            } else {
                int i = 0;
                HashMap hashMap = new HashMap();
                for (WebElement element1 : element.findElements(By.tagName("td"))) {
                    String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                    hashMap.put(header, element1);
                    LOGGER.info("Key:" + tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim() + " Value:" + element1.getText());
                    i++;
                }
                LOGGER.info("---------------------------------------");
                listOfElement.add(hashMap);
                rowNr++;
            }
        }
        this.listViewElements = listOfElement;
        System.out.println(listViewElements.size());
        return listOfElement;
    }


    public void selectCheckBox(String options) {
        String[] optionsAsArray = options.split(",");

        for (String option : optionsAsArray) {
            VoyantaDriver.findElement(By.xpath("//label[text()='" + option + "']/preceding-sibling::input")).click();
        }
    }

    public void selectPermission(String permission) {
        VoyantaDriver.findElement(By.xpath("//*[contains(text(),'Permission:')]/following-sibling::div/a/h6")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='" + permission + "']")));
        VoyantaDriver.findElement(By.xpath("//a[text()='" + permission + "']")).click();
    }

    public void selectFirstPermission() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.ReviewButton));
        pageContainer.ReviewButton.click();
    }

    public void enterEmail(String email) {
        LOGGER.info("Entering Email :" + email);
        WebElement emailElement = mDriver.findElement(By.className("block-inner")).findElement(By.xpath("//input[@value='Enter email']"));
        emailElement.click();
        emailElement.sendKeys(email);
        VUtils.waitFor(5);
        WebElement element = mDriver.findElement(By.className("block-inner")).findElement(By.xpath("//div[@id = 'emailAutocomplete_chzn']")).findElement(By.className("chzn-drop"));
        List<WebElement> options = element.findElement(By.className("chzn-results")).findElements(By.tagName("li"));
        for (WebElement e : options) {
            if (e.getText().equals(email)) {
                LOGGER.info("Found the Email : " + e.getText());
                e.click();
                VUtils.waitFor(2);
                return;
            } else {
                LOGGER.info("Not Found the Email : " + e.getText());
            }
        }

    }

    public void selectSend() {
        pageContainer.sendButton.click();
        VUtils.waitFor(4);

    }

    public void clickOnManageAdministratorsTab() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.manageAdministratorsLink));
        pageContainer.manageAdministratorsLink.click();
    }

    public void clickOnManageSuperViewerTab() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("list-grid")));
        pageContainer.manageSuperViewerLink.click();
    }

    public void clickOnObjectCreatorTab() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("list-grid")));
        pageContainer.manageObjectCreatorLink.click();
    }

    public void filterByEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.filterByEmail));
        pageContainer.filterByEmail.click();
        By optionsSelectXpath = By.xpath("//a[@class='search-choice-close']");
        if (VUtils.isElementPresent(optionsSelectXpath)) {
            List<WebElement> optionsSelected = mDriver.findElements(optionsSelectXpath);
            for (WebElement option : optionsSelected) {
                option.click();
            }
        }
        pageContainer.filterByEmail.sendKeys(email);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@id='emailAutocomplete_chzn_o_0'][text()='" + email + "']")));
        mDriver.findElement(By.xpath("//li[@id='emailAutocomplete_chzn_o_0'][text()='" + email + "']")).click();
        pageContainer.filterButton.click();
    }

    public void clickUpdateButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='voyantaButton medium callToAction actionEdit']")));
        VoyantaDriver.findElement(By.xpath("//a[@class='voyantaButton medium callToAction actionEdit']")).click();
    }

    public Boolean isConfirmationMessagePresent(String message) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(confirmationMessage + "[text()='" + message + "']")));
        WebElement revokeMessage = VoyantaDriver.findElement(By.xpath(confirmationMessage));
        return revokeMessage.getText().equals(message);
    }

    public void clickLevelOfAccess(String email) {
        List<WebElement> rows = VoyantaDriver.findElements(By.xpath(tableRows));
        for (WebElement row : rows) {
            if (row.findElement(By.xpath("//td[@class='user']/span")).getText().contains(email.toLowerCase())) {
                row.findElement(By.xpath("//td[@class='level']/span/a")).click();
            }
        }
    }

    public void selectAccessType(String accessType) {
        if (accessType.equals("Approver"))
            pageContainer.approver.click();
        else if (accessType.equals("Submitter"))
            pageContainer.submitter.click();
        else if (accessType.equals("Viewer"))
            pageContainer.viewer.click();
        pageContainer.continueButton.click();
    }

    public void clickStopSharing(String object, String reference, String email) {
        int rows = VoyantaDriver.findElements(By.xpath(tableRows)).size();
        for (int i = 1; i < rows; i++) {
            if (VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]" + "//td[4]")).getText().equals(object + " (" + reference + ")")
                    && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]" + "//td[6]")).getText().contains(email)) {
                VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]" + "//td[8]/a[2]")).click();
                VUtils.waitFor(2);
                VoyantaDriver.findElement(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']")).click();
                VUtils.waitFor(2);
                break;
            }
        }
    }

    public void clickYesButton() {
        pageContainer.yesButton.click();
    }

    public void clickNoButton() {
        pageContainer.noButton.click();
    }

    public void canSelectManageSharedObjct(boolean b, String result) {
        if (result.equals("can")) {
            wait.until(ExpectedConditions.visibilityOfAllElements(pageContainer.manageSharedObjectCSS));
            VerifyUtils.equals(b, pageContainer.manageSharedObjectCSS.stream().anyMatch(element -> element.getText().contains("Shared Objects")));
            VUtils.waitFor(2);
        } else
            VerifyUtils.equals(b, isManageSharedObjectPresent());
    }

    public boolean isManageSharedObjectPresent() {
        try {
            return pageContainer.manageSharedObjectLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Manage Shared Objects Tab is Not Visible");
        }
        return false;
    }

    public void canSeeAddButtonOnShared(boolean b) {
        LOGGER.info("Checking '+' Button Present : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.cssSelector("span.icon.add")));
    }

    public void canEditPermission(boolean b) {
        LOGGER.info("Checking Edit Button Present on Shared Object Tab : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.xpath("//a[@class='voyantaButton small icon actionEdit']")));
    }

    public void canStopSharingPermission(boolean b) {
        LOGGER.info("Checking Stop Sharing Button Present on Shared Object Tab : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.xpath("(//a[contains(text(),'Stop Sharing')])[2]")));
    }

    public void canSelectObjectCreator(boolean b, String result) {
        if (result.equals("can")) {
            LOGGER.info(" GOES TO OBJECT CREATOR TAB ");
            wait.until(ExpectedConditions.visibilityOfAllElements(pageContainer.manageSharedObjectCSS));
            //pageContainer.manageSharedObjectCSS.stream().forEach(element -> {
            //    if(element.getText().contains("Object Creators")) element.click();
            //});
            pageContainer.manageObjectCreatorLink.click();
            VUtils.waitFor(2);
        } else {
            LOGGER.info("Checking the Object Creator Tab Present : " + b);
            VerifyUtils.equals(b, isObjectCreatorPresent());
        }
    }

    public void canSelectManageByDST(boolean b, String result) {
        if (result.equalsIgnoreCase("can")) {
            LOGGER.info("Goes To Manage By DST Tab");
            pageContainer.manageByDSTLink.click();
            VUtils.waitFor(2);
        } else {
            LOGGER.info("Checking for Manage By DST Tab Present : " + b);
            VerifyUtils.equals(b, isManageDSTPresent());
        }
    }

    public boolean isObjectCreatorPresent() {
        try {
            return pageContainer.manageObjectCreatorLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Manage Object Creator tab is not Visible");
        }
        return false;
    }

    public boolean isManageDSTPresent() {
        try {
            return pageContainer.manageByDSTLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Manage By DST Tab is Not Visible");
        }
        return false;
    }

    public void canRevokePermission(boolean b) {
        LOGGER.info("Checking the Revoke Button Present : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.linkText("Revoke")));
    }

    public void canSelectAdministratorTab(boolean b, String result) {
        if (result.equals("can")) {
            LOGGER.info(" GOES TO ADMINISTRATOR TAB ");
            pageContainer.manageAdministratorsLink.click();
            VUtils.waitFor(2);
        } else
            LOGGER.info("Checking Administrator Tab is Present : " + b);
        VerifyUtils.equals(b, isAdministratorTabPresent());
    }

    public boolean isAdministratorTabPresent() {
        try {
            return pageContainer.manageAdministratorsLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Manage Administrator Tab is Not Visible");
        }
        return false;
    }

    public void canSelectGroupsTab(boolean b, String result) {
        if (result.equalsIgnoreCase("can")) {
            LOGGER.info(" GOES TO GROUPS TAB ");
            pageContainer.groupsLink.click();
            VUtils.waitFor(2);
        } else
            LOGGER.info("Checking Groups Tab is Present : " + b);
        VerifyUtils.equals(b, isGropsTabPresent());
    }

    public boolean isGropsTabPresent() {
        try {
            return pageContainer.groupsLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Groups Tab is Not Visible");
        }
        return false;
    }

    public void canSelectSuperViewerTab(boolean b, String result) {
        if (result.equals("can")) {
            pageContainer.manageSuperViewerLink.click();
            VUtils.waitFor(2);
        } else
            LOGGER.info("Checking the SuperViewers Tab Present : " + b);
        VerifyUtils.equals(b, isSuperViewertabPresent());
    }

    public boolean isSuperViewertabPresent() {
        try {
            return pageContainer.manageSuperViewerLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Manage Super Viewer Tab is Not Visible");
        }
        return false;
    }

    public void canSeePermissionShared(String email) {
        List<WebElement> rowsOfData = VoyantaDriver.findElements(By.xpath("//*[@class='list-grid']/tbody/tr"));
        List<WebElement> userEmails = VoyantaDriver.findElements(By.xpath("//*[@class='list-grid']//td[contains(text(),'" + email + "')]"));
        VerifyUtils.equals(rowsOfData.size(), userEmails.size());
    }

    public void goToManageobjCreatorTab() {
        pageContainer.manageObjectCreatorLink.click();
        VUtils.waitFor(2);
        LOGGER.info("Goes to Manage Object Creator Tab");
        getRowsfromCreator();
    }

    public void noPermissionsOnCreator() {
        WebElement noObject = VoyantaDriver.findElement(By.cssSelector("tbody"));
        if (noObject.getText().equals("No results")) {
            LOGGER.info("The User has No Permissions on Manage Object Creator Tab to Show !!!");
        } else {
            VerifyUtils.fail("User Can See the Object Permissions");
        }
    }

    public void noPermissionsOnShared() {
        WebElement noObject = VoyantaDriver.findElement(By.cssSelector("tbody"));
        if (noObject.getText().equals("No results")) {
            LOGGER.info("The User has No Permissions on Manage Shared Object Tab to Show !!!");
        } else {
            VerifyUtils.fail("User Can See the Object Permissions");
        }
    }

    public void objectTypeOnCreator(String objectType) {
        int rowNumber = getRowNumber("Type", objectType);
        LOGGER.info("Found the ObjectType in row number " + rowNumber);
    }

    public void userOnCreator(String email) {
        int rowNumber = getRowNumber("Email", email);
        LOGGER.info("Found the User in Row Number " + rowNumber);
    }


    public void ownerListPermission(String type, String object, String sharedWith) {
        String[] typeList = type.split(",");
        String[] objectList = object.split(",");
        getRowsAsHashIgnoreBlankHeaders();

        for (int i = 0; i < typeList.length; i++) {
            for (HashMap map : listViewElements) {
                String typeValue = ((WebElement) map.get("Type")).getText().trim();
                String objValue = ((WebElement) map.get("Object")).getText().trim();
                String sharedValue = ((WebElement) map.get("Shared with")).getText().trim();
                if (typeValue.equals(typeList[i]) && objValue.equals(objectList[i]) && sharedValue.equals(sharedWith)) {
                    LOGGER.info("Found the Type = " + typeList[i] + "  Object = " + objectList[i] + " Shared with = " + sharedWith);
                }
            }
        }
    }

    public void clickDeleteGroup(String groupName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='voyantaButton small icon actionDelete']")));
        VoyantaDriver.findElement(By.xpath("//tr[@class='item-row']/td[text()='" + groupName + "']/following-sibling::td/a[@class='voyantaButton small icon actionDelete']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']")));
        VoyantaDriver.findElement(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']")).click();
        VUtils.waitFor(5);
    }

    public void selectDisplayNumberOfRows(String rows) {
        int attempts = 0;
        while (attempts < 10) {
            try {
                wait.until(ExpectedConditions.visibilityOf(pageContainer.numberOfRowsDisplayed));
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
        VUtils.waitFor(5);

        //((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);",pageContainer.numberOfRowsDisplayed);
        javascript("scrollIntoViewTrue", pageContainer.numberOfRowsDisplayed);
        pageContainer.numberOfRowsDisplayed.click();
        VUtils.waitFor(2);
        List<WebElement> results = pageContainer.numberOfRowsDropDownList;

        results.stream().filter(res -> res.getText().equals(rows))
                .forEach(res -> {
                    res.click();
                });

        if (VUtils.isElementPresent(By.xpath("//*[@id='loader']")))
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        VUtils.waitFor(5);
    }

    public void clickCancelSharedObject() {
        VUtils.waitFor(5);
        pageContainer.cancelButton.click();
    }

    public boolean isGroupsTabDisplayed() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.manageSharedObjectLink));
        try {
            return pageContainer.groupsLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public int numberOfTimesGroupIsDisplayed(String groupName, String users) {
        int permissionEntries = 0;
        List<WebElement> rows = VoyantaDriver.findElements(By.xpath(tableRows));

        for (int i = 1; i <= rows.size(); i++) {
            if (VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[1]")).getText().equals(groupName)
                    && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[2]")).getText().equals(users)) {
                permissionEntries++;
                LOGGER.info("Group is Displayed : " + groupName + "  With User : " + users);
            }
        }
        return permissionEntries;

    }

    public boolean groupSaveButtonIsDisabled() {
        return VUtils.isElementPresent(By.xpath("//a[@class='voyantaButton medium callToAction actionSave disabled']"));
    }

    public String getGroupErrorMsg() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='message-list failure']")));
        return VoyantaDriver.findElement(By.xpath("//div[@class='message-list failure']")).getText();
    }

    public void selectManageByDST() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.manageByDSTLink));
        pageContainer.manageByDSTLink.click();
        VUtils.waitFor(2);
    }

    public void canSeeMyPermissionsDST(boolean b) {
        VerifyUtils.equals(b, isMyPermissionsDSTButtonPresent());
    }

    private boolean isMyPermissionsDSTButtonPresent() {
        try {
            LOGGER.info("My Permissions DST Button is Present ");
            return pageContainer.myPermissionsDSTLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("My Permissions DST Button is not Present ");
        }
        return false;
    }

    public void canSeeGoToDM(boolean b) {
        VerifyUtils.equals(b, isGoToDMButtonPresent());
    }

    private boolean isGoToDMButtonPresent() {
        try {
            LOGGER.info("Go To Data Manger Button is Present ");
            return pageContainer.gotoDMFromPermissionsLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Go To Data Manager Button is not Present  ");
        }
        return false;
    }

    public void selectMyPermissionsDST() {
        pageContainer.myPermissionsDSTLink.click();
        VUtils.waitFor(4);
    }

    public void selectDxPermissionsDST() {
        pageContainer.dxPermissionsDSTLink.click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.dxPermissionsDSTLink));
    }

    public void selectGoTODM() {
        pageContainer.gotoDMFromPermissionsLink.click();
        uploadPage = new UploadPage();
    }

    public void selectObjectCreator() {
        pageContainer.manageObjectCreatorLink.click();
        VUtils.waitFor(1);
    }

    public void viewObjectCreator() {
        int count = 0;
        int maxTries = 5;
        try {
            wait.until(ExpectedConditions.visibilityOf(pageContainer.addObjectCreator));
            pageContainer.addObjectCreator.isDisplayed();
        } catch (NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }
}
