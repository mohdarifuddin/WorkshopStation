package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;
import voyanta.ui.utils.unused.FileSearch;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sriramangajala on 24/07/2014.
 */
public class AbstractPageWithList extends AbstractVoyantaPage {
    static Logger LOGGER = Logger.getLogger(AbstractPageWithList.class);
    public WebElement tableElement;
    public String tableId = "sharing-table";
    public List<HashMap> listViewElements;
    int rowCount;

    public List<HashMap> getListViewElements() {
        return listViewElements;
    }

    public int getRowCount() {
        return rowCount;
    }

    /**
     * @param text: the text user wants to search in
     * @return
     */
    public boolean checkTheRowPresentWithText(String text) {
        for (WebElement element : getRowsAsElements()) {
            if (element.getText().contains(text))
                return true;
        }

        return false;
    }

    /**
     * @return all the rows within the table in the page as a list
     */
    public List<WebElement> getRowsAsElements() {
        return tableElement.findElements(By.tagName("tr"));
    }

    /**
     * @return get the whole table as HashMap
     */
    public List<HashMap> getRowsInHash() {

        List<HashMap> listOfElement = new LinkedList<>();

	/*	Hiten had commented this Because it is Affecting Other Test -- This is general Method so can't use this for specific page

	    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/*//*[@id=\"list-table-holder\"]/table/tbody/tr")));
        for (WebElement element : tableElement.findElements(By.xpath("/*//*[@id=\"list-table-holder\"]/table/tbody/tr"))) {
      */
        if (tableElement.findElements(By.cssSelector(".pages div a span")).size() > 1) {
            VoyantaDriver.getCurrentDriver().navigate().refresh();
            for (int page = 1; page <= tableElement.findElements(By.cssSelector(".pages div a span")).size(); page++) {
                for (WebElement element : tableElement.findElements(By.xpath("//tbody/tr"))) {
                    if (!element.getText().trim().equals("")) {
                        if (!element.getAttribute("class").contains("expandedView")) {
                            int i = 0;
                            HashMap hashMap = new HashMap();
                            for (WebElement element1 : element.findElements(By.tagName("td"))) {
                                String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                                hashMap.put(header, element1.getText());
                                LOGGER.debug("Key:" + header + " Value:" + element1.getText());
                                i++;
                            }
                            LOGGER.debug("---------------------------------------");
                            listOfElement.add(hashMap);
                        }
                    }
                }
                if (tableElement.findElement(By.cssSelector(".left.next")).isEnabled()) {
                    tableElement.findElement(By.cssSelector(".left.next a")).click();
                }
            }
        } else {
            for (WebElement element : tableElement.findElements(By.xpath("//tbody/tr"))) {
                if (!element.getText().trim().equals("")) {
                    if (!element.getAttribute("class").contains("expandedView")) {
                        int i = 0;
                        HashMap hashMap = new HashMap();

//					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"list-table-holder\"]/table/tbody/tr/td")));
                        for (WebElement element1 : element.findElements(By.tagName("td"))) {
                            String header = tableElement.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                            hashMap.put(header, element1);
                            LOGGER.debug("Key:" + header + " Value:" + element1.getText());
                            i++;
                        }

                        LOGGER.debug("---------------------------------------");
                        listOfElement.add(hashMap);
                    }
                }
            }
        }

        this.listViewElements = listOfElement;
//        System.out.println(listViewElements.size());
        return listOfElement;
    }

