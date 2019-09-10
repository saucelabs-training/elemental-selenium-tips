import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Upload {
    private val testUrl = "http://the-internet.herokuapp.com/upload"

    private val driver by lazy {
        System.setProperty("webdriver.gecko.driver", "/home/dragon/bin/webdrivers/geckodriver")
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    @Throws(Exception::class)
    fun uploadFile() {
        val filePath = "src/test/kotlin/some-file.txt"
        val file = File(filePath)
        val filename = file.name
        val path = file.absolutePath

        driver.get(testUrl)
        driver.findElement(By.id("file-upload")).sendKeys(path)
        driver.findElement(By.id("file-submit")).click()

        val actual = driver.findElement(By.id("uploaded-files")).text
        assertEquals(
            filename,
            actual
        )
    }
}