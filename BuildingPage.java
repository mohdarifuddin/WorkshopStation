package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.BuildingPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

/**
 * Created by reshma on 28/10/2015.
 */
public class BuildingPage extends AbstractVoyantaPage {

    private static final String url = PropertiesLoader.getProperty("ui_url") + "/browse/building";
    static Logger LOGGER = Logger.getLogger(BuildingPage.class);
    static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 5);

    BuildingPageContainer pageContainer = BuildingPage.getDataContainer(BuildingPageContainer.class);

//    public void goToBuildingPage(){
//       HomePage homePage = new HomePage();
//        homePage.selectBrowse();
//        VUtils.waitFor(1);
//        VoyantaDriver.findElement(By.cssSelector(".container-fluid>ul>li>ul>li>a")).click();
//        VUtils.waitFor(2);
//    }

    public void isBrowserPageDisplayed() {
        VUtils.waitFor(2);
        String expectedTitle = "Voyanta - Object Browse";
        VerifyUtils.equals(expectedTitle, VoyantaDriver.getCurrentDriver().getTitle());

        String expectedHeading = "Building List";

        VerifyUtils.equals(expectedHeading, pageContainer.h1.getText());
        LOGGER.info("User is on Building page");
    }

    public AssertOverviewPage clickOnAssertOverviewButton() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.assertOverviewButton));
        pageContainer.assertOverviewButton.click();
        //VUtils.waitFor(3);
        return new AssertOverviewPage();
    }

    public void searchWithBuildingName(String buildingName) {
        LOGGER.info(("Searching with Building name: " + buildingName));
        wait.until(ExpectedConditions.visibilityOf(pageContainer.searchTextBox));
        pageContainer.searchTextBox.clear();
        pageContainer.searchTextBox.sendKeys(buildingName);
        //VUtils.waitFor(2);
        pageContainer.searchButton.click();
        //VUtils.waitFor(2);
    }

    public void clickOnBuildingName(String expectedBuildingName) {

        //WebElement buildingNameLink = VoyantaDriver.findElement(By.linkText(buildingName));
        String buildingNameAfterSearch = pageContainer.objectDetailsLinkTitle.getText();
        //Verify that building name found after search
        VerifyUtils.equals(expectedBuildingName, buildingNameAfterSearch);
        pageContainer.objectDetailsLinkTitle.click();
        VUtils.waitFor(2);

    }


}
