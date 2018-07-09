package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

/**
 * Created by Hiten.Parma on 20/01/2016.
 */
public class RiskScorePage extends AbstractVoyantaPage {

    Logger LOGGER = Logger.getLogger(RiskScorePage.class);
    public WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 60);

    public void exportRiskScore() {
        if (VUtils.isElementPresent(By.cssSelector("span.control-msg")))
            this.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.control-msg")));

        WebElement investmentInfoElement = VoyantaDriver.findElement(By.xpath("//*[@id='wj-report']/div[2]/div[1]"));
        WebElement exportElement = investmentInfoElement.findElement(By.xpath("//span[@id='exportRisk']/a/span[2]"));
        exportElement.click();
        LOGGER.info("***** Exporting Risk Score Report *****");
        VUtils.waitFor(10);
    }

}
