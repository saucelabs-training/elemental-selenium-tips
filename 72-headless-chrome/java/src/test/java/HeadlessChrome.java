import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;

/* a single instance of of the test class is used for every method */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeadlessChrome {
    private WebDriver driver;
    private static Logger LOGGER = Logger.getLogger(HeadlessChrome.class.getName());

    @BeforeAll
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--window-size=1920,1200", "--ignore-certificate-errors", "--silent");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void HeadlessTest() {
        driver.get("http://the-internet.herokuapp.com/");
        assertEquals(driver.getTitle(), "The Internet");

        // Take a screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("headless.png"));
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }
}