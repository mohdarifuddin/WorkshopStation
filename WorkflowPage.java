package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.AbstractPageContainer;
import voyanta.ui.pagecontainers.WorkflowPageContainer;
import voyanta.ui.utils.VoyantaDriver;
import voyanta.ui.webdriver.core.elements.impl.internal.ElementFactory;

/**
 * Created by lprescott on 25/08/2016.
 */
public class WorkflowPage extends AbstractVoyantaPage implements VoyantaPage {

    static Logger LOGGER = Logger.getLogger(AbstractVoyantaPage.class);
    static WorkflowPageContainer pageContainer = getDataContainer(WorkflowPageContainer.class);
    protected WebDriverWait wait;

    public WorkflowPage() {
        pageContainer = getDataContainer(WorkflowPageContainer.class);
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


    public static void selectWorkflowMenu() {
        int count = 0;
        int maxTries = 5;
        try {
            pageContainer.TasksMenu.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void navigateToMyTasks() {
        int count = 0;
        int maxTries = 5;
        try {
            pageContainer.MyTasks.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void assertAppraisalText(String appraisalText) {
        int count = 0;
        int maxTries = 5;
        try {
            wait.until(ExpectedConditions.visibilityOf(pageContainer.AppraisalMyTasks));
            Assert.assertTrue(pageContainer.AppraisalMyTasks.getText().contains(appraisalText));
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void assertAppraisalTitleText(String appraisalText) {
        int count = 0;
        int maxTries = 3;
        try {
            Assert.assertTrue(pageContainer.AppraisalTitle.getText().contains(appraisalText));
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void selectNewTask() {
        int count = 0;
        int maxTries = 3;
        try {
            pageContainer.NewTask.click();
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void selectRFP(String option, String tabName) {
        int count = 0;
        int maxTries = 3;
        final WebElement[] ele = new WebElement[1];
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(pageContainer.TaskOptionAppraisal));
            pageContainer.TaskOptionAppraisal.stream().forEach(element -> {
                if (element.getText().equalsIgnoreCase(option)) {
                    ele[0] = element;
                }
            });

            javascript("mouseover", ele[0]);
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
        int count1 = 0;
        int maxTries1 = 5;
        try {
            pageContainer.AppraisalHref.stream().forEach(element -> {
                if (element.getText().equalsIgnoreCase(tabName)) {
                    element.click();
                }
            });
        } catch (ElementNotVisibleException | NoSuchElementException f) {
            if (++count1 == maxTries1) throw f;
        }
    }

    public void assertMaterialEventTitleText(String appraisalText) {
        int count = 0;
        int maxTries = 3;
        try {
            wait.until(ExpectedConditions.visibilityOf(pageContainer.MaterialEventTitle));
            Assert.assertTrue(pageContainer.MaterialEventTitle.getText().contains(appraisalText));
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

    public void filesTabloadContent() {
        int count = 0;
        int maxTries = 3;
        try {
            wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.xpath("//div[@id='tab-content']/descendant::div[@id='table']"))));
            Assert.assertTrue(VoyantaDriver.findElement(By.xpath("//div[@id='tab-content']/descendant::div[@id='table']")).isDisplayed());
            LOGGER.info("files table loads");
        } catch (ElementNotVisibleException | NoSuchElementException e) {
            if (++count == maxTries) throw e;
        }
    }

}
