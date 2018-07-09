package voyanta.ui.pageobjects;


import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.LoginPageContainer;
import voyanta.ui.utils.PropertiesLoader;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

/**
 * @author ting
 * This class defines all the relevant elements and services provided by Sign In Page
 */
public class SignInPageObject extends AbstractVoyantaPage {
    public static final int MAX_TIME_OUT = 60;
    static Logger LOGGER = Logger.getLogger(SignInPageObject.class);
    private LoginPageContainer pageContainer = SignInPageObject.getDataContainer(LoginPageContainer.class);
    ;
    private WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10);
    ;

    public SignInPageObject() {
        this.pageContainer = SignInPageObject.getDataContainer(LoginPageContainer.class);
        this.wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10);
    }

    public void signIn(String userName, String passWord) {
        LOGGER.info("Logging in with the username and password :" + userName + " , " + passWord);
        pageContainer.inputEmail.sendKeys(userName);
        pageContainer.inputPassword.sendKeys(passWord);
        pageContainer.buttonSignIn.click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.logo));
        //Assert.assertTrue("Login unsuccessful. Please check if application is up and running!", pageContainer.logo.getAttribute("data-tid").equals("siteLogo"));
        LOGGER.info("Loggin success with the username and password: " + userName + " , " + passWord);
        VUtils.waitFor(4);
    }

    public void signInToNaukriProfile(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(pageContainer.naukriInitialLoginBtn));
        pageContainer.naukriInitialLoginBtn.click();
        VUtils.waitFor(2);
        pageContainer.inputNaukriEmail.sendKeys(username);
        pageContainer.inputNaukriPassword.sendKeys(password);
        pageContainer.naukriButtonSignIn.click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.naukriLogo));
        LOGGER.info("Loggin success with the username and password: " + username + " , " + password);
        VUtils.waitFor(4);

    }

    public void login_gmail(String userName, String passWord) {
        LOGGER.info("Logging in to Gmail with UserName and Password : " + userName + " , " + passWord);
        pageContainer.gmailEmail.sendKeys(userName);
        pageContainer.gmailNext.click();
        pageContainer.gmailPassWord.sendKeys(passWord);
        pageContainer.gmailSignIn.click();
        wait.until(ExpectedConditions.titleContains(pageContainer.gmailName.getText()));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException i) {
            System.out.println("sleep error");
        }
        Assert.assertTrue("Login unsuccessful. Please check the login details!", pageContainer.gmailName.getText().contains("Gmail"));
        LOGGER.info("Log in success to Gmail!!!");
    }

    public void forgotPW() {
        pageContainer.linkFP.click();
    }

    public static String getURL() {
        return URL;
    }

    public void waitForFirstPageToLoad(WebDriver driver, By by) throws InterruptedException {
        driver.switchTo().frame("report-page");
        waitForElementLoaded(driver, by);
        driver.switchTo().defaultContent();
    }

    private void waitForElementLoaded(WebDriver driver, By by) throws InterruptedException {
        int i = 1000;
        int counter = 0;
        while ((!elementPresent(driver, by)) && counter < MAX_TIME_OUT) {

            Thread.sleep(i);
            counter++;
            System.out.println("Waiting for a sec....");
        }
    }

    private boolean elementPresent(WebDriver driver, By by) {
        try {
            return driver.findElement(by).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public void signIn() {
        signIn(PropertiesLoader.getProperty("username"), PropertiesLoader.getProperty("password"));
    }

    public void enterEmail(String userEmail) {
        LOGGER.info("Enter User Email : " + userEmail);
        pageContainer.inputEmail.sendKeys(userEmail);
    }

    public void enterPassword(String password) {
        LOGGER.info("Enter Password : " + password);
        pageContainer.inputPassword.sendKeys(password);
    }

    public void selectSubmit() {
        pageContainer.buttonSignIn.click();
    }

    public String loginErrorMessages() {
        wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.id(pageContainer.ErrorMSGID))));
        return VoyantaDriver.findElement(By.id(pageContainer.ErrorMSGID)).getText();
    }

    public String accessDeniedPage() {
        wait.until(ExpectedConditions.visibilityOf(VoyantaDriver.findElement(By.cssSelector("body > div.container-fluid"))));
        return VoyantaDriver.findElement(By.cssSelector("body > div.container-fluid")).getText();
    }

    public void verifySignInPage(String userName, String passWord) {
        LOGGER.info("Logging in with the username and password :" + userName + " , " + passWord);
        pageContainer.inputEmail.sendKeys(userName);
        pageContainer.inputPassword.sendKeys(passWord);
        pageContainer.buttonSignIn.click();
        wait.until(ExpectedConditions.visibilityOf(pageContainer.errorMesg));
        VUtils.waitFor(2);
    }
}
