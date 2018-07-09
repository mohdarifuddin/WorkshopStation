package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VoyantaDriver;

/**
 * Created by javier.sirvent on 28/08/2015.
 */
public class PendingPage extends AbstractVoyantaPage {

    private static final String url = PropertiesLoader.getProperty("ui_url") + "data-management/pending";
    static Logger LOGGER = Logger.getLogger(PendingPage.class);
    private static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 300);

    public PendingPage() {

    }

    public static PendingPage openPage() {
        VoyantaDriver.getCurrentDriver().get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));

        return new PendingPage();
    }
}
