package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import voyanta.ui.pagecontainers.CreateRulesPageContainers;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaBucket;
import voyanta.ui.utils.VoyantaDriver;

import java.util.List;


public class CreateRulePage extends AbstractVoyantaPage {

    static Logger LOGGER = Logger.getLogger(CreateRulePage.class);

    CreateRulesPageContainers pageContainer = CreateRulePage.getDataContainer(CreateRulesPageContainers.class);

    BusinessRule businessRule = new BusinessRule();
    //  Rules rules = new Rules();

    public CreateRulePage() {
        int i = 0;

        while (i < 2) {
            if (!VUtils.waitForPageToLoad(pageContainer.getDefaultElement())) {
                VoyantaDriver.getCurrentDriver().navigate().refresh();
                i++;
            }

            if (VUtils.waitForPageToLoad(pageContainer.getDefaultElement()))
                return;
        }
    }


    public String getHeaderText() {
        return pageContainer.getHeaderElement().getText();
    }

    public void selectSeverityType(String value) {
        String brType = "Trigger an error with the following message";
        VoyantaDriver.selectByList(pageContainer.drpOutCome, null, value);

    }

    public void createBusinessRuleStep1(String ruleName, String objectType, String field, String providerOption, String provider) {

        businessRule.withName(ruleName).withObjectType(objectType).withfield(field).withproviderOption(providerOption).withprovider(provider);
        //rules.withName(ruleName).withObjectType(objectType).withfield(field).withproviderOption(providerOption).withprovider(provider);
        pageContainer.textRuleName.clearText();
        pageContainer.textRuleName.sendKeys(ruleName);
        VoyantaDriver.selectByList(pageContainer.drpObjectType, pageContainer.drpObjectTypeSearch, objectType);
        VoyantaDriver.selectByList(pageContainer.drpField, pageContainer.drpFieldSearch, field);
        if (providerOption.equalsIgnoreCase("Exclude")) {
            {
                pageContainer.radioproviderOption1.click();
            }
        } else {
            pageContainer.radioproviderOption1.click();
        }

        if (provider.equalsIgnoreCase("HCS"))
            pageContainer.HCS.click();
        else if (provider.equalsIgnoreCase("PROV001"))
            pageContainer.PROV001.click();
        else if (provider.equalsIgnoreCase("PROV002"))
            pageContainer.PROV002.click();
        else if (provider.equalsIgnoreCase("PROV003"))
            pageContainer.PROV003.click();
        else if (provider.equalsIgnoreCase("R04"))
            pageContainer.R04.click();
        else if (provider.equalsIgnoreCase("R05"))
            pageContainer.R05.click();
        pageContainer.btnContinue.click();
    }

    public void dragDataValues(String value, String boxName) {
        WebElement firstElement;
        WebElement secondElement;

        if (boxName.equalsIgnoreCase("first expression")) {
            firstElement = getElementfromText(value);
            secondElement = pageContainer.dropClause1;
        } else if (boxName.equalsIgnoreCase("second expression")) {
            firstElement = getElementfromText(value);
            secondElement = pageContainer.dropClause2;
        } else {
            firstElement = getElementfromText(value);
            secondElement = pageContainer.dropClause5;
        }
        //  clearExistingRules();
        // pageContainer.linkDataValueHeader.click();
        if (boxName.equalsIgnoreCase("message")) {
            javascript("scrollIntoView", secondElement);
            //((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].scrollIntoView();", secondElement);
            VUtils.waitFor(2);
            VoyantaDriver.moveElement(firstElement, secondElement);
            VUtils.waitFor(2);
        } else {
            VUtils.waitFor(2);
            VoyantaDriver.moveElement(firstElement, secondElement);
            VUtils.waitFor(2);
        }
    }

