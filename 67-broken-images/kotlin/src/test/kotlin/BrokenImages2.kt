import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import java.io.IOException
import java.util.ArrayList
import kotlin.test.*

/* a single instance of of the test class is used for every method */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BrokenImages2 {

    private val driver by lazy {
        FirefoxDriver()
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
            val client = HttpClientBuilder.create().build()
            val response = client.execute(HttpGet(images[image].getAttribute("src")))
            val responseCode = response.statusLine.statusCode
            if (responseCode != 200) {
                brokenImages.add(images[image].getAttribute("src"))
            }
        }

        val emptyCollection = ArrayList<String>()
        assertEquals(
                brokenImages,
                emptyCollection
        )
    }
}