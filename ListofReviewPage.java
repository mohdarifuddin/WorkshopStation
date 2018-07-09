package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import voyanta.ui.pagecontainers.ListReviewPageContainer;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;
import voyanta.ui.utils.WaitUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ting.liu
 */
public class ListofReviewPage extends AbstractPageWithList {

    static Logger LOGGER = Logger.getLogger(ListofReviewPage.class);

    ListReviewPageContainer reviewContainer;


    public ListofReviewPage() {
        VUtils.waitFor(4);
        reviewContainer = ListofReviewPage.getDataContainer(ListReviewPageContainer.class);
//		wait.until(ExpectedConditions.visibilityOf(reviewContainer.exportButton));
        wait.until(ExpectedConditions.visibilityOf(reviewContainer.tableElement));
        super.tableElement = reviewContainer.tableElement;
        LOGGER.info("the hashmap table is going to be built");
        getRowsInHashBasedOnIndex(0);
    }

    public ListofReviewPage click_DST_Object(String objectName) {
        WebElement objectElement = reviewContainer.getDSTObjectButton(objectName);
        if (objectElement == null) {
            throw new RuntimeException("the targed DST Object is not available on the review page");
        } else objectElement.click();
        //	WebElement defaultElement=
        //	WaitUtils.waitForElement(reviewContainer.getDefaultElement());
        WaitUtils.waitFor(5);
        return new ListofReviewPage();
    }

    public String getValueForDSTColumn(int row, String columnName) {
        //this.getRowsInHashBasedOnIndex(0);
        return this.getCellElementWithRow(row, columnName).getText().replace(",", "");
    }

    public String getValidationResultForDSTColumn(int row, String columnName) {
        LOGGER.info("recieve value for row " + row + " under column " + columnName);
        String validationResult = null;
        WebElement element = getCellElementWithRow(row, columnName);
        if (element.findElement(By.tagName("Span")).getAttribute("class").contains("red")) {
            return "error";
        } else if (element.findElement(By.tagName("Span")).getAttribute("class").equalsIgnoreCase("orange")) {
            return "warning";
        } else
            return null;
    }

    public WebElement getSentinelElement(int row) {
        WebElement element = getCellElementWithRow(row, "sentinel");
        WebElement linkElement = element.findElement(By.tagName("a"));
        return linkElement;
    }

    public WebElement getSentinelErrorElement(int row) {
        WebElement element = getCellElementWithRow(row, "sentinel");
        WebElement linkErrorElement = element.findElement(By.cssSelector("a.actionShowErrors"));
        return linkErrorElement;
    }

    public String getNumberOfUpdates(int row) {
        String numberOfUpdates = getUpdateElement(row).getText();
        return numberOfUpdates;
    }

    public String getValidationTypeFromSentinel(int row) {
        String sentinelType = getSentinelElement(row).getText();
        return sentinelType;
    }

    public void clickSentinel(int row) {
        getSentinelElement(row).click();
        reviewContainer = ListofReviewPage.getDataContainer(ListReviewPageContainer.class);
    }

    public String getMsg() {
        String msg = "";
        List<WebElement> elements = VoyantaDriver.getCurrentDriver().findElement(By.id(reviewContainer.BRMsgID)).findElements(By.tagName("li"));
        LOGGER.info("there are " + elements.size() + " errors in this submission ");
        for (WebElement element : elements) {
            if (!element.findElement(By.cssSelector("span.type")).getText().equals("Update")) {
                LOGGER.info("this is the error message " + element.getText());
                msg = msg.concat(element.getText());
            }
        }
        return msg;
    }


    public void closeBRBox() {
        wait.until(ExpectedConditions.visibilityOf(reviewContainer.closeDialogButton));
        reviewContainer.closeDialogButton.click();
    }

    public ListofReviewPage deleteSingleRow(int index) {
        reviewContainer.getDeleteSingleRowButtons().get(index).click();
        return new ListofReviewPage();
    }

