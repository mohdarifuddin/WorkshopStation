package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import voyanta.ui.pagecontainers.DstTypeEnum;
import voyanta.ui.pagecontainers.ListDownloadDSTPageContainer;
import voyanta.ui.utils.ExcelSheetComaprism;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ting.liu on 08/10/2014.
 */
public class ListDownloadDSTPage extends AbstractPageWithList {

    Workbook objExcelWorkBook;
    static Sheet objExcelInputSheet;
    private static int sheetCol;
    private static int sheetRow;
    private static int column = 1;
    public List<HashMap> objHashMap = new LinkedList<HashMap>();
    static Logger LOGGER = Logger.getLogger(ListDownloadDSTPage.class);
    AbstractPageWithList AbstractPage;
    public WebDriver mDriver;
    ExcelSheetComaprism excelSheetComaprism;

    ListDownloadDSTPageContainer downloadPageContainer;


    public ListDownloadDSTPage() {

        downloadPageContainer = ListDownloadDSTPage.getDataContainer(ListDownloadDSTPageContainer.class);
        super.tableElement = downloadPageContainer.tableElement;
        getRowsInHashBasedOnIndex(0);
    }

    public List<String> getExpectedDSTList() {
        List<String> expectedDST = new LinkedList<String>();
        for (DstTypeEnum element : DstTypeEnum.values()) {
            expectedDST.add(element.getName());
        }
        return expectedDST;
    }

    public void downloadAll() {
        downloadPageContainer.checkBoxSelectAll.click();
    }

    public void downloadSelected(String[] DSTList) {

        for (int i = 0; i < DSTList.length; i++) {
            this.getRowElementFromText("Name", DSTList[i], "selected").findElement(By.tagName("input")).click();
        }

    }

    public void clickDownloadButton() {
        downloadPageContainer.dowloadSelectedLink.click();
        VUtils.waitFor(10);
        downloadPageContainer.table.click();

    }

    public List<HashMap> getDSTList() {
        List<HashMap> listOfElement = new LinkedList<HashMap>();
        mDriver = VoyantaDriver.getCurrentDriver();
        WebElement table = mDriver.findElement(By.id("list-table-holder"));
        int rowNr = 0;
        LOGGER.info("******* List from the Download DST Page *******");
        for (WebElement element : table.findElements(By.xpath("//tbody/tr"))) {
            if (!element.getText().trim().equals("")) {
                if (element.getAttribute("class").contains("expandedView")) {
                    continue;
                } else {
                    int i = 0;
                    HashMap hashMap = new HashMap();
                    for (WebElement element1 : element.findElements(By.tagName("td"))) {
                        String header = table.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim();
                        hashMap.put(header, element1);
                        //  System.out.println("this is the row" + rowNr+ " value "+ element1.getText() + " for header " + header);
                        LOGGER.info("Key:" + table.findElements(By.xpath("//thead/tr/th")).get(i).getText().trim() + " Value:" + element1.getText());
                        i++;
                    }
                    LOGGER.info("---------------------------------------");
                    listOfElement.add(hashMap);
                    rowNr++;
                }
            }
        }
        this.listViewElements = listOfElement;
        System.out.println(listViewElements.size());
        return listOfElement;
    }


    public List<String> getList(String column) {
        return getColumnValueAsList(column);
    }

    public List<String> getFileList(FileInputStream file) {
        List<String> dstList = new LinkedList<>();
        LOGGER.info("******** List From Stored ExcelSheet *********");
        try {
            Workbook w = WorkbookFactory.create(file);
            Sheet s = w.getSheetAt(0);
            Iterator<org.apache.poi.ss.usermodel.Row> row1 = s.rowIterator();
            int noOfRow = s.getPhysicalNumberOfRows();
            for (int r = 1; r < noOfRow; r++) {
                while (row1.hasNext()) {
                    org.apache.poi.ss.usermodel.Row r1 = row1.next();
                    Iterator<Cell> cells = r1.cellIterator();
                    while (cells.hasNext()) {
                        Cell c1 = cells.next();
                        LOGGER.info(c1);
                        dstList.add(String.valueOf(c1));
                    }
                }
            }

        } catch (IOException e) {
            LOGGER.info("Error :");
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return dstList;
    }

    public void checkEveryDST(File actualFile, String version) {
        Workbook actualBook = excelSheetComaprism.getWorkBookFromFile(actualFile);
        int noOfSheet = actualBook.getNumberOfSheets();
        CellReference cellNo = new CellReference("D1");
        LOGGER.info("****** Checking the Version of Downloaded DSTs ******");
        for (int i = 0; i < noOfSheet; i++) {
            Sheet actualSheet = actualBook.getSheetAt(i);
            org.apache.poi.ss.usermodel.Row r = actualSheet.getRow(cellNo.getRow());
            if (r != null) {
                Cell c = r.getCell(cellNo.getCol());
                if (c.getStringCellValue().equals(version)) {
                    LOGGER.info("Version :" + version + " in Downloaded DSTs :" + actualSheet.getSheetName());
                } else {
                    VerifyUtils.fail("The Version of the DSTs is change ");
                }
            }

        }
    }
}
