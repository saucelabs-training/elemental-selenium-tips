import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL
import kotlin.test.*

/* a single instance of of the test class is used for every method */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Cloud {
    private val driver by lazy {
        val username = "username"
        val accessKey = "accessKey"

        // https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/
        val caps = DesiredCapabilities.internetExplorer()
        caps.setCapability("platform", "Windows 7")
        caps.setCapability("version", "9.0")
        caps.setCapability("name", "Cloud test")
        caps.setCapability("tags", "Sample Tag")
        caps.setCapability("build", "build-0")
        val url = "https://$username:$accessKey@ondemand.saucelabs.com:443/wd/hub"
        RemoteWebDriver(URL(url), caps)
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun `Cloud test`(){
        driver.get("http://the-internet.herokuapp.com")
        assertEquals(
                true,
                driver.title.contains("The Internet")
        )
    }
}