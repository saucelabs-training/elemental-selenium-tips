import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HighlightElement {
    WebDriver driver;
    JavascriptExecutor js;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void highlightElementTest() throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com/large");
        WebElement element = driver.findElement(By.id("sibling-2.3"));
        System.out.println(element);
        highlightElement(element, 3);
    }

    /**
     * @param element  element on the page that will be highlighted
     * @param duration the time in second how much the element will be highlighted
     */

    private void highlightElement(WebElement element, int duration) throws InterruptedException {
        //store original style so it can be reset later
        String original_style = element.getAttribute("style");

        //style element with red border
        js.executeScript(
                "arguments[0].setAttribute(arguments[1], arguments[2])",
                element,
                "style",
                "border: 2px solid red; border-style: dashed;");

        // keep element highlighted for a spell and then revert
        if (duration > 0) {
            // TODO: implement waitWithoutReason method
            Thread.sleep(duration * 1000);
            js.executeScript(
                    "arguments[0].setAttribute(arguments[1], arguments[2])",
                    element,
                    "style",
                    original_style);
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}