import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Grid {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//        capabilities.setBrowserName("firefox");
//        capabilities.setPlatform(Platform.LINUX);
//        capabilities.setVersion("3.6")
//        capabilities.setCapability(,);

        String url = "http://localhost:4444/wd/hub";
        driver = new RemoteWebDriver(new URL(url), capabilities);
    }

    @Test
    public void gridTest() {
        // before run test make sure that Selenium Grid is running and it has at least one node with testing browser
        // run Selenium Grid  ./vendor/java -jar selenium-server-standalone-2.45.0.jar -role hub
        // register node      ./vendor/java -jar selenium-server-standalone-2.45.0.jar -role node -hub http://localhost:4444/grid/register

        driver.get("http://the-internet.herokuapp.com/");
        assertThat(driver.getTitle(), is(equalTo("The Internet")));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}