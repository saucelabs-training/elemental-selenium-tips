package org.gradle;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarPostData;
import net.lightbody.bmp.core.har.HarPostDataParam;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.proxy.CaptureType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
public class Login {

    WebDriver driver;
    BrowserMobProxy proxy;
    Har har ;
    String uName = "tomsmith";
    String pwd = "SuperSecretPassword!";
    HashMap<String,String> param_map = new HashMap<String,String>();
    HashMap<String,String> param_values = new HashMap<String,String>();

    @Before
    public void setUp() throws Exception {
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        driver = new FirefoxDriver(capabilities);

        HashSet<CaptureType> captureTypes = new HashSet<CaptureType>();
        captureTypes.add(CaptureType.REQUEST_HEADERS);
        captureTypes.add(CaptureType.REQUEST_CONTENT);
        captureTypes.add(CaptureType.RESPONSE_HEADERS);
        captureTypes.add(CaptureType.RESPONSE_CONTENT);
        proxy.enableHarCaptureTypes(captureTypes);

        { //mocking parameters to be passed/accepted at server end
            param_map.put("username",uName);  //change accordingly
            param_map.put("password",pwd);
        }
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
        driver.findElement(By.id("username")).sendKeys(uName);
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.cssSelector("button")).click();
        WebElement successMessage = driver.findElement(By.cssSelector(".flash.success"));
        assertThat(successMessage.isDisplayed(), is(Boolean.TRUE));
        har = proxy.getHar();
        File harFile = new File(System.getProperty("user.dir") + "/test.har");
        har.writeTo(harFile);   // performance capture
        validatePassedPayload(har, "auth"); // post data validation
        System.out.println("Passed:: Captured har for performance check \"test.har\" + validated posted parameters");
    }


    /**
     * To sniff post payload for validating if client side parameters are being passed correctly
     * useful to use with ui automation test like with webdriver to ensure if user has posted correct data to server 
     * @param har
     * @param filterStr: to refine url 
     */ 
    public void validatePassedPayload(Har har, String filterStr){
        for (HarEntry entry : har.getLog().getEntries()) {
            if (entry.getRequest() != null) {

                HarRequest request = entry.getRequest();
                String requestUrl = entry.getRequest().getUrl();
                if (requestUrl.contains(filterStr)) {

                    HarPostData postData = request.getPostData();
                    if (postData != null) {
                        System.out.println(requestUrl);
                        List<HarPostDataParam> paramsList = request
                                .getPostData().getParams();
                        if (paramsList != null) {
                            for (int i = 0; i < paramsList.size(); i++) {
                                HarPostDataParam param = paramsList.get(i);
                                String paramName = param.getName();
                                System.out.println(param.getName()+":"+param.getValue()); //to debug
                                assertThat(param.getValue(), is(param_map.get(paramName))); //will fail if either param doesn't exist or value doesn't match 

                            }
                        }
                    }

                }
            }
        }
    }

}