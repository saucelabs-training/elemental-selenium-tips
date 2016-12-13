package org.gradle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class OperaTest {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        //download respective executable from https://github.com/operasoftware/operachromiumdriver/releases and keep in project directory
        System.setProperty("webdriver.opera.driver", System.getProperty("user.dir")+"/vendor/operadriver"); 
        WebDriver driver = new OperaDriver();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void dropdownTest() {
        driver.get("http://the-internet.herokuapp.com/");
        String title = driver.getTitle();
        assertThat(title, is(equalTo("The Internet")));
    }
}