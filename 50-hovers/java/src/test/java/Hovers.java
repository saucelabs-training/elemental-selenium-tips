package src.test.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Hovers {
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
    public void hoversTest() {
        driver.get("http://the-internet.herokuapp.com/hovers");

        // Find and hover over desired element
        WebElement avatar = driver.findElement(By.className("figure"));
        Actions builder = new Actions(driver);
        builder.moveToElement(avatar).build().perform();

        // Wait until the hover appears
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("figcaption")));

        // Assert that the hover displayed
        assertThat(driver.findElement(By.className("figcaption")).isDisplayed(), is(Boolean.TRUE));
    }

}