    public void setClause(String value1, String value2, String numberOfCondition) {
        WebElement firstElement;
        WebElement secondElement;
        if (numberOfCondition.equalsIgnoreCase("first")) {
            firstElement = getElementfromText(value1);
            secondElement = pageContainer.dropClause1;
        } else {
            firstElement = getElementfromText(value1);
            secondElement = pageContainer.dropClause3;
        }
        //  clearExistingRules();
        pageContainer.linkDataValueHeader.click();
        VUtils.waitFor(2);
        VoyantaDriver.moveElement(firstElement, secondElement);
        VUtils.waitFor(2);
        if (numberOfCondition.equalsIgnoreCase("first")) {
            firstElement = getElementfromText(value2);

            secondElement = pageContainer.dropClause2;
        } else {
            firstElement = getElementfromText(value2);
            secondElement = pageContainer.dropClause4;
        }
        //     pageContainer.linkSpecialValuesHeader.click();
        VUtils.waitFor(2);
        VoyantaDriver.moveElement(firstElement, secondElement);
        VUtils.waitFor(2);
    }

    private WebElement getElementfromText(String value1) {
        if (value1.equalsIgnoreCase("Submitted Value"))
            return pageContainer.drgSubmittedValue;
        else if (value1.equalsIgnoreCase("Current Value"))
            return pageContainer.drgCurrentValue;
        else if (value1.equalsIgnoreCase("Text")) {
            VoyantaDriver.findElement(By.xpath("//*[@id='ui-accordion-rule-selector-header-1']")).click();
            return pageContainer.drgText;
        } else if (value1.equalsIgnoreCase("Number"))
            return pageContainer.drgNumber;
        else if (value1.equalsIgnoreCase("BLANK"))
            return pageContainer.drgBLANK;
        else if ((value1.equalsIgnoreCase("Today"))) {
            VoyantaDriver.findElement(By.xpath("//*[@id='ui-accordion-rule-selector-header-2']")).click();
            return pageContainer.drgTODAY;
        } else
            return null;
    }

    public void selectValuesForFirstExpressionBox(String value) {
        WebElement selectElement = pageContainer.linkSelectValue1;
        selectElement.click();
        // VoyantaDriver.findElement(By.linkText("Select value")).click();
        VUtils.waitFor(2);
        // VoyantaDriver.findElement(By.id("ui-id-497")).click();
        VoyantaDriver.findElement(By.id("searchMenu")).clear();
        VoyantaDriver.findElement(By.id("searchMenu")).sendKeys(value);
        VoyantaDriver.scrollfindElement(By.linkText(value), By.cssSelector("div.scroll-down.active"));
        VUtils.waitFor(1);

    }

    public void selectValueForSecondExpressionBox(String value1, String value2) {
        if (value1.equalsIgnoreCase("")) {
            value1 = null;
        }

        if (value2.equalsIgnoreCase("")) {
            value2 = null;
        }
        if (value1 != null) {

            WebElement selectElement = pageContainer.linkSelectValue2;
            selectElement.click();
            VUtils.waitFor(2);
            VoyantaDriver.findElement(By.xpath("(//input[@id='searchMenu'])[2]")).clear();
            VoyantaDriver.findElement(By.xpath("(//input[@id='searchMenu'])[2]")).sendKeys(value1);
            WebElement secondExpression = VoyantaDriver.findElement(By.xpath("//*[@id='app-clauses']/div/div/div/div/div/div/div/div/div[3]"));
            secondExpression.findElement(By.linkText(value1)).click();
            VUtils.waitFor(1);
        } else if (value2 != null) {
            WebElement selectElement = pageContainer.linkSelectValue2;
            selectElement.click();
            WebElement textbox = VoyantaDriver.findElement(By.xpath("//*[@id='app-clauses']/div/div/div/div/div/div/div/div/div[3]/div/input"));
            JavascriptExecutor executor = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
            executor.executeScript("arguments[0].style.display='inline'", textbox);
            textbox.sendKeys(value2);

        }
    }

