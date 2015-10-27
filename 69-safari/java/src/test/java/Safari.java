import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Safari {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new SafariDriver();
    }

    @Test
    public void dropdownTest() {
        driver.get("http://the-internet.herokuapp.com/");
        String title = driver.getTitle();
        assertThat(title, is(equalTo("The Internet")));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}