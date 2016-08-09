package src.test.java;

import java.io.File;
import java.io.IOException;
import net.lightbody.bmp.core.har.Har;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Login {

    WebDriver driver;
    BrowserMobProxy proxy;

    @Before
    public void setUp() throws Exception {
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        driver = new FirefoxDriver(capabilities);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
    }

    @After
    public void tearDown() throws Exception {
        proxy.stop();
        driver.quit();
    }

    @Test
    public void withValidCredentials() throws IOException {
        proxy.newHar();
            driver.navigate().to("http://the-internet.herokuapp.com/login");
            driver.findElement(By.id("username")).sendKeys("tomsmith");
            driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
            driver.findElement(By.cssSelector("button")).click();
            WebElement successMessage = driver.findElement(By.cssSelector(".flash.success"));
            assertThat(successMessage.isDisplayed(), is(Boolean.TRUE));
        Har har = proxy.getHar();
        File harFile = new File(System.getProperty("user.dir") + "/test.har");
        har.writeTo(harFile);
    }

}