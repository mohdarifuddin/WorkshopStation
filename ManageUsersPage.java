package voyanta.ui.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.List;

public class ManageUsersPage extends PermissionsPage {

    private static final String url = PropertiesLoader.getProperty("ui_url") + "permissions/";
    private WebDriverWait shortWait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10);

    final String tableRows = "//table[@class='list-grid']/tbody/tr";
    final By emailTextField = By.xpath("//li[@class='multi-selector-input-field']");
    final By saveButton = By.xpath("//a[@class='voyantaButton medium callToAction actionSave']");
    final By cancelAddButton = By.xpath("//a[@class='voyantaButton medium actionCancel']");
    final By revokeButton = By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']");
    final By cancelRevokeButton = By.xpath("//a[text()='Cancel']");
    final By revokeConfirmationMessage = By.xpath("//*[@class='message-list']/ul/li");

    @FindBy(xpath = "//*[@id='name']")
    public WebElement groupName;

    @FindBy(xpath = "//a[@class='voyantaButton small right actionCreateNewGroup']")
    public WebElement createNewGroupButton;

    @FindBy(how = How.ID, using = "userSearch")
    public WebElement searchGroup;

    @FindBy(how = How.LINK_TEXT, using = "-")
    public WebElement filterResetButton;

    @FindBy(how = How.LINK_TEXT, using = "Delete Group")
    public WebElement deleteFromEditGroup;

    /*New Navigation */
    @FindBy(how = How.CSS, using = "span.icon.add")
    public WebElement createNewGroupPlusButton;

    public static ManageUsersPage openPage() {
        VoyantaDriver.getCurrentDriver().get(url);
        return new ManageUsersPage();
    }

    public void enterUser(String user) {
        shortWait.until(ExpectedConditions.elementToBeClickable(emailTextField));
        WebElement email = VoyantaDriver.findElement(emailTextField);
        email.click();
        email.sendKeys(user.substring(0, user.length() - 1));
        VUtils.waitFor(2);
        email.sendKeys(user.substring(user.length() - 1, user.length()));
        try {
            shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'" + user + "')]")));
            try {
                VoyantaDriver.getCurrentDriver().findElement(By.xpath("//a[text()='" + user + "']")).click();
            } catch (NoSuchElementException | TimeoutException e) {
                VoyantaDriver.getCurrentDriver().findElement(By.xpath("//a[contains(text(),'" + user + "')]")).click();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            email.sendKeys(Keys.RETURN);
        }

    }

    public void clickOnSaveButton() {
        shortWait.until(ExpectedConditions.presenceOfElementLocated(saveButton));
        WebElement save = VoyantaDriver.findElement(saveButton);
        save.click();
        VUtils.waitFor(5);
    }

    public int getCountOfPermissionPresent(String name, String user, String action) {
        int permissionEntries = 0;
        List<WebElement> rows = VoyantaDriver.findElements(By.xpath(tableRows));

        if (!action.equals("")) {
            for (int i = 1; i <= rows.size(); i++) {
                if (VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[1]")).getText().equals(name)
                        && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[2]")).getText().equals(user)
                        && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[3]/a")).getText().equals(action)) {
                    permissionEntries++;
                }
            }
        } else {
            for (int i = 1; i <= rows.size(); i++) {
                if (VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[1]")).getText().equals(name)
                        && VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]//td[2]")).getText().equals(user)
                        && !VUtils.isElementPresent(By.xpath(tableRows + "[" + i + "]//td[3]/a"))) {
                    permissionEntries++;
                }
            }
        }
        return permissionEntries;
    }

    public void clickOnCancelAddButton() {
        VUtils.waitFor(5);
        WebElement cancelButton = VoyantaDriver.findElement(cancelAddButton);
        cancelButton.click();
    }

    public void clickOnCancelRevokeButton() {
        VUtils.waitFor(5);
        WebElement saveButton = VoyantaDriver.findElement(cancelRevokeButton);
        saveButton.click();
    }

    public void revokePermission(String email) {
        clickRevokeForUser(email);
    }

    public void clickConfirmRevoke() {
        WebElement revoke = VoyantaDriver.findElement(revokeButton);
        revoke.click();
        shortWait.until(ExpectedConditions.invisibilityOfElementLocated(revokeButton));
    }

    public Boolean isRevokeConfirmationMessagePresent(String message) {
        shortWait.until(ExpectedConditions.presenceOfElementLocated(revokeConfirmationMessage));
        WebElement revokeMessage = VoyantaDriver.findElement(revokeConfirmationMessage);
        return revokeMessage.getText().equals(message);
    }

    private void clickRevokeForUser(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("list-grid")));
        List<WebElement> rows = VoyantaDriver.findElements(By.xpath(tableRows));
        for (int i = 1; i <= rows.size(); i++) {
            if (VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]/td[2]")).getText().equals(email.toLowerCase())) {
                VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]/td[3]/a")).click();
                break;
            }
        }
    }


    public void clickOnGroupsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.groupsLink));
        pageContainer.groupsLink.click();
        VUtils.waitFor(3);
        LOGGER.info(" User Goes to Groups Tab ");
    }

    public void enterGroupName(String group) {
        wait.until(ExpectedConditions.visibilityOf(groupName));
        groupName.click();
        groupName.clear();
        groupName.sendKeys(group);
        LOGGER.info("Entering Group Name As : " + group);
    }

    public void enterUserEmails(String users) {
        String[] userNames = users.replaceAll(" ", "").split(",");
        int i = 1;

        for (String name : userNames) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='multiSelector-region']/div/div[2]/ul/li[" + i + "]")));
