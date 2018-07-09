package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.ListOfBusinessRulesPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

/**
 * Created by sriramangajala on 23/07/2014.
 */
public class ListOfBusinessRulesPage extends AbstractPageWithList {

    static Logger LOGGER = Logger.getLogger(ListOfBusinessRulesPage.class);
    public static final String url = PropertiesLoader.getProperty("ui_url") + "administration/rule-builder/business-rules";
    static WebDriverWait wait;

    ListOfBusinessRulesPageContainer container = ListOfBusinessRulesPage.getDataContainer(ListOfBusinessRulesPageContainer.class);

    public ListOfBusinessRulesPage() {
        //   VUtils.waitForElement(container.getDefaultElement());
        super.tableElement = container.tableElement;
    }

    public CreateRulePage createRule() {
        wait.until(ExpectedConditions.visibilityOf(container.buttonCreateBR));
        container.buttonCreateBR.click();
//        VoyantaDriver.getCurrentDriver().navigate().refresh();
        return new CreateRulePage();
    }


    public static ListOfBusinessRulesPage openPage() {
        VoyantaDriver.getCurrentDriver().get(url);
        wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        return new ListOfBusinessRulesPage();
    }

    public CreateRulePage editRule(String businessruleName) {
        container.buttonEdit.click();
        return new CreateRulePage();
    }

    public int findTheRuleByName(String ruleName) {
        int rowNumber = 0;
        return rowNumber;
    }

    public void checkBusinessRuleExists(String businessRule) {
        checkTheRowPresentWithText(businessRule);
    }

    public void printBRS() {
        getRowsInHash();
    }

    public void deleteRule(String ruleName) {

        //   LOGGER.info("Element found with text:"+actionsElements.getText());
        getActionElements(ruleName).findElement(By.linkText("Delete")).click();
        VUtils.waitFor(2);

    }

    public void deleteRuleByName(String ruleName) {
        VoyantaDriver.getCurrentDriver().findElement(By.xpath("//*[@id='list-table-holder']/table/tbody/tr/td[text()='" + ruleName + "']/following-sibling::td/a[text()='Delete']")).click();
        VUtils.waitFor(2);
        confirmDelete();
    }

    public WebElement getActionElements(String ruleName) {
        VUtils.waitFor(2);
        return getRowElementFromText("Name", ruleName, "Actions");
    }


    public void confirmDelete() {
        VoyantaDriver.click(By.linkText("Yes"));
        VUtils.waitFor(4);
        VoyantaDriver.getCurrentDriver().navigate().refresh();
        VUtils.waitFor(4);
    }

    public boolean isConfirmationDialogShown() {
        return VoyantaDriver.isTextPresent("Confirm deletion");
    }

    public boolean isRulePresent(String ruleName) {
        return VoyantaDriver.isTextPresent(ruleName);
    }

    public CreateRulePage editRuleByName(String ruleName) {
        getActionElements(ruleName).findElement(By.linkText("Edit")).click();
        VUtils.waitFor(2);
        return new CreateRulePage();
    }

    public ListOfMappingRulesPage gotoMappingRulePage() {
//            container.linkMappingRule.click();
        container.replaceMappingLink.click();
        return new ListOfMappingRulesPage();
    }

    public CreateRulePage createMappingRule() {
        container.createMappingRule.click();
        VoyantaDriver.getCurrentDriver().navigate().refresh();
        return new CreateRulePage();
    }

    public CreateRulePage clickOnAndButton() {
        VoyantaDriver.findElement(By.linkText("+ AND")).click();
        VUtils.waitFor(2);
        return new CreateRulePage();
    }

    public void canSeeBusinessRulesTab(boolean b) {
        LOGGER.info("Checking Business Rules Tab is Present : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.xpath("(//a[contains(text(),'Business Rules')])[2]")));
    }

    public void isViewSystemRulesBox(boolean b) {
        LOGGER.info("Checking View System Rules Box is Present : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.cssSelector("div.filter-checkbox")));
    }

    public void isFilterResultsPresent(boolean b) {
        LOGGER.info("Checking Filter Results is Present : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.cssSelector("label.filters-label")));
    }

    public void isCreateRulePresent(boolean b) {
        LOGGER.info("Checking Create Business Rule Button Present : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.linkText("Create Business Rule")));
    }

    public void isOrgBusinessRulePresent(boolean b, String ruleName) {
        LOGGER.info("Checking Organisation Business Rule Present : " + b);
        if (b == true) {
            WebElement rules = VoyantaDriver.findElement(By.xpath("//*/tr/td[1]"));
            VerifyUtils.equals(ruleName, rules.getText());
        } else {
            LOGGER.info("User Don't have Permission ");
        }
    }

    public void isRMTabPresent(boolean b, String result) {
        LOGGER.info("Checking Replace / Mappings Tab Present :" + b);
        if (result.equalsIgnoreCase("can")) {
//           container.linkMappingRule.click();
            container = new ListOfBusinessRulesPageContainer();
            //wait.until(ExpectedConditions.elementToBeClickable(container.replaceMappingLink));
            //container.replaceMappingLink.click();
            pageContainer.businessRulesList.forEach(element -> {
                if (element.getText().contains("Replace / Mappings")) element.click();
            });
            //System.out.println("Tabs: " + businessRuleTabList);
            //VerifyUtils.equals(b,businessRuleTabList.contains("Replace / Mappings"));
            VUtils.waitFor(2);
        } else {
            VerifyUtils.equals(b, isRMTabDisplayed(b));
        }
    }

    public boolean isRMTabDisplayed(boolean b) {
        try {
//            return container.linkMappingRule.isDisplayed();
            return container.replaceMappingLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Checking the Replace / Mapping Tab Present : " + b);
        }
        return false;
    }

    public void isCreateRMPresent(boolean b) {
        LOGGER.info("Checking Create Replace or Mapping Rule Button Present : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.linkText("Create Replace or Mapping Rule")));
    }

    public void isFilterResultsPresentOnRM(boolean b) {
        LOGGER.info("Checking Filter Results on Replace / Mappings Page " + b);
        if (b == true) {
            LOGGER.info("The user can see Filter Result");
            VerifyUtils.equals(b, VUtils.isElementPresent(By.cssSelector("label.filters-label")));
        } else {
            LOGGER.info("The User Cannot See Filter Results");
        }
    }
}
