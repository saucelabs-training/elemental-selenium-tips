import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
    public void withValidEmailAddress() {
        driver.navigate().to("http://the-internet.herokuapp.com/forgot_password");
        //driver.findElement(By.id("email")).sendKeys("");
        //driver.findElement(By.id("form_submit")).click();
        //MailboxApi mailbox = new MailboxApi("6ly1czh5", "ilKj0F6Qp0xUumn");
        //Email[] emails = mailbox.getEmailsByRecipient("no-reply@the-internet.herokuapp.com");
        //System.out.println(emails);
    }

}