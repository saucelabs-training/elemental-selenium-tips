import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BrokenImages3 {

    WebDriver driver;
    JavascriptExecutor js;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void allImagesLoaded() throws IOException {
        driver.navigate().to("http://the-internet.herokuapp.com/broken_images");
        List brokenImages = new ArrayList();
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for(int image = 0; image < images.size(); image++) {
            Object result = js.executeScript("return arguments[0].complete && " +
                            "typeof arguments[0].naturalWidth != \"undefined\" && " +
                            "arguments[0].naturalWidth > 0", images.get(image));
            if (result == Boolean.FALSE) {
                brokenImages.add(images.get(image).getAttribute("src"));
            }
        }

        List emptyCollection = new ArrayList();
        assertThat(brokenImages, is(emptyCollection));
    }

}