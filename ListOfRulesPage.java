package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.datamodel.DBUtils;
import voyanta.ui.pagecontainers.ListOfRulesPageContainer;
import voyanta.ui.utils.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Reshma.shaik on 06/10/2015.
 */
public class ListOfRulesPage extends AbstractPageWithList {

    static Logger LOGGER = Logger.getLogger(ListOfRulesPage.class);
    public static final String url = PropertiesLoader.getProperty("ui_url") + "administration/rule-builder/business-rules";
    static WebDriverWait wait;
    int rowNumber;
    int row = 0;

    public WebDriver mDriver = VoyantaDriver.getCurrentDriver();
    public String parentWindow = mDriver.getWindowHandle();
    public String subWindow = null;
    public Set<String> handles = mDriver.getWindowHandles();
    public Iterator<String> iterator = handles.iterator();


    ListOfRulesPageContainer container = ListOfRulesPage.getDataContainer(ListOfRulesPageContainer.class);

    public ListOfRulesPage() {
        super.tableElement = container.tableElement;
    }

    public CreateRulePage createRule() {
        //VoyantaDriver.getCurrentDriver().navigate().refresh();
        WebElement createBR = container.buttonCreateBR;
        WaitUtils.waitForElement(createBR);
        createBR.click();
        //VoyantaDriver.getCurrentDriver().navigate().refresh();
        return new CreateRulePage();
    }

    public static ListOfRulesPage openPage() {
        VoyantaDriver.getCurrentDriver().get(url);
        //VUtils.waitFor(10);
        wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        return new ListOfRulesPage();
    }

    public static void openPage(String page) {
        VoyantaDriver.getCurrentDriver().get(PropertiesLoader.getProperty("ui_url") + page);
        //VUtils.waitFor(10);
        wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
    }

    public CreateRulePage editRule(String rulename) {
        container.buttonEdit.click();
        return new CreateRulePage();
    }

    public int findTheRuleByName(String ruleName) {
        int rowNumber = 0;
        return rowNumber;
    }

    public void checkRuleExists(String businessRule) {
        checkTheRowPresentWithText(businessRule);
    }

    public void printBRS() {
        getRowsInHash();
    }

