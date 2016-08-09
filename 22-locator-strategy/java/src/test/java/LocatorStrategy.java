package src.test.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by andrew on 8/22/15.
 */
public class LocatorStrategy {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();

    }

    @Test
    public void locatorStategyCSS() {
        driver.get("http://the-internet.herokuapp.com/download");
        String link = driver.findElement(By.cssSelector("a")).getAttribute("href");
        System.out.println(link);
    }

    @Test
    public void locatorStategyPreciseCSS() {
        driver.get("http://the-internet.herokuapp.com/download");
        String link = driver.findElement(By.cssSelector("#content a")).getAttribute("href");
        System.out.println(link);
    }

    @Test
    public void locatorStategyExactCSS() {
        driver.get("http://the-internet.herokuapp.com/download");
        String link = driver.findElement(By.cssSelector("#content .example a")).getAttribute("href");
        System.out.println(link);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
