package voyanta.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

public class CreateProviderPage {

    WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10L);

    @FindBy(id = "providerName")
    WebElement providerName;

    @FindBy(id = "providerReference")
    WebElement providerReference;

    @FindBy(id = "providerDescription")
    WebElement providerDescription;

    @FindBy(id = "save-provider")
    WebElement saveButton;


    public CreateProviderPage() {
//        wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//*[@id='content']/h1")), "Create Provider"));
    }

    public void enterProviderName(String name) {
        providerName.sendKeys(name);
    }

    public void enterProviderReference(String reference) {
        providerReference.sendKeys(reference);
    }

    public void enterProviderDescription(String description) {
        providerDescription.sendKeys(description);
    }

    public void clickSave() {
        saveButton.click();
        VUtils.waitFor(3);
    }

    public boolean isSaveButtonDisabled() {
        return VUtils.isElementPresent(By.xpath("//*[@id='save-provider'][@disabled='disabled']"));
    }
}