    /**
     * @return get the whole table as HashMap
     */
    public int getNumberOfRows() {
        int nb = 0;
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"list-table-holder\"]/table/tbody/tr")));
        for (WebElement element : tableElement.findElements(By.xpath("//*[@id=\"list-table-holder\"]/table/tbody/tr"))) {
            if (!element.getText().trim().equals("")) {
                nb++;
            }
        }
        LOGGER.info("Total Number of Rows on the Page : " + nb);
        return nb;
    }

    public void deleteRow(int rowNumber) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"list-table-holder\"]/table/tbody/tr")));
        WebElement rowToDelete = tableElement.findElement(By.xpath("//*[@id=\"list-table-holder\"]/table/tbody/tr/td[" + rowNumber + "]/a"));

        if (rowToDelete != null) {
            rowToDelete.findElement(By.tagName("td[1]")).findElement(By.tagName("a")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"modal-dialog\"]/div/div[3]/a[1]")));
            VoyantaDriver.findElement(By.xpath("//*[@id=\"modal-dialog\"]/div/div[3]/a[1]")).click();
        }
    }

    public List<HashMap> getRowsAsHashIgnoreBlankHeaders() {
        List<HashMap> listOfElement = new LinkedList<HashMap>();

        for (WebElement element : tableElement.findElements(By.xpath("//tbody/tr"))) {
            if (!element.getAttribute("class").contains("expandedView")) {
                int colNum = 0;
                HashMap hashMap = new HashMap();

                for (WebElement element1 : element.findElements(By.tagName("td"))) {
//                    tableId = "sharing-table";
                    String xpath = "//table/thead/tr/th";
                    VUtils.waitForElement(VoyantaDriver.getCurrentDriver().findElement(By.xpath(xpath)));
                    VUtils.waitFor(5);
                    String header = VoyantaDriver.getCurrentDriver().findElements(By.xpath(xpath)).get(colNum).getText().trim();
                    System.out.println("HEADER " + header);

                    if (!header.trim().equals("")) {
                        hashMap.put(header, element1);
                        LOGGER.info("Key:" + header + " Value:" + element1.getText());
                    }

                    colNum++;
                }

                LOGGER.info("---------------------------------------");
                listOfElement.add(hashMap);
            }
        }

        this.listViewElements = listOfElement;
        return listOfElement;
    }

    /**
     * @param rowNumber： how many rows in the table you want to use for building the hash map list
     * @return: listViewElements
     */
    public List<HashMap> getRowsInHashBasedOnIndex(int rowNumber) {
        List<HashMap> listOfElement = new LinkedList();
//		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"list-table-holder\"]//tbody/tr")));
        List<WebElement> rowList = tableElement.findElements(By.xpath("//tbody/tr"));
        List<String> headerList = getHeaderList();

        if (rowNumber == 0 || rowNumber > rowList.size()) {
            rowNumber = rowList.size();
        }

        for (int j = 0; j < rowNumber; j++) {
            WebElement element = rowList.get(j);

            if (!element.getAttribute("class").contains("expandedView")) {
                int i = 0;
                HashMap hashMap = new HashMap();

                for (WebElement element1 : element.findElements(By.tagName("td"))) {
                    String header = headerList.get(i).trim();
                    hashMap.put(header, element1);
                    LOGGER.debug("Key:" + header + " Value:" + element1.getText());
                    i++;
                }

                LOGGER.debug("---------------------------------------");
                listOfElement.add(hashMap);
            }
        }

        this.listViewElements = listOfElement;
        rowCount = listViewElements.size();
        System.out.println("there are in total " + rowCount + " lines");
        return listViewElements;
    }

    public List<String> getHeaderList() {
        List<String> headerList = new ArrayList();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//thead/tr/th")));
        for (WebElement element : tableElement.findElements(By.xpath("//thead/tr/th"))) {
            if (element.getText().equals("")) {
                List<WebElement> childElements = element.findElements(By.xpath(".//*"));
                if (VUtils.checkTagInChild(childElements, "div")) {
                    wait.until(ExpectedConditions.visibilityOf(element.findElement(By.tagName("div"))));
                    if (element.findElement(By.tagName("div")).getAttribute("class").equals("sentinel")) {
                        headerList.add("sentinel");
                    } else if (element.findElement(By.tagName("div")).getAttribute("class").equals("updated")) {
                        headerList.add("updated");
                    }
                } else if (VUtils.checkTagInChild(childElements, "a") && !VUtils.checkTagInChild(childElements, "div")) {
                    if (element.findElement(By.tagName("a")).getAttribute("title").equals("Select all")) {
                        headerList.add("selected");
                    }
                }
            } else {
                headerList.add(element.getText());
            }
        }

        return headerList;
    }

    public String getHeader(String header) {
        try {
            for (String s : getHeaderList()) {
                if (s.equalsIgnoreCase(header))
                    return s;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Header Not Found");
        }
        return null;
    }

    public WebElement getElementInColumn() {
        int i = getRowNumber("Name", "CBR-Error-001");
        return getCellElementWithRow(i + 1, "Actions");
    }

    /**
     * @param column:       the column name where user already know the value
     * @param text:         the known value of the column which user uses as reference to find the value of another column
     * @param returnColumn: the column name whose value user wants to find
     * @return: the WebElement of the column user wants to find
     */
    public WebElement getRowElementFromText(String column, String text, String returnColumn) {
        int i = getRowNumber(column, text);
        return getCellElementWithRow(i + 1, returnColumn);
    }

    /**
     * @param i:      row number
     * @param header: header(column) name of the table
     * @return: the value for the column in row i
     */
    public WebElement getCellElementWithRow(int i, String header) {
        i = i - 1;

        try {
            if (listViewElements == null) {
                this.getRowsInHash();
            }
            if (listViewElements.get(i).containsKey(header)) {
                WebElement element = (WebElement) listViewElements.get(i).get(header);
                if (ExpectedConditions.stalenessOf(element).apply(VoyantaDriver.getCurrentDriver())) {
                    this.getRowsInHash();
                    element = (WebElement) listViewElements.get(i).get(header);
                }
                wait.until(ExpectedConditions.visibilityOf(element));
                return element;
            } else {
                throw new RuntimeException("The given header is not valid: " + header);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Row number not valid :" + i);
        }
    }

    /**
     * @param column: Name of the column
     * @param value:  The value of the column
     * @return : the row number where the column has the value
     */
    public int getRowNumber(String column, String value) {
        listViewElements = getListViewElements();
        int i = 0;
        if (tableElement.findElements(By.cssSelector(".pages div a span")).size() > 1) {
            for (HashMap map : listViewElements) {
                String stringValue = (map.get(column)).toString().trim();

                if (stringValue.equalsIgnoreCase(value)) {
                    LOGGER.info("Found record with text :" + stringValue);
                    return i;
                }

                i++;
            }
        } else {
            for (HashMap map : listViewElements) {
                String stringValue = ((WebElement) map.get(column)).getText().trim();

                if (stringValue.equalsIgnoreCase(value)) {
                    LOGGER.info("Found record with text :" + ((WebElement) map.get(column)).getText());
                    return i;
                }

                i++;
            }
        }

        VerifyUtils.fail("Given value :" + value + " not found in column :" + column);
        return i;
    }

    /**
     * @param columnName: Name of the column
     * @return : the list of value for the given column on that pagethe row number where the column has the value
     */
    public List<String> getColumnValueAsList(String columnName) {
        List<String> listValue = new LinkedList<String>();

        for (HashMap map : listViewElements) {
            listValue.add(((WebElement) map.get(columnName)).getText());
        }

        return listValue;
    }

    protected String checkFileExist(String fileName, String folder) {
        LOGGER.info("Searching for the file :" + fileName + " in folder :" + folder);
        File excelFolder = FileSearch.findFile(fileName, new File(folder));

        if (excelFolder == null) {
            throw new RuntimeException("file " + fileName + "doesn't exist in folder " + folder);
        } else {
            return excelFolder.getAbsolutePath();
        }
    }

    protected void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

}
