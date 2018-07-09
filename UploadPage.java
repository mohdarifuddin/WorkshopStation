package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.UploadPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;
import voyanta.ui.utils.unused.FileSearch;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.List;

public class UploadPage extends AbstractVoyantaPage {

    private static final String url = PropertiesLoader.getProperty("ui_url") + "data-management/upload";
    static Logger LOGGER = Logger.getLogger(UploadPage.class);
    UploadPageContainer uploadPageContainer;
    public SubmissionObject submission;

    public UploadPage() {

        uploadPageContainer = UploadPage.getDataContainer(UploadPageContainer.class);
        submission = new SubmissionObject();

        // CreateRulesPageContainers pageContainer = CreateRulePage.getDataContainer(CreateRulesPageContainers.class);
    }

    public static UploadPage openPage() {
        VoyantaDriver.getCurrentDriver().get(url);
        return new UploadPage();
    }

    public void uploadFiles(String folder, String[] fileList) {
        int listLength = fileList.length;
        for (int i = 0; i < listLength; i++) {
            try {
                selectFile(fileList[i], folder);
            } catch (AWTException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // uploadPageContainer= UploadPage.getDataContainer(UploadPageContainer.class);
    }

    public void typeName(String name) {
        uploadPageContainer.nameInput.sendKeys(name);
        submission.setName(name);
    }

    public void typeNotes(String notes) {
        uploadPageContainer.notesInput.sendKeys(notes);
        submission.setNote(notes);
    }

    public boolean getSucElement() {
        if (uploadPageContainer.getSuccessMsg() == null) {
            return true;
        }
        return false;
    }

    public String getUploadSuccessMsg() {
        return uploadPageContainer.getSuccessMsg().getText();
    }

    public String getMultiFileUplaodStatus() {
        WebElement fileStatus = VoyantaDriver.findElement(By.cssSelector("div.status-bar"));
        return fileStatus.getText();
    }

    public String getUploadErrorMsg() {
        return uploadPageContainer.getUploadMSG().getText();
    }

    public String getUploadResult() {
        return uploadPageContainer.getUploadResult().getText();
    }

    public String getUploadMSG() {
        return uploadPageContainer.getUploadMSG().getText();
    }

    public String getURL() {
        return uploadPageContainer.url;
    }

    public void waitTillFileIsUploaded() {

        VoyantaDriver.waitForElement(uploadPageContainer.getUploadedStatus());
    }

    private static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    private void selectFile(String fileName, String folder) throws AWTException {

        String filePath = checkFileExist(fileName, folder);
        LOGGER.info("Uploading the file from location:" + filePath);
        VUtils.waitFor(5);
//        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("fileupload")));
        uploadPageContainer.selectFileButton.sendKeys(filePath);
        WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='voyantaButton medium']")));
    }

    private String checkFileExist(String fileName, String folder) {
        LOGGER.info("Searching for the file :" + fileName + " in folder :" + folder);
        File excelFolder = FileSearch.findFile(fileName, new File(folder));
        if (excelFolder == null) {
            throw new RuntimeException("file " + fileName + " doesn't exist in folder " + folder);
        } else
//  Assert.assertTrue(excelFolder.getAbsolutePath().contains(datasheet));
            return excelFolder.getAbsolutePath();
    }

    public ListDataManagerPage submitFiles() {

        uploadPageContainer.submitButton.click();

        //VoyantaDriver.waitFor(10000);
        VUtils.waitFor(5);
        return new ListDataManagerPage();
    }

    public void submitWrongFiles() {

        uploadPageContainer.submitButton.click();
        VUtils.waitFor(3);
//        VoyantaDriver.waitFor(10000);
    }

    public void selectSubmitFromUpload() {
        uploadPageContainer.submitButton.click();
        try {
            wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath("//*[@id='content']/h1"), "Data Manager"));
        } catch (org.openqa.selenium.TimeoutException t) {
            LOGGER.info("Error On Upload Page ");
        }

    }

    public String getSubmissionErrorMsgFromUpload() {
        WebElement message = VoyantaDriver.findElement(By.xpath("//*[@id='form-messenger']"));
        return message.getText();
    }

    public String getFileName() {
        WebElement uploadMonitor = VoyantaDriver.findElement(By.id("uploader-monitor"));
        int i = 0;
        String text = "";
        List<WebElement> fileList = uploadMonitor.findElement(By.className("file-list")).findElements(By.className("file"));
        for (WebElement file : fileList) {

            LOGGER.info("Found the File ");
            text = text.concat(file.getText());
            System.out.println("print the text " + text);
        }
        LOGGER.info(text);
        return text;
    }

    public void enterFileName(String fileName) {
        uploadPageContainer.nameInput.sendKeys(fileName);
        VUtils.waitFor(2);
    }

    public void enterNotesToSubmission(String notes) {
        uploadPageContainer.notesInput.sendKeys(notes);
        VUtils.waitFor(2);
    }

    public void saveForUploadLoV() {
        selectSubmitFromUpload();
    }

    public String getUploadDSTstatusMsg() {
        WebElement uploadStatusMsg = VoyantaDriver.findElement(By.xpath("//div[@id='uploader-monitor']/div/div/span"));
        return uploadStatusMsg.getText();
    }

    public boolean isErrorPresentOnUploadPage() {
        if (VUtils.isElementPresent(By.xpath("//*[@id='form-messenger']")))
            return true;
        else
            return false;
    }
}
