package num12;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by andrew on 8/22/15.
 */
public class example {

    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void TestSwitchingCookie() {
        driver.get("http://the-internet.herokuapp.com/abtest");
        String headingText = driver.findElement(By.tagName("h3")).getText();
        assert (headingText.equals("A/B Test Variation 1") || headingText.equals("A/B Test Control"));
        driver.manage().addCookie(new Cookie("optimizelyOptOut", "true"));
        driver.navigate().refresh();
        headingText = driver.findElement(By.cssSelector("h3")).getText();
        assert headingText.equals("No A/B Test");
    }

    @Test
    public void TestCookie() {
        driver.manage().addCookie(new Cookie("optimizelyOptOut", "true"));
        driver.get("http://the-internet.herokuapp.com/abtest");
        assert driver.findElement(By.cssSelector("h3")).getText().equals("No A/B Test");
    }

    @Test
    public void TestOptOutViaUrl() {
        driver.get("http://the-internet.herokuapp.com/abtest?optimizely_opt_out=true");
        assert driver.findElement(By.cssSelector("h3")).getText().equals("No A/B Test");
    }


}
