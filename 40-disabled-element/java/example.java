package num40;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

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
    public void test() {
        driver.get("http://the-internet.herokuapp.com/dropdown");
        Select dropdown = new Select(driver.findElement(By.tagName("option")));
        WebElement itemsOfInterest = dropdown.getOptions().get(1);
        assert (itemsOfInterest.isEnabled());
    }
}
