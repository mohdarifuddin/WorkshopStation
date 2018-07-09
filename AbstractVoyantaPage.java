package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.AbstractPageContainer;
import voyanta.ui.pagecontainers.AbstractVoyantaPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;
import voyanta.ui.webdriver.core.elements.impl.internal.ElementFactory;

import java.util.*;

/**
 * Created by sriramangajala on 24/07/2014.
 */
public class AbstractVoyantaPage implements VoyantaPage {
    static Logger LOGGER = Logger.getLogger(AbstractVoyantaPage.class);
    static AbstractVoyantaPageContainer pageContainer = getDataContainer(AbstractVoyantaPageContainer.class);
    protected WebDriverWait wait;

    public AbstractVoyantaPage() {
        pageContainer = getDataContainer(AbstractVoyantaPageContainer.class);
        this.wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 15);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public boolean isPageDisplayed() {
        return false;
    }

    @Override
    public VoyantaUser getCurrentUser() {
        return null;
    }

    @Override
    public String getCurrentPageText() {
        return null;
    }

    @Override
    public boolean waitTillPageLoaded() {
        return false;
    }

    public static <T extends AbstractPageContainer> T getDataContainer(Class<T> className) {
        T boundDataContainer = (T) ElementFactory.initElements(VoyantaDriver.getCurrentDriver(), className);
        return boundDataContainer;
}


    public boolean checkDisplayedOrganization(String orgName) {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.orgSwitchLink));
        return pageContainer.orgSwitchLink.getText().equals(orgName);
    }

    public ListOfBusinessRulesPage gotoBusinessRulePage() {
/*        VoyantaDriver.mouseOver(By.id("menu-button"));
        pageContainer.linkBusinessRules.click();
        VUtils.waitFor(2);*/
        selectAdmin();
        pageContainer.businessRulesLink.click();
        VUtils.waitFor(2);
        return new ListOfBusinessRulesPage();
    }


    public ListDataManagerPage gotoDataManagerPage() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.dataManagementLink));
        pageContainer.dataManagementLink.click();
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.submissionDMLink));
        pageContainer.submissionDMLink.click();
        return new ListDataManagerPage();
    }

    public HomePage gotoHomePage() {
//        pageContainer.linkHome.click();
        int count = 0;
        int maxTries = 3;
        try {
            pageContainer.browseLink.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
        VUtils.waitFor(2);
        return new HomePage();
    }

    public UploadPage goToUploadPage() {
        /*VoyantaDriver.mouseOver(By.id("menu-button"));
        VoyantaDriver.waitFor(5);
        pageContainer.linkUploadData.click();
        VUtils.waitFor(5);*/
        selectDataManagement();
        pageContainer.uploadDataDMLink.click();
        VUtils.waitFor(2);
        return new UploadPage();
    }

    public ManageTagPage gotoManageTag() {
        VoyantaDriver.mouseOver(By.id("menu-button"));
        VoyantaDriver.waitFor(10);
        pageContainer.linkManageTags.click();
        VUtils.waitFor(3);
        return new ManageTagPage();
    }

    public ReportsPage gotoReportsPage() {
        return new ReportsPage();
    }

    /*public AdhocReportPage goToAdhocReport(){
        return new AdhocReportPage();
    }*/

    public void goToURL(String urlPath) {
        VoyantaDriver.getCurrentDriver().get(PropertiesLoader.getProperty("ui_url") + urlPath);
    }

    public AssetAttributionPage goToAssetAttributionPage() {
        //selectReporting();
        goToURL("reporting/19/68");
        //pageContainer.assetAttributionLink.click();
        return new AssetAttributionPage();
    }

    public FundLevelPage goToFundLevelPage() {
        selectReporting();
        pageContainer.fundAttributionLink.click();
        return new FundLevelPage();
    }

    public RiskScorePage goToRiskScorePage() {
        selectReporting();
        pageContainer.riskScoreLink.click();
        return new RiskScorePage();
    }

    public BuildingPage goToBuildingPage() {
        selectBrowse();
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.buildingLink));
        pageContainer.buildingLink.click();
        //VUtils.waitFor(3);
        return new BuildingPage();
    }

    public void gotoOperatingPage() {
        int count = 0;
        int maxTries = 3;
        try {
            VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
            pageContainer.linkOperatingPage.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void goToLeasingOverview() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.leasingOverviewLink));
        pageContainer.leasingOverviewLink.click();
        VUtils.waitFor(1);
    }

    public void LeasingOverview() {
        int count = 0;
        int maxTries = 3;
        try {
            pageContainer.leasingOverviewLink.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
        VUtils.waitFor(1);
    }

    public PermissionsPage gotoPermissionsPage() {
        int count = 0;
        int maxTries = 3;
        try {
            pageContainer.linkPermission.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
        VUtils.waitFor(3);
        return new PermissionsPage();
    }

    public AppraisalSummaryPage goToAppraisalSummaryPage() {
        selectReporting();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.appraisalSummaryLink));
        pageContainer.appraisalSummaryLink.click();
        return new AppraisalSummaryPage();
    }

    public DXTasksPage goToDXTasksPage() {
        selectTasks();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.dxMyTasksLink));
        pageContainer.dxMyTasksLink.click();
        return new DXTasksPage();
    }

    public void selectTasks() {
        pageContainer.tasksLink.click();
        VUtils.waitFor(2);
    }

    public ReportsPage gotoFinancePage() {
        int count = 0;
        int maxTries = 3;
        try {
            VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
            pageContainer.linkFinance.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
        return new ReportsPage();
    }

    public ReportsPage goToFinancialOverview() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.financialOverviewLink));
        pageContainer.financialOverviewLink.click();
        return new ReportsPage();
    }

    public void FinancialOverview() {
        int count = 0;
        int maxTries = 3;
        try {
            pageContainer.financialOverviewLink.click();
            LOGGER.info("clicking on financical overview link");
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public ReportLinksPage gotoReportsLinkInTop() {
        int count = 0;
        int maxTries = 3;
        try {
            VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
            pageContainer.linkReports.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
        VUtils.waitFor(2);
        return new ReportLinksPage();
    }

    public void gotoTenantPage() {
        int count = 0;
        int maxTries = 3;
        try {
            VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
            pageContainer.linkTenant.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void goToTenantDashboard() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.tenantDashboardLink));
        pageContainer.tenantDashboardLink.click();
        VUtils.waitFor(1);
    }

    public void goToNOIStatement() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.nOIStatementLink));
        pageContainer.nOIStatementLink.click();
        VUtils.waitFor(1);
    }

    public void goToBalanceSheet() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.balanceSheetLink));
        pageContainer.balanceSheetLink.click();
        VUtils.waitFor(1);
    }

    public void goToRentalArrears() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.rentalArrearsLink));
        pageContainer.rentalArrearsLink.click();
        VUtils.waitFor(1);
    }

    public void goToAssetStatistics() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.assetStatisticsLink));
        pageContainer.assetStatisticsLink.click();
        VUtils.waitFor(1);
    }

    public void goToAssets() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.assetLink));
        pageContainer.assetLink.click();
        VUtils.waitFor(1);
    }

    public void goToUnitInventory() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.unitInventoryLink));
        pageContainer.unitInventoryLink.click();
        VUtils.waitFor(1);
    }

    public void goToRentalAnalysis() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.rentalAnalysisLink));
        pageContainer.rentalAnalysisLink.click();
        VUtils.waitFor(1);
    }

    public void goToTenancySchedule() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.tenancyScheduleLink));
        pageContainer.tenancyScheduleLink.click();
        VUtils.waitFor(1);
    }

    public void goToLeaseExpiry() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.leaseExpiryLink));
        pageContainer.leaseExpiryLink.click();
        VUtils.waitFor(1);
    }

    public void goToLoanSchedule() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.loanScheduleLink));
        pageContainer.loanScheduleLink.click();
        VUtils.waitFor(1);
    }

    public void LoanSchedule() {
        int count = 0;
        int maxTries = 5;
        try {
            pageContainer.loanScheduleLink.click();
            wait.until(ExpectedConditions.visibilityOf(pageContainer.loanScheduletab));
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
        VUtils.waitFor(1);
    }

    public void logout() {
       /* wait.until(ExpectedConditions.elementToBeClickable(pageContainer.TaskMenu));
        pageContainer.TaskMenu.click();
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.linkLogout));
        pageContainer.linkLogout.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/*//*[text()='Email Address: ']")));*/

        pageContainer.userIconlink.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='user-info-panel']")));
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.logOutLink));
        pageContainer.logOutLink.click();
        VUtils.waitFor(1);
    }

    public ListOfValuePage gotoListOfValuePage() {
        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.adminLink));
        pageContainer.adminLink.click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.lovColumn));
        pageContainer.listOfValuesLink.click();
