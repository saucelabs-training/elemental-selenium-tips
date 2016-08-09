package src.test.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.lightbody.bmp.core.har.HarEntry;
import org.apache.commons.lang3.ObjectUtils;
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

public class BrokenImages1 {

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
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT,
                                    CaptureType.RESPONSE_CONTENT);
    }

    @After
    public void tearDown() throws Exception {
        proxy.stop();
        driver.quit();
    }

    @Test
    public void allImagesLoaded() throws IOException {
        proxy.newHar();
        driver.navigate().to("http://the-internet.herokuapp.com/broken_images");
        List<WebElement> images = driver.findElements(By.tagName("img"));
        List brokenImages = new ArrayList();
        List<HarEntry> harEntries = proxy.getHar().getLog().getEntries();
        for(int entry = 0; entry < harEntries.size(); entry++) {
            for(int image = 0; image < images.size(); image++) {
                if (harEntries.get(entry).getRequest().getUrl().equals(
                        images.get(image).getAttribute("src"))
                    & harEntries.get(entry).getResponse().getStatus() == 404)
                    brokenImages.add(images.get(image).getAttribute("src"));
            }
        }
        List emptyCollection = new ArrayList();
        assertThat(brokenImages, is(emptyCollection));
    }

}