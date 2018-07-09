package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.RentalArrearsPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;


public class RentalArrearsPage extends TenancySchedulePage {

    private static final String url = PropertiesLoader.getProperty("ui_url") + "report/";
    public static Logger LOGGER = Logger.getLogger(ReportsPage.class);
    static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10);

    RentalArrearsPageContainer rentalArrearsPageContainer;

    public RentalArrearsPage() {
        rentalArrearsPageContainer = RentalArrearsPage.getDataContainer(RentalArrearsPageContainer.class);
        LOGGER.info("user goes to report page");
    }

    // This should be embedded into Rental arrears page once is refactored
    // ideally a 'common elements object'
    public void goToRentalArrears2() {
        pageContainer.reportingLink.click();
        wait.until(ExpectedConditions.visibilityOf(rentalArrearsPageContainer.rentalArrears2Link));
        rentalArrearsPageContainer.rentalArrears2Link.click();
        VUtils.waitFor(1);
    }

    public void validateReportIsSelected() {
        VerifyUtils.True(
                rentalArrearsPageContainer.rentalArrears2NavBarEle.getAttribute("class")
                        .equalsIgnoreCase("selected"));
    }
}
