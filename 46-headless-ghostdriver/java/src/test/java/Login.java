package src.test.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.IOException;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class Login {

    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("phantomjs.binary.path",
                System.getProperty("user.dir") +
                "/vendor/phantomjs-2.1.1-macosx/bin/phantomjs");
        driver = new PhantomJSDriver();
        // Alternatively, you can connect to it with Selenium Remote
        // You'll first need to launch the binary with the --webdriver flag and a port number
        // e.g., ./phantomjs --webdriver=8001
        // Then it's just a matter of using DesiredCapabilities and RemoteWebDriver
        //
        // DesiredCapabilities caps = new DesiredCapabilities();
        // caps.setBrowserName("phantomjs");
        // driver = new RemoteWebDriver(new URL("http://localhost:8001"), caps);
        //
        // Alternatively, PhantomJS can be connected as a node to a Selenium Grid
        // http://se.tips/ghost-driver-grid
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