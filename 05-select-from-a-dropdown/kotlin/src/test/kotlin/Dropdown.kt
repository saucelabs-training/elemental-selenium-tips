import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.Select
import kotlin.test.assertEquals
import kotlin.test.AfterTest

class Dropdown {
    private val testUrl = "http://the-internet.herokuapp.com/dropdown"

    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun dropdownTest() {
        driver.get(testUrl)
        val dropdownList = driver.findElement(By.id("dropdown"))
        val options = dropdownList.findElements(By.tagName("option"))
        for (i in options.indices) {
            if (options[i].text == "Option 1") {
                options[i].click()
            }
        }
        for (i in options.indices) {
            if (options[i].isSelected) {
                assertEquals(
                        "Option 1",
                        options[i].text
                )
            }
        }

    }

    @Test
    fun dropdownTestRedux() {
        driver.get(testUrl)
        val selectList = Select(driver.findElement(By.id("dropdown")))
        selectList.selectByVisibleText("Option 1")
        // You could also use select.selectByValue("1");
        assertEquals(
                "Option 1",
                selectList.firstSelectedOption.text
        )
    }
}