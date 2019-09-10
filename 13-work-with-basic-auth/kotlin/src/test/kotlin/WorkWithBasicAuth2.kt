import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.AfterTest
import kotlin.test.assertTrue

class WorkWithBasicAuth2 {

    private val driver by lazy {
        FirefoxDriver()
    }

    @BeforeTest
    @Throws(Exception::class)
    fun setUp() {
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth")
    }

    @AfterTest
    @Throws(Exception::class)
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun workWithBasicAuthTest2() {
        driver.get("http://the-internet.herokuapp.com/basic_auth")
        val pageMessage = driver.findElement(By.cssSelector("p")).text
        assertTrue(pageMessage.contains("Congratulations!"))
    }
}