    public void selectValueForMessage(String value1, String value2) {

        if (value1.equalsIgnoreCase("")) {
            value1 = null;
        }

        if (value2.equalsIgnoreCase("")) {
            value2 = null;
        }
        if (value1 != null) {

            WebElement selectElement = pageContainer.linkSelectValue5;
            selectElement.click();
            VUtils.waitFor(2);
            WebElement messageBox = VoyantaDriver.findElement(By.xpath("//*[@id='regular-output']/div"));
            messageBox.findElement(By.id("searchMenu")).clear();
            messageBox.findElement(By.id("searchMenu")).sendKeys(value1);
            messageBox.findElement(By.linkText(value1)).click();
            VUtils.waitFor(1);

        } else if (value2 != null) {
            WebElement selectElement = pageContainer.linkSelectValue5;
            selectElement.click();
            WebElement messageBox = VoyantaDriver.findElement(By.xpath("//*[@id='regular-output']/div"));
            WebElement textbox = VoyantaDriver.findElement(By.xpath("//*[@id='regular-output']/div/div/input"));
            JavascriptExecutor executor = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
            executor.executeScript("arguments[0].style.display='inline'", textbox);
            textbox.sendKeys(value2);
        }
    }

    public void selectOperator(String value) {
        // WebElement selectElement = pageContainer.drpComparator;
        // String operator = "is not equal to";
        VoyantaDriver.selectByList(pageContainer.drpComparator, null, value);


    }

    public void saveBusinessRule() {
        pageContainer.btnSaveNewRule.click();
        VUtils.waitFor(10);
    }

    public ListOfBusinessRulesPage createBusinessRuleStep2(String field, String fieldName, String operator, String value, String fieldName2, String operator2, String value2, String brType, String messageType, String message, boolean andClause) {
        businessRule.createRule().withfield(field).withfieldName(fieldName).withOperator(operator).withValue(value).withbrType(brType).withMessageType(messageType).withMessage(message);
        VoyantaBucket.setBusinessRule(businessRule);

        //First condition
        selectElementFromDragAndDropfield(pageContainer.linkSelectValue1, fieldName);
        VUtils.waitFor(2);
        selectElementFromDragAndDropfield(pageContainer.linkSelectValue2, value);

        //Optional second condition
        if (andClause) {
            selectElementFromDragAndDropfield(pageContainer.linkSelectValue3, fieldName2);
            VUtils.waitFor(2);
            selectElementFromDragAndDropfield(pageContainer.linkSelectValue4, value2);
        }

        //message type (alert, warning, error)
        VoyantaDriver.selectByList(pageContainer.drpOutCome, null, brType);
        WebElement firstElement = getElementfromText(messageType);
        WebElement secondElement = pageContainer.dropClause5;
        VUtils.waitFor(2);
        VoyantaDriver.moveElement(firstElement, secondElement);
        VUtils.waitFor(5);
        selectElementFromDragAndDropfield(pageContainer.linkSelectValue5, message);
//        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.waitFor(3);
//        VoyantaDriver.findElement(By.linkText("Enter Text")).click();
////        VUtils.waitFor(2);
//
////        VoyantaDriver.findElement(By.linkText("Enter Text")).click();
//        VoyantaDriver.waitFor(3);
//
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
//        VoyantaDriver.waitFor(3);
//
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].text = 'hfa'");
////        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
////        new Actions(VoyantaDriver.getCurrentDriver()).moveToElement(VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input"))).doubleClick().perform();
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).hover()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")));
////        VoyantaDriver.mouseOver(By.cssSelector("div.type-item.userDefinedValue:nth-child(1) > input"));
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
//        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys("\n");
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
//        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
//        VUtils.waitFor(2);
//        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
//        VUtils.waitFor(2);
//        pageContainer.outSideArea2.click();
//        VUtils.waitFor(2);
//
////        VoyantaDriver.executeJS("return typeof jQuery == 'undefined'");
//
//
//     //   VoyantaDriver.executeJS("var jq = document.createElement('script');jq.src = '//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js';document.getElementsByTagName('head')[0].appendChild(jq);");
//
////          ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].cl()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")));
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).click()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")));
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).click()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")));
//////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys("\n");
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(Keys.RETURN);
        //  VUtils.waitFor(5);
        pageContainer.btnSaveNewRule.click();
