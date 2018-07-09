package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VoyantaDriver;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Lewis.Prescott on 18/04/2016.
 */
public class CommonMethodsPage extends AbstractVoyantaPage {

    static Logger LOGGER = Logger.getLogger(CommonMethodsPage.class);
    public static final String url = PropertiesLoader.getProperty("ui_url") + "administration/rule-builder/business-rules";
    static WebDriverWait wait;

    public static WebDriver mDriver = VoyantaDriver.getCurrentDriver();
    public String parentWindow = mDriver.getWindowHandle();
    public String subWindow = null;
    public Set<String> handles = mDriver.getWindowHandles();
    public Iterator<String> iterator = handles.iterator();

    public static void openPage(String page) {
        mDriver.get(PropertiesLoader.getProperty("ui_url") + page);
        //VUtils.waitFor(10);
        wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
    }

}

