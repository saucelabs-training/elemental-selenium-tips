import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import kotlin.test.Test
import kotlin.test.AfterTest
import kotlin.test.assertTrue

//10-retry-test-actions
class RetryTestActions {
    private val testUrl = "http://the-internet.herokuapp.com/notification_message"

    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun retryTestActionsTest() {
        driver.get(testUrl)
        assertTrue(retryIfNotificationMessageContains("please try again"))
    }

    private fun getNotificationMessage(): String = driver.findElement(By.id("flash")).text

    private fun retryIfNotificationMessageContains(message: String): Boolean {
        for (count in 0..2) {
            if (getNotificationMessage() == message)
                driver.navigate().refresh()
            else
                return true
        }
        return false
    }
}