//        pageContainer.linkTextField1.click();
//        pageContainer.txtTextFiled1.sendKeys(value);
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue > a")).get(1).click();
        VUtils.waitFor(2);

//        VoyantaDriver.findElement(By.xpath("//input[@type='text'])[7]")).sendKeys(message);
//        pageContainer.txtTextFiled1.sendKeys(message);
//        VUtils.waitFor(2);
//        pageContainer.btnSaveNewRule.click();
//        VUtils.waitFor(10);
//
//        if(VoyantaDriver.elementExists(pageContainer.btnSaveNewRule)) {
////            pageContainer.linkTextField.click();
//            VUtils.waitFor(2);
//            pageContainer.txtTextFiled.click();
//            VUtils.waitFor(2);
//            pageContainer.txtTextFiled.sendKeys(value);
//            VUtils.waitFor(2);
//            pageContainer.txtTextFiled.click();
//            VUtils.waitFor(2);
////            VoyantaDriver.findElement(By.linkText("Enter Text")).click();
////        VUtils.waitFor(2);
//
////        VoyantaDriver.findElement(By.linkText("Enter Text")).click();
//            VoyantaDriver.waitFor(3);
//            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VoyantaDriver.waitFor(3);
//
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].text = 'hfa'");
////        new Actions(VoyantaDriver.getCurrentDriver()).moveToElement(VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input"))).doubleClick().perform();
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).hover()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")));
////        VoyantaDriver.mouseOver(By.cssSelector("div.type-item.userDefinedValue:nth-child(1) > input"));
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
//            VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys("\n");
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
//            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
//            VUtils.waitFor(2);
////            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).click();
////            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).
////            pageContainer.btnSaveNewRule.click();
////            pageContainer.linkTextField.click();
//            VUtils.waitFor(2);
//            pageContainer.btnSaveNewRule.click();
//        }

//        VUtils.waitFor(10);

//        if(VoyantaDriver.elementExists(By.linkText(message))) {
////        VUtils.WaitForAllAjaxCalls(VoyantaDriver.getCurrentDriver(),30);
//            VoyantaDriver.findElement(By.linkText(message)).click();
//            VUtils.waitFor(10);
//            pageContainer.btnSaveNewRule.click();
//        }
        VUtils.waitFor(10);
        return new ListOfBusinessRulesPage();
    }

    public ListOfRulesPage createNewRuleStep2(String field, String fieldName, String operator, String value, String fieldName2, String operator2, String value2, String brType, String messageType, String message, boolean andClause) {
        businessRule.createRule().withfield(field).withfieldName(fieldName).withOperator(operator).withValue(value).withbrType(brType).withMessageType(messageType).withMessage(message);
        VoyantaBucket.setBusinessRule(businessRule);

        //First condition
        selectElementFromDragAndDropfield(pageContainer.linkSelectValue1, fieldName);
        VUtils.waitFor(2);
        selectElementFromDragAndDropfield(pageContainer.linkSelectValue2, value);

        //Optional second condition
        if (andClause) {
            selectElementFromDragAndDropfield(pageContainer.linkSelectValue3, fieldName2);
            VUtils.waitFor(2);
            selectElementFromDragAndDropfield(pageContainer.linkSelectValue4, value2);
        }

        //message type (alert, warning, error)
        VoyantaDriver.selectByList(pageContainer.drpOutCome, null, brType);
        WebElement firstElement = getElementfromText(messageType);
        WebElement secondElement = pageContainer.dropClause5;
        VUtils.waitFor(2);
        VoyantaDriver.moveElement(firstElement, secondElement);
        VUtils.waitFor(5);
        selectElementFromDragAndDropfield(pageContainer.linkSelectValue5, message);
//        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.waitFor(3);
//        VoyantaDriver.findElement(By.linkText("Enter Text")).click();
////        VUtils.waitFor(2);
//
////        VoyantaDriver.findElement(By.linkText("Enter Text")).click();
//        VoyantaDriver.waitFor(3);
//
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
//        VoyantaDriver.waitFor(3);
//
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].text = 'hfa'");
////        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
////        new Actions(VoyantaDriver.getCurrentDriver()).moveToElement(VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input"))).doubleClick().perform();
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).hover()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")));
////        VoyantaDriver.mouseOver(By.cssSelector("div.type-item.userDefinedValue:nth-child(1) > input"));
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
//        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys("\n");
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
//        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
//        VUtils.waitFor(2);
//        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
//        VUtils.waitFor(2);
//        pageContainer.outSideArea2.click();
//        VUtils.waitFor(2);
//
////        VoyantaDriver.executeJS("return typeof jQuery == 'undefined'");
//
//
//     //   VoyantaDriver.executeJS("var jq = document.createElement('script');jq.src = '//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js';document.getElementsByTagName('head')[0].appendChild(jq);");
//
////          ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].cl()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")));
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).click()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")));
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).click()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")));
//////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys("\n");
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(Keys.RETURN);
        //  VUtils.waitFor(5);
        pageContainer.btnSaveNewRule.click();
