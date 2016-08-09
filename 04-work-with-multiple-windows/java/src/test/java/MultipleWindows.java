package src.test.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.Set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class MultipleWindows {
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
    public void multipleWindows() {
        driver.get("http://the-internet.herokuapp.com/windows");
        driver.findElement(By.cssSelector(".example a")).click();
        Object[] allWindows = driver.getWindowHandles().toArray();
        driver.switchTo().window(allWindows[0].toString());
        assertThat(driver.getTitle(), is(not("New Window")));
        driver.switchTo().window(allWindows[1].toString());
        assertThat(driver.getTitle(), is("New Window"));
    }

    @Test
    public void multipleWindowsRedux() {
        driver.get("http://the-internet.herokuapp.com/windows");

        // Get initial window handle
        String firstWindow = driver.getWindowHandle();
        // Create a newWindow variable
        String newWindow = "";
        // Trigger new window to open
        driver.findElement(By.cssSelector(".example a")).click();
        // Grab all window handles
        Set<String> allWindows = driver.getWindowHandles();

        // Iterate through window handles collection
        // Find the new window handle, storing it in the newWindow variable
        for (String window : allWindows) {
            if (!window.equals(firstWindow)) {
                newWindow = window;
            }
        }

        // Switch to the first window & verify
        driver.switchTo().window(firstWindow);
        assertThat(driver.getTitle(), is(not(equalTo("New Window"))));

        // Switch to the new window & verify
        driver.switchTo().window(newWindow);
        assertThat(driver.getTitle(), is(equalTo("New Window")));
    }

}