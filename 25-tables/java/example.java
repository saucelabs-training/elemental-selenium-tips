package num25;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by andrew on 8/22/15.
 */
public class example {
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
    public void test()
    {
        // sort dues ascending
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click();

        // get dues
        List<WebElement> dues = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(4)"));
        List<Double> dueValues = new LinkedList<Double>();
        for(WebElement element : dues){
            dueValues.add(Double.parseDouble(element.getText().replace("$", "")));
        }

        // assert dues are ascending
        for(int counter = 1; counter < dueValues.size(); counter++){
            assert(dueValues.get(counter - 1) <= dueValues.get(counter));
        }

        // sort dues descending
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click();

        // get dues
        dues = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(4)"));
        dueValues = new LinkedList<Double>();
        for(WebElement element : dues){
            dueValues.add(Double.parseDouble(element.getText().replace("$", "")));
        }

        // assert dues are descending
        for(int counter = 1; counter < dueValues.size(); counter++){
            assert(dueValues.get(counter - 1) >= dueValues.get(counter));
        }

        // sort email ascending
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(3)")).click();
        List<String> emailsValues = new LinkedList<String>();
        List<WebElement> emails = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(3)"));

        for(int counter = 1; counter < emails.size(); counter++){
            assert (emailsValues.get(counter -1).compareToIgnoreCase(emailsValues.get(counter)) < 0);
        }
    }

    @Test
    public void testDues()
    {
        driver.get("http://the-internet.herokuapp.com/tables");
        List<WebElement> dues = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(4)"));
        List<Double> dueValues = new LinkedList<Double>();
        for(WebElement element : dues){
            dueValues.add(Double.parseDouble(element.getText().replace("$", "")));
        }
    }
}