//        pageContainer.linkTextField1.click();
//        pageContainer.txtTextFiled1.sendKeys(value);
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue > a")).get(1).click();
        VUtils.waitFor(2);

//        VoyantaDriver.findElement(By.xpath("//input[@type='text'])[7]")).sendKeys(message);
//        pageContainer.txtTextFiled1.sendKeys(message);
//        VUtils.waitFor(2);
//        pageContainer.btnSaveNewRule.click();
//        VUtils.waitFor(10);
//
//        if(VoyantaDriver.elementExists(pageContainer.btnSaveNewRule)) {
////            pageContainer.linkTextField.click();
//            VUtils.waitFor(2);
//            pageContainer.txtTextFiled.click();
//            VUtils.waitFor(2);
//            pageContainer.txtTextFiled.sendKeys(value);
//            VUtils.waitFor(2);
//            pageContainer.txtTextFiled.click();
//            VUtils.waitFor(2);
////            VoyantaDriver.findElement(By.linkText("Enter Text")).click();
////        VUtils.waitFor(2);
//
////        VoyantaDriver.findElement(By.linkText("Enter Text")).click();
//            VoyantaDriver.waitFor(3);
//            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VoyantaDriver.waitFor(3);
//
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].text = 'hfa'");
////        new Actions(VoyantaDriver.getCurrentDriver()).moveToElement(VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input"))).doubleClick().perform();
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).hover()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")));
////        VoyantaDriver.mouseOver(By.cssSelector("div.type-item.userDefinedValue:nth-child(1) > input"));
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
//            VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys("\n");
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
//            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
//            VUtils.waitFor(2);
////            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).click();
////            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).
////            pageContainer.btnSaveNewRule.click();
////            pageContainer.linkTextField.click();
//            VUtils.waitFor(2);
//            pageContainer.btnSaveNewRule.click();
//        }

//        VUtils.waitFor(10);