    public int checkErrorRowNumbers() {
        return reviewContainer.getDeleteSingleRowButtons().size();
    }

    /**
     * @param row
     * @return the validation message after clicking the validation result link under sentinel
     */
    public String getSentinelMSG(int row) {
        this.clickSentinel(row);
        return getMsg();
    }

    public boolean checkDeleteSingleButton() {
        // TODO Auto-generated method stub
        return VUtils.isElementPresent(reviewContainer.getDeleteSinglerLocator());
    }

    public boolean checkDeleteAllButton() {
        // TODO Auto-generated method stub
        return VUtils.isElementPresent(reviewContainer.getDeleteAllErrorLocator());
    }

    public boolean checkDeleteAllEWButton() {
        // TODO Auto-generated method stub
        return VUtils.isElementPresent(reviewContainer.getDeleteAllErrorAndWarningLocator());
    }

    public ListofReviewPage deleteAllErrors() {
        reviewContainer.buttonRemoveError.click();
        VUtils.waitFor(5);
        return new ListofReviewPage();
    }

    public ListofReviewPage confirmDeleteYES() {
        wait.until(ExpectedConditions.visibilityOf(this.reviewContainer.confirmationYes));
        reviewContainer.confirmationYes.click();
        VUtils.waitFor(5);
        return new ListofReviewPage();
    }

    public ListDataManagerPage confirmDeleteAllYES() {
        reviewContainer.confirmationYes.click();
        VUtils.waitFor(5);
        return new ListDataManagerPage();
    }

    public void confirmDeleteNO() {
        reviewContainer.confirmationNO.click();
    }

    public String readMSG() {
        return reviewContainer.confirmationBox.getText();
    }

    public ListofReviewPage deleteAllWE() {
        reviewContainer.buttonRemoveErrorWarning.click();
        return new ListofReviewPage();
    }

    public void cancelSubmission() {
        reviewContainer.cancelSubmission.click();
        reviewContainer = ListofReviewPage.getDataContainer(ListReviewPageContainer.class);
    }

    public ListDataManagerPage confirmCancelSubmission() {
        reviewContainer.confirmCancelSubmission.click();
        return new ListDataManagerPage();
    }

    public ListDataManagerPage ignoreWarning() {
        wait.until(ExpectedConditions.visibilityOf(this.reviewContainer.ignoreWarningButton));
        reviewContainer.ignoreWarningButton.click();
        return new ListDataManagerPage();
    }

    public ListDataManagerPage approve() {
        wait.until(ExpectedConditions.visibilityOf(this.reviewContainer.approveButton));
        reviewContainer.approveButton.click();
        return new ListDataManagerPage();
    }

    public ListDataManagerPage reject() {
        wait.until(ExpectedConditions.visibilityOf(this.reviewContainer.buttonReject));
        reviewContainer.buttonReject.click();
        return new ListDataManagerPage();
    }

    public ListofReviewPage showOnlyEAndW() {
        reviewContainer.boxErrorRows.click();
        return new ListofReviewPage();
    }

    public ListofReviewPage showOnlyWarningAndAlert() {
        reviewContainer.boxErrorRows.click();
        return new ListofReviewPage();
    }

    public ListofReviewPage showOnlyChangedRows() {
        reviewContainer.boxChangedRows.click();
        return new ListofReviewPage();
    }

    public List<String> getDSTList() {
        List<String> dstList = new ArrayList<String>();
        for (WebElement element : reviewContainer.getDstTypeList()) {
            dstList.add(element.getText());
        }
        return dstList;
    }

    public void rejectSubmission() {
        reviewContainer.buttonReject.click();
        LOGGER.info("Rejecting The Submission !!!");
        reviewContainer = ListofReviewPage.getDataContainer(ListReviewPageContainer.class);
    }

    public void confirmRejection(String message) {
        reviewContainer.inputRejectNote.sendKeys(message);
        reviewContainer.buttonRejectConfirm.click();
    }

