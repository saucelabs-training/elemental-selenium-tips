import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import kotlin.test.AfterTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import org.openqa.selenium.support.ui.WebDriverWait

class MultipleWindows {

    private val testUrl = "http://the-internet.herokuapp.com/windows"
    private val firstTabTitle = "The Internet"
    private val secondTabTitle = "New Window"

    private val driver by lazy {
        FirefoxDriver()
    }

    private val wait by lazy {
        WebDriverWait(driver, 20)
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun multipleWindows() {
        driver.get(testUrl)
        driver.findElement(By.cssSelector(".example a")).click()

        val allWindows = driver.windowHandles.toTypedArray()

        driver.switchTo().window(allWindows[0].toString())
        wait.until(ExpectedConditions.titleIs(firstTabTitle))
        assertNotEquals(
            secondTabTitle,
            driver.title
        )


        driver.switchTo().window(allWindows[1].toString())
        wait.until(ExpectedConditions.titleIs(secondTabTitle))
        assertEquals(
            secondTabTitle,
            driver.title
        )
    }

    @Test
    fun multipleWindowsRedux() {
        driver.get(testUrl)

        // Get initial window handle
        val firstWindow = driver.windowHandle

        // Trigger new window to open
        driver.findElement(By.cssSelector(".example a")).click()

        // Grab all window handles
        val allWindows = driver.windowHandles

        // Iterate through window handles collection
        // Find the new window handle, storing it in the newWindow variable
        for (window in allWindows) {
            if (window != firstWindow) {
                // Switch to the new window & verify
                driver.switchTo().window(window)
                wait.until(ExpectedConditions.titleIs(secondTabTitle))
                assertEquals(
                    secondTabTitle,
                    driver.title
                )
            }
        }

        // Switch to the first window & verify
        driver.switchTo().window(firstWindow)
        wait.until(ExpectedConditions.titleIs(firstTabTitle))
        assertNotEquals(
            secondTabTitle,
            driver.title
        )
    }
}