//        if(VoyantaDriver.elementExists(By.linkText(message))) {
////        VUtils.WaitForAllAjaxCalls(VoyantaDriver.getCurrentDriver(),30);
//            VoyantaDriver.findElement(By.linkText(message)).click();
//            VUtils.waitFor(10);
//            pageContainer.btnSaveNewRule.click();
//        }
        VUtils.waitFor(10);
        return new ListOfRulesPage();
    }

    public ListOfBusinessRulesPage modifyBusinessRuleStep2(String field, String fieldName, String operator, String value, String brType, String messageType, String message) {

        businessRule.createRule().withfield(field).withfieldName(fieldName).withOperator(operator).withValue(value).withbrType(brType).withMessageType(messageType).withMessage(message);
        VoyantaBucket.setBusinessRule(businessRule);

        List<WebElement> existingRules = VoyantaDriver.getCurrentDriver().findElements(By.className("actionRemoveType"));
//        for(WebElement element:existingRules)
//        {
//            element.click();
//        }
        existingRules.get(2).click();
//        selectElementFromDragAndDropfield(pageContainer.linkSelectValue, field, fieldName);
//        VUtils.waitFor(2);
//        VoyantaDriver.selectByList(pageContainer.drpOutCome, null, brType);
//        selectElementFromDragAndDropfield(pageContainer.linkSelectValue, value, fieldName);

        WebElement firstElement = getElementfromText(messageType);
        WebElement secondElement = pageContainer.dropClause5;
        VUtils.waitFor(2);
        //  pageContainer.linkSpecialValuesHeader.click();
        VUtils.waitFor(2);
        VoyantaDriver.moveElement(firstElement, secondElement);
        VUtils.waitFor(5);
        selectElementFromDragAndDropfield(pageContainer.linkSelectValue5, message);
//        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.waitFor(3);
//        VoyantaDriver.findElement(By.linkText("Enter Text")).click();
////        VUtils.waitFor(2);
//
////        VoyantaDriver.findElement(By.linkText("Enter Text")).click();
//        VoyantaDriver.waitFor(3);
//
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
//        VoyantaDriver.waitFor(3);
//
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].text = 'hfa'");
////        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
////        new Actions(VoyantaDriver.getCurrentDriver()).moveToElement(VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input"))).doubleClick().perform();
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).hover()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")));
////        VoyantaDriver.mouseOver(By.cssSelector("div.type-item.userDefinedValue:nth-child(1) > input"));
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
//        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys("\n");
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
//        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
//        VUtils.waitFor(2);
//        VoyantaDriver.executeJS("$( \".hidden\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].setAttribute(\"Style\",\"\")");
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
//        VUtils.waitFor(2);
//        pageContainer.outSideArea2.click();
//        VUtils.waitFor(2);
//
////        VoyantaDriver.executeJS("return typeof jQuery == 'undefined'");
//
//
//     //   VoyantaDriver.executeJS("var jq = document.createElement('script');jq.src = '//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js';document.getElementsByTagName('head')[0].appendChild(jq);");
//
////          ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].cl()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")));
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).click()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")));
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).click()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")));
//////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys("\n");
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(Keys.RETURN);
        //  VUtils.waitFor(5);
        pageContainer.btnSaveNewRule.click();
//        pageContainer.linkTextField1.click();
//        pageContainer.txtTextFiled1.sendKeys(value);
//        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue > a")).get(1).click();
        VUtils.waitFor(2);

        //  VoyantaDriver.findElement(By.xpath("//input[@type='text'])[7]")).sendKeys(message);
