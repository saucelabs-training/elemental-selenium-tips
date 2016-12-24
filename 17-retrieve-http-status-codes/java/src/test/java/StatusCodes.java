import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatusCodes {

    WebDriver driver;
    BrowserMobProxy proxy;

    @Before
    public void setUp() throws Exception {
        // start the proxy immediately
        proxy = new BrowserMobProxyServer();
        proxy.start(0);

        // create a proxy instance and configure Selenium to use it
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        // create an instance of Selenium connected to the proxy
        driver = new FirefoxDriver(capabilities);

        // enable detailed HAR capture
        proxy.enableHarCaptureTypes(
                CaptureType.REQUEST_CONTENT,
                CaptureType.RESPONSE_CONTENT);
    }

    @After
    public void tearDown() throws Exception {
        proxy.stop();
        driver.quit();
    }

    @Test
    public void ResourceNotFound() {
        proxy.newHar();
        driver.navigate().to("http://the-internet.herokuapp.com/status_codes/404");
        Har har = proxy.getHar();
        int response = har.getLog().getEntries().get(0).getResponse().getStatus();
        assertThat(response, is(404));
    }

}
