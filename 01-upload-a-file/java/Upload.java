import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Upload {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @Test
    public void uploadFile() throws Exception {
        File file = new File("some-file.txt");
        String path = file.getAbsolutePath();
        driver.get("http://the-internet.herokuapp.com/upload");
        driver.findElement(By.id("file-upload")).sendKeys(path);
        driver.findElement(By.id("file-submit")).click();
        assertThat(driver.findElement(By.id("uploaded-files")).isDisplayed(), is(true));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}