import net.lightbody.bmp.BrowserMobProxyServer
import net.lightbody.bmp.client.ClientUtil
import net.lightbody.bmp.proxy.CaptureType
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.CapabilityType
import java.io.IOException
import kotlin.test.*

/* a single instance of of the test class is used for every method */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BrokenImages1 {

    private val driver by lazy {
        // create an instance of Selenium connected to the proxy
        val profile = FirefoxProfile()
        // create a proxy instance and configure Selenium to use it
        val seleniumProxy = ClientUtil.createSeleniumProxy(proxy)
        val options = FirefoxOptions().setProfile(profile)
        options.setCapability(CapabilityType.PROXY, seleniumProxy)
        FirefoxDriver(options)
    }

    private val proxy by lazy {
        // start the proxy immediately
        BrowserMobProxyServer()
    }

    @BeforeTest
    @Throws(Exception::class)
    fun setUp() {

        proxy.start(0)

        // enable detailed HAR capture
        proxy.enableHarCaptureTypes(
                CaptureType.REQUEST_CONTENT,
                CaptureType.RESPONSE_CONTENT
        )
    }

    @AfterTest
    @Throws(Exception::class)
    fun tearDown() {
        proxy.stop()
        driver.quit()
    }

    @Test
    @Throws(IOException::class)
    fun `All images loaded`() {
        proxy.newHar()
        driver.navigate().to("http://the-internet.herokuapp.com/broken_images")
        val images = driver.findElements(By.tagName("img"))
        val brokenImages = ArrayList<String>()
        val harEntries = proxy.har.log.entries
        for (entry in harEntries.indices) {
            for (image in images.indices) {
                if ((harEntries[entry].request.url == images[image].getAttribute("src")) &&
                        (harEntries[entry].response.status == 404)) {
                    brokenImages.add(images[image].getAttribute("src"))
                }
            }
        }
        val emptyCollection = ArrayList<String>()
        assertEquals(
                brokenImages,
                emptyCollection
        )
    }
}