//            WebElement input = VoyantaDriver.findElement(By.xpath("//*[@id='multiSelector-region']/div/div[2]/ul/li[" + i + "]"));
            WebElement input = VoyantaDriver.findElement(By.xpath("//*[@id='multiSelector-inputField']"));
            input.click();
            input.sendKeys(name);
            VUtils.waitFor(2);
            input.sendKeys(Keys.RETURN);
            VUtils.waitFor(3);
            LOGGER.info("Entering User Email : " + name);
            i++;
        }
    }

    public void clickCreateNewGroupButton() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.createNewPlusButton));
        pageContainer.createNewPlusButton.click();
        LOGGER.info("Creating New Group ");
    }

    public boolean isErrorMessageDisplayed(String error) {
        try {
            shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='message-list failure']")));
            return VoyantaDriver.findElement(By.xpath("//div[@class='message-list failure']")).getText().equals(error);
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void searchForGroup(String search) {
        LOGGER.info("Filter the Group List by : " + search);
        searchGroup.sendKeys(search);
        wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.id("searchResultsContainer"))));
        WebElement list = VoyantaDriver.findElement(By.id("searchResultsContainer"));
        List<WebElement> searchResult = list.findElements(By.xpath("//a/span"));
        for (WebElement e : searchResult) {
            if (e.getText().equals(search)) {
                LOGGER.info("Found the Group with User Email : " + e.getText());
                e.click();
                VUtils.waitFor(5);
                return;
            }
        }
    }

    public void noUserFoundMsg(String msg) {
        WebElement errorMsg = VoyantaDriver.findElement(By.xpath("//*[@id='searchResultsContainer']/a"));
        VerifyUtils.equals(msg, errorMsg.getText());
    }

    public void resetGroupFilterButton() {
        LOGGER.info("Removing the Filter...");
        filterResetButton.click();
        VUtils.waitFor(2);
    }

    public void editToGroup(String grpName) {
        LOGGER.info("Editing to the Group : " + grpName);
        WebElement editButton = VoyantaDriver.findElement(By.xpath("//tr[@class='item-row']/td[text()='" + grpName + "']/following-sibling::td/a[@class='voyantaButton small icon actionEdit']"));
        editButton.click();
        wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//*[@id='main-region']/div/h2")), "Edit Group"));
    }

    public void removeUserFromGroup(String removeUser) {
        LOGGER.info("Removing the User From Group : " + removeUser);
        WebElement list = VoyantaDriver.findElement(By.xpath("//*[@class='multi-selector-choices']"));
        List<WebElement> remove = list.findElements(By.cssSelector("li.multi-selector-choise"));
        int i = 1;
        for (WebElement e : remove) {
            if (e.getText().contains(removeUser)) {
                LOGGER.info("Found The User to Remove : " + e.getText());
                WebElement selectRemove = VoyantaDriver.findElement(By.cssSelector("li.multi-selector-choise")).findElement(By.xpath("(//a[contains(text(),'X')])[" + i + "]"));
                selectRemove.click();
                return;
            }
            i++;
        }
    }

    public void deleteGroupFromEditGroup() {
        deleteFromEditGroup.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']")));
        VoyantaDriver.findElement(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']")).click();
        VUtils.waitFor(5);
    }

    public void checkAndDeleteGroupIfExists(String groupName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("list-grid")));
        List<WebElement> rows = VoyantaDriver.findElements(By.xpath(tableRows));
        for (int i = 1; i <= rows.size(); i++) {
            if (VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]/td[1]")).getText().equals(groupName)) {
                VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]/td[contains(@class,'actionsCol')]/a[contains(@class,'actionDelete')]")).click();
                if (VUtils.isElementPresent(VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".ui-dialog-title"))) && VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".ui-dialog-title")).getText().equalsIgnoreCase("Delete a Group")) {
                    Assert.assertTrue("Its not "+groupName+" group delete dialog box", VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".ui-dialog-content.ui-widget-content p")).getText().equalsIgnoreCase("Are you sure you want to delete "+groupName+" group?"));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']")));
                    VoyantaDriver.findElement(By.xpath("//a[@class='voyantaButton medium callToAction actionConfirm']")).click();
                    Assert.assertTrue("'Group deleted' success message is not shown", VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".message-list.success[style*='block']")).getText().equals("Group deleted"));
                }
                break;
            }
            Assert.assertFalse("Still the group has not been deleted", VoyantaDriver.findElement(By.xpath(tableRows + "[" + i + "]/td[1]")).getText().equals(groupName));
        }
    }
}
