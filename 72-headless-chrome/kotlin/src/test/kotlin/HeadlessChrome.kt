import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.io.File
import kotlin.test.Test
import kotlin.test.AfterTest
import kotlin.test.assertEquals

/* a single instance of of the test class is used for every method */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HeadlessChrome {

    private val driver by lazy {
        val options = ChromeOptions()
        options.addArguments("--headless", "--window-size=1920,1200", "--ignore-certificate-errors", "--silent")
        ChromeDriver(options)
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun `Chrome Driver Test`() {
        driver.get("http://the-internet.herokuapp.com/")
        assertEquals(
                driver.title,
                "The Internet"
        )

        // Take a screenshot with Selenium
        val screenshot = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        FileUtils.copyFile(screenshot, File("headless.png"))
    }
}