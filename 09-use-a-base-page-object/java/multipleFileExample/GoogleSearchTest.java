package num9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by andrew on 8/22/15.
 */

public class GoogleSearchTest {

    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @Test
    public void workWithBasicAuthTest() {
        GoogleSearch google = new GoogleSearch(driver);
        google.searchFor("elemental selenium tips");
        boolean result = google.searchResultPresent("Recieve a Free, Weekly tip");
        assert (result);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}

