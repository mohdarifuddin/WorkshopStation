package voyanta.ui.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.DemograhicsDisclaimerPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VoyantaDriver;

/**
 * Created by EPhilip on 03/04/2017.
 */
public class DemographicsDisclaimerPage extends AbstractVoyantaPage {
    WebDriver driver = VoyantaDriver.getCurrentDriver();
    WebDriverWait wait = new WebDriverWait(driver, 20);
    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DemographicsDisclaimerPage.class);
    private static final String url = PropertiesLoader.getProperty("ui_url") + "account/disclaimers";
    DemograhicsDisclaimerPageContainer disclaimerPageContainer = DemographicsDisclaimerPage.getDataContainer(DemograhicsDisclaimerPageContainer.class);

    public void navigate_to_disclaimer() {

        driver.get(url);
        LOGGER.info("Navigating to disclaimer page");
    }

    public void uncheck_disclaimer_checkbox() {
        LOGGER.info("Unchecking the disclaimer accept checkbox");
        wait.until(ExpectedConditions.elementToBeClickable(disclaimerPageContainer.disclaimerAcceptCheckbox));
        if (disclaimerPageContainer.disclaimerAcceptCheckbox.isSelected()) {
            disclaimerPageContainer.disclaimerAcceptCheckbox.click();
        }
    }

    public void accept_disclaimer() {
        LOGGER.info("Accepting the disclaimer on the upload page");
        disclaimerPageContainer.acceptDemoDisUpload.click();
    }

    public void close_popup() {
        LOGGER.info("Closing the disclaimer pop up");
        disclaimerPageContainer.close.click();
    }
}
