import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;

public class Login {

    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        // For Xvfb headless execution, launch it before you run this test
        // E.g., as part of a CI build step
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void withValidCredentials() throws IOException {
        driver.navigate().to("http://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button")).click();
        WebElement successMessage = driver.findElement(By.cssSelector(".flash.success"));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + "/screenshot.png"));
        assertThat(successMessage.isDisplayed(), is(Boolean.TRUE));
    }

}
