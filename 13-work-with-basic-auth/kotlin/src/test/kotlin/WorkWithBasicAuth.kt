import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue

class WorkWithBasicAuth {

    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    @Throws(Exception::class)
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun workWithBasicAuthTest() {
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth")
        val pageMessage = driver.findElement(By.cssSelector("p")).text
        assertTrue(pageMessage.contains("Congratulations!"))
    }
}