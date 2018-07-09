package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.AbstractPageContainer;
import voyanta.ui.pagecontainers.AccountPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;
import voyanta.ui.webdriver.core.elements.impl.internal.ElementFactory;

import java.util.List;


public class AccountPage {
    public static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10L);
    static Logger LOGGER = Logger.getLogger(ListOfValuePage.class);
    WebDriver mDriver = VoyantaDriver.getCurrentDriver();
    private static final String url = PropertiesLoader.getProperty("ui_url") + "account";
    AccountPageContainer accountPageContainer = AccountPage.getDataContainer(AccountPageContainer.class);

    @FindBy(id = "name")
    WebElement nameTextField;

    @FindBy(xpath = "//*[@id='account']/div[2]/div/div[2]/strong/a")
    WebElement accountNameInHeader;

    @FindBy(linkText = "My Preferences")
    WebElement tabMyPreference;

    @FindBy(id = "autoApprove")
    WebElement autoapprovecheckbox;

    @FindBy(id = "vForm")
    WebElement PreferencesSave;

    @FindBy(how = How.CSS, using = "a.user.clearfix")
    WebElement accountIcon;

    @FindBy(how = How.XPATH, using = "//*[@class='user-info-panel']")
    WebElement accountPanel;

    @FindBy(how = How.CSS, using = "label.white")
    WebElement accountName;

    public AccountPage() {
//        wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//*[@id='content']/h1")), "Account Page"));
    }

    public static <T extends AbstractPageContainer> T getDataContainer(Class<T> className) {
        return ElementFactory.initElements(VoyantaDriver.getCurrentDriver(), className);
    }

    public static AccountPage openPage() {
        VoyantaDriver.getCurrentDriver().get(url);
        VUtils.waitFor(2);
        return new AccountPage();
    }

    public String getName() {
        return nameTextField.getAttribute("value");
    }

    public void enterName(String name) {
        nameTextField.clear();
        nameTextField.sendKeys(name);
    }

    public void clickSave() {
        accountPageContainer.btnSave.click();
        VUtils.waitFor(2);
    }

    public Boolean isUpdateMessageDisplayed(String message) {
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='form-messenger'][text()='" + message + "']")));
        if (VoyantaDriver.findElement(By.xpath("//*[@id='form-messenger'][text()='" + message + "']")).isDisplayed()) {
            VUtils.waitFor(3);
            return true;
        } else
            return false;
    }

    public String getAccountNameInHeader() {
        accountIcon.click();
        wait.until(ExpectedConditions.visibilityOf(accountPanel));
        return accountName.getText();
    }

    public void selectTab(String tabName) {
        switch (tabName) {
            case "Name":
                accountPageContainer.tabName.click();
                break;
            case "Password":
                accountPageContainer.tabPassword.click();
                break;
            case "My Preferences":
                accountPageContainer.tabMyPreferences.click();
                break;
            case "Notifications":
                accountPageContainer.tabNotification.click();
                break;
        }
    }

    public void checkCurrentLanguage(String currentLanguage) {
        WebElement selectedLanguage = mDriver.findElement(By.id("language_chzn")).findElement(By.className("chzn-single")).findElement(By.tagName("span"));
        VerifyUtils.True(selectedLanguage.getText().equals(currentLanguage));
    }

    public void selectLanguage(String newLanguage) {
        LOGGER.info("Selecting language from the List: " + newLanguage);
        accountPageContainer.dropLang.click();
        VUtils.waitFor(2);
        WebElement rowElement = mDriver.findElement(By.id("language_chzn")).findElement(By.className("chzn-drop"));
        List<WebElement> options = rowElement.findElement(By.className("chzn-results")).findElements(By.tagName("li"));

        for (WebElement e : options) {
            if (e.getText().equals(newLanguage)) {
                e.click();
                VUtils.waitFor(2);
                return;
            } else {
                LOGGER.info(newLanguage + " is not present in the language list...");
            }
        }
    }

    public void checkCurrentCurrency(String currentCurrency) {
        WebElement selectedLanguage = mDriver.findElement(By.id("defaultCurrency_chzn")).findElement(By.className("chzn-single")).findElement(By.tagName("span"));
        VerifyUtils.True(selectedLanguage.getText().equals(currentCurrency));
    }

    public void selectCurrency(String newCurrency) {
        LOGGER.info("Selecting currency from the List: " + newCurrency);
        accountPageContainer.dropCurrency.click();
        VUtils.waitFor(2);
        WebElement rowElement = mDriver.findElement(By.id("defaultCurrency_chzn")).findElement(By.className("chzn-drop"));
        List<WebElement> options = rowElement.findElement(By.className("chzn-results")).findElements(By.tagName("li"));

        for (WebElement e : options) {
            if (e.getText().equals(newCurrency)) {
                e.click();
                VUtils.waitFor(2);
                return;
            } else {
                LOGGER.info(newCurrency + " is not present in the currency list...");
            }
        }
    }

    public void checkCurrentMeasurementUnit(String currentUnit) {
        WebElement selectedLanguage = mDriver.findElement(By.id("defaultAreaMeasurementUnit_chzn")).findElement(By.className("chzn-single")).findElement(By.tagName("span"));
        VerifyUtils.True(selectedLanguage.getText().equals(currentUnit));
    }

    public void selectCurrentMeasurementUnit(String newUnit) {
        LOGGER.info("Selecting measurement unit from the List: " + newUnit);
        accountPageContainer.dropAMU.click();
        VUtils.waitFor(2);
        WebElement rowElement = mDriver.findElement(By.id("defaultAreaMeasurementUnit_chzn")).findElement(By.className("chzn-drop"));
        List<WebElement> options = rowElement.findElement(By.className("chzn-results")).findElements(By.tagName("li"));

        for (WebElement e : options) {
            if (e.getText().equals(newUnit)) {
                e.click();
                VUtils.waitFor(2);
                return;
            } else {
                LOGGER.info(newUnit + " is not present in the measurement unit list...");
            }
        }
    }

    public void MyPreferencestab() {
        tabMyPreference.click();
    }

    public void checkAutoApprove() {
        autoapprovecheckbox.click();
        VUtils.waitFor(3);
    }

    public void SaveButton() {
        PreferencesSave.click();
        VUtils.waitFor(3);
    }

    public void checkAutoApproval(String currentApproval) {
        if (currentApproval.equals("Enabled")) {
            VerifyUtils.True(autoapprovecheckbox.isSelected());
        } else if (currentApproval.equals("Disabled")) {
            VerifyUtils.False(autoapprovecheckbox.isSelected());
        }
    }

    public void enterPassword(String password, String which) {
        switch (which) {
            case "old":
                accountPageContainer.inputOldPassword.sendKeys(password);
                break;
            case "new":
                accountPageContainer.inputNewPassword.sendKeys(password);
                break;
            case "confirm":
                accountPageContainer.inputConfirmPW.sendKeys(password);
                break;
        }
    }

    public void checkPopUpErrorMessage(String which, String message) {
        String actualMessage = "";
        String[] orders;

        switch (which) {
            case "old":
                actualMessage = accountPageContainer.oldPasswordErrorMessage.getText();
                break;
            case "new":
                actualMessage = accountPageContainer.newPasswordErrorMessage.getText();
                break;
            case "confirm":
                actualMessage = accountPageContainer.confirmPasswordErrorMessage.getText();
                break;
        }

        //In case of multiple error messages in the same pop up, take into account that the order is not fixed.
        if (actualMessage.contains("\n")) {
            orders = actualMessage.split("\n");
            boolean isActualMessage = true;

            for (String order : orders) {
                if (actualMessage.contains(order)) {
                    actualMessage = actualMessage.replace(order, "");
                } else {
                    isActualMessage = false;
                }
            }

            VerifyUtils.True(isActualMessage);
        } else {
            VerifyUtils.True(actualMessage.equals(message));
        }
    }

    public void clickErrorSign(String which) {
        switch (which) {
            case "old":
                accountPageContainer.errorSignOldPassword.click();
                break;
            case "new":
                accountPageContainer.errorSignNewPassword.click();
                break;
            case "confirm":
                accountPageContainer.errorSignConfirmPassword.click();
                break;
        }
    }

    public void checkErrorSign(String which) {
        switch (which) {
            case "old":
                VerifyUtils.True(accountPageContainer.errorSignOldPassword.isDisplayed());
                break;
            case "new":
                VerifyUtils.True(accountPageContainer.errorSignNewPassword.isDisplayed());
                break;
            case "confirm":
                VerifyUtils.True(accountPageContainer.errorSignConfirmPassword.isDisplayed());
                break;
        }
    }

    public void checkNotificationPreferenceStatus(String which, boolean enabled) {
        boolean isActuallyEnabled = false;

        switch (which) {
            case "waiting":
                isActuallyEnabled = accountPageContainer.waitingForApprovalNotification.isSelected();
                break;
            case "rejected":
                isActuallyEnabled = accountPageContainer.rejectedNotification.isSelected();
                break;
        }

        VerifyUtils.True(enabled == isActuallyEnabled);
    }


    public void changeNotificationPreferenceStatus(String which) {
        switch (which) {
            case "waiting":
                accountPageContainer.waitingForApprovalNotification.click();
                break;
            case "rejected":
                accountPageContainer.rejectedNotification.click();
                break;
        }
    }

    public void setAutoApprovalOnOrOff(boolean b) {
        if (b == true && !autoapprovecheckbox.isSelected()) {
            autoapprovecheckbox.click();
            VUtils.waitFor(1);
            LOGGER.info("Making AutoApprove Enable : " + b);
        } else if (b == false && autoapprovecheckbox.isSelected()) {
            autoapprovecheckbox.click();
            VUtils.waitFor(1);
            LOGGER.info("Making AutoApprove Disable : " + b);
        }
    }

    public void checkPreferenceUpdateMsg(String msg) {
        WebElement actualMsg = VoyantaDriver.findElement(By.id("form-messenger"));
        VerifyUtils.equals(msg, actualMsg.getText());
        VUtils.waitFor(5);
    }
}