    public void deleteRule(String ruleName) {
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
        container.CreateMappingRule.click();
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
        LOGGER.info("Checking Replace / Mapping Tab Present :" + b);
        if (result.equalsIgnoreCase("can")) {
//           container.linkMappingRule.click();
            container.replaceMappingLink.click();
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

    public void checkRulePresent(boolean b, String ruleName) {
        String column = "Name";
        getRowsInHash();
//        rowNumber = getRowFromRuleList(column, ruleName);
//        VerifyUtils.equals(ruleName,getCellElementWithRow(rowNumber+1,"Name").getText());
        LOGGER.info("Verifying the Rule is Present : " + b);
        VerifyUtils.equals(b, isRulePresentInList(column, ruleName));
    }

    public int getRowFromRuleList(String column, String value) {
        listViewElements = getListViewElements();
        int i = 0, j = 0;
        for (HashMap map : listViewElements) {
            String stringValue = ((WebElement) map.get(column)).getText().trim();
            if (stringValue.equalsIgnoreCase(value)) {
                LOGGER.info("Found Rule :" + ((WebElement) map.get(column)).getText());
                return i;
            }
            i++;
        }
        LOGGER.info("Rule is Not Present On List of Rules Page :" + i);
        return j;
    }

    public boolean isRulePresentInList(String column, String ruleName) {
        listViewElements = getListViewElements();
        for (HashMap map : listViewElements) {
            String stringValue = ((WebElement) map.get(column)).getText().trim();
            if (stringValue.equalsIgnoreCase(ruleName)) {
                LOGGER.info("Found Rule : " + ((WebElement) map.get(column)).getText() + " at Row : " + row);
                return true;
            }
            row++;
        }
        LOGGER.info("Given Rule Not Present");
        return false;
    }

    public boolean isIdPresentInList(int ruleId) {
        int noRows = 0;
        List<WebElement> rowItems = VoyantaDriver.getCurrentDriver().findElements(By.className("item-row"));
        for (WebElement row : rowItems) {
            String stringValue = row.getText().trim();
            if (stringValue.contains(String.valueOf(ruleId))) {
                LOGGER.info("Found Rule Id : " + (row.getText() + " at Row : " + row));
                return true;
            }
            noRows++;
        }
        LOGGER.info("Given Rule Not Present");
        return false;
    }

    public void deleteRuleFromList(String ruleName) {
        String column = "Name";
        String returnColumn = "Actions";
        try {
            getRowsInHash();
            WebElement element = getRowElementFromText(column, ruleName, returnColumn);
            List<WebElement> element1 = element.findElements(By.tagName("a"));
            for (WebElement e : element1) {
                if (e.getText().equals("Delete")) {
                    e.click();
                    VUtils.waitFor(2);
                    while (iterator.hasNext()) {
                        subWindow = iterator.next();
                    }
                    mDriver.switchTo().window(subWindow);
                    LOGGER.info("Deleting Rule: " + ruleName);
                    mDriver.findElement(By.xpath("//div[@id='dialog-region']/div/a")).click();
                    LOGGER.info(ruleName + " is Deleted.");
                    VUtils.waitFor(2);
                    mDriver.switchTo().window(parentWindow);
                    return;
                }
            }
        } catch (NoSuchElementException e) {
            LOGGER.info("Given Rule is not Available to Delete");
        }
    }

    public int getId(String ruleName) throws SQLException {
        DBUtils.connectToDataBase(PropertiesLoader.getProperty("dbuserName"), PropertiesLoader.getProperty("dbpassword"), PropertiesLoader.getProperty("url"));
        String ruleIDQuery = "SELECT business_rule_id FROM voyanta3565.business_rule where description = '" + ruleName + "';";
        return Integer.parseInt(DBUtils.executeAndGetResults(ruleIDQuery).get(0).get("business_rule_id").toString());
    }

    public void deleteRuleFromDB(String ruleName) throws SQLException {
        DBUtils.connectToDataBase(PropertiesLoader.getProperty("dbuserName"), PropertiesLoader.getProperty("dbpassword"), PropertiesLoader.getProperty("url"));
        String ruleDeleteQuery1 = "DELETE FROM voyanta3565.business_rule_i18n where i18ned_id IN (SELECT business_rule_id FROM voyanta3565.business_rule where description = '" + ruleName + "');";
        String ruleDeleteQuery2 = "DELETE FROM voyanta3565.business_rule_to_organization where business_rule_id IN (SELECT business_rule_id FROM voyanta3565.business_rule where description = '" + ruleName + "');";
        String ruleDeleteQuery3 = "DELETE FROM voyanta3565.business_rule where description = '" + ruleName + "';";
        DBUtils.executeAndUpdate(ruleDeleteQuery1);
        DBUtils.executeAndUpdate(ruleDeleteQuery2);
        DBUtils.executeAndUpdate(ruleDeleteQuery3);
    }

    public void createRuleFromDB(String ruleName) throws SQLException {
        DBUtils.connectToDataBase(PropertiesLoader.getProperty("dbuserName"), PropertiesLoader.getProperty("dbpassword"), PropertiesLoader.getProperty("url"));
        String ruleIdUnique = "Select Max(business_rule_id) FROM voyanta3783.business_rule;";
        DBUtils.executeStatement(ruleIdUnique);
        String ruleCreateQuery1 = "INSERT INTO voyanta3783.business_rule" +
                "(business_rule_id, expression, organization_id, dst_column_id, account_id, severity_type_id, description, created, modified, is_private, dst_type_id, mandatory" +
                "(?,?,?,?,?,?,?,?,?,?,?,?";
        PreparedStatement preparedStatement = DBUtils.conn.prepareStatement(ruleCreateQuery1);
        preparedStatement.setInt(1, 2169);
        preparedStatement.setString(2, "$$((obj.column('Debt Facility Name').value() == 'DF Name'))$$");
        preparedStatement.setInt(3, 3783);
        preparedStatement.setInt(4, 858);
        preparedStatement.setInt(5, 1447);
        preparedStatement.setInt(6, 2);
        preparedStatement.setString(7, ruleName);
        preparedStatement.setString(8, "2016-10-13 14:53:07");
        preparedStatement.setString(9, "2016-10-13 14:53:07");
        preparedStatement.setInt(10, 0);
        preparedStatement.setInt(11, 5022);
        preparedStatement.setInt(12, 0);
// execute insert SQL stetement
        preparedStatement.executeUpdate();
    }

    //public static class openPage extends ListOfRulesPage {

    //}
}

