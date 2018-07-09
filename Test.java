package voyanta.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.Set;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(System.getProperty("user.name"));

        WebDriver driver = VoyantaDriver.getCurrentDriver();
        driver.get("http://test.voyanta.com");
        SignInPageObject signInPage = new SignInPageObject();
        VUtils.waitFor(5);
        signInPage.signIn("tester@businessrules.com", "password1!");

        Set<String> windows = driver.getWindowHandles();
        String adminToolHandle = driver.getWindowHandle();
        ((JavascriptExecutor) driver).executeScript("window.open();");
        Set<String> customerWindow = driver.getWindowHandles();
        customerWindow.removeAll(windows);
        String customerSiteHandle = ((String) customerWindow.toArray()[0]);
        driver.switchTo().window(customerSiteHandle);
        driver.get("https://accounts.google.com");
        driver.switchTo().window(adminToolHandle);
        driver.findElement(By.linkText("Reports")).click();
        driver.switchTo().window(customerSiteHandle);
        driver.findElement(By.id("Email")).sendKeys("jitongs@gmail.com");
        driver.findElement(By.id("Passwd")).sendKeys("123456");
        driver.findElement(By.id("signIn")).click();

        driver.close();

    }


}
