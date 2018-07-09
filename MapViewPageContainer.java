package voyanta.ui.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import voyanta.ui.pagecontainers.AbstractVoyantaPageContainer;

import java.util.List;

/**
 * Created by arifuddin.mohd on 1/22/2018.
 */
public class MapViewPageContainer extends AbstractVoyantaPageContainer {

    @FindBy(how = How.CSS, using = "a.toggleButton.actionToggle")
    public WebElement filterToggle;

    @FindBy(how = How.CSS, using = "a.button.actionApply")
    public WebElement applyFilterButton;

    @FindBy (how = How.XPATH, using = "//button[@title='Zoom in']")
    public WebElement zoomIn;

    @FindBy (how = How.XPATH, using = "//h6[text()='Property Type']/../..//div[@class='filter-content']")
    public WebElement propertyType;

    @FindBy (how = How.XPATH, using = "//h6[text()='Region']/../..//div[@class='filter-content']")
    public WebElement regionSelect;

    @FindBy (how = How.XPATH, using = "//h6[text()='Asset Manager']/../..//div[@class='filter-content']")
    public WebElement assetManager;

    @FindBy (how = How.XPATH, using = "//div[@class='assets-region']//div[@class='header']")
    public WebElement assetRegionPlaneHeader;

    @FindBy (how = How.XPATH, using = "//div[@class='assets-container']//div[@class='asset']")
    public List <WebElement> assetsContainer;

    @FindBy(how = How.CSS, using = "a.button.actionClear")
    public WebElement clearButton;

}
