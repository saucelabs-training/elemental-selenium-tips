package src.test.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GoogleSearchPageObjTest {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @Test
    public void workWithBasicAuthTest() {
        GoogleSearchPageObj google = new GoogleSearchPageObj(driver);
        google.searchFor("elemental selenium tips");
        boolean result = google.searchResultPresent("Recieve a Free, Weekly tip");
        assert (result == true);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
