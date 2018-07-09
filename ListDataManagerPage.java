package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.ListDataManagerContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.List;


public class ListDataManagerPage extends AbstractPageWithList {
    static Logger LOGGER = Logger.getLogger(ListDataManagerPage.class);
    public static final String url = PropertiesLoader.getProperty("ui_url") + "data-management/submissions";

    ListDataManagerContainer DMpageContainer;
    public static SubmissionObject submission;
    private static String currentPage;
    private WebDriverWait wait;
    private WebDriver mDriver;

    public WebElement successfulUploadMsg;

    public String getCurrentPage() {
        return currentPage;
    }

    public ListDataManagerPage() {
        this.wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 120);
        this.mDriver = VoyantaDriver.getCurrentDriver();
        VUtils.waitFor(4);
        DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
        wait.until(ExpectedConditions.visibilityOf(DMpageContainer.tableElement));
        super.tableElement = DMpageContainer.tableElement;
        currentPage = "Submissions";
        if (VUtils.isElementPresent(By.xpath("//*[@id='loader']")))
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        VUtils.waitFor(2);
        getRowsInHashBasedOnIndex(2);
    }

    public void go_to_tab(String NewLink) {
        if (NewLink.equals("Submissions")) {
            go_to_Submissions();

        } else if (NewLink.equals("Pending Approval")) {
            go_to_PendingApproval();

        } else if (NewLink.equals("History")) {
            go_to_History();
        }
    }

    public void goToSubmissionsPage() {
        go_to_Submissions();
    }

    //-----------------------------------------------------
    // PRIVATE METHODS
    //-----------------------------------------------------
    private void go_to_Submissions() {
//		wait.until(ExpectedConditions.elementToBeClickable(DMpageContainer.SubmissionsTab));
//		DMpageContainer.SubmissionsTab.click();
        DMpageContainer.submissionLink.click();
        DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
        wait.until(ExpectedConditions.visibilityOf(DMpageContainer.tableElement));
        super.tableElement = DMpageContainer.tableElement;
        currentPage = "Submissions";
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        VUtils.waitFor(2);
        getRowsInHashBasedOnIndex(2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"items_per_page_chzn\"]/a/span")));

    }

    private void go_to_PendingApproval() {
//		wait.until(ExpectedConditions.elementToBeClickable(DMpageContainer.PendingApproval));
        LOGGER.info("Goes to Pending Approval Tab");
//		DMpageContainer.PendingApproval.click();
        DMpageContainer.pendingApprovalLink.click();
        VUtils.waitFor(5);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
//        wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//td[7]")), "Pending..."));
        this.DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
        wait.until(ExpectedConditions.visibilityOf(DMpageContainer.tableElement));
        super.tableElement = DMpageContainer.tableElement;
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        VUtils.waitFor(2);
        getRowsInHashBasedOnIndex(2);
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"items_per_page_chzn\"]/a/span")));
        currentPage = "Pending Approval";
    }

    /**
     * to to history page, rebuild the page and the tableElement
     */
    private void go_to_History() {
//        wait.until(ExpectedConditions.elementToBeClickable(DMpageContainer.SubmissionHistory));
        LOGGER.info("Goes to History Tab");
//		wait.until(ExpectedConditions.visibilityOf(DMpageContainer.SubmissionHistory));
//		DMpageContainer.SubmissionHistory.click();
        DMpageContainer.submissionHistoryLink.click();
        this.DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
        wait.until(ExpectedConditions.visibilityOf(DMpageContainer.tableElement));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"list-table-holder\"]/table/tbody")));
        super.tableElement = DMpageContainer.tableElement;
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
        VUtils.waitFor(2);
        getRowsInHashBasedOnIndex(2);
        currentPage = "History";
    }

    private boolean checkName() {
        if (getCellElementWithRow(1, "Name").getText().contains(submission.getName())) {
            return true;
        }
        return false;
    }

    public void setValidationStatus() {
        //WebElement validationCell=(WebElement) this.getCellElementWithRow(1,"Validation");
        WebElement validationCell = (WebElement) this.getRowElementFromText("Name", submission.getName(), "Validation");
        String status = validationCell.getText();
        System.out.println("current status is-" + validationCell.getText() + "-");
        if (status.contains("failed")) {
            throw new RuntimeException("Test failed with 'validation failed' error");
        } else if (status.replaceAll("\\s+", "").equalsIgnoreCase("")) {
            // submission.setValidationStatus("successfull");
            submission.setValidationStatus("Correct");
            System.out.println("current status is set as  correct");
        } else submission.setValidationStatus(validationCell.getText().replace("/n", ","));

    }

    public void setApprovalStatus() {
        //	WebElement approvalCell=(WebElement) this.getCellElementWithRow(1,"Approval");
        String firstSubmission = this.getCellElementWithRow(1, "Name").getText();
        if (!firstSubmission.equalsIgnoreCase(submission.getName())) {
            VUtils.waitFor(4);
            this.DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
            wait.until(ExpectedConditions.visibilityOf(DMpageContainer.tableElement));
            super.tableElement = DMpageContainer.tableElement;
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
            VUtils.waitFor(2);
            getRowsInHashBasedOnIndex(2);
        }
        WebElement approvalCell = (WebElement) this.getRowElementFromText("Name", submission.getName(), "Approval");
        if (approvalCell.getText().contains("failed")) {
            throw new RuntimeException("Test failed with 'Approval failed' error");
        } else submission.setApprovalStatus(approvalCell.getText());
        LOGGER.info("this is the current approval Status " + submission.getApprovalStatus());
    }

    public void getSubmissionFromPending() {
        String firstsubmission = this.getCellElementWithRow(1, "Name").getText();
        System.out.println("File Submission Name " + submission.getName());
        VerifyUtils.equals(submission.getName(), firstsubmission);
    }

    public void getFirstSubmissionsPending() {
        String status = "Pending...";
        String firstsubmission = this.getCellElementWithRow(1, "Name").getText();
        String submissionStatus = this.getCellElementWithRow(1, "Approval").getText();
        VerifyUtils.equals(status, submissionStatus);
    }

    public void editingObjectSubmissionStatus(String objectType, String objectName, String status) {

        if (objectName.equals("Loan")) {
            objectName = "Debt Facility";
            String edit = "Editing - " + objectName + " - " + objectType;
            String firstsubmissionName = this.getCellElementWithRow(1, "Name").getText();
            String submissionStatus = this.getCellElementWithRow(1, "Approval").getText();
            VerifyUtils.equals(edit, firstsubmissionName);
            VerifyUtils.equals(status, submissionStatus);
        } else {
            String edit = "Editing - " + objectName + " - " + objectType;
            String firstsubmissionName = this.getCellElementWithRow(1, "Name").getText();
            String submissionStatus = this.getCellElementWithRow(1, "Approval").getText();
            VerifyUtils.equals(edit, firstsubmissionName);
            VerifyUtils.equals(status, submissionStatus);
        }
    }

    public void deletingTLObjectSubmissionStatus(String objectType, String objectName, String status) {
        if (objectName.equals("Loan")) {
            objectName = "Debt Facility";
            String delete = "Deleting - " + objectType + " - " + objectName;
            String firstsubmissionName = this.getCellElementWithRow(1, "Name").getText();
            String submissionStatus = this.getCellElementWithRow(1, "Approval").getText();
            VerifyUtils.equals(delete, firstsubmissionName);
            VerifyUtils.equals(status, submissionStatus);
        } else {
            String delete = "Deleting - " + objectType + " - " + objectName;
            String firstsubmissionName = this.getCellElementWithRow(1, "Name").getText();
            String submissionStatus = this.getCellElementWithRow(1, "Approval").getText();
            VerifyUtils.equals(delete, firstsubmissionName);
            VerifyUtils.equals(status, submissionStatus);
        }
    }

    public void deletedObjectSubmissionStatus(String objectName, String objectType, String status) {
        String submissionStatus = this.getCellElementWithRow(1, "Approval").getText();
        VerifyUtils.equals(status, submissionStatus);
    }

    public void getApprovedFromPendingStatus() {
        String firstsubmission = this.getCellElementWithRow(1, "Name").getText();
        System.out.println("File Submission Name " + submission.getName());
        VerifyUtils.equals(submission.getName(), firstsubmission);
        WebElement approvalCell = (WebElement) this.getRowElementFromText("Name", submission.getName(), "Approval");
        if (approvalCell.getText().contains("failed")) {
            throw new RuntimeException("Test failed with 'Approval failed' error");
        } else submission.setApprovalStatus(approvalCell.getText());
        LOGGER.info("this is the current approval Status " + submission.getApprovalStatus());
    }


    /**
     * wait until the current submission is validated or approved, go to the page which has the current submission
     */


    public void waitValidatedSubmission() {
        LOGGER.info("checking where is the current submission");
        if (checkName()) {
            setApprovalStatus();
            setValidationStatus();
            while (submission.getValidationStatus().contains("Validating") || submission.getApprovalStatus().contains("Approving")) {
                LOGGER.info("current submission is still on My submission page waiting for validation or approval");
                VUtils.waitFor(5);
                DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
                wait.until(ExpectedConditions.visibilityOf(DMpageContainer.tableElement));
                VUtils.waitFor(5);
                super.tableElement = DMpageContainer.tableElement;
                setValidationStatus();
                setApprovalStatus();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
                VUtils.waitFor(2);
                getRowsInHashBasedOnIndex(2);
            }
            LOGGER.info("validated submission is on My submission page");
        } else {
            go_to_History();
            LOGGER.info("current submission goes to history page");
            checkName();
            setApprovalStatus();
            setValidationStatus();
            // currentPage="History";
        }
    }

    public ListofReviewPage go_to_ReviewPage() {
        DMpageContainer.ReviewButton.click();
        LOGGER.info("Reviewing the Submissions ****");
        return new ListofReviewPage();
    }

    public void delete_submission() {
        String currentSubmissionName = submission.getName();
        int rowNr = this.getRowNumber("Name", currentSubmissionName);
        DMpageContainer.CancelSubmissionButton.get(rowNr).click();
        this.DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);

    }

    public ListDataManagerPage confirmCancelSubmission() {
        DMpageContainer.confirmCancleSubmissionButton.click();
        // VUtils.waitFor(2);
        return new ListDataManagerPage();
    }

    public boolean deleteMsgExist() {
        return VUtils.isElementWithTextPresentBy(DMpageContainer.deleteAllMsg);
        //  VUtils.isElementPresent(DMpageContainer.getdeleteAllMsgBy());
    }

    public void checkRowApprovalStatus(String objectName, boolean status) {
        getRowsInHash();
        int rowNumber = getRowNumber("Name", objectName);
        boolean checkStatus = checkApproved(rowNumber, status);
        VerifyUtils.True(rowNumber >= 0 && checkStatus);
    }

    public void checkRowApprovalStatus(int row, boolean status) {
        getRowsInHash();
        boolean checkStatus = checkApproved(row, status);
        VerifyUtils.True(row >= 0 && checkStatus);
    }

    public void checkRowRejection(String objectName) {
        getRowsInHash();
        int rowNumber = getRowNumber("Name", objectName);
        VerifyUtils.True(rowNumber >= 0);
    }

    private boolean checkApproved(int rowNumber, boolean approved) {
        WebElement status = (WebElement) listViewElements.get(rowNumber).get("Approval");
        return status.getText().equals("Approved") && approved
                || status.getText().equals("Pending...") && !approved;
    }

    private String checkApproved(int rowNumber) {
        WebElement status = (WebElement) listViewElements.get(rowNumber).get("Approval");
        return status.getText();
    }

    private String checkValidation(int rowNumber) {
        WebElement status = (WebElement) listViewElements.get(rowNumber).get("Validation");
        return status.getText();
    }

    public void approveSubmission() {
        String currentSubmissionName = submission.getName();
        int rowNr = this.getRowNumber("Name", currentSubmissionName);
        DMpageContainer.approveButton.get(rowNr).click();
        this.DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
    }

    public void approveSubmissionRefreshingElements() {
        VUtils.waitFor(5);
        getRowsInHash();
        String currentSubmissionName = submission.getName();
        int rowNr = this.getRowNumber("Name", currentSubmissionName);
        DMpageContainer.approveButton.get(rowNr).click();
        this.DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
    }

    public void notesSubmission() {
        String currentSubmissionName = submission.getName();
        this.getRowNumber("Name", currentSubmissionName);
    }

    public void selectNotesSubmission() {
        String currentSubmissionName = submission.getName();
        int rowNr = this.getRowNumber("Name", currentSubmissionName);
        DMpageContainer.notesButton.get(rowNr).click();
        VUtils.waitFor(5);
        this.DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
    }

    public void selectNotesForSubmission(String fileName) {
        int rowNumber = this.getRowNumber("Name", fileName);
        DMpageContainer.notesButton.get(rowNumber).click();
        VUtils.waitFor(2);
        this.DMpageContainer = ListDataManagerPage.getDataContainer(ListDataManagerContainer.class);
    }

    public void waitForAutoApproveMessage() {
        WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10L);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='autoApprovedNotice']/div")));
    }

    public void goToHistoryPage() {
        go_to_History();
    }

    public void uploadButtonPresent(boolean b) {
        LOGGER.info("Verifying the Upload Button Present ");
//		VerifyUtils.equals(b, pageContainer.linkUploadData.isDisplayed());
        VerifyUtils.equals(b, VUtils.isElementPresent(By.xpath("(//a[contains(text(),'Upload Data')])[2]")));
    }

    public void showSubmissionDropDownPresent(boolean b) {
        LOGGER.info("Verifying the Show Submission Drop Down Present ");
//		VerifyUtils.equals(b,VoyantaDriver.findElement(By.xpath("//*[@id='type_selector_chzn']/a")).isDisplayed());
        VerifyUtils.equals(b, VUtils.isElementPresent(By.xpath("//*[@id='type_selector_chzn']/a")));
    }

    public void allUserSubmissionCheckBoxPresent(boolean b) {
        LOGGER.info("Verifying the All User Submission CheckBox Present ");
//		VerifyUtils.equals(b, VoyantaDriver.findElement(By.cssSelector("div.filter-checkbox")).isDisplayed());
        VerifyUtils.equals(b, VUtils.isElementPresent(By.cssSelector("div.filter-checkbox")));
    }

    public void goToPendingApprovalTab() {
        go_to_PendingApproval();
    }

    public void approvePendingSubmission() {
        WebElement approveButton = VoyantaDriver.findElement(By.xpath("//a[text()='Approve']"));
        approveButton.click();
        LOGGER.info("Approving the Submission");
        VUtils.waitFor(2);
    }

    public void rejectSubmission() {
        WebElement rejectButton = VoyantaDriver.findElement(By.xpath("//a[contains(text(),'Reject')]"));
        rejectButton.click();
        LOGGER.info("Rejecting the Submission");
        VUtils.waitFor(2);
    }

    public void selectShowAllSubmissions() {
        WebElement allSubmissionBox = VoyantaDriver.findElement(By.id("showAllUserSubmissions"));
        LOGGER.info("Selecting Show All user submissions CheckBox");
        allSubmissionBox.click();
        VUtils.waitFor(2);
    }

    public void checkErrorMessage() {
        //todo verify error msg...
        VUtils.waitFor(5);
    }

    public UploadPage selectUploadData() {
        DMpageContainer.UploadDataButton.click();
        VUtils.waitFor(5);
        return new UploadPage();
    }

    public UploadPage selectUploadDataButtonLink() {
        DMpageContainer.uploadDataButtonLink.click();
        VUtils.waitFor(2);
        LOGGER.info("Goes to Upload Data Page");
        return new UploadPage();
    }

    public String getRejectedMessage() {
        String actualMessage = VoyantaDriver.findElement(By.cssSelector("div.message-list.success")).getText();
        LOGGER.debug("Rejected Message : " + actualMessage);
        return actualMessage;
    }

    public void seeRejectedMessage(String expMessage) {
        WebElement message = VoyantaDriver.findElement(By.xpath("//*[@id='container']/ul"));
        VerifyUtils.equals(expMessage, message.getText());
    }

    public void selectNumberOfRows(String nbRows) {
        mDriver.findElement(By.xpath("//*[@id=\"items_per_page_chzn\"]/a")).findElement(By.tagName("span")).click();
        VUtils.waitFor(1);
        WebElement rowElement = mDriver.findElement(By.id("items_per_page_chzn")).findElement(By.className("chzn-drop"));
        List<WebElement> options = rowElement.findElement(By.className("chzn-results")).findElements(By.tagName("li"));
        for (WebElement e : options) {
            if (e.getText().equals(nbRows)) {
                LOGGER.info("Found the ObjectType :" + e.getText());
                LOGGER.info("---------------------------------------");
                e.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='loader']")));
                return;
            } else {
                LOGGER.debug("Not Found Object Name :" + e.getText());
            }
        }
    }

    public String getBannerMessage() {
        return this.DMpageContainer.getBannerMessage().getText();
    }

    public String getSuccessfulUploadMsg() {
//		return this.DMpageContainer.getSuccessfulUploadMsg().getText();
        return successfulUploadMsg.getText();
    }

    public static void openPage() {
        VoyantaDriver.getCurrentDriver().get(url);
    }

    public Boolean canSeePendingApprovalPageMsg(String message) {
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='container']/ul[@class='message-list success']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.message-list.success")));
        WebElement approveMsg = VoyantaDriver.findElement(By.cssSelector("div.message-list.success"));
        System.out.println("Approved Message : " + approveMsg.getText());
        return approveMsg.getText().equals(message);
    }

    public void checkLoVFileStatus(String fileName, String status) {
        getRowsInHash();
        int rowNumber = getRowNumber("Name", fileName);
        VerifyUtils.True(status.equals(checkApproved(rowNumber)));
    }

    public void checkLoVFileOnSubmission(String fileName, String status) {
        getRowsInHash();
        int rowNumber = getRowNumber("Name", fileName);
        VerifyUtils.True(status.equals(checkValidation(rowNumber)));
    }

    public String checkNoteVisible() {
        return DMpageContainer.notesPopUp.getText();
    }

    public String checkSubmissionNote() {
        WebElement noteMSG = VoyantaDriver.findElement(By.cssSelector("ul.list > li"));
        return noteMSG.getText();
    }

    public void checkSubmissionsTabSelected() {
        VerifyUtils.True(DMpageContainer.submissionsTabSelected != null);
    }

    public void enterPageNumber(String pageNumber) {
        DMpageContainer.goToPageBox.sendKeys(pageNumber);
        LOGGER.info("Go to Page : " + pageNumber);
        VUtils.waitFor(1);
    }

    public void selectGoButton() {
        DMpageContainer.goButton.click();
        VUtils.waitFor(3);
    }

    public String getPageNumber() {
        WebElement pages = VoyantaDriver.findElement(By.cssSelector("div.pages"));
        return pages.findElement(By.cssSelector("a.voyantaButton.current")).getText();
    }

    public void selectSubmissionToViewFiles(String fileName) {
        getRowsInHashBasedOnIndex(2);
        int rowNumber = getRowNumber("Name", fileName);
        WebElement fileNameElement = getCellElementWithRow(rowNumber + 1, "Name");
        fileNameElement.findElement(By.cssSelector("a.expander.actionToggleExpandedView")).click();
        LOGGER.info("Submitted File to Select : " + fileNameElement.getText());
        wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.cssSelector("tr.expandedView > td"))));
    }

    public String seeUploadedFileInExpandedView() {
        WebElement expandedView = VoyantaDriver.findElement(By.cssSelector("tr.expandedView > td"));
        return expandedView.getText();
    }

    public void waitForSubmissionsTable() {
        wait.until(ExpectedConditions.visibilityOf(DMpageContainer.listTableHolder));
    }
}