//        pageContainer.txtTextFiled1.sendKeys(message);
//        VUtils.waitFor(2);
//        pageContainer.btnSaveNewRule.click();
//        VUtils.waitFor(10);
//
//        if(VoyantaDriver.elementExists(pageContainer.btnSaveNewRule)) {
////            pageContainer.linkTextField.click();
//            VUtils.waitFor(2);
//            pageContainer.txtTextFiled.click();
//            VUtils.waitFor(2);
//            pageContainer.txtTextFiled.sendKeys(value);
//            VUtils.waitFor(2);
//            pageContainer.txtTextFiled.click();
//            VUtils.waitFor(2);
////            VoyantaDriver.findElement(By.linkText("Enter Text")).click();
////        VUtils.waitFor(2);
//
////        VoyantaDriver.findElement(By.linkText("Enter Text")).click();
//            VoyantaDriver.waitFor(3);
//            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VoyantaDriver.waitFor(3);
//
////        VoyantaDriver.executeJS("$( \".label.actionActivate\")[2].text = 'hfa'");
////        new Actions(VoyantaDriver.getCurrentDriver()).moveToElement(VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input"))).doubleClick().perform();
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        ((JavascriptExecutor)VoyantaDriver.getCurrentDriver()).executeScript("$(arguments[0]).hover()",VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")));
////        VoyantaDriver.mouseOver(By.cssSelector("div.type-item.userDefinedValue:nth-child(1) > input"));
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
////        VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).click();
//            VUtils.waitFor(2);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys("\n");
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
////        VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).click();
//            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("input")).sendKeys(message);
//            VUtils.waitFor(2);
////            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).click();
////            VoyantaDriver.findElements(By.cssSelector("div.type-item.userDefinedValue")).get(1).findElement(By.tagName("a")).
////            pageContainer.btnSaveNewRule.click();
////            pageContainer.linkTextField.click();
//            VUtils.waitFor(2);
//            pageContainer.btnSaveNewRule.click();
//        }

//        VUtils.waitFor(10);

//        if(VoyantaDriver.elementExists(By.linkText(message))) {
////        VUtils.WaitForAllAjaxCalls(VoyantaDriver.getCurrentDriver(),30);
//            VoyantaDriver.findElement(By.linkText(message)).click();
//            VUtils.waitFor(10);
//            pageContainer.btnSaveNewRule.click();
//        }
        VUtils.waitFor(10);
        return new ListOfBusinessRulesPage();
    }

    private void clearExistingRules() {
        List<WebElement> existingRules = VoyantaDriver.getCurrentDriver().findElements(By.className("actionRemoveType"));
        for (WebElement element : existingRules) {
            element.click();
        }
    }

    private void selectElementFromDragAndDropfield(WebElement selectelement, String fieldName) {
        selectelement.click();
        VUtils.waitFor(2);

        VoyantaDriver.scrollfindElement(By.linkText(fieldName), By.cssSelector("div.scroll-down.active"));
        VUtils.waitFor(1);
    }

    public String getMessageAfterSave() {
        return pageContainer.pageText.getText();
    }

    public String checkSucessMessage() {
        if (VoyantaDriver.elementExists(pageContainer.sucessMessage)) {
            return pageContainer.sucessMessage.getText();
        }
        return null;
    }

    public void createMappingRuleStep2(String from, String to, String notes) {

        VoyantaBucket.setBusinessRule(businessRule);
        VUtils.waitFor(2);

        pageContainer.outComeTableFromRow1.clickAndDoubleClick();
        pageContainer.textArea.clearText();
        pageContainer.textArea.clickAndSendKeys(from);

        pageContainer.outComeTableToRow1.clickAndDoubleClick();
        pageContainer.textArea.clearText();
        pageContainer.textArea.clickAndSendKeys((to));
        pageContainer.outComeTableNotesRow1.clickAndDoubleClick();
        pageContainer.textArea.clearText();
        pageContainer.textArea.clickAndSendKeys(notes);

        pageContainer.btnSaveNewRule.click();
        VUtils.waitFor(29);
    }

    public void searchColumnValue(String columnValue) {
        pageContainer.inputColumnSearch.sendKeys(columnValue);
    }

    public void gotoPreviousPage() {
        pageContainer.gotoStep1.click();
    }

    public void removeClauses() {
        while (VUtils.isElementPresent(By.xpath("//a[@class='actionRemoveType']"))) {
            VoyantaDriver.getCurrentDriver().findElement(By.xpath("//a[@class='actionRemoveType']")).click();
        }
    }

    public void enterRuleName(String ruleName) {
        pageContainer.textRuleName.clearText();
        pageContainer.textRuleName.sendKeys(ruleName);
    }

    public void selectObjectType(String objectType) {
        VoyantaDriver.selectByList(pageContainer.drpObjectType, pageContainer.drpObjectTypeSearch, objectType);
    }

    public void selectField(String fieldName) {
        VoyantaDriver.selectByList(pageContainer.drpField, pageContainer.drpFieldSearch, fieldName);
    }

    public void selectContinue() {
        pageContainer.btnContinue.click();
        VUtils.waitForElement(pageContainer.getDefaultElement());
    }

    public void addBRClause(String dataValue, String objectName) {
        WebElement firstElement;
        WebElement secondElement;
        if (dataValue.equalsIgnoreCase("Submitted Value")) {
            firstElement = getElementfromText(dataValue);
            secondElement = pageContainer.dropClause1;
            pageContainer.linkDataValueHeader.click();
            VUtils.waitFor(2);
            VoyantaDriver.moveElement(firstElement, secondElement);
            VUtils.waitFor(2);
            selectElementFromDragAndDropfield(pageContainer.linkSelectValue1, objectName);
            VUtils.waitFor(2);
        } else if (dataValue.equalsIgnoreCase("Current Value")) {
            firstElement = getElementfromText(dataValue);
            secondElement = pageContainer.dropClause2;
            pageContainer.linkDataValueHeader.click();
            VUtils.waitFor(2);
            VoyantaDriver.moveElement(firstElement, secondElement);
            VUtils.waitFor(2);
            selectElementFromDragAndDropfield(pageContainer.linkSelectValue2, objectName);
            VUtils.waitFor(2);
        }
    }

    public void selectCondition(String condition) {
        VoyantaDriver.selectByList(pageContainer.drpComparator, pageContainer.drpComparatorSearch, condition);
    }

    public void selectOutComeAction() {
        pageContainer.drpOutCome.click();
        VUtils.waitFor(2);
    }

    public void selectActionTrigger(String actionMsg) {
        VoyantaDriver.findElement(By.cssSelector("#severityType_selector_chzn > a.chzn-single")).click();
        VoyantaDriver.findElement(By.xpath("//li[contains(text(),'" + actionMsg + "')]")).click();
        VUtils.waitFor(1);
    }

    public void addOutcomeCaluse(String dataValue, String objectName) {
        WebElement firstElement;
        WebElement secondElement;
        if (dataValue.equalsIgnoreCase("Submitted Value")) {
            firstElement = getElementfromText(dataValue);
            secondElement = pageContainer.dropClause5;
            pageContainer.linkDataValueHeader.click();
            VUtils.waitFor(2);
            VoyantaDriver.moveElement(firstElement, secondElement);
            VUtils.waitFor(2);
            selectElementFromDragAndDropfield(pageContainer.linkSelectValue5, objectName);
            VUtils.waitFor(2);
        } else if (dataValue.equalsIgnoreCase("Current Value")) {
            firstElement = getElementfromText(dataValue);
            secondElement = pageContainer.dropClause5;
            pageContainer.linkDataValueHeader.click();
            VUtils.waitFor(2);
            VoyantaDriver.moveElement(firstElement, secondElement);
            VUtils.waitFor(2);
            selectElementFromDragAndDropfield(pageContainer.linkSelectValue5, objectName);
            VUtils.waitFor(2);
        }
    }

    public void waitForPageToLoad() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        } catch (NoSuchElementException e) {
        }
    }

