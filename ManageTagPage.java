package voyanta.ui.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.ManageTagPageContainer;
import voyanta.ui.utils.*;

import java.util.*;

/**
 * Created by Hiten.Parma on 12/11/2014.
 */
public class ManageTagPage extends AbstractPageWithList {

    private static final String url = PropertiesLoader.getProperty("ui_url") + "administration/miscellaneous/tags";
    public static WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 30);
    static Logger LOGGER = Logger.getLogger(ManageTagPage.class);

    public WebDriver mDriver = VoyantaDriver.getCurrentDriver();
    public String pageName = "Manage Tags";
    public WebElement tableElement;

    public List<HashMap> listViewElements;

    public String tag;

    public String parentWindow = mDriver.getWindowHandle();
    public String subWindow = null;
    public Set<String> handles = mDriver.getWindowHandles();
    public Iterator<String> iterator = handles.iterator();
    ManageTagPageContainer manageTagPageContainer = ManageTagPage.getDataContainer(ManageTagPageContainer.class);

    public ManageTagPage() {
        super.tableElement = manageTagPageContainer.tableElement;
    }

    public static ManageTagPage openPage() {

        VoyantaDriver.getCurrentDriver().get(url);
        LOGGER.info("**** Arriving on Manage Tag Page ****");
        /* Waiting for the Page to get load  */
        wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//*[@id='tags-table']/table/tfoot/tr/td/div/div[4]/div/a/span")), "1"));
        return new ManageTagPage();
    }

    public void selectAddNew() {
        manageTagPageContainer.addNewTagLink.click();
        VUtils.waitFor(1);
    }


    public void enterTagName(String tag) {

        this.tag = tag;
        tagName(tag);
    }

    private WebDriver tagName(String tag) {
        try {
            while (iterator.hasNext()) {
                subWindow = iterator.next();
            }
            mDriver.switchTo().window(subWindow);
            LOGGER.info("Adding New Tag : " + tag);
            mDriver.findElement(By.id("tag-name")).clear();
            mDriver.findElement(By.id("tag-name")).sendKeys(tag);
            VUtils.waitFor(1);
        } catch (NoAlertPresentException n) {
            LOGGER.info("Popup is not present....");

        }
        return mDriver;
    }

    public void checkTagCreated(String tagName) {
        WaitUtils.loaderWait();
        WaitUtils.waitFor(2);
        getRowsInHash();
        int rowNumber = getRowNumber("Tag Name", tagName);
        VerifyUtils.True(rowNumber >= 0);
    }


    public List<HashMap> getRowsFromTagsPage() {
        List<HashMap> listOfElement = new LinkedList<HashMap>();

        int rowNr = 0;
        tableElement = VoyantaDriver.findElement(By.id("tags-table"));
        for (WebElement element : tableElement.findElements(By.className("table-row"))) {
            if (element.getAttribute("class").contains("expandedView")) {
                continue;
            } else {
                int colNum = 0;

                HashMap hashMap = new HashMap();
                for (WebElement element1 : element.findElements(By.tagName("td"))) {

                    String header = VoyantaDriver.getCurrentDriver().findElements(By.xpath("//thead/tr/th")).get(colNum).getText().trim();
                    LOGGER.info("HEADER : " + header);
                    if (!header.trim().equals("")) {
                        hashMap.put(header, element1);
                        //  System.out.println("this is the row" + rowNr+ " value "+ element1.getText() + " for header " + header);
                        LOGGER.info("Key:" + element.findElements(By.xpath("//thead/tr/th")).get(colNum).getText().trim() + " Value:" + element1.getText());
//                        LOGGER.info("Value : "+element1.getText());

                    }
                    colNum++;
                }
                LOGGER.info("---------------------------------------");
                listOfElement.add(hashMap);
                rowNr++;
            }
        }
        this.listViewElements = listOfElement;
        System.out.println("-----" + listViewElements.size());
        return listViewElements;
    }


    public void checkTagPresent(String tagName, boolean b) {
        VerifyUtils.equals(b, isTagPresentInList(tagName));

    }

    public boolean isTagPresentInList(String tagName) {
        mDriver.navigate().refresh();
        getRowsInHash();
        int rowNumber = getRowFromTagList("Tag Name", tagName);
//            LOGGER.info("Found the Tag in row number "+rowNumber);
//            VerifyUtils.True(rowNumber>=0);
        if (rowNumber >= 0)
            return true;
        else
            return false;
    }

    public int getRowFromTagList(String column, String value) {
        listViewElements = getListViewElements();
        int i = 0;
        tableElement = VoyantaDriver.findElement(By.id("tags-table"));
        if (tableElement.findElements(By.cssSelector(".pages div a span")).size() > 1) {
            for (int page = 1; page <= tableElement.findElements(By.cssSelector(".pages div a span")).size(); page++) {
                for (HashMap map : listViewElements) {
                    String stringValue = (map.get(column)).toString().trim();

                    if (stringValue.equalsIgnoreCase(value)) {
                        LOGGER.info("Found Tag with text :" + stringValue);
                        return i;
                    }

                    i++;
                }
                if (tableElement.findElement(By.cssSelector(".left.next")).isEnabled()) {
                    tableElement.findElement(By.cssSelector(".left.next a")).click();
                }
            }
        } else {
            for (HashMap map : listViewElements) {
                String stringValue = ((WebElement) map.get(column)).getText().trim();
                if (stringValue.equalsIgnoreCase(value)) {
                    LOGGER.info("Found Tag with text :" + map.toString());
                    return i;
                }
                i++;
            }
        }
//        return i;
//        VerifyUtils.fail("Given value :" + value + " not found in column :" + column);
        LOGGER.info("Tag " + tag + " is Not Present in List of Tags :" + i);
        return -1;
    }

    public void deleteTag(String column, String name, String returnColumn) {
        mDriver.navigate().refresh();
        tableElement = VoyantaDriver.findElement(By.id("tags-table"));
        if (tableElement.findElements(By.cssSelector(".pages div a span")).size() > 1) {
            for (int page = 1; page <= tableElement.findElements(By.cssSelector(".pages div a span")).size(); page++) {
                try {
                    if (mDriver.findElements(By.xpath("//tbody/tr/td/span[text()='" + name + "']/ancestor::tr/td[@class='actions']/div")).isEmpty()) {
                        if (tableElement.findElement(By.cssSelector(".left.next")).isEnabled()) {
                            tableElement.findElement(By.cssSelector(".left.next a")).click();
                        }
                    } else {
                        for (WebElement e : mDriver.findElements(By.xpath("//tbody/tr/td/span[text()='" + name + "']/ancestor::tr/td[@class='actions']/div/a"))) {
                            if (e.getAttribute("title").equals("Delete")) {
                                e.click();
                                VUtils.waitFor(2);
                                while (iterator.hasNext()) {
                                    subWindow = iterator.next();
                                }
                                mDriver.switchTo().window(subWindow);
                                LOGGER.info("Deleting Tag: " + name);
                                return;
                            }
                        }
                    }
                } catch (NoSuchElementException e) {
                    LOGGER.info("Tag is not Available to Delete");
                }
            }
        } else {
            try {
                getRowsInHash();
                WebElement element = getRowElementFromText(column, name, returnColumn);
                LOGGER.info("Tag Name: " + name + " Available Actions : " + element.getText());
                List<WebElement> element1 = element.findElements(By.tagName("a"));
                for (WebElement e : element1) {
                    if (e.getAttribute("title").equals("Delete")) {
                        e.click();
                        VUtils.waitFor(2);
                        while (iterator.hasNext()) {
                            subWindow = iterator.next();
                        }
                        mDriver.switchTo().window(subWindow);
                        LOGGER.info("Deleting Tag: " + name);
//                       mDriver.findElement(By.id("dialog-button-yes")).click();
//                       LOGGER.info(name + " is Deleted.");
//                       VUtils.waitFor(2);
//                       mDriver.switchTo().window(parentWindow);
                        return;
                    }
                }
            } catch (NoSuchElementException e) {
                LOGGER.info("Tag is not Available to Delete");
            }
        }
    }

    public void selectYes() {
        LOGGER.info("Selecting Yes ");
        mDriver.findElement(By.id("dialog-button-yes")).click();
        VUtils.waitFor(2);
        mDriver.switchTo().window(parentWindow);
    }

    public void selectNo() {
        LOGGER.info("Selecting No");
        mDriver.findElement(By.cssSelector("input[type='button']")).click();
        VUtils.waitFor(2);
        mDriver.switchTo().window(parentWindow);
    }

    public void selectRename(String tagName) {
        mDriver.navigate().refresh();
        tableElement = VoyantaDriver.findElement(By.id("tags-table"));
        if (tableElement.findElements(By.cssSelector(".pages div a span")).size() > 1) {
            for (int page = 1; page <= tableElement.findElements(By.cssSelector(".pages div a span")).size(); page++) {
                try {
                    if (mDriver.findElements(By.xpath("//tbody/tr/td/span[text()='" + tagName + "']/ancestor::tr/td[@class='actions']/div")).isEmpty()) {
                        if (tableElement.findElement(By.cssSelector(".left.next")).isEnabled()) {
                            tableElement.findElement(By.cssSelector(".left.next a")).click();
                        }
                    } else {
                        LOGGER.info("**** Select Rename From Actions ****");
                        for (WebElement e : mDriver.findElements(By.xpath("//tbody/tr/td/span[text()='" + tagName + "']/ancestor::tr/td[@class='actions']/div/a"))) {
                            if (e.getAttribute("title").equals("Rename")) {
                                e.click();
                                VUtils.waitFor(2);
                                while (iterator.hasNext()) {
                                    subWindow = iterator.next();
                                }
                                mDriver.switchTo().window(subWindow);
                                LOGGER.info("Renaming Tag: " + tagName);
                                return;
                            }
                        }
                    }
                } catch (NoSuchElementException e) {
                    LOGGER.info("Tag is not Available to Rename");
                }
            }
        } else {
            getRowsInHash();
            WebElement element = getRowElementFromText("Tag Name", tagName, "Actions");
            LOGGER.info("Tag Name: " + tagName + " Available Actions : " + element.getText());
            List<WebElement> element1 = element.findElements(By.tagName("a"));
            LOGGER.info("**** Select Rename From Actions ****");
            for (WebElement e : element1) {
                if (e.getAttribute("title").equals("Rename")) {
                    e.click();
                    VUtils.waitFor(2);
                }
            }
        }
    }

    public void reNameTag(String reName) {
        this.tag = reName;
        while (iterator.hasNext()) {
            subWindow = iterator.next();
        }
        mDriver.switchTo().window(subWindow);
        WebElement rename = mDriver.findElement(By.xpath("//input[@name= 'name']"));
        rename.clear();
        rename.sendKeys(reName);
        VUtils.waitFor(2);
    }

    public void publishTag(String column, String name, String returnColumn) {
        mDriver.navigate().refresh();
        tableElement = VoyantaDriver.findElement(By.id("tags-table"));
        if (tableElement.findElements(By.cssSelector(".pages div a span")).size() > 1) {
            for (int page = 1; page <= tableElement.findElements(By.cssSelector(".pages div a span")).size(); page++) {
                try {
                    if (mDriver.findElements(By.xpath("//tbody/tr/td/span[text()='" + name + "']/ancestor::tr/td[@class='actions']/div")).isEmpty()) {
                        if (tableElement.findElement(By.cssSelector(".left.next")).isEnabled()) {
                            tableElement.findElement(By.cssSelector(".left.next a")).click();
                        }
                    } else {
                        LOGGER.info("****** Making '" + name + "' tag as Public ******");
                        for (WebElement e : mDriver.findElements(By.xpath("//tbody/tr/td/span[text()='" + name + "']/ancestor::tr/td[@class='actions']/div[@class='publish-buttons']/div/a"))) {
                            if (e.getAttribute("title").equals("Publish")) {
                                e.click();
                                VUtils.waitFor(5);
                                while (iterator.hasNext()) {
                                    subWindow = iterator.next();
                                }
                                mDriver.switchTo().window(subWindow);
                                LOGGER.info("Publishing Tag: " + name);
                                return;
                            }
                        }
                    }
                } catch (NoSuchElementException e) {
                    LOGGER.info("Tag " + name + " is not Available to Publish");
                }
            }
        } else {
            getRowsInHash();
            WebElement element = getRowElementFromText(column, name, returnColumn);
            LOGGER.info("Tag Name: " + name + " Available Actions : " + element.getText());
            List<WebElement> element1 = element.findElements(By.tagName("a"));
            LOGGER.info("****** Making '" + name + "' tag as Public ******");
            for (WebElement e : element1) {
                if (e.getAttribute("title").equals("Publish")) {
                    e.click();
                    VUtils.waitFor(5);
                }
            }
        }
    }

    public void unpublishTag(String column, String name, String returnColumn) {
        tableElement = VoyantaDriver.findElement(By.id("tags-table"));
        if (tableElement.findElements(By.cssSelector(".pages div a span")).size() > 1) {
            mDriver.navigate().refresh();
            for (int page = 1; page <= tableElement.findElements(By.cssSelector(".pages div a span")).size(); page++) {
                try {
                    if (mDriver.findElements(By.xpath("//tbody/tr/td/span[text()='" + name + "']/ancestor::tr/td[@class='actions']/div")).isEmpty()) {
                        if (tableElement.findElement(By.cssSelector(".left.next")).isEnabled()) {
                            tableElement.findElement(By.cssSelector(".left.next a")).click();
                        }
                    } else {
                        LOGGER.info("****** Making '" + name + "' as Private ******");
                        for (WebElement e : mDriver.findElements(By.xpath("//tbody/tr/td/span[text()='" + name + "']/ancestor::tr/td[@class='actions']/div[@class='publish-buttons']/div/a"))) {
                            if (e.getAttribute("title").equals("Unpublish")) {
                                e.click();
                                VUtils.waitFor(5);
                                while (iterator.hasNext()) {
                                    subWindow = iterator.next();
                                }
                                mDriver.switchTo().window(subWindow);
                                LOGGER.info("Unpublishing Tag: " + name);
                                return;
                            }
                        }
                    }
                } catch (NoSuchElementException e) {
                    LOGGER.info("Tag is not Available to Unpublish");
                }
            }
        } else {
            getRowsInHash();
            WebElement element = getRowElementFromText(column, name, returnColumn);
            LOGGER.info("Tag Name: " + name + " Available Actions : " + element.getText());
            List<WebElement> element1 = element.findElements(By.tagName("a"));
            LOGGER.info("****** Making '" + name + "' tag as Public ******");
            for (WebElement e : element1) {
                if (e.getAttribute("title").equals("Unpublish")) {
                    e.click();
                    VUtils.waitFor(5);
                }
            }
        }
    }

    public void canSeeAction(String tagName, String action) {
        String[] actionList = action.split(",");
        int size = actionList.length;
        mDriver = VoyantaDriver.getCurrentDriver();
        getRowsInHash();
        listViewElements = getListViewElements();
        tableElement = VoyantaDriver.findElement(By.id("tags-table"));
        if (tableElement.findElements(By.cssSelector(".pages div a span")).size() > 1) {
            //mDriver.navigate().refresh();
            //for (int page = 1; page <= tableElement.findElements(By.cssSelector(".pages div a span")).size(); page++) {
            try {
                Optional<String> value = Optional.of(listViewElements.stream().filter(hashMap -> hashMap.get("Tag Name").toString().trim().equalsIgnoreCase(tagName)).findFirst().get().get("Actions").toString());
                LOGGER.info("Tag Name: " + tagName + " Available Actions : " + value.get());
                for (int i = 0; i < size; i++) {
                    if (value.get().contains(actionList[i])) {
                        LOGGER.info(actionList[i] + " is Present in Actions");
                    } else {
                        VerifyUtils.fail(actionList[i] + " is Not Present in Actions");
                    }
                }
            } catch (java.util.NoSuchElementException e) {
                LOGGER.info("Tag is not Present...");
            }
//                if (tableElement.findElement(By.cssSelector(".left.next")).isEnabled()) {
//                    tableElement.findElement(By.cssSelector(".left.next a")).click();
//                }
            //}
        } else {
            try {
                getRowsInHash();
                WebElement element = getRowElementFromText("Tag Name", tagName, "Actions");
                LOGGER.info("Tag Name: " + tagName + " Available Actions : " + element.getText());
                for (int i = 0; i < size; i++) {
                    if (element.getText().contains(actionList[i])) {
                        LOGGER.info(actionList[i] + " is Present in Actions");
                    } else {
                        VerifyUtils.fail(actionList[i] + " is Not Present in Actions");
                    }
                }
            } catch (java.util.NoSuchElementException e) {
                LOGGER.info("Tag is not Present...");
            }
        }
    }

    public void errorOnPopUp(String errorMsg) {
        WebElement error = mDriver.findElement(By.cssSelector("ul.globalError"));
        VerifyUtils.equals(errorMsg, error.getText());
    }

    public void selectSaveTag() {
        LOGGER.info("**** Selecting Save ****");
        if (VUtils.isElementPresent(By.id("dialog-button-send"))) {
            mDriver.findElement(By.id("dialog-button-send")).click();
            VUtils.waitFor(2);
        } else {
            mDriver.findElement(By.xpath("(//input[@id=''])[3]")).click();
            VUtils.waitFor(2);
        }
//        mDriver.findElement(By.id("dialog-button-send")).click();
//        VUtils.waitFor(2);
        if (VUtils.isElementPresent(By.cssSelector("ul.globalError"))) {
//                throw new RuntimeException("A tag with this name already exists.");
            LOGGER.info("Tag is not Created");
        } else
            LOGGER.info("New tag is Created : " + tag);

        mDriver.switchTo().window(parentWindow);
        VerifyUtils.contains("Manage Tags", pageName);
    }


    public void selectCancelTag() {
        LOGGER.info("**** Selecting Cancel ****");
        if (VUtils.isElementPresent(By.cssSelector("ul.globalError + form input[value=\"Cancel\"]"))) {
            mDriver.findElement(By.cssSelector("ul.globalError + form input[value=\"Cancel\"]")).click();
            VUtils.waitFor(2);
        }

        mDriver.switchTo().window(parentWindow);
        VerifyUtils.contains("Manage Tags", pageName);
    }

    public void usesFieldOfTag(String tagName, String use) {
        getRowsInHash();
        listViewElements = getListViewElements();
//        final WebElement element = getRowElementFromText("Tag Name", tagName, "Uses");
        final WebDriverWait textWait = new WebDriverWait(mDriver, 120);
        final String useText = use;
        final String name = tagName;
        tableElement = VoyantaDriver.findElement(By.id("tags-table"));
        if (tableElement.findElements(By.cssSelector(".pages div a span")).size() > 1) {
//            mDriver.navigate().refresh();
            // for (int page = 1; page <= tableElement.findElements(By.cssSelector(".pages div a span")).size(); page++) {
            textWait.until(new ExpectedCondition<Boolean>() {
                Optional<String> value = Optional.of(listViewElements.stream().filter(hashMap -> hashMap.get("Tag Name").toString().trim().equalsIgnoreCase(name)).findFirst().get().get("Uses").toString());
                int i = 0;

                @Override
                public Boolean apply(WebDriver webDriver) {
                    // useText.charAt(0) returns the ASCII value
                    if (Character.isDigit(useText.charAt(0)) && useText.charAt(0) >= 49 && useText.matches("[1-9]+ objects? tagged")) {
                        int maxAttempts = 300;
                        for (int i = 0; i < maxAttempts; i++) {
                            try {
                                if (i == maxAttempts)
                                    break;
                                mDriver.navigate().refresh();
                                WaitUtils.loaderWait();
                                getRowsInHash();
                                value = Optional.of(listViewElements.stream().filter(hashMap -> hashMap.get("Tag Name").toString().trim().equalsIgnoreCase(name)).findFirst().get().get("Uses").toString());
                                if (value.get().equals(useText))
                                    break;
                            } catch (AssertionError e) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                                i++;
                                LOGGER.info("Tagging not yet done: " + e + " and Waiting for a second");
                            }
                        }
//                        try {
//                            TimeUnit.MINUTES.sleep(5);
//                            mDriver.navigate().refresh();
//                            WaitUtils.loaderWait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } finally {
//                            getRowsInHash();
//                            value = Optional.of(listViewElements.stream().filter(hashMap -> hashMap.get("Tag Name").toString().trim().equalsIgnoreCase(name)).findFirst().get().get("Uses").toString());
//                        }
                    }
                    if (value.get().equals(useText)) {
                        LOGGER.info("Given " + name + " tag has Uses as " + value.get());
                        return true;
                    } else {
                        i++;
                        LOGGER.info(".....Waiting for Uses Text To Present ");
                        if (i == 200) {
                            mDriver.navigate().refresh();
//                        VUtils.waitFor(3);
                            wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//*[@id='tags-table']/table/tfoot/tr/td/div/div[4]/div/a/span")), "1"));
                            getRowsInHash();
                            value = Optional.of(listViewElements.stream().filter(hashMap -> hashMap.get("Tag Name").toString().trim().equalsIgnoreCase(name)).findFirst().get().get("Uses").toString());
                            LOGGER.info("The USES ARE : " + value.get());
                        }
                    }
                    return false;
                }
            });
//                if (tableElement.findElement(By.cssSelector(".left.next")).isEnabled()) {
//                    tableElement.findElement(By.cssSelector(".left.next a")).click();
//                }
            // }
        } else {
            textWait.until(new ExpectedCondition<Boolean>() {
                WebElement element = getRowElementFromText("Tag Name", name, "Uses");
                int i = 0;

                @Override
                public Boolean apply(WebDriver webDriver) {
                    // useText.charAt(0) returns the ASCII value
                    if (Character.isDigit(useText.charAt(0)) && useText.charAt(0) >= 49 && useText.matches("[1-9]+ objects? tagged")) {
                        int maxAttempts = 300;
                        for (int i = 0; i < maxAttempts; i++) {
                            try {
                                if (i == maxAttempts)
                                    break;
                                mDriver.navigate().refresh();
                                WaitUtils.loaderWait();
                                getRowsInHash();
                                element = getRowElementFromText("Tag Name", name, "Uses");
                                if (element.getText().equals(useText))
                                    break;
                            } catch (AssertionError e) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                                i++;
                                LOGGER.info("Tagging not yet done: " + e + " and Waiting for a second");
                            }
                        }
//                        try {
//                            TimeUnit.MINUTES.sleep(5);
//                            mDriver.navigate().refresh();
//                            WaitUtils.loaderWait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } finally {
//                            getRowsInHash();
//                            element = getRowElementFromText("Tag Name", name, "Uses");
//                        }
                    }
                    if (element.getText().equals(useText)) {
                        LOGGER.info("Given " + name + " tag has Uses as " + element.getText());
                        return true;
                    } else {
                        i++;
                        LOGGER.info(".....Waiting for Uses Text To Present ");
                        if (i == 200) {
                            mDriver.navigate().refresh();
//                        VUtils.waitFor(3);
                            wait.until(ExpectedConditions.textToBePresentInElement(VoyantaDriver.findElement(By.xpath("//*[@id='tags-table']/table/tfoot/tr/td/div/div[4]/div/a/span")), "1"));
                            getRowsInHash();
                            element = getRowElementFromText("Tag Name", name, "Uses");
                            LOGGER.info("The USES ARE : " + element.getText());
                        }
                    }
                    return false;
                }
            });
        }
    }

    public void downloadDSTs() {
        pageContainer.linkDownloadDST.click();
        VUtils.waitFor(3);
    }

    public void canSelectTag(boolean b, String result) {
        if (result.equalsIgnoreCase("can")) {
            LOGGER.info("Goes to Tag Page");
            manageTagPageContainer.tagLink.click();
            VUtils.waitFor(2);
        } else {
            LOGGER.info("Checking Tag Link is Present : " + b);
            VerifyUtils.equals(b, isTagLinkPresent());
        }
    }

    public boolean isTagLinkPresent() {
        try {
            return manageTagPageContainer.tagLink.isDisplayed();
        } catch (NoSuchElementException e) {
            LOGGER.info("The User have No Access To Tag");
        }
        return false;
    }

    public void isAddNewTagLinkPresent(boolean b) {
        LOGGER.info("Verifying Add New Tag Link Present : " + b);
        VerifyUtils.equals(b, VUtils.isElementPresent(By.linkText("Add New Tag")));
    }
}
