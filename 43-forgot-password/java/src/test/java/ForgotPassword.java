import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.mailosaur.MailboxApi;
import com.mailosaur.exception.MailosaurException;
import com.mailosaur.model.Email;

public class ForgotPassword {

    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void withValidEmailAddress() throws MailosaurException, IOException, InterruptedException {
        MailboxApi mailbox = new MailboxApi(
            System.getenv("MAILOSAUR_MAILBOX_ID"),
            System.getenv("MAILOSAUR_API_KEY"));

        driver.navigate().to("http://the-internet.herokuapp.com/forgot_password");

        String emailAddress = mailbox.generateEmailAddress();
        driver.findElement(By.id("email")).sendKeys(emailAddress);
        driver.findElement(By.id("form_submit")).click();
        Thread.sleep(15000); // allow time for email to be delivered by your email provider

        Email email = mailbox.getEmailsByRecipient(emailAddress)[0];

        System.out.println("Subject: " + email.subject);
        System.out.println("Body:");
        System.out.println(email.text);
    }

}