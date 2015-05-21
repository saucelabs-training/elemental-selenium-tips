import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ChromeDriverExample {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "vendor/chrome-driver-2.15/chromedriver_linux64");
        driver = new ChromeDriver();

//    Option 2
//    Start the chromedriver in your terminal
//    Connect to it via Selenium Remote, like so:
//        driver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());
    }

    @Test
    public void chromeDriverTest() {
        driver.get("http://the-internet.herokuapp.com/");
        assertThat(driver.getTitle(), is(equalTo("The Internet")));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}