package voyanta.ui.pageobjects;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.InvestmentsPageContainer;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.util.List;

public class InvestmentsPage extends AbstractVoyantaPage {

	private static Logger LOGGER = Logger.getLogger(InvestmentsPage.class);
	private static WebDriverWait wait;
	private WebDriver driver;
	private static String page = "Investments";
	InvestmentsPageContainer investmentsPageContainer;
	private WebDriver mDriver = VoyantaDriver.getCurrentDriver();

    public InvestmentsPage() {
        this.wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 30);
        investmentsPageContainer = InvestmentsPage.getDataContainer(InvestmentsPageContainer.class);
        driver = VoyantaDriver.getCurrentDriver();
    }

    public boolean canSeeInvestmentReportPage() {
        List<WebElement> elements = driver.findElement(By.xpath("//*[@id='tabs-region']")).findElements(By.xpath("//span/div/div/ul/li"));
        for (WebElement e : elements) {
            if (e.getText().equals(page)) {
                LOGGER.info("Page Name: " + e.getText());
                return true;
            }
        }
        return false;
    }

	public void selectFilterDropDown() {
		wait.until(ExpectedConditions.visibilityOf(investmentsPageContainer.filterDropDown));
		investmentsPageContainer.filterDropDown.click();
		VUtils.waitFor(2);
		if(!investmentsPageContainer.filterContainerBox.isDisplayed())
			investmentsPageContainer.filterDropDown.click();
		VUtils.waitFor(3);
	}

    public boolean canSeeOwnershipFilterData() {
        WebElement element = investmentsPageContainer.ownershipFilter;
        try {
            wait.until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector("div.filter-content")).findElement(By.xpath("//ul/li"))));
        } catch (StaleElementReferenceException e) {

        }
        List<WebElement> data = element.findElement(By.cssSelector("div.filter-content")).findElements(By.xpath("//ul/li"));

        for (WebElement e : data) {
            if (e.getText().equals("Helios Investment Management")) {
                LOGGER.info("Investment Name : " + e.getText());
                return true;
            }
        }
        return false;
    }

	public boolean canSeeInvestmentDiversificationReport() {

		List<WebElement> reports = driver.findElements(By.xpath("//div[@id='charts-region']/div/section"));

		for (WebElement element : reports) {
			WebElement e = element.findElement(By.cssSelector("div.title-bar > label"));
			if(e.getText().equals("Investment Diversification")){
				LOGGER.info("Reports Title : "+e.getText());

				wait.until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector("div.chart-region"))
						.findElement(By.xpath("//div/div[@id='report__Investment_Diversification_Chart']"))));

				WebElement ele = element.findElement(By.cssSelector("div.chart-region"))
						.findElement(By.xpath("//div/div[@id='report__Investment_Diversification_Chart']"));

				if(ele.isDisplayed()) {
					LOGGER.info("Report Displayed!");
					return true;
				}
			}
		}
		return false;
	}

	public boolean canSeeInvestmentDiversificationReportSmoke() {
		WebElement report = getReportElement("Investment Diversification");
		WebElement reportContent = report.findElement(By.cssSelector("div.chart-region"))
				.findElement(By.xpath("//div/div[@id='report__Investment_Diversification_Chart']"));

		LOGGER.info("Report Displayed!");
		if (reportContent.isDisplayed()) {
			LOGGER.info("Report Displayed!");
			return true;
		} else
			return false;

	}

	public boolean investmentDownloadButtonIsEnabled() {

		WebElement report = getReportElement("Investment Diversification");
		WebElement buttonsExport = report.findElement(By.className("actionExport"));
		String classNames = buttonsExport.getAttribute("class");
		LOGGER.info("Report Displayed!");
		if (!classNames.contains("disabled")) {
			LOGGER.info("Export button enabled!");
			return true;
		} else {
			LOGGER.info("Export button was not enabled!");
			return false;
		}
	}

	public WebElement getReportElement(String reportName) {
		for (WebElement element : investmentsPageContainer.reports) {
			WebElement e = element.findElement(By.cssSelector("div.title-bar > label"));
			if (e.getText().equals(reportName)) {
				LOGGER.info("Reports Title : " + e.getText());

				wait.until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector("div.chart-region"))
						.findElement(By.xpath("//div/div[@id='report__Investment_Diversification_Chart']"))));

				LOGGER.info("Report Found!");
				return element;
			}

		}
		LOGGER.info("Report:" + reportName + " not found Found!");
		return null;
	}

	private void selectMonthFilter() {
		WebElement monthQuarterYearHeaderFilter = investmentsPageContainer.monthQuarterYearFilter.findElement(By.cssSelector("div.mode-selector"));

		monthQuarterYearHeaderFilter.click();
		List<WebElement> elements = monthQuarterYearHeaderFilter.findElements(By.xpath("div/ul/li"));

		for(WebElement e : elements){
			if(e.getText().equals("Month")){
				LOGGER.info("Selecting Month ");
				e.click();
				break;
			}
		}
	}

	private void selectMonthQuarterYearStartFilter(final String values) {
		String [] value = values.split(",");
		WebElement start = investmentsPageContainer.monthQuarterYearFilter.findElement(By.cssSelector("div.filter-content")).
				findElement(By.cssSelector("div.calendar-field")).findElement(By.cssSelector("div.input-field")).findElement(By.cssSelector("span.value"));

		start.click();
		WebElement year = investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.range-selector"));
		year.click();
		List<WebElement> elements = year.findElement(By.cssSelector("div.options")).findElements(By.xpath("ul/li"));
		for(WebElement e : elements){
			if(e.getText().equals(value[0])){
				LOGGER.info("Selecting Start Year : "+e.getText());
				e.click();
				VUtils.waitFor(1);
				break;
			}
		}

		WebElement month = investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.drs-list"));
		for(WebElement e : month.findElements(By.xpath("ul/li"))) {
			if(e.getText().equals(value[1].trim())){
				LOGGER.info("Selecting Start Month : "+e.getText());
				e.click();
				VUtils.waitFor(1);
				break;
			}
		}
	}

	private void selectMonthQuarterYearEndFilter(final String values) {
		String [] value = values.split(",");
		WebElement end = investmentsPageContainer.monthQuarterYearFilter.findElement(By.cssSelector("div.filter-content")).
				findElement(By.xpath("//div[2][@class='calendar-field']")).findElement(By.cssSelector("div.input-field")).findElement(By.cssSelector("span.value"));

		end.click();
		WebElement year = investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.range-selector"));
		year.click();

		List<WebElement> elements = year.findElement(By.cssSelector("div.options")).findElements(By.xpath("ul/li"));
		for(WebElement e : elements){
			if(e.getText().equals(value[0])){
				LOGGER.info("Selecting End Year : "+e.getText());
				e.click();
				VUtils.waitFor(1);
				break;
			}
		}

		WebElement month = investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.drs-list"));
		for(WebElement e : month.findElements(By.xpath("ul/li"))) {
			if(e.getText().equals(value[1].trim())){
				LOGGER.info("Selecting End Month : "+e.getText());
				e.click();
				VUtils.waitFor(1);
				break;
			}
		}
	}

	private void selectMonthQuarterYearStartFilterEx(final String values) {
		String [] value = values.split(",");
		WebElement start = investmentsPageContainer.monthQuarterYearFilter.findElement(By.cssSelector("div.filter-content")).
				findElement(By.cssSelector("div.calendar-field")).findElement(By.cssSelector("div.input-field")).findElement(By.cssSelector("span.value"));

		//start.click();
		JavascriptExecutor js = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
		js.executeScript("arguments[0].click();", start);


		VUtils.waitFor(2);
		WebElement year;
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.drs-header")).findElement(By.cssSelector("div.range-selector")));
			year = investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.drs-header")).findElement(By.cssSelector("div.range-selector"));
		}
		catch (Exception ex){
			VUtils.waitFor(5);
			year = investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.drs-header")).findElement(By.cssSelector("div.range-selector"));
		}

		year.click();
		List<WebElement> elements = year.findElement(By.cssSelector("div.options")).findElements(By.xpath("ul/li"));
		for(WebElement e : elements){
			if(e.getText().equals(value[0])){
				LOGGER.info("Selecting Start Year : "+e.getText());
				e.click();
				VUtils.waitFor(1);
				break;
			}
		}

		WebElement month = investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.drs-list"));
		for(WebElement e : month.findElements(By.xpath("ul/li"))) {
			LOGGER.info("values:"+value[1].trim()+", "+value[2].trim());
			if(e.getText().equals(value[1].trim()+", "+value[2].trim())){
				LOGGER.info("Selecting Start Month : "+e.getText());
				e.click();
				VUtils.waitFor(1);
				break;
			}
		}
	}

	private void selectMonthQuarterYearEndFilterEx(final String values) {
		String [] value = values.split(",");
		WebElement end = investmentsPageContainer.monthQuarterYearFilter.findElement(By.cssSelector("div.filter-content")).
				findElement(By.xpath("//div[2][@class='calendar-field']")).findElement(By.cssSelector("div.input-field")).findElement(By.cssSelector("span.value"));

		try{
		end.click();
		}catch(Exception ex){
			JavascriptExecutor js = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
			js.executeScript("arguments[0].click();", end);
		}

		WebElement year = investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.range-selector"));
		year.click();

		List<WebElement> elements = year.findElement(By.cssSelector("div.options")).findElements(By.xpath("ul/li"));
		for(WebElement e : elements){
			if(e.getText().equals(value[0])){
				LOGGER.info("Selecting End Year : "+e.getText());
				e.click();
				VUtils.waitFor(1);
				break;
			}
		}

		WebElement month = investmentsPageContainer.dateSelector.findElement(By.cssSelector("div.drs-list"));
		for(WebElement e : month.findElements(By.xpath("ul/li"))) {
			LOGGER.info("values:"+value[1].trim()+", "+value[2].trim());
			if(e.getText().equals(value[1].trim()+", "+value[2].trim())){
				LOGGER.info("Selecting End Month : "+e.getText());
				try {
					e.click();
				}catch(Exception ex){
					VUtils.waitFor(5);
					JavascriptExecutor js = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
					js.executeScript("arguments[0].click();", e);
				}
				VUtils.waitFor(1);
				break;
			}
		}
	}

	private void selectQuarterFilter() {
		wait.until(ExpectedConditions.visibilityOf(investmentsPageContainer.monthQuarterYearFilter));
		WebElement monthQuarterYearHeaderFilter = investmentsPageContainer.monthQuarterYearFilter.findElement(By.cssSelector("div.mode-selector"));

		monthQuarterYearHeaderFilter.click();
		List<WebElement> elements = monthQuarterYearHeaderFilter.findElements(By.xpath("div/ul/li"));

		for(WebElement e : elements){
			if(e.getText().equals("Quarter")){
				LOGGER.info("Selecting Quarter");
				e.click();
				break;
			}
		}
	}

	private void selectOwnershipFilter(final String value) {
		String label[] = value.split(",");

		for(int i=0; i<label.length; i++) {
			WebElement search = VoyantaDriver.findElement(By.xpath("//h6[text()='Ownership']/preceding-sibling::a[text()='Search']"));

			//search.click();
			JavascriptExecutor js = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
			js.executeScript("arguments[0].click();", search);

			WebElement searchBox = VoyantaDriver.findElement(By.xpath("//h6[text()='Ownership']/following::div[1]//input"));
			searchBox.sendKeys(label[i].trim());
			VUtils.waitFor(2);
			List<WebElement> labels = VoyantaDriver.findElements(By.xpath("//ul[@class='hierarchy']/descendant::li[@class='closed'][1]"));
			for (WebElement e : labels) {
				if (e.getText().equalsIgnoreCase(label[i].trim())) {
					LOGGER.info("Selecting the Ownership: " + e.getText());
					e.click();
					VUtils.waitFor(1);
					break;
				}
			}
			searchBox.clear();
		}
	}

	private void selectCurrencyFilterOnInvestmentPage(String value) {

		List<WebElement> elements = VoyantaDriver.findElement(By.xpath("//h6[text()='Currency']/../..")).findElement(By.cssSelector("div.filter-content"))
				.findElements(By.xpath("ul/li"));

		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(value)) {
				LOGGER.info("Selecting Currency : " + e.getText());
				try {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",e);
					e.click();
				}
				catch(Exception ex){
					VUtils.waitFor(5);
					JavascriptExecutor js = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
					js.executeScript("arguments[0].click();", e);
					//e.click();
				}
				VUtils.waitFor(1);
				break;
			}
		}
	}

	private void selectValuationTypeFilter(String value) {
		List<WebElement> elements = VoyantaDriver.findElement(By.xpath("//h6[text()='Valuation Type (Asset)']/../..")).findElement(By.cssSelector("div.filter-content"))
				.findElements(By.xpath("ul/li"));

		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(value)) {
				LOGGER.info("Selecting Valuation Type : " + e.getText());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",e);
				e.click();
				VUtils.waitFor(1);
				break;
			}
		}
	}

	public void applyFilters() {
		investmentsPageContainer.filterApplyButton.click();
		VUtils.waitFor(2);
	}


	public void selectInvestmentFilterValues(final Map<String, String> data) {
		String value;
		String Qstar=null;

		for(String key : data.keySet()){
			if(!data.get(key).equals("")){
				if(key.equals("Month Start")) {
					value = data.get(key);
					selectMonthFilter();
					selectMonthQuarterYearStartFilter(value);
				}
				else if(key.equals("Month End")){
					value = data.get(key);
					selectMonthQuarterYearEndFilter(value);
				}
				else if(key.equals("Quarter Start")){
					value = data.get(key);
					Qstar = value;
					selectQuarterFilter();
					selectMonthQuarterYearStartFilterEx(value);
				}
				else if(key.equals("Quarter End")){
					value = data.get(key);
					selectMonthQuarterYearEndFilterEx(value);
					if(VoyantaDriver.findElement(By.cssSelector("span.error-sign")).isDisplayed()){
						selectMonthQuarterYearStartFilterEx(Qstar);
					}
				}
				else if(key.equals("Ownership")){
					value = data.get(key);
					selectOwnershipFilter(value);
				}
				else if(key.equals("Currency")) {
					value = data.get(key);
					selectCurrencyFilterOnInvestmentPage(value);
				}
				else if(key.equals("Valuation Type(Asset)")) {
					value = data.get(key);
					selectValuationTypeFilter(value);
				}
			} else
				LOGGER.info("Ignore the filter as the value is not given in Scenario : " + key);
		}
	}

	public void investmentExportToExcel(String reportName){
		int attempts = 0;
		int maxAttempts = 3;
		mDriver.navigate().refresh();
		VUtils.waitForElement(VoyantaDriver.findElement(By.cssSelector(".charts-container.clearfix")));
		try {
			if (VUtils.isElementPresent(By.cssSelector(".title-bar>label")))
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".title-bar>label")));
		} catch (Exception e){
			if (attempts++ == maxAttempts) throw e;
		}

		VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());
		WebElement exportToExcel = VoyantaDriver.findElement(By.xpath("//label[text()='"+reportName+"']/following-sibling::a"));
		int attempts1 = 0;
		int maxAttempts1 = 10;
		try {
			JavascriptExecutor executor = (JavascriptExecutor)VoyantaDriver.getCurrentDriver();
			executor.executeScript("arguments[0].click();", exportToExcel);
		} catch (WebDriverException e){
			if (attempts1++ == maxAttempts1) throw e;
		}
		LOGGER.info("***** Exporting "+reportName+" Information *****");
		VUtils.waitForJQuery(VoyantaDriver.getCurrentDriver());
		VUtils.waitFor(10);
	}

	public String noReportsAvailable(String error, String reportName) {
		int attempts = 0;
		int maxAttempts = 3;
		mDriver.navigate().refresh();
		String mesg = null;
		try {
			if (VUtils.isElementPresent(By.cssSelector(".title-bar>label")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".title-bar>label")));
		} catch (Exception e) {
			if (attempts++ == maxAttempts) throw e;
		}
		mesg = VoyantaDriver.findElement(By.xpath("//label[text()='" + reportName + "']/../../following-sibling::div/descendant::div[@class='exception-message']")).getText();
		VerifyUtils.contains(error, mesg);
		LOGGER.info("No report available");
		return mesg;
	}

	public void selectInvestmentReportProperties(String report, String measureType) {
		int attempts = 0;
		int maxAttempts = 3;
		mDriver.navigate().refresh();
		String type[] = measureType.split(",");
		try {
			if (VUtils.isElementPresent(By.cssSelector(".title-bar>label")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".title-bar>label")));
		} catch (Exception e) {
			if (attempts++ == maxAttempts) throw e;
		}
		WebElement propertyType = VoyantaDriver.findElement(By.xpath("//label[text()='"+report+"']/../../following-sibling::div//descendant::label[contains(text(),'"+type[0].trim()+"')]/following::a[1]"));
		try {
			propertyType.click();
		}
		catch(Exception ex){
			JavascriptExecutor js = (JavascriptExecutor) VoyantaDriver.getCurrentDriver();
			js.executeScript("arguments[0].click();", propertyType);

		}

		List<WebElement> elements = VoyantaDriver.findElements(By.xpath("//label[text()='"+report+"']/../../following-sibling::div//descendant::label[contains(text(),'"+type[0].trim()+"')]//following-sibling::div//ul/li"));
		LOGGER.info("size: "+elements.size());
		for (WebElement e : elements) {
			if (e.getText().equalsIgnoreCase(type[1].trim())) {
				LOGGER.info("Selecting report property: " + e.getText());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",e);
				e.click();
				VUtils.waitFor(1);
				break;
			}
		}

	}

	public void navigateToReportingTab(){
		VoyantaDriver.getCurrentDriver().switchTo().defaultContent();
		pageContainer.reportLink.click();
		VUtils.waitFor(1);
	}

}
