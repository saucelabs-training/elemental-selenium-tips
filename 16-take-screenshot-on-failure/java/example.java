package num16;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public void screenShotOnErrorTest() {
        driver.get("http://the-internet.herokuapp.com");
        try {
            assert driver.findElement(By.cssSelector("h1")).getText().equals("Welcome to the Internet!");
        }
        catch (Exception e){
            System.out.println(e);
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File(new SimpleDateFormat("failshot__dd_MM_yyyy__HH_mm_ss").format(new Date()) + ".png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
