import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Checkboxes {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @Test
    public void checkboxDiscoveryTest() {
        driver.get("http://the-internet.herokuapp.com/checkboxes");
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type=\"checkbox\"]"));

        System.out.println("With .attribute('checked')");
        for (WebElement checkbox : checkboxes) {
            System.out.println(String.valueOf(checkbox.getAttribute("checked")));
        }

        System.out.println("\nWith .selected?");
        for (WebElement checkbox : checkboxes) {
            System.out.println(checkbox.isSelected());
        }
    }

    @Test
    public void checkboxOption1Test() throws Exception {
        // check if 1st checkbox is not checked
        driver.get("http://the-internet.herokuapp.com/checkboxes");
        WebElement checkbox1 = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/input[1]"));
        assertThat(checkbox1.isSelected(), is(false));
    }

    @Test
    public void checkboxOption2Test() throws Exception {
        // check if 2nd checkbox is checked
        driver.get("http://the-internet.herokuapp.com/checkboxes");
        driver.get("http://the-internet.herokuapp.com/checkboxes");
        WebElement checkbox1 = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/input[2]"));
        assertThat(checkbox1.isSelected(), is(true));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}