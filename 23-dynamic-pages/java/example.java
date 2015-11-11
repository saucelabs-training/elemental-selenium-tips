package num23;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by andrew on 8/22/15.
 */
public class example {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();

    }

    @Test
    public void noSuchElementErrorTest() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        driver.findElement(By.cssSelector("#start button")).click();
        assert driver.findElement(By.cssSelector("#finish")).getText().equals("Hello World!");
    }

    @Test
    public void webDriverWait8Test() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        driver.findElement(By.cssSelector("#start button")).click();
        new WebDriverWait(driver, 8).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#finish")));
        assert driver.findElement(By.cssSelector("#finish")).getText().equals("Hello World!");
    }

    @Test
    public void webDriverWaitTimeOutErrorTest() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        driver.findElement(By.cssSelector("#start button")).click();
        new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#finish")));
        assert driver.findElement(By.cssSelector("#finish")).getText().equals("Hello World!");
    }

    @Test
    public void cleanUpTest() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        driver.findElement(By.cssSelector("#start button")).click();
        waitFor(By.cssSelector("#finish"));
        assert driver.findElement(By.cssSelector("#finish")).getText().equals("Hello World!");
    }

    public void waitFor(By locator) {
        new WebDriverWait(driver, 8).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
