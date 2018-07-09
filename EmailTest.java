package voyanta.ui.pageobjects;

/**
 * Created by Hiten.Parma on 06/11/2014.
 */

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import voyanta.ui.utils.VUtils;
import voyanta.ui.utils.VerifyUtils;
import voyanta.ui.utils.VoyantaDriver;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailTest {
    /**
     * Searches for e-mail messages containing the specified keyword in
     * Subject field.
     *
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param keyword
     * @throws IOException
     */
    @SuppressWarnings("unused")
    private boolean textIsHtml = false;
    SignInPageObject signIn;
    Logger LOGGER = Logger.getLogger(EmailTest.class);
    public static Object emailContent;

    /**
     * Return the primary text content of the message.
     */
    private String getText(Part p) throws MessagingException, IOException {

        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            textIsHtml = p.isMimeType("text/html");


            System.out.println("Text Message --:" + p.getContent().toString().trim());
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null) {
                        System.out.println("Text Message :" + s);
                        return s;
                    }
                } else {
                    return getText(bp);
                }
            }
            System.out.println("Text Message :" + text);
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    System.out.println("Text Message :" + s);
                return s;
            }
        }
        System.out.println("Not Found the Message");
        return null;
    }

    public boolean searchEmail(String userName, String password, final String subjectKeyword, final String fromEmail, final String bodySearchText) throws IOException {
        Properties properties = new Properties();
        boolean val = false;
        int i;
        // server setting
        properties.put("mail.imap.host", "imap.gmail.com");
        properties.put("mail.imap.port", 993);
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port", String.valueOf(993));
        Session session = Session.getDefaultInstance(properties);
        try {
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(userName, password);
            signIn = new SignInPageObject();
            signIn.login_gmail(userName, password);
            VUtils.waitFor(5);
            LOGGER.info("Connected to Email server….");
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
            //create a search term for all “unseen” messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
            //create a search term for all recent messages
            Flags recent = new Flags(Flags.Flag.RECENT);
            FlagTerm recentFlagTerm = new FlagTerm(recent, false);
            SearchTerm searchTerm = new OrTerm(unseenFlagTerm, recentFlagTerm);
            // performs search through the folder
            Message[] foundMessages = folderInbox.search(searchTerm);
            LOGGER.info("Total Messages Found :" + foundMessages.length);
            for (i = foundMessages.length - 1; i >= foundMessages.length - 10; i--) {
                Message message = foundMessages[i];
                Address[] froms = message.getFrom();
                LOGGER.info("Message --- " + message.getSubject());
                String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();

                if (message.getSubject() == null) {
                    continue;
                }

                Date date = new Date();//Getting Present date from the system
                long diff = date.getTime() - message.getReceivedDate().getTime();//Get The difference between two dates
                long diffMinutes = diff / (60 * 1000) % 60; //Fetching the difference of minute
                // if(diffMinutes>2){
                // diffMinutes=2;
                // }
                System.out.println("Difference in Minutes b/w present time & Email Recieved time :" + diffMinutes);
                //try {
                //if(message.getSubject().contains(subjectKeyword) &&email.equals(fromEmail) && getText(message).contains(bodySearchText) && diffMinutes<=3){
                if (message.getSubject().contains(bodySearchText)) {
                    String subject = message.getSubject();
                    //System.out.println(getText(message));
                    System.out.println("Found message #" + i + ": ");
                    System.out.println("At " + i + ": " + " Subject: " + subject);
                    System.out.println("From: " + email + " on : " + message.getReceivedDate());
                    emailContent = message.getContent();
                    //if(getText(message).contains(bodySearchText)== true){
                    //System.out.println("Message contains the search text "+bodySearchText);
                    //val=true;
                    //}
                    //else{
                    //val=false;
                    //}
//                    getEmailInfo();
//                  getText(message);
//                    Object con = message.getContent();
//                    if(con instanceof String)
//                        System.out.println((String)con);
                    break;
                }
                //} catch (NullPointerException expected) {
                // TODO Auto-generated catch block
                //expected.printStackTrace();
                //}
                System.out.println("Searching.…" + "At " + i);
            }
            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
        val = true;
        return val;
    }


    public void fileNameinEmail(String submissionFileName) {
        if (emailContent.toString().trim().contains(submissionFileName)) {
            LOGGER.info("The User had Received an Email with FileName : " + submissionFileName);
        } else
            VerifyUtils.fail("The User Didn't Received an Email");
    }

    /**
     * Test this program with a Gmail’s account
     *
     * @throws IOException
     */
    public static void emailNotification() throws IOException {
        String userName = "approvervoyanta@gmail.com";
        String password = "v0yanta!";
        EmailTest searcher = new EmailTest();
        String subjectKeyword = "Voyanta Support";
        String fromEmail = "Voyanta Support";
        String bodySearchText = "A submission is awaiting your approval";
        searcher.searchEmail(userName, password, subjectKeyword, fromEmail, bodySearchText);
    }

    public static void emailNotificationForRejection() throws IOException {
        String userName = "submitvoyanta@gmail.com";
        String password = "V0yanta!";
        EmailTest searcher = new EmailTest();
        String subjectKeyword = "Voyanta Support";
        String fromEmail = "Voyanta Support";
        String bodySearchText = "Your submission has been rejected";
        searcher.searchEmail(userName, password, subjectKeyword, fromEmail, bodySearchText);
    }

    public static void noEmailForPendingApproval() throws IOException {
        String userName = "approvervoyanta@gmail.com";
        String password = "v0yanta!";
        EmailTest searcher = new EmailTest();
        String subjectKeyword = "Voyanta Support";
        String fromEmail = "Voyanta Support";
        String bodySearchText = "A submission is awaiting your approval";
        searcher.noEmail(userName, password, subjectKeyword, fromEmail, bodySearchText);
    }

    public boolean noEmail(String userName, String password, String subjectKeyword, String fromEmail, String bodySearchText) throws IOException {
        Properties properties = new Properties();
        boolean val = false;
        int i;
        // server setting
        properties.put("mail.imap.host", "imap.gmail.com");
        properties.put("mail.imap.port", 993);
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port", String.valueOf(993));
        Session session = Session.getDefaultInstance(properties);
        try {
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(userName, password);
            signIn = new SignInPageObject();
            signIn.login_gmail(userName, password);
            VUtils.waitFor(5);
            LOGGER.info("Connected to Email server….");
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
            //create a search term for all “unseen” messages
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
            //create a search term for all recent messages
            Flags recent = new Flags(Flags.Flag.RECENT);
            FlagTerm recentFlagTerm = new FlagTerm(recent, false);
            SearchTerm searchTerm = new OrTerm(unseenFlagTerm, recentFlagTerm);
            // performs search through the folder
            Message[] foundMessages = folderInbox.search(searchTerm);
            LOGGER.info("Total Messages Found :" + foundMessages.length);
            for (i = foundMessages.length - 1; i >= foundMessages.length - 10; i--) {
                Message message = foundMessages[i];
                Address[] froms = message.getFrom();
                LOGGER.info("Message --- " + message.getSubject());
                String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();

                if (message.getSubject() == null) {
                    continue;
                }

                Date date = new Date();//Getting Present date from the system
                long diff = date.getTime() - message.getReceivedDate().getTime();//Get The difference between two dates
                long diffMinutes = diff / (60 * 1000) % 60; //Fetching the difference of minute
                // if(diffMinutes>2){
                // diffMinutes=2;
                // }
                System.out.println("Difference in Minutes b/w present time & Email Recieved time :" + diffMinutes);
                //try {
                if (message.getSubject().contains(subjectKeyword) && getText(message).contains(bodySearchText) && diffMinutes <= 2) {
                    String subject = message.getSubject();
                    //System.out.println(getText(message));
                    System.out.println("Found message #" + i + ": ");
                    System.out.println("At " + i + ": " + " Subject: " + subject);
                    System.out.println("From: " + email + " on : " + message.getReceivedDate());
                    emailContent = message.getContent();
                    break;
                } else {
                    LOGGER.info("*** The User Didn't received an Email ***");
                    Assert.assertTrue("The User Received An Email", diffMinutes > 5);
                    break;
                }
            }
            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store.");
            ex.printStackTrace();
        }
        val = false;
        return val;
    }

    public void getEmailInfo() {
        System.out.println("----------Title :" + VoyantaDriver.getCurrentDriver().getCurrentUrl());
        VoyantaDriver.getCurrentDriver();
        try {
            LOGGER.info("Opening an Email ");
            VoyantaDriver.findElement(By.id(":3l")).click();
            VUtils.waitFor(2);

        } catch (NoSuchElementException e) {
            System.out.println("Error : Element is not Visible");
            e.printStackTrace();
        }
    }
}