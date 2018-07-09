package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

/**
 * Created by Hiten.Parma on 16/05/2016.
 */
public class AppraisalSummaryPage extends AbstractVoyantaPage {

    private Logger LOGGER = Logger.getLogger(AppraisalSummaryPage.class);
    private WebDriverWait wait;
    private WebDriver driver = VoyantaDriver.getCurrentDriver();
    private String pageLoading = "//*[@id='loader']";

    public AppraisalSummaryPage() {
        this.wait = new WebDriverWait(driver, 60);
        if (VUtils.isElementPresent(By.xpath(pageLoading))) {
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(pageLoading)));
        }
    }
}
