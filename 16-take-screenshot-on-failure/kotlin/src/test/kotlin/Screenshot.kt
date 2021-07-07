import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.openqa.selenium.firefox.FirefoxDriver
import java.io.File
import kotlin.test.assertFalse
import org.junit.Test
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import javax.imageio.ImageIO

class Screenshot {

    private val driver by lazy {
        FirefoxDriver()
    }

    @get:Rule
    val watcher: TestWatcher = object : TestWatcher() {
        override fun failed(e: Throwable?, description: Description?) {
            helperAShot("fail")
        }

        override fun succeeded(description: Description?) {
            helperAShot("success")
        }

        override fun finished(description: Description?) {
            // Quit session (closes browser)
            driver.quit()
        }
    }

    private fun helperAShot(fileName: String) {
        val screenshot = AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver)
        ImageIO.write(screenshot.image, "PNG", File("~/screenshots/$fileName.png"))
    }

    @Test
    fun onError() {
        driver.get("http://the-internet.herokuapp.com")
        assertFalse(true)
    }
}