import org.openqa.selenium.By
import org.openqa.selenium.Cookie
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ABTestOptOut {

    private val driver by lazy {
        FirefoxDriver()
    }

    private val wait by lazy {
        WebDriverWait(driver, 20)
    }

    @AfterTest
    @Throws(Exception::class)
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun WithCookieAfterVisitingPage() {
        driver.get("http://the-internet.herokuapp.com/abtest")
        val actual: String = driver.findElement(By.tagName("h3")).text
        assertTrue(actual.startsWith("A/B Test"))

        driver.manage().addCookie(Cookie("optimizelyOptOut", "true"))
        driver.navigate().refresh()

        assertEquals(
            "No A/B Test",
            driver.findElement(By.cssSelector("h3")).text
        )
    }

    @Test
    fun WithCookieBeforeVisitingPage() {
        driver.get("http://the-internet.herokuapp.com")
        driver.manage().addCookie(Cookie("optimizelyOptOut", "true"))
        driver.get("http://the-internet.herokuapp.com/abtest")
        assertEquals(
            "No A/B Test",
            driver.findElement(By.cssSelector("h3")).text
        )
    }

    @Test
    fun WithOptOutUrl() {
        driver.get("http://the-internet.herokuapp.com/abtest?optimizely_opt_out=true")
        driver.switchTo().alert().dismiss()
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3")))
        assertEquals(
            "No A/B Test",
            driver.findElement(By.cssSelector("h3")).text
        )
    }
}