//        wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.cssSelector("h2")), "Review List of Values")); The Page doesn't have title
        VUtils.waitFor(1);
        LOGGER.info("Goes to List of Values Page");
        return new ListOfValuePage();
    }

    public void gotoTab(String tabname) {

        switch (tabname) {
            case "Building":
                wait.until(ExpectedConditions.visibilityOf(pageContainer.buildingBrowseLink));
                if(VUtils.isElementPresent(pageContainer.buildingBrowseLink)) //to avoid NoSuchElementException
                    pageContainer.buildingBrowseLink.click();
                VUtils.waitFor(2);
                break;
            case "Loan":
                wait.until(ExpectedConditions.visibilityOf(pageContainer.loanBrowseLink));
                if(VUtils.isElementPresent(pageContainer.loanBrowseLink))
                    pageContainer.loanBrowseLink.click();
                VUtils.waitFor(2);
                break;
            case "Investment":
                wait.until(ExpectedConditions.visibilityOf(pageContainer.investmentBrowseLink));
                if(VUtils.isElementPresent(pageContainer.investmentBrowseLink))
                    pageContainer.investmentBrowseLink.click();
                VUtils.waitFor(2);
                break;
            case "Legal Entity":
                wait.until(ExpectedConditions.visibilityOf(pageContainer.legalEntityBrowseLink));
                if(VUtils.isElementPresent(pageContainer.legalEntityBrowseLink))
                    pageContainer.legalEntityBrowseLink.click();
                VUtils.waitFor(2);
                break;
            case "Tenant":
                wait.until(ExpectedConditions.visibilityOf(pageContainer.tenantBrowseLink));
                if(VUtils.isElementPresent(pageContainer.tenantBrowseLink))
                    pageContainer.tenantBrowseLink.click();
                VUtils.waitFor(2);
                break;
            case "Vendor":
                wait.until(ExpectedConditions.visibilityOf(pageContainer.vendorBrowseLink));
                if(VUtils.isElementPresent(pageContainer.vendorBrowseLink))
                    pageContainer.vendorBrowseLink.click();
                VUtils.waitFor(2);
                break;
            case "Investor":
                wait.until(ExpectedConditions.visibilityOf(pageContainer.investorBrowseLink));
                if(VUtils.isElementPresent(pageContainer.investorBrowseLink))
                    pageContainer.investorBrowseLink.click();
                VUtils.waitFor(2);
                break;
            case "Building (BETA)":
                wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(pageContainer.buildingBETABrowseLinkNonAngular),
                        ExpectedConditions.visibilityOf(pageContainer.buildingBETABrowseLinkAngular)));
                if (pageContainer.buildingBETABrowseLinkNonAngular.isDisplayed()) {
                    pageContainer.buildingBETABrowseLinkNonAngular.click();
                } else if (pageContainer.buildingBETABrowseLinkAngular.isDisplayed()) {
                    pageContainer.buildingBETABrowseLinkAngular.click();
                }
                wait.until(ExpectedConditions.urlContains("ng/browse-ng/buildings"));
                VUtils.waitFor(2);
                break;
        }
    }

    public void checkButtonDisabled(boolean b, String buttonText) {
        try {
            VerifyUtils.equals(b, !VoyantaDriver.findElement(By.linkText(buttonText)).findElement(By.xpath("parent::div")).getAttribute("class").contains("disabled"));
        } catch (TimeoutException | NoSuchElementException e) {
            throw new RuntimeException("Unable to find the button with text " + buttonText);
        }
    }

    public void checkImageDisabled(boolean b, String imageName) {
        try {
            VerifyUtils.equals(b, !VoyantaDriver.findElement(By.cssSelector("img[alt='" + imageName + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::div")).getAttribute("class").contains("disabled"));
        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to find the image with alt " + imageName);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Unable to find the image with alt  " + imageName);
        }
    }

    public void checkButtonVisible(boolean expected, String buttonName) {
        VerifyUtils.equals(expected, VUtils.isElementPresent(By.linkText(buttonName)));
    }

    public void checkButtonVisibleByCss(boolean expected, String cssClass) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='loader']")));
        VerifyUtils.equals(expected, VUtils.isElementPresent(By.cssSelector(cssClass)));
    }

    public HomePage switchOrg(String Org) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='loader']")));
        if (pageContainer.orgSwitchLink.getText().equalsIgnoreCase(Org)) {
            LOGGER.info("user is already on the right Organization");
            return (HomePage) AbstractVoyantaPage.this;
        } else {
            pageContainer.orgSwitchLink.click();
            VUtils.waitFor(3);
            pageContainer = getDataContainer(AbstractVoyantaPageContainer.class);
            LOGGER.info("switch to ORG " + Org);
//            VoyantaDriver.findElement(By.xpath("//*[@data-tid='OrganisationLinks']")).findElement(By.linkText(Org)).click();
            WebElement orgName = VoyantaDriver.findElement(By.xpath("//*[@data-tid='OrganisationLinks']")).findElement(By.linkText(Org));
            ((JavascriptExecutor) VoyantaDriver.getCurrentDriver()).executeScript("arguments[0].click();", orgName);
            VUtils.waitFor(3);
            return new HomePage();
        }
    }

    public void changeDropDownValue(String name, String value) {
        try {
            WebElement tableRowElement = VoyantaDriver.findElement(By.xpath("//strong[text()='" + name + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::tr"));
            LOGGER.info("Found the row with name " + name);
            tableRowElement.findElement(By.className("chzn-single")).click();
            VUtils.waitFor(5);

            try {
                tableRowElement = VoyantaDriver.findElement(By.xpath("//strong[text()='" + name + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::tr"));
                LOGGER.info("Found the row with name " + name);
                VUtils.waitFor(2);
                List<WebElement> options = tableRowElement.findElements(By.className("active-result"));

                for (WebElement element : options) {
                    if (element.getText().equalsIgnoreCase(value)) {
                        LOGGER.info("Found the option with text " + value);
                        element.click();
                        VUtils.waitFor(5);
                        return;
                    }
                }

                LOGGER.info("Option with text " + value + " not found");
                VerifyUtils.fail("Option with text " + value + " not found");
            } catch (TimeoutException e) {
                throw new RuntimeException("Unable to find the name with alt " + name);
            }
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Unable to find the name with alt " + name);
        }
    }

    public ListDownloadDSTPage getToDownloadPage() {
//        VoyantaDriver.mouseOver(By.id("menu-button"));
        pageContainer.dataManagementLink.click();
        pageContainer.downloadDSTDMLink.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        VUtils.waitFor(3);
        return new ListDownloadDSTPage();
    }

    public void getFileName(String dstWithTab) {
        WebElement uploadMonitor = VoyantaDriver.findElement(By.id("uploader-monitor"));
        int i = 0;

        try {
            List<WebElement> fileList = uploadMonitor.findElement(By.className("file-list")).findElement(By.className("file")).findElement(By.className("progress")).findElements(By.cssSelector("strong"));

            for (WebElement file : fileList) {
                if (file.getText().equalsIgnoreCase(dstWithTab)) {
                    LOGGER.info("Found the File ");
                    i++;
                    System.out.println("File name " + file.getText());
                    System.out.println("No of tab -" + i);
                    return;
                }
            }
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Unable to find the fileName " + dstWithTab);
        }
    }


    public void selectOptIn() {
        WebDriver driver = VoyantaDriver.getCurrentDriver();
        WebElement approval = driver.findElement(By.xpath("//div/input[@id='1']"));
        WebElement reject = driver.findElement(By.xpath("//div/input[@id='2']"));
        WebElement saveButton = driver.findElement(By.name("save"));

        LOGGER.info("Checking For the Email Notification OptIn");
        if (!approval.isSelected() && !reject.isSelected()) {
            LOGGER.info("OptIn for Both Email Notification ");
            approval.click();
            reject.click();
            saveButton.click();
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("form-messenger")), "Email settings updated."));
        } else if (approval.isSelected() && !reject.isSelected()) {
            LOGGER.info("OptIn for submission Rejected Email Notifiaction");
            reject.click();
            saveButton.click();
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("form-messenger")), "Email settings updated."));
        } else if (!approval.isSelected() && reject.isSelected()) {
            LOGGER.info("OptIn for  Pending Approval Email Notification");
            approval.click();
            saveButton.click();
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("form-messenger")), "Email settings updated."));
        } else {
            LOGGER.info("*****  The User is Already OptIn for Email Notification *****");
        }
    }

    public void selectOptOut() {
        WebDriver driver = VoyantaDriver.getCurrentDriver();
        WebElement approval = driver.findElement(By.xpath("//div/input[@id='1']"));
        WebElement reject = driver.findElement(By.xpath("//div/input[@id='2']"));
        WebElement saveButton = driver.findElement(By.name("save"));

        LOGGER.info("Checking For the Email Notification OutOut !!!");
        if (approval.isSelected() && reject.isSelected()) {
            LOGGER.info("Making OptOut for Both Email Notification");
            approval.click();
            reject.click();
            saveButton.click();
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("form-messenger")), "Email settings updated."));
        } else if (approval.isSelected() && !reject.isSelected()) {
            LOGGER.info("OptOut for Pending Approval Email Notification");
            approval.click();
            saveButton.click();
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("form-messenger")), "Email settings updated."));
        } else if (!approval.isSelected() && reject.isSelected()) {
            LOGGER.info("OptOut for Submission Rejected Email Notification");
            reject.click();
            saveButton.click();
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("form-messenger")), "Email settings updated."));
        } else {
            LOGGER.info("***** The User is Already OptOut for Email Notification *****");
        }
    }

    public void enterInSearchBox(String objRef) {
        //pageContainer.searchBox.click();
        pageContainer.searchBox.sendKeys(objRef);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'" + objRef + "')]")));
        List<WebElement> options = VoyantaDriver.findElements(By.xpath("//a[contains(text(),'" + objRef + "')]"));
        options.get(options.size() - 1).click();
        VUtils.waitFor(2);
        wait.until(ExpectedConditions.visibilityOf(pageContainer.filterResults));
    }

    public void searchResults(String results) {
        String[] res = results.split(",");

        for (String re : res) {
            WebElement element = VoyantaDriver.findElement(By.id("sidebar")).findElement(By.className("type-results"));

            if (element.getText().equals("")) {
                Assert.fail("***** NO RESULT TO SHOW *****");
            }

            List<WebElement> options = element.findElements(By.className("search-result-type"));

            for (WebElement e : options) {
                if (e.getText().equalsIgnoreCase(re)) {
                    System.out.println();
                    LOGGER.info("The Object Reference is Present on Following Object Type  : " + e.getText());
                    e.click();
                    VUtils.waitFor(3);
                    getResults();
                    return;
                }
            }

            VerifyUtils.fail("The Given Object is not Present in Filter Result : " + re);
        }
    }

    public List<HashMap> getResults() {
        List<HashMap> listOfElement = new LinkedList<HashMap>();
        List<HashMap> listViewElements;
        try {
            wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.id("search-table"))));
        } catch (TimeoutException e) {
            return listOfElement;
        }
        WebElement tableElement = VoyantaDriver.findElement(By.id("search-table"));

        for (WebElement element : tableElement.findElements(By.xpath("//tbody/tr"))) {
            if (!element.getText().trim().equals("")) {
                if (!element.getAttribute("class").contains("expandedView")) {
                    int i = 0;
                    HashMap hashMap = new HashMap();

                    for (WebElement element1 : element.findElements(By.tagName("td"))) {
                        String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                        hashMap.put(header, element1);
                        LOGGER.info("Key:" + tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim() + " Value:" + element1.getText());
                        i++;
                    }

                    LOGGER.info("---------------------------------------");
                    listOfElement.add(hashMap);
                }
            }
        }

        listViewElements = listOfElement;
        System.out.println("No Of Record :" + listViewElements.size());
        return listOfElement;
    }

    public static void fillTheForm(String[] fieldsName, String[] fieldValue) {
        for (int i = 0; i < fieldsName.length; i++) {
            WebElement formElement = VoyantaDriver.findElement(By.xpath("//strong[text()='" + fieldsName[i] + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::tr"));
            WebElement typeOFValue = VoyantaDriver.findElement(By.xpath("//strong[text()='" + fieldsName[i] + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("following-sibling::td/*"));

            if (formElement.getText().contains(fieldsName[i])) {
                LOGGER.info("Found The Field : " + fieldsName[i]);

                if (!typeOFValue.getAttribute("class").equals("input-edit  ")) {
                    formElement.findElement(By.className("chzn-single")).click();
                    formElement = VoyantaDriver.findElement(By.xpath("//strong[text()='" + fieldsName[i] + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::tr"));
                    VUtils.waitFor(3);
                    List<WebElement> options = formElement.findElements(By.className("active-result"));

                    for (WebElement element : options) {
                        if (element.getText().equalsIgnoreCase(fieldValue[i])) {
                            element.click();
                            LOGGER.info("Selecting Value from DropDown " + fieldValue[i]);
                            VUtils.waitFor(5);
                        }
                    }
                } else {
                    formElement = VoyantaDriver.findElement(By.xpath("//strong[text()='" + fieldsName[i] + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::tr"));
                    WebElement textBox = formElement.findElement(By.cssSelector("td > input"));
                    textBox.clear();
                    textBox.sendKeys(fieldValue[i]);
                    LOGGER.info("Typing The Value : " + fieldValue[i]);
                    VUtils.waitFor(3);
                }
            } else {
                LOGGER.info("Given " + fieldsName[i] + " is not Present in the Form");
                VerifyUtils.fail("Field not Found !!! ");
            }
        }
    }

    public static void addNewForm(String[] fieldsName, String[] fieldValue) {
        for (int i = 0; i < fieldsName.length; i++) {
            WebElement formElement = VoyantaDriver.findElement(By.xpath("//strong[text()='" + fieldsName[i] + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::tr"));

            if (formElement.getText().contains(fieldsName[i])) {
                LOGGER.info("Found The Field : " + fieldsName[i]);

                if (!formElement.getAttribute("style").equals("")) {
                    formElement.findElement(By.className("chzn-single")).click();
                    formElement = VoyantaDriver.findElement(By.xpath("//strong[text()='" + fieldsName[i] + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::tr"));
                    VUtils.waitFor(3);
                    List<WebElement> options = formElement.findElements(By.className("active-result"));

                    for (WebElement element : options) {
                        if (element.getText().equalsIgnoreCase(fieldValue[i])) {
                            element.click();
                            LOGGER.info("Selecting Value from DropDown " + fieldValue[i]);
                            VUtils.waitFor(5);
                        }
                    }
                } else {
                    formElement = VoyantaDriver.findElement(By.xpath("//strong[text()='" + fieldsName[i] + "']")).findElement(By.xpath("parent::*")).findElement(By.xpath("parent::tr"));
                    WebElement textBox = formElement.findElement(By.cssSelector("td > input"));
                    textBox.clear();
                    textBox.sendKeys(fieldValue[i]);
                    LOGGER.info("Typing The Value : " + fieldValue[i]);
                    VUtils.waitFor(3);
                }
            } else {
                LOGGER.info("Given " + fieldsName[i] + " is not Present in the Form");
                VerifyUtils.fail("Field not Found !!! ");
            }
        }
    }

    public static void selectSaveObjectBrowse() {
        LOGGER.info("Selecting Save To Save New Object");
        VoyantaDriver.findElement(By.cssSelector("input.edit-button")).click();
        VUtils.waitFor(5);
    }

    public void enterEmailToShareObject(String users) {
        String[] userNames = users.replaceAll(" ", "").split(",");
        int i = 1;

        for (String name : userNames) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='userSelector-region']/div/div[2]/ul/li[" + i + "]")));
            WebElement input = VoyantaDriver.findElement(By.xpath("//*[@id='userSelector-region']/div/div[2]/ul/li[" + i + "]"));
            input.click();
            input.sendKeys(name);
            input.sendKeys(Keys.RETURN);
            i++;
        }

    }

    public void selectNoOfObjectAccess(String[] noOfObjectAccess) {
        WebElement shareObjectcheckBox = VoyantaDriver.findElement(By.id("share-objects"));

        if (noOfObjectAccess[0].equals("Select All")) {
            shareObjectcheckBox.findElement(By.cssSelector("input.selectAll.left")).click();
            LOGGER.info("Selecting share Object as 'Select All'");
            VUtils.waitFor(1);
        } else {
            WebElement moreOptions = shareObjectcheckBox.findElement(By.id("related-objects"));
            List<WebElement> options = moreOptions.findElements(By.tagName("li"));

            for (String noOfObjectAcces : noOfObjectAccess) {
                int k = 1;

                for (WebElement e : options) {
                    if (e.getText().equals(noOfObjectAcces)) {
                        WebElement w = VoyantaDriver.findElement(By.xpath("//*[@id='related-objects']/ul/li[" + k + "]/input"));
                        w.click();
                        LOGGER.info("Sharing the Permission to : " + noOfObjectAcces);
                        VUtils.waitFor(1);
                    }

                    k++;
                }
            }
        }
    }

    public void selectSendFromShare() {
        VoyantaDriver.findElement(By.xpath("//*[@id='share-button']")).click();
        LOGGER.info("Selecting Send from Sharing the Object");
        VUtils.waitFor(3);
    }

    public boolean isManageOrganisationTabPresent() {
        try {
            pageContainer.userIconlink.click();
            wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.xpath("//*[@class='user-info-panel']"))));
            return pageContainer.orgnisationLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("Organization Link is Not Present");
        }
        return false;
    }

    public void manageOrgPresentInTask(boolean b) {
        VerifyUtils.equals(b, isManageOrganisationTabPresent());
    }

    public void checkObjectIsInResults(String objectName, boolean is) {
        List<HashMap> results = getResults();
        Boolean foundInResult = false;

        if (results != null && !results.isEmpty()) {
            for (HashMap result : results) {
                String resultName = ((WebElement) result.get("Name")).findElement(By.cssSelector(".report-link")).getText();

                if (resultName.contains(objectName)) {
                    foundInResult = true;
                    LOGGER.info("Found result with name " + resultName);
                }
            }
        }

        if (is) {
            VerifyUtils.True(foundInResult);
        } else {
            VerifyUtils.False(foundInResult);
        }
    }

    public List<String> browseListString = new ArrayList<>();

    public void selectBrowse() {
        wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(pageContainer.browseLink), ExpectedConditions.elementToBeClickable(pageContainer.browseLink)));
        pageContainer.browseLink.click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.buildingBrowseLink));
        VUtils.waitFor(1);
    }

    public void selectDataManagement() {
        wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(pageContainer.dataManagementLink), ExpectedConditions.elementToBeClickable(pageContainer.dataManagementLink)));
        pageContainer.dataManagementLink.click();
        VUtils.waitFor(1);
    }

    public void selectAdmin() {
        wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(pageContainer.adminLink), ExpectedConditions.elementToBeClickable(pageContainer.adminLink)));
        pageContainer.adminLink.click();
        VUtils.waitFor(2);
        if(VoyantaDriver.getCurrentDriver().findElement(By.xpath("//*[@data-tid='megaMenuTrigger_Admin']/div[@class='megamenu-container']")).getAttribute("style").contains("none")) {
            pageContainer.adminLink.click();
        }
        Assert.assertTrue("Admin is not clicked", VoyantaDriver.getCurrentDriver().findElement(By.xpath("//*[@data-tid='megaMenuTrigger_Admin']/div[@class='megamenu-container']")).getAttribute("style").contains("block"));
    }

    public void selectReporting() {
        VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
        pageContainer.reportLink.click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.loanScheduleLink));
    }

    public ManageTagPage selectTag() {
        pageContainer.tagLink.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        Assert.assertTrue("We are not on the Tags page as we are in "+VoyantaDriver.getCurrentDriver().getCurrentUrl(), VoyantaDriver.getCurrentDriver().getCurrentUrl().contains("administration/miscellaneous/tags"));
        return new ManageTagPage();
    }

    public AuditPage selectAudit() {
        pageContainer.auditDMLink.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        return new AuditPage();
    }

    public PermissionsPage selectPermissions() {
        /*int count = 0;
        int maxTries = 5;
        try {
            wait.until(ExpectedConditions.visibilityOf(pageContainer.sharedObjectPermissionsLink));
            pageContainer.sharedObjectPermissionsLink.click();
        } catch (NoSuchElementException e){
            if (++count == maxTries) throw e;
        }*/

        wait.until(ExpectedConditions.elementToBeClickable(pageContainer.sharedObjectPermissionsLink));
        pageContainer.sharedObjectPermissionsLink.click();
        return new PermissionsPage();
    }

    public void seeBrowseObject(boolean b, String browseObjectName) {
        VerifyUtils.equals(b, isBrowseObjectPresent(b, browseObjectName));
    }

    public boolean isBrowseObjectPresent(boolean b, String browseObject) {
        try {
            LOGGER.info("Verifying Given Browse Object : " + browseObject + " Present = " + b);
            VUtils.waitFor(2);
            pageContainer.browseList.stream().forEach(element -> browseListString.add(element.getText()));
            return browseListString.contains(browseObject);
        } catch (NoSuchElementException e) {
            LOGGER.info("The User has no Access to this Object : " + browseObject);
            return false;
        }
    }

    public void seeDataManagementLink(boolean b, String linkName) {
        VerifyUtils.equals(b, isDataManagementLinkPresent(b, linkName));
    }

    public boolean isDataManagementLinkPresent(boolean b, String linkName) {
        try {
            LOGGER.info("Verifying Given Link : " + linkName + " Present = " + b);
            return VUtils.isElementPresent(By.linkText(linkName));
        } catch (NoSuchElementException e) {
            LOGGER.info("The User doesn't have Access to : " + linkName);
            return false;
        }
    }

    public List<String> businessRuleTabList = new ArrayList<>();

    public ListOfBusinessRulesPage selectBRFromAdmin() {
        pageContainer.businessRulesLink.click();
        pageContainer.businessRulesList.forEach(element -> businessRuleTabList.add(element.getText()));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        return new ListOfBusinessRulesPage();
    }

    public boolean isDataManagementLinkPresent() {
        try {
            LOGGER.info("Verifying Data Management Link Present");
            //return VUtils.isElementPresent(By.linkText("Data Management"));
            return VUtils.isElementPresent(By.xpath("//span[@class='label' and contains(text(), 'Data Management')]"));
        } catch (NoSuchElementException e) {
            LOGGER.info("The User doesn't have Access to Data Management ");
            return false;
        }
    }

    public void canSeeAdmin() {
        WebElement adminLink;
        //VUtils.isElementPresent(By.linkText("Admin"));
        //WebElement adminLink = VoyantaDriver.findElement(By.linkText("Admin"));

        //new login will face the T's & C's popup dailog box on first login attempt
        if (VUtils.isElementPresent(By.xpath("//h2[@class='modal-title']"))) {
            if (VoyantaDriver.getCurrentDriver().findElement(By.xpath("//h2[@class='modal-title']")).getText().equalsIgnoreCase("Terms and Conditions")) {
                VoyantaDriver.getCurrentDriver().findElement(By.id("remove-all-notifications")).click();
                if (VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".voyantaButton.small.callToAction.actionSaveAndContinue")).getText().equalsIgnoreCase("I accept the terms and conditions") || VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".voyantaButton.small.callToAction.actionSaveAndContinue")).isDisplayed()) {
                    VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".voyantaButton.small.callToAction.actionSaveAndContinue")).click();
                }
            }
        }

        if (VUtils.isElementPresent(By.xpath("//span[@class='label' and contains(text(), 'Admin')]"))) {
            // Code Below to Handle New Admin Locators
            adminLink = VoyantaDriver.findElement(By.xpath("//span[@class='label' and contains(text(), 'Admin')]"));
        } else {
            // For Backward compatibility - Application has old and new links.
            adminLink = VoyantaDriver.findElement(By.linkText("Admin"));
        }
        adminLink.click();
    }

    public void selectDataManagementLink() {
        WebElement dataManagementLink;

        //new login will face the T's & C's popup dailog box on first login attempt
        if (VUtils.isElementPresent(By.xpath("//h2[@class='modal-title']"))) {
            if (VoyantaDriver.getCurrentDriver().findElement(By.xpath("//h2[@class='modal-title']")).getText().equalsIgnoreCase("Terms and Conditions")) {
                VoyantaDriver.getCurrentDriver().findElement(By.id("remove-all-notifications")).click();
                if (VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".voyantaButton.small.callToAction.actionSaveAndContinue")).getText().equalsIgnoreCase("I accept the terms and conditions") || VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".voyantaButton.small.callToAction.actionSaveAndContinue")).isDisplayed()) {
                    VoyantaDriver.getCurrentDriver().findElement(By.cssSelector(".voyantaButton.small.callToAction.actionSaveAndContinue")).click();
                }
            }
        }

        if(VUtils.isElementPresent(By.xpath("//span[@class='label' and contains(text(), 'Data Management')]"))) {
            // Code Below to Handle New Data Management Locator
            dataManagementLink = VoyantaDriver.findElement(By.xpath("//span[@class='label' and contains(text(), 'Data Management')]"));
        } else {
            // For Backward compatibility - Application has old and new links.
            dataManagementLink = VoyantaDriver.findElement(By.linkText("Data Management"));
        }
        dataManagementLink.click();
        VUtils.waitFor(2);
    }

    public void selectSubmissionsPage() {
        pageContainer.submissionDMLink.click();
        LOGGER.info("Goes to Submissions Page");
        VUtils.waitFor(2);
    }

    public void selectPendingApprovalPage() {
        pageContainer.pendingApprovalDMLink.click();
        LOGGER.info("Goes to Pending Approval Page");
        VUtils.waitFor(2);
    }

    public void selectHistoryPage() {
        pageContainer.historyDMLink.click();
        LOGGER.info("Goes to History Page");
        VUtils.waitFor(2);
    }

    public void selectDataLOVPage() {
        pageContainer.listOfValuesLink.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        LOGGER.info("Goes to Admin -> Data LOVs Page");
        VUtils.waitFor(2);
    }

    public void selectManageByDSTPage() {
        pageContainer.manageDSTPermissionsLink.click();
        LOGGER.info("Goes to Permissions -> Manage By DST Page ");
        VUtils.waitFor(2);
    }

    public String getActivePageName() {
        String pageName = null;

        //Wait for page to get load 
        if (VUtils.isElementPresent(By.xpath("//*[@id='loader']"))) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        }
        for (WebElement e : VoyantaDriver.findElements(By.cssSelector("ul.tabs-menu > li > a"))) {
            if (e.getAttribute("class").equals("selected")) {
                pageName = e.getText();
                return pageName;
            }
        }
        return pageName;
    }

    public void viewFileUpload() {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.fileUpload));
        pageContainer.fileUpload.isDisplayed();
    }

    public boolean checkUserCanSeeReport(String reportName) {
        int count = 0;
        int maxTries = 5;
        boolean result = false;
        try {
            VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
            VUtils.waitFor(5);
            WebElement tabs = VoyantaDriver.getCurrentDriver().findElement(By.xpath("//*[@id='tabs-region']")).findElement(By.cssSelector("ul.tabs-menu"));
            List<WebElement> listTabs = tabs.findElements(By.xpath("//li/a"));
            for (WebElement e : listTabs) {
                if (e.getAttribute("class").equals("selected") && e.getText().equalsIgnoreCase(reportName)) {
                    LOGGER.info("Displaying Report : " + e.getText());
                    result = true;
                }
            }
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
        return result;
    }

    public void selectMenu() {
        pageContainer.mainMenu.click();
    }

    public void selectReport() {
        int count = 0;
        int maxTries = 3;
        try {
            pageContainer.appraisalSummaryLink.click();
        } catch (NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }

    }

    public void viewAppraisalReport() {
        int count = 0;
        int maxTries = 3;
        try {
            pageContainer.appraisalSummaryTable.isDisplayed();
        } catch (NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void clickOnFiltersLabel() {
        pageContainer.filtersTab.click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.clearButtonOnFilterPage));
    }

    public void validateAppraisalSummaryData(Map<String, String> dataTable) {
        String value;
        for (String key : dataTable.keySet()) {
            if (!dataTable.keySet().equals("")) {
                System.out.println("the Value in map table " + dataTable.get(key));

                if (key.equals("Appraisal Name")) {
                    value = dataTable.get(key);
                    List<WebElement> listName = pageContainer.appraisalName;
                    for (WebElement e : listName) {
                        int attmp = 0;
                        int maxAttmp = 3;
                        try {
                            if (e.getText().equals(value)) {
                                LOGGER.info("selecting Name : " + e.getText());
                                try {
                                    e.click();
                                } catch (WebDriverException we) {
                                    if (attmp == maxAttmp) throw we;
                                }
                                VUtils.waitFor(2);
                                break;
                            }
                        } catch (StaleElementReferenceException se) {
                            if (attmp == maxAttmp) throw se;
                        }
                    }
                } else if (key.equals("Currency")) {
                    value = dataTable.get(key);
                    List<WebElement> listCurrency = pageContainer.currency;
                    for (WebElement e : listCurrency) {
                        int attmp = 0;
                        int maxAttempts = 3;
                        try {
                            if (e.getText().equals(value)) {
                                LOGGER.info("select Currency: " + e.getText());
                                try {
                                    e.click();
                                } catch (WebDriverException we) {
                                    if (attmp == maxAttempts) throw we;
                                }
                                VUtils.waitFor(2);
                                break;
                            }
                        } catch (StaleElementReferenceException se) {
                            if (attmp == maxAttempts) throw se;
                        }
                    }
                } else if (key.equals("Asset Name")) {

                }
            } else {
                LOGGER.info("No Filter Value is Available in Scenario");
            }
        }
    }

    public boolean verifyApplyFiltersButton() {
        boolean filter = false;
        int attempts = 0;
        int maxAttempt = 3;
        while (!filter) {
            if (attempts++ == maxAttempt) break;
            try {
                if (pageContainer.applyFilters.getAttribute("class").equals("button actionApply highlighted")) {
                    LOGGER.info("Apply Filters Button is Enable ");
                    filter = true;
                    break;
                }
            } catch (WebDriverException e) {
                if (attempts == maxAttempt) throw e;
                LOGGER.info("Apply Filter is not Enable");
                break;
            }
        }
        return filter;
    }

    public void validateTableData(Map<String, String> dataTable) {
        String value;
        for (String key : dataTable.keySet()) {
            if (!dataTable.keySet().equals("")) {
                value = dataTable.get(key);
                List<WebElement> allRows = pageContainer.tableData;
                //LOGGER.info("$$$$$$$$$$$$ list of values " + allRows.toString());
                for (WebElement e : allRows) {
                    List<WebElement> cells = e.findElements(By.xpath("td[4]/span"));
                    for (WebElement cell : cells) {
                        if (cell.getText().equals(value)) {
                            LOGGER.info("Validating data " + cell.getText() + " is equal to the table data" + value);
                            break;
                        }
                    }
                }
            } else {
                LOGGER.info("No Filter Value is Available in Scenario");
            }
        }
    }

    public String clickJavascript = "arguments[0].click();";
    public String scrollIntoView = "arguments[0].scrollIntoView();";
    public String scrollIntoViewTrue = "arguments[0].scrollIntoView(true);";
    public String mouseover = "var evObj = document.createEvent('MouseEvents');" +
            "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
            "arguments[0].dispatchEvent(evObj);";
    public JavascriptExecutor js = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();

    public void javascript(String javascriptString, WebElement element) {
        switch (javascriptString) {
            case "click":
                js.executeScript(clickJavascript, element);
                break;
            case "scrollIntoView":
                js.executeScript(scrollIntoView, element);
                break;
            case "scrollIntoViewTrue":
                js.executeScript(scrollIntoViewTrue, element);
                break;
            case "mouseover":
                js.executeScript(mouseover, element);
                break;
        }
    }

    ;

    public InvestmentsPage goToInvestments() {
        pageContainer.investmentsLink.click();
        VUtils.waitFor(2);
        return new InvestmentsPage();
    }

    public boolean isVersion2ReportDisplayed() {

        VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());

        (new WebDriverWait(VoyantaDriver.getCurrentDriver(), 300)).ignoring(java.util.NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(pageContainer.AssetNameHeader));

        return pageContainer.AssetNameHeader.isDisplayed();
    }

    public boolean isAccountGroupsReportDisplayed() {

        VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());

        (new WebDriverWait(VoyantaDriver.getCurrentDriver(), 300)).ignoring(java.util.NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(pageContainer.AccountGroupBalanceHeader));

        return pageContainer.AccountGroupBalanceHeader.isDisplayed();
    }

    public void goToTenancySchedule20() {
        int count = 0;
        int maxTries = 3;
        try {
            pageContainer.tenancySchedule20Link.click();
            LOGGER.info("clicking on tenancy schedule link");
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void goToAccountGroups() {
        int count = 0;
        int maxTries = 3;
        try {
            pageContainer.accountGroupsLink.click();
            LOGGER.info("clicking on Account groups link");
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }

    }

}


