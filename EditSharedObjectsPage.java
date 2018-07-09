package voyanta.ui.pageobjects;

import org.openqa.selenium.By;
import voyanta.ui.utils.VoyantaDriver;

public class EditSharedObjectsPage {

    public void selectCheckBox(String options) {
        String[] optionsAsArray;
        optionsAsArray = options.split(",");
        for (String option : optionsAsArray) {
            //checkbox xpath
            VoyantaDriver.getCurrentDriver().findElement(By.xpath("//*[@id='related-objects']/ul/li/span[text()='" + option + "']/preceding-sibling::input")).click();
        }
    }
}
