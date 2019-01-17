import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.FileReader
import java.util.concurrent.TimeUnit
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDataDriven {
    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    /* Read CSV using org.apache.commons/commons-csv */
    private fun readCSVData(): CSVParser {
        val filePath = "src/test/kotlin/user_data.csv"
        val fileReader = FileReader(filePath)
        return CSVFormat.RFC4180.withFirstRecordAsHeader().parse(fileReader)
    }

    /* Get Notification Message */
    private fun notificationText(): String = driver.findElement(By.id("flash")).text.trim().replace(Regex("[^a-zA-Z !.]"), "")

    /* Implicit wait */
    //private fun waitFor() = driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS

    /* Explicit wait */
    private fun waitExplicit(locator: By) {
        WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(locator))
    }

    @Test
    fun `Test using CSV file as source of the user data`() {
        for (user in readCSVData()) {
            driver.get("http://the-internet.herokuapp.com/login")
            val username = user.get("username")
            val password = user.get("password")
            val notification = user.get("notification_message")
            driver.findElement(By.id("username")).sendKeys(username)
            driver.findElement(By.id("password")).sendKeys(password)
            driver.findElement(By.id("login")).submit()

            //waitFor()
            waitExplicit(By.id("flash"))

            assertEquals(
                notification,
                notificationText()
            )
        }
    }
}