package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.AccountCreatePageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VoyantaDriver;

import java.util.concurrent.TimeUnit;

public class AccountCreatePage extends AbstractVoyantaPage {
    WebDriver voyantaDriver = VoyantaDriver.getCurrentDriver();
    WebDriverWait wait = new WebDriverWait(voyantaDriver, 10L);
    private static final String URL = PropertiesLoader.getProperty("ui_url") + "account/create";
    static Logger LOGGER = Logger.getLogger(AccountCreatePage.class);
    AccountCreatePageContainer accountCreatePageContainer = this.getDataContainer(AccountCreatePageContainer.class);
    public static final String PAGE_NAME = "Account Create";

    public static AccountCreatePage openPage() {
        VoyantaDriver.getCurrentDriver().get(URL);
        LOGGER.info(String.format("**** Arriving on %s ****", PAGE_NAME));
        return new AccountCreatePage();
    }

    public void enterAdminPasssword(String password) {
        accountCreatePageContainer.adminPassword.sendKeys(password);
    }

    public void enterUserEmail(String email) {
        accountCreatePageContainer.userEmail.sendKeys(email);
    }

    public void enterUserName(String userName) {
        accountCreatePageContainer.userName.sendKeys(userName);
    }

    public void enterUserPassword(String userPassword) {
        accountCreatePageContainer.userPassword.sendKeys(userPassword);
    }

    public void enterOrganisationName(String organisationName) {
        accountCreatePageContainer.organizationName.sendKeys(organisationName);
    }

    public void enterOrganisationDomain(String organisationDomain) {
        accountCreatePageContainer.organizationDomain.sendKeys(organisationDomain);
    }

    public void pressSubmitButton() {

//		TODO: Remove this click once VOY-15316 fix
        VoyantaDriver.getCurrentDriver().findElement(By.xpath("//div")).click();

        accountCreatePageContainer.submitButton.click();
        wait.withTimeout(5, TimeUnit.MILLISECONDS);
    }

    public Boolean isDisplayMessagePresent(String message) {
        final String xpathForMessage = "//div[@id='messenger-region']";
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(String.format(xpathForMessage, message))));
        return true;
    }

    @Override
    public void logout() {
        try {
            super.logout();
        } catch (Error e) {
            LOGGER.info("Failed to logout, may never have been logged in");
        }
    }
}
