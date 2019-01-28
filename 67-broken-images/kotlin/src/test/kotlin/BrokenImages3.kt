import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.firefox.FirefoxDriver
import java.io.IOException
import java.util.ArrayList
import kotlin.test.*

/* a single instance of of the test class is used for every method */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BrokenImages3 {

    private val driver by lazy {
        FirefoxDriver()
    }

    private val js by lazy {
        driver as JavascriptExecutor
    }

    @AfterTest
    @Throws(Exception::class)
    fun tearDown() {
        driver.quit()
    }

    @Test
    @Throws(IOException::class)
    fun `All images loaded`() {
        driver.get("http://the-internet.herokuapp.com/broken_images")
        val brokenImages = ArrayList<String>()
        val images = driver.findElements(By.tagName("img"))
        for (image in images.indices) {
            val result = js.executeScript(
                    "return arguments[0].complete && " +
                            "typeof arguments[0].naturalWidth != \"undefined\" && " +
                            "arguments[0].naturalWidth > 0", images[image])
            if (result == false) brokenImages.add(images[image].getAttribute("src"))
        }

        val emptyCollection = ArrayList<String>()
        assertEquals(
                brokenImages,
                emptyCollection
        )
    }
}