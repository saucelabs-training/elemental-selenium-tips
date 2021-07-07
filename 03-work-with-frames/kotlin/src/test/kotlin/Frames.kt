import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import kotlin.test.AfterTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Frames {

    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun `test of the nested frames`() {
        driver.get("http://the-internet.herokuapp.com/nested_frames")
        driver.switchTo().frame("frame-top")
        driver.switchTo().frame("frame-middle")

        val expected = "MIDDLE"
        val actual : String = driver.findElement(By.id("content")).text

        assertEquals(
                expected,
                actual
        )
    }

    @Test
    fun `iFrame with TinyMCE`() {
        driver.get("http://the-internet.herokuapp.com/tinymce")
        driver.switchTo().frame("mce_0_ifr")

        val editor = driver.findElement(By.id("tinymce"))
        val beforeText = editor.text

        editor.clear()
        editor.sendKeys("Hello World!")

        val afterText = editor.text

        assertNotEquals(
                beforeText,
                afterText
        )

        driver.switchTo().defaultContent()

        assertEquals(
                "An iFrame containing the TinyMCE WYSIWYG Editor",
                driver.findElement(By.cssSelector("h3")).text
        )
    }
}