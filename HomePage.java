package voyanta.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.HomePageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VoyantaDriver;

import static org.junit.Assert.assertTrue;

public class HomePage extends AbstractVoyantaPage {
    final static String URL = PropertiesLoader.getProperty("ui_url");
    WebDriver voyantaDriver = VoyantaDriver.getCurrentDriver();
    public TermsAndConditionPopUp termsAndConditionPopUp = new TermsAndConditionPopUp();

    HomePageContainer homePageContainer = this.getDataContainer(HomePageContainer.class);

    public HomePage() {
        super();
    }

    public String getAccount() {
        return homePageContainer.linkAccount.getText();
    }

    public String getOrganisationName() {
        return homePageContainer.testOrg.getText();
    }

    public static HomePage openPage() {
        VoyantaDriver.getCurrentDriver().get(URL);
        return new HomePage();
    }

    public class TermsAndConditionPopUp {

        public void verifyTermsAndConditionsPopUpWindowExists() {
            final WebDriverWait wait = new WebDriverWait(voyantaDriver, 10L);
            final String popUpId = "modal-dialog";

            wait.until(ExpectedConditions.titleIs("Voyanta - Dashboard"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(popUpId)));
            WebElement popUp = voyantaDriver.findElement(By.id(popUpId));
            WebElement content = popUp.findElement(By.className("dialog-frame"));
            assertTrue(content.getText().contains("Please agree to the policies below to continue:"));
        }

        public void clickDoNotShowMeCheckBox() {
            final String popUpId = "modal-dialog";
            WebElement popUp = voyantaDriver.findElement(By.id(popUpId));
            WebElement checkBox = popUp.findElement(By.className("notification-controls-row"));
            checkBox.findElement(By.id("remove-all-notifications")).click();
        }

        public void clickSubmitIAcceptTheseChanges() {
            final String popUpId = "modal-dialog";
            WebElement popUp = voyantaDriver.findElement(By.id(popUpId));
            WebElement checkBox = popUp.findElement(By.className("controls-row"));
            checkBox.findElement(By.className("actionSaveAndContinue")).click();
        }

    }

}
