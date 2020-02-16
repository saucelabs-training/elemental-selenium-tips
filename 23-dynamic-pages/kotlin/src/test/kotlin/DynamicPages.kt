import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import kotlin.test.AfterTest
import kotlin.test.Test

class DynamicPages {

    val testUrl = "http://the-internet.herokuapp.com/dynamic_loading/2"

    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    private fun waitFor(locator: By) {
        WebDriverWait(driver, 8).until(ExpectedConditions.presenceOfElementLocated(locator))
    }

    /* RETURNS: org.openqa.selenium.NoSuchElementException: Unable to locate element: #finish */
    @Test
    fun `no Such Element Error Test`() {
        driver.get(testUrl)
        driver.findElement(By.cssSelector("#start button")).click()
        assert(driver.findElement(By.cssSelector("#finish")).text == "Hello World!")
    }

    /* WORKS */
    @Test
    fun `webDriver waits 8 seconds Test`() {
        driver.get(testUrl)
        driver.findElement(By.cssSelector("#start button")).click()
        WebDriverWait(driver, 8).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#finish")))
        assert(driver.findElement(By.cssSelector("#finish")).text == "Hello World!")
    }

    /* RETURNS Expected condition failed: waiting for presence of element located by: By.cssSelector: #finish
    * (tried for 2 second(s) with 500 milliseconds interval) */
    @Test
    fun `webDriver waits TimeOutErrorTest`() {
        driver.get(testUrl)
        driver.findElement(By.cssSelector("#start button")).click()
        WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#finish")))
        assert(driver.findElement(By.cssSelector("#finish")).text == "Hello World!")
    }

    /* WORKS */
    @Test
    fun `cleanUp Test`() {
        driver.get(testUrl)
        driver.findElement(By.cssSelector("#start button")).click()
        waitFor(By.cssSelector("#finish"))
        assert(driver.findElement(By.cssSelector("#finish")).text == "Hello World!")
    }
}