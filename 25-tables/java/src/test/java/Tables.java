import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

public class Tables {
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
    public void withoutHelpfulMarkupDuesAscending() {
        driver.get("http://the-internet.herokuapp.com/tables");

        // sort dues column in ascending order
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click();

        // get values from dues column (w/o $)
        List<WebElement> dues = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(4)"));
        List<Double> dueValues = new LinkedList<Double>();
        for (WebElement element : dues) {
            dueValues.add(Double.parseDouble(element.getText().replace("$", "")));
        }

        // check that dues are in ascending order
        for (int counter = 0; counter < dueValues.size() - 1; counter++) {
            assertThat(dueValues.get(counter), is(lessThanOrEqualTo(dueValues.get(counter + 1))));
        }
    }

    @Test
    public void withoutHelpfulMarkupDuesDescending() {
        driver.get("http://the-internet.herokuapp.com/tables");

        // sort dues column in descending order
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click();
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click();

        // get values from dues column (w/o $) again
        List<WebElement> dues = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(4)"));
        List<Double> dueValues = new LinkedList<Double>();
        for (WebElement element : dues) {
            dueValues.add(Double.parseDouble(element.getText().replace("$", "")));
        }

        // assert dues are in descending order
        for (int counter = 0; counter < dueValues.size() - 1; counter++) {
            assertThat(dueValues.get(counter), is(greaterThanOrEqualTo(dueValues.get(counter + 1))));
        }
    }

    @Test
    public void withoutHelpfulMarkupEmailAscending() {
        driver.get("http://the-internet.herokuapp.com/tables");

        // sort email column in ascending order
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(3)")).click();

        // get values from email column
        List<WebElement> emails = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(3)"));

        // assert emails are in ascending order
        for(int counter = 0; counter < emails.size() -1; counter++){
            assertThat(
                    emails.get(counter).getText().compareTo(emails.get(counter + 1).getText()),
                    is(lessThan(0)));
            // checking for a negative number for ascending order
            // would check for a positive number if descending
        }
    }

    @Test
    public void withHelpfulMarkup()
    {
        driver.get("http://the-internet.herokuapp.com/tables");
        driver.findElement(By.cssSelector("#table2 thead .dues")).click();
        List<WebElement> dues = driver.findElements(By.cssSelector("#table2 tbody .dues"));
        List<Double> dueValues = new LinkedList<Double>();
        for(WebElement element : dues){
            dueValues.add(Double.parseDouble(element.getText().replace("$", "")));
        }
        for(int counter = 0; counter < dueValues.size() - 1; counter++){
            assertThat(dueValues.get(counter), is(lessThanOrEqualTo(dueValues.get(counter + 1))));
        }
    }
}
