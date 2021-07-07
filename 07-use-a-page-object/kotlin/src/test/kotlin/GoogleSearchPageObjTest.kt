import org.openqa.selenium.firefox.FirefoxDriver
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GoogleSearchPageObjTest {

    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun workWithBasicAuthTest() {
        val google = GoogleSearchPageObj(driver)
        google.searchFor("elemental selenium tips")
        val result = google.searchResultPresent("Receive a Free, Weekly Tip")
        assert(result)
    }

}