    public ListDataManagerPage confirmRejection() {
        confirmRejection("test");
        return new ListDataManagerPage();
    }

    public void checkRejectionNoteContent(String note) {
        wait.until(ExpectedConditions.elementToBeClickable(reviewContainer.buttonNotes));
        reviewContainer.buttonNotes.click();
        VerifyUtils.True(reviewContainer.notesText.getText().contains(note));
    }

    public void clickUpdate(int row) {
        getUpdateElement(row).click();
        reviewContainer = ListofReviewPage.getDataContainer(ListReviewPageContainer.class);
    }

    private WebElement getUpdateElement(int row) {
        WebElement element = getCellElementWithRow(row, "updated");
        return element.findElement(By.tagName("a"));
    }

    public String revalidate() {
        wait.until(ExpectedConditions.visibilityOf(this.reviewContainer.ButtonRevalidate));
        String submissionName = this.reviewContainer.submissionName.getText().split(" ")[1];
        this.reviewContainer.ButtonRevalidate.click();
        return submissionName;
    }

    public String ignore() {

        wait.until(ExpectedConditions.visibilityOf(this.reviewContainer.ignoreWarningButton));
        String submissionName = this.reviewContainer.submissionName.getText().split(" ")[1];
        this.reviewContainer.ignoreWarningButton.click();

        return submissionName;
    }

    public String cancelSubmissionFromReview() {

        wait.until(ExpectedConditions.visibilityOf(this.reviewContainer.cancelSubmission));
        String submissionName = this.reviewContainer.submissionName.getText().split(" ")[1];
        this.reviewContainer.cancelSubmission.click();

        return submissionName;
    }

    public void selectDebtTabs(String tabName) {

        if ("Debt Facility".equals(tabName.trim())) {
            wait.until(ExpectedConditions.visibilityOf(this.reviewContainer.debtFacility));
            this.reviewContainer.debtFacility.click();
        } else if ("Debt Collateral".equals(tabName.trim())) {
            wait.until(ExpectedConditions.visibilityOf(this.reviewContainer.debtCollateral));
            this.reviewContainer.debtCollateral.click();
        }
    }

    public boolean checkIgnoreWarnings() {
        return VUtils.isElementPresent(reviewContainer.getIgnoreWarningsLocator());
    }

    public boolean checkWarningApprovalPage(String message) {
        return reviewContainer.getWarningApprovalPage(message);
    }

    public void checkReviewSubmissionsPage() {
        VerifyUtils.True(reviewContainer.reviewSubmissionsPage != null);
    }

    public void checkUploadPage() {
        VerifyUtils.True(reviewContainer.uploadPage != null);
    }

    public void checkSubmissionReviewPageTabs(String[] tabs) {

        for (int i = 0; i < tabs.length; i++) {
            switch (tabs[i]) {
                case "Debt Facility": {
                    VerifyUtils.True(reviewContainer.debtFacility != null);
                    break;
                }

                case "Covenant": {
                    VerifyUtils.True(reviewContainer.covenant != null);
                    break;
                }

                case "Equity Participation": {
                    VerifyUtils.True(reviewContainer.equityParticipation != null);
                    break;
                }

                case "Building": {
                    VerifyUtils.True(reviewContainer.building != null);
                    break;
                }

                case "Legal Entity": {
                    VerifyUtils.True(reviewContainer.legalEntity != null);
                    break;
                }

                case "Account Activity": {
                    VerifyUtils.True(reviewContainer.accountActivity != null);
                    break;
                }

                case "Lease": {
                    VerifyUtils.True(reviewContainer.lease != null);
                    break;
                }

                default: {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public List<WebElement> getErrorsInTable() {
        return reviewContainer.errorsInTable;
    }

    public WebElement getUpdateReviewMessage() {
        return reviewContainer.updateReviewMessage;
    }

    public boolean noUpdateColumn() {
        return VUtils.isElementPresent(By.cssSelector("div.updated"));
    }

}
