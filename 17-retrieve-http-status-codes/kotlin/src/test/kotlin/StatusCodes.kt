import net.lightbody.bmp.BrowserMobProxyServer
import net.lightbody.bmp.client.ClientUtil
import net.lightbody.bmp.proxy.CaptureType
import org.junit.Before
import org.junit.After
import org.junit.Test
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.CapabilityType
import kotlin.test.assertEquals

class StatusCodes {

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

    @Before
    @Throws(Exception::class)
    fun setUp() {

        proxy.start(0)

        // enable detailed HAR capture
        proxy.enableHarCaptureTypes(
            CaptureType.REQUEST_CONTENT,
            CaptureType.RESPONSE_CONTENT
        )
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        // Quit session (closes browser)
        proxy.stop()
        driver.quit()
    }

    @Test
    fun `Resource not found`() {
        proxy.newHar()
        driver.navigate().to("http://the-internet.herokuapp.com/status_codes/404")
        val har = proxy.har
        val response = har.log.entries[0].response.status
        assertEquals(
            404,
            response
        )
    }
}