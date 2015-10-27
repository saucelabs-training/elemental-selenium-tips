import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KeyboardKeys {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @Test
    public void uploadFile() throws Exception {
        driver.get("http://the-internet.herokuapp.com/key_presses");
        //  Set focus on page content
        driver.findElement(By.id("content")).sendKeys("");
        // Send keys to page input
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.LEFT).build().perform();
        assertThat(driver.findElement(By.id("result")).getText(), is("You entered: LEFT"));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
