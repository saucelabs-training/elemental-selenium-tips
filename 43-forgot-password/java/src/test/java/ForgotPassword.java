import com.mailosaur.MailboxApi;
import com.mailosaur.exception.MailosaurException;
import com.mailosaur.model.Email;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
//import org.openqa.selenium.By;
//import java.io.IOException;
//import com.mailosaur.MailboxApi;
//import com.mailosaur.exception.MailosaurException;
//import com.mailosaur.model.Email;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;

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
        MailboxApi mailbox = new MailboxApi("6ly1czh5", "ilKj0F6Qp0xUumn");

        driver.navigate().to("http://the-internet.herokuapp.com/forgot_password");
        String email = mailbox.generateEmailAddress();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("form_submit")).click();
        Thread.sleep(2000); // allow time for email to be delivered by your email provider
        Email emails = mailbox.getEmailsByRecipient(email)[0];
        System.out.println(emails.subject + emails.html.body);
    }

}