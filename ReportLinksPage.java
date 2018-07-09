package voyanta.ui.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.List;

public class ReportLinksPage extends AbstractVoyantaPage {

    public ReportLinksPage() {

    }

    public void clickOnReport() {
        WebElement report = VoyantaDriver.findElement(By.linkText("Reports"));
        report.click();
        VUtils.waitFor(0);
    }


    public void gotoViewReportPage(String reportName) {
//        VoyantaDriver.getCurrentDriver().switchTo().frame("report-page");
        int i = 0;
        WebElement elements = VoyantaDriver.findElement(By.id("reportPagesList")); //.findElements(By.xpath("//li/div/a"));
        for (WebElement report : elements.findElements(By.tagName("h3"))) {
            if (report.getText().equals(reportName)) {
                //System.out.println(report.getText());
                VoyantaDriver.findElements(By.linkText("View")).get(i).click();
                VUtils.waitFor(2);
                break;
            }
            i++;
        }
        //VoyantaDriver.switchTo().defaultContent();
    }

    public boolean isReportDisplayed(String title) {
        int i = 0;
        VoyantaDriver.getCurrentDriver().switchTo().frame("report-page");
        VUtils.waitFor(5);
        List<WebElement> elements = VoyantaDriver.findElements(By.xpath("//div[contains(@class,'QvCaptiontext')]"));
        if (getHeaders(title, elements)) return true;
        elements = VoyantaDriver.findElements(By.xpath("//div[contains(@class,'QvCaptionmultitext')]"));
        if (getHeaders(title, elements)) return true;
        VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
        return false;
    }

    private boolean getHeaders(String title, List<WebElement> elements) {
        // System.out.print("Number:" + elements.size());

        for (WebElement reportTitle : elements) {
            if (reportTitle.findElement(By.tagName("div")).getText().equals(title)) {
                // System.out.println("pass");
                VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
                return true;
            }
        }
        return false;
    }


}


















