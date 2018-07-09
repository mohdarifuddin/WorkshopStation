package voyanta.ui.pageobjects;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import voyanta.ui.pagecontainers.GmailPageContainer;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;
import voyanta.ui.webdriver.core.elements.impl.internal.ElementFactory;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GmailPage {
    WebDriverWait wait = new WebDriverWait(VoyantaDriver.getCurrentDriver(), 10L);
    static Logger LOGGER = Logger.getLogger(ListOfValuePage.class);
    WebDriver driver = VoyantaDriver.getCurrentDriver();
    private GmailPageContainer pageContainer = GmailPage.getDataContainer(GmailPageContainer.class);
    private boolean loggedIn;

    public GmailPage() {
    }

    public static <T> T getDataContainer(Class<T> className) {
        return ElementFactory.initElements(VoyantaDriver.getCurrentDriver(), className);
    }

    public void login(String userName, String passWord) {
        if (loggedIn) {
            LOGGER.info("Logging out of Gmail first");
            logout();
        }

        LOGGER.info("Logging into Gmail with UserName and Password : " + userName + ", " + passWord);
        pageContainer.inputEmail.sendKeys(userName);
        pageContainer.buttonNext.click();
        pageContainer.inputPassword.sendKeys(passWord);

        if (pageContainer.checkBoxStaySignedIn.isEnabled()) {//TODO doesn't work
            pageContainer.checkBoxStaySignedIn.click();
        }

        pageContainer.buttonSignIn.click();
        VUtils.waitFor(5);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//body"), "Loading, please wait ..."));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOGGER.error("sleep error");
        }

        Assert.assertTrue("Login unsuccessful. Please check the login details!", pageContainer.gmailName.getText().contains("Gmail"));
        LOGGER.info("Log in success into Gmail!");
        loggedIn = true;
    }

    public WebElement findNotificationEmail(String subject) {
        boolean found = false;
        WebElement foundEmail = null;
        int attempts = 0;
        while (!found && attempts < 10) {
            VUtils.waitFor(20);
            for (WebElement email : pageContainer.emailList.findElements(By.tagName("tr"))) {
                if (email.getAttribute("class").equals("zA zE")) {//unread email
                    for (WebElement column : email.findElements(By.tagName("td"))) {
                        if (column.getText().contains(subject)) {
                            found = true;
                            foundEmail = email;
                            break;
                        }
                    }
                }
            }
            attempts++;
        }
        VerifyUtils.True(found);
        return foundEmail;
    }

    public void openVoyantaLink() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageContainer.showMoreImage));
            pageContainer.showMoreImage.click();
        } catch (NoSuchElementException e) {
            LOGGER.info("No need to click on 'more info'");
        }
        //fake the click to keep driver control of the page
        String linkUrl = pageContainer.voyantaLink.getAttribute("href");
        driver.get(linkUrl);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void logout() {
        pageContainer.usernameTopRight.click();
        pageContainer.buttonSignOut.click();
        loggedIn = false;
    }

    private static final String APPLICATION_NAME =
            "Gmail API Java Quickstart";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/gmail-java-quickstart.json");

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     * <p>
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart.json
     */
    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.GMAIL_MODIFY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    // delete file
    public static void removeCredentialsFile() {
        Path path = Paths.get("C://Users//Lewis.Prescott//.credentials//gmail-java-quickstart.json//StoredCredential");
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(String secretJson) throws IOException {
        // Load client secrets.
        //removeCredentialsFile();
        InputStream in =
                GmailPage.class.getResourceAsStream("/client_secret_" + secretJson + ".json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     *
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService(String secretJson) throws IOException {
        Credential credential = authorize(secretJson);
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static List<String> messageSnippetFinal;
    public static String messageBodyFinal;

    public static void setGmailMessage(String userId, String query) throws IOException {
        messageSnippetFinal = getMessageSnippet(userId, query);
    }

    public static void setGmailBody(String userId, String query) throws IOException, MessagingException {
        messageBodyFinal = getBodySnippet(userId, query);
    }

    public static boolean getGmailMessage(String snippet) {
        boolean bool = false;
        for (int i = 0; i < messageSnippetFinal.size(); i++) {
            if (messageSnippetFinal.get(i).contains(snippet)) {
                bool = true;
            } else {
                bool = false;
            }
        }
        return bool;
    }

    public static boolean connectGmailAPI(String userId, String query)
            throws IOException {
        String start = StringUtils.substringBefore(userId, "@");
        System.out.println(start);
        Gmail service = getGmailService(start);
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();
        if (response.getMessages().size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static List<String> getMessageSnippet(String userId, String query)
            throws IOException {
        String start = StringUtils.substringBefore(userId, "@");
        Gmail service = getGmailService(start);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/M/dd");
        Date date = new Date();
        String todaysDate = dateFormat.format(date);
        query = query + " after:" + todaysDate;
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();
        List<String> MessageSnippetList = new ArrayList();
        if (response.getMessages().size() != 0) {
            for (int i = 0; i < response.getMessages().size(); i++) {
                String emailId = response.getMessages().get(i).getId();
                Message message = service.users().messages().get(userId, emailId).execute();
                //System.out.println("Snippet of Message: " + message.getSnippet());
                String messageSnippet = message.getSnippet();
                MessageSnippetList.add(messageSnippet);
                return MessageSnippetList;
            }
        } else {
            MessageSnippetList = null;
        }
        return MessageSnippetList;
    }

    public static String getBodySnippet(String userId, String query)
            throws IOException, MessagingException {
        String start = StringUtils.substringBefore(userId, "@");
        Gmail service = getGmailService(start);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/M/dd");
        Date date = new Date();
        String todaysDate = dateFormat.format(date);
        query = query + " after:" + todaysDate;
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();
        String messageBody = null;
        if (response.getMessages().size() != 0) {
            for (int i = 0; i < response.getMessages().size(); i++) {
                String emailId = response.getMessages().get(i).getId();
                Message message = service.users().messages().get(userId, emailId).setFormat("raw").execute();
                byte[] emailBytes = Base64.decodeBase64(message.getRaw());
                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props, null);

                MimeMessage email = new MimeMessage(session, new ByteArrayInputStream(emailBytes));
                System.out.println(email.getSubject());
                System.out.println(email.getDataHandler());
                return email.toString();

                //System.out.println(messageBody.contains("https://test.voyanta.com/tasks/task-manager/appraisal/update/57171"));
            }
        } else {
            messageBody = null;
        }
        return messageBody;
    }

    public static String Email = null;
    public static String Query = null;

    public static void setGmailEmail(String gmailEmail) {
        Email = gmailEmail;
    }

    public String getGmailEmail() {
        return Email;
    }

    public static void setGmailQuery(String gmailQuery) {
        Query = gmailQuery;
    }

    public String getGmailQuery() {
        return Query;
    }

    public boolean getGmailBody(String bodyText) {
        boolean bool;
        if (messageBodyFinal.contains(bodyText)) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }
}
