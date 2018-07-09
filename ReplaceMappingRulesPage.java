package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import voyanta.ui.pagecontainers.ReplaceMappingPageContainer;
import voyanta.ui.utils.VoyantaDriver;

/**
 * Created by EPhilip on 07/03/2017.
 */
public class ReplaceMappingRulesPage extends AbstractVoyantaPage {

    WebDriver driver = VoyantaDriver.getCurrentDriver();
    ReplaceMappingPageContainer replaceMappingPageContainer = ReplaceMappingRulesPage.getDataContainer(ReplaceMappingPageContainer.class);
    static Logger LOGGER = Logger.getLogger(ListDataManagerPage.class);

    public void clickOrderRules() {
        LOGGER.info("Clicking the order rules link");
        replaceMappingPageContainer.orderRules.click();
    }

    public void dragRule() {
        LOGGER.info("Dragging the rule");

        Actions builder = new Actions(driver);

        Action dragAndDrop = builder.clickAndHold(driver.findElement(By.xpath(".//*[@id='ordered-list-region']/table/tbody/tr[2]/td[1]/div")))
                .moveToElement(driver.findElement(By.xpath(".//*[@id='ordered-list-region']/table/tbody/tr[1]/td[1]")))
                .release(driver.findElement(By.xpath(".//*[@id='ordered-list-region']/table/tbody/tr[2]/td[1]/div")))
                .build();

        dragAndDrop.perform();

    }

    public void clickSave() {
        LOGGER.info("Saving the newly ordered rules");
        replaceMappingPageContainer.save.click();
    }
}
