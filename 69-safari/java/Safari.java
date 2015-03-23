import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Safari {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new SafariDriver();
    }

    // Not tested with Safari web browser
    // please remove this comment in case test is passed with Safari web browser
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