//    public void setNextClause(String value1, String value2) {
//        WebElement firstElement = getElementfromText(value1);
//        WebElement secondElement = pageContainer.dropClause1;
//        //  clearExistingRules();
//        pageContainer.linkDataValueHeader.click();
//        VUtils.waitFor(2);
//        VoyantaDriver.moveElement(firstElement, secondElement);
//        VUtils.waitFor(2);
//        firstElement = getElementfromText(value2);
//        secondElement = pageContainer.dropClause2;
//        //     pageContainer.linkSpecialValuesHeader.click();
//        VUtils.waitFor(2);
//        VoyantaDriver.moveElement(firstElement, secondElement);
//        VUtils.waitFor(2);
//
//    }
//
//    private WebElement setNextclause(String value1) {
//        if (value1.equalsIgnoreCase("Current Value"))
//            return pageContainer.drgSubmittedValue;
//        else if (value1.equalsIgnoreCase("Submitted Value"))
//            return pageContainer.drgCurrentValue;
//        else if (value1.equalsIgnoreCase("Text"))
//            return pageContainer.drgText;
//        else if (value1.equalsIgnoreCase("Number"))
//            return pageContainer.drgNumber;
//        else if (value1.equalsIgnoreCase("BLANK"))
//            return pageContainer.drgBLANK;
//        else
//            return null;
//    }
}
