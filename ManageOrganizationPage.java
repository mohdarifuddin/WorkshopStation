package voyanta.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.List;

public class ManageOrganizationPage extends AbstractVoyantaPage {

    WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10L);
    private static final String url = PropertiesLoader.getProperty("ui_url") + "admin/organization/update";

    @FindBy(xpath = "//*[@id='name']")
    WebElement orgName;

    @FindBy(id = "changename-button")
    WebElement changeButton;

    @FindBy(xpath = "//*[@id='tabs-menu']//a[text()='Providers']")
    WebElement providersTab;

    @FindBy(xpath = "//*[@id='create-button']/a")
    WebElement createProviderButton;

    @FindBy(xpath = "//*[@id='dialog-button-yes']")
    WebElement yesButton;

//    @FindBy (xpath = "//*[@id='content']/div[1]/form/div[3]/div[2]/div/a/span/input")
//    WebElement noButton;

    @FindBy(how = How.CSS, using = "input[type=\"button\"]")
    WebElement noButton;

    @FindBy(xpath = "(//input[@id=''])[6]")
    WebElement saveButton;

    @FindBy(xpath = "(//input[@id=''])[5]")
    WebElement cancelButton;

    @FindBy(name = "providerName")
    WebElement providerName;

    @FindBy(name = "providerReference")
    WebElement providerReference;

    @FindBy(name = "providerDescription")
    WebElement providerDescription;

    /*New Navigation Admin Sub Link*/
    @FindBy(linkText = "Provider")
    WebElement providerLink;

    @FindBy(linkText = "Create New Provider")
    WebElement createNewProviderLink;

    @FindBy(how = How.CSS, using = "a.user.clearfix > span.icon")
    WebElement userIcon;

    @FindBy(how = How.LINK_TEXT, using = "Organization")
    WebElement organizationLink;

    final String providerRows = "//table[@class='data-manager']/tbody/tr";
    final By deleteConfirmationMessage = By.xpath("//form/div[2]");

    public ManageOrganizationPage() {
//        wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//*[@id='content']/h1")), "Manage Organization"));
//        wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//*[@data-tid='megaMenuSection_Miscellaneous']/h5")), "OTHER"));
    }

    public ManageOrganizationPage(String pageName) {
        pageName = "Other";
        wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.cssSelector("h1.sub-navigation-dropdown-trigger")), pageName));
    }

    public static ManageOrganizationPage openPage() {
//        VoyantaDriver.getCurrentDriver().get(url);
        VoyantaDriver.findElement(By.xpath("//*[@data-tid='megaMenuTrigger_Admin']")).click();
        return new ManageOrganizationPage();
    }

    public void selectUser() {
//       VoyantaDriver.findElement(By.cssSelector("a.user.clearfix > span.icon")).click();
        userIcon.click();
        wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.xpath("//*[@class='user-info-panel']"))));
    }

    public void selectOrg() {
//        VoyantaDriver.findElement(By.linkText("Organization")).click();
        organizationLink.click();
        VUtils.waitFor(2);
    }

    public String getOrganizationName() {
        return orgName.getAttribute("value");
    }

    public ManageOrganizationPage typeOrgName(String newOrgName) {
        orgName.sendKeys(newOrgName);
        return new ManageOrganizationPage();
    }

    public void clickChange() {
        changeButton.click();
        VUtils.waitFor(3);
    }

    public Boolean isOrganizationUpdatedMessageDisplayed() {
        return isMessageDisplayed("Organisation updated.");
    }

    public Boolean isProviderSavedConfirmationMessageDisplayed(String message) {
        return isMessageDisplayed(message);
    }

    public Boolean isMessageDisplayed(String message) {
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='container']//ul/li[text()='" + message + "']")));
        if (VoyantaDriver.findElement(By.xpath("//div[@id='content']/ul/li")).getText().equals(message)) {
            VUtils.waitFor(5);
            return true;
        } else
            return false;
    }

    public ManageOrganizationPage clickOnProvidersTab() {
//        providersTab.click();
        wait.until(ExpectedConditions.visibilityOf(providerLink));
        providerLink.click();
        return new ManageOrganizationPage("Other");
    }

    public CreateProviderPage clickCreateProvider() {
//        createProviderButton.click();
        createNewProviderLink.click();
        return new CreateProviderPage();
    }

    public int getCountOfPermissionPresent(String name, String user, String description) {
        int permissionEntries = 0;
        for (int i = 1; i <= 10; i++) {
            if (VoyantaDriver.findElement(By.xpath(providerRows + "[" + i + "]//td[@class='name']/span")).getText().equals(name)
                    && VoyantaDriver.findElement(By.xpath(providerRows + "[" + i + "]//td[@class='reference']/span")).getText().equals(user)
                    && VoyantaDriver.findElement(By.xpath(providerRows + "[" + i + "]//td[@class='description']/span")).getText().equals(description)) {
                permissionEntries++;
            }
        }
        return permissionEntries;
    }

    public boolean clickDelete(String name) {
        boolean found = false;
        List<WebElement> rows = VoyantaDriver.findElements(By.xpath(providerRows));
        for (WebElement row : rows) {
            if (row.findElement(By.xpath("//td[@class='name']/span")).getText().equals(name)) {
                row.findElement(By.xpath("//td[@class='actions']/div[2]/a")).click();
                found = true;
                break;
            }
        }
        return found;
    }

    public boolean isDeleteConfirmationMessagePresent(String message) {
        wait.until(ExpectedConditions.presenceOfElementLocated(deleteConfirmationMessage));
        WebElement deleteMessage = VoyantaDriver.findElement(deleteConfirmationMessage);
        return deleteMessage.getText().equals(message);
    }

    public void clickNoButton() {
        wait.until(ExpectedConditions.elementToBeClickable(noButton));
        noButton.click();
    }

    public void clickYesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        yesButton.click();
        VUtils.waitFor(1);
    }

    public void clickEdit(String name) {
        List<WebElement> rows = VoyantaDriver.findElements(By.xpath(providerRows));
        for (int i = 0; i < rows.size(); i++) {
            if (VoyantaDriver.findElement(By.xpath("//table[@class='data-manager']/tbody/tr[" + (i + 1) + "]//td[@class='name']/span")).getText().equals(name)) {
                VoyantaDriver.findElement(By.xpath("//table[@class='data-manager']/tbody/tr[" + (i + 1) + "]//td[@class='actions']/div[1]/a")).click();
                break;
            }
        }
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
        VUtils.waitFor(2);
    }

    public void clickCancelButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        cancelButton.click();
    }

    public void enterProviderName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(providerName));
        providerName.clear();
        providerName.sendKeys(name);
    }

    public void enterProviderReference(String reference) {
        wait.until(ExpectedConditions.elementToBeClickable(providerReference));
        providerReference.clear();
        providerReference.sendKeys(reference);
    }

    public void enterProviderDescription(String description) {
        wait.until(ExpectedConditions.elementToBeClickable(providerDescription));
        providerDescription.clear();
        providerDescription.sendKeys(description);
    }

    public String getProviderNameOnEditbox() {
        VUtils.waitFor(1);
        return providerName.getAttribute("value");
    }

    public String getProviderReferenceOnEditbox() {
        VUtils.waitFor(1);
        return providerReference.getAttribute("value");
    }

    public String getProviderDescriptionOnEditbox() {
        VUtils.waitFor(1);
        return providerDescription.getAttribute("value");
    }

    public boolean isNewProviderErrorMessageDisplayed(String message) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"provider-update\"]/ul/li[text()='" + message + "']")));
        return true;
    }

    public void canSelectProvidersLink(boolean b, String result) {
        if (result.equalsIgnoreCase("can")) {
            LOGGER.info("Going to Provider Page");
            if (VUtils.isElementPresent(VoyantaDriver.getCurrentDriver().findElement(By.xpath("//a[contains(text(),'Provider')]")))) {
                try {
                    VoyantaDriver.findElement(By.linkText("Provider")).click();
                } catch (Exception e) {
                    VoyantaDriver.getCurrentDriver().findElement(By.xpath("//a[contains(text(),'Provider')]")).click();
                }
            }
            VUtils.waitFor(2);
        } else {
            LOGGER.info("Verifying the Providers Link Present  : " + b);
            VerifyUtils.equals(b, VUtils.isElementPresent(By.linkText("Providers")));
        }
    }

    public void canSeeCreateNewProvider(boolean b) {
        LOGGER.info("Verifying the Create New Provider Link Present : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.linkText("Create New Provider")));
    }

    public void canSelectExportLink(boolean b, String result) {
        if (result.equalsIgnoreCase("can")) {
            LOGGER.info("Going to Export Page");
            VoyantaDriver.findElement(By.linkText("Exports")).click();
            VUtils.waitFor(2);
        } else {
            LOGGER.info("Verifying the Export Link Present : " + b);
            VerifyUtils.equals(b, VUtils.isElementPresent(By.linkText("Exports")));
        }
    }

    public void goToProviderPage() {
        selectAdmin();
        selectProvider();
    }

    private void selectProvider() {
        VoyantaDriver.findElement(By.linkText("Provider")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
    }
}
