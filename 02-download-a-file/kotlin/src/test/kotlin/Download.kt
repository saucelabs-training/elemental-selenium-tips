import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.AfterTest
import kotlin.test.assertNotEquals
import org.openqa.selenium.firefox.FirefoxOptions

// 02-download-a-file
class Download {
    private val testUrl = "http://the-internet.herokuapp.com/download"
    private val folder = File(UUID.randomUUID().toString())

    private val driver by lazy {
        folder.mkdir()
        val profile = FirefoxProfile()
        profile.setPreference("browser.download.dir", folder.absolutePath)
        profile.setPreference("browser.download.folderList", 2)
        profile.setPreference(
            "browser.helperApps.neverAsk.saveToDisk",
            "image/jpeg, application/pdf, application/octet-stream"
        )
        profile.setPreference("pdfjs.disabled", true)
        val options = FirefoxOptions().setProfile(profile)
        FirefoxDriver(options)
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
        for (file in folder.listFiles()!!) {
            file.delete()
        }
        folder.delete()
    }

    @Test
    @Throws(Exception::class)
    fun download() {
        driver.get(testUrl)
        driver.findElement(By.cssSelector(".example a")).click()

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

        val listOfFiles = folder.listFiles()

        // Make sure the directory is not empty
        assertNotEquals(
            listOfFiles!!.size,
            0
        )
        for (file in listOfFiles) {
            // Make sure the downloaded file(s) is(are) not empty
            assertNotEquals(
                file.length(),
                0
            )
        }
    }
}