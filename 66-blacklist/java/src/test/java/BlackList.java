import net.lightbody.bmp.core.har.HarEntry;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import java.util.List;

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
import net.lightbody.bmp.proxy.CaptureType;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlackList {

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
        proxy.blacklistRequests("http:\\/\\/the-internet.herokuapp.com\\/slow_external", 404);
    }

    @After
    public void tearDown() throws Exception {
        proxy.stop();
        driver.quit();
    }

    @Test
    public void slowLoadingResourceBlocked() throws IOException {
        // Can't do the following approach since blacklisted entries skip the HAR Filter
        // re: https://github.com/lightbody/browsermob-proxy/issues/327
        //proxy.newHar();
        //driver.navigate().to("http://the-internet.herokuapp.com/slow");
        //int response = 0;
        //List<HarEntry> harEntries = proxy.getHar().getLog().getEntries();
        //for (int i = 0; i < harEntries.size(); i++) {
        //    if (harEntries.get(i).getRequest().getUrl().contains("slow_external")) {
        //        response = harEntries.get(i).getResponse().getStatus();
        //    }
        //}
        //assertThat(response, is(404));

        HttpClient client = HttpClientBuilder.
                                create().
                                setProxy(new HttpHost("127.0.0.1",proxy.getPort())).
                                build();
        HttpResponse response = client.execute(
                                    new HttpGet("http://the-internet.herokuapp.com/slow_external"));
        assertThat(response.getStatusLine().getStatusCode(), is(404));
    }

}
