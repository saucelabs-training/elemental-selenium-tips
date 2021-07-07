import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import kotlin.test.AfterTest
import kotlin.test.Test

class LocatorStrategy {

    val testUrl = "http://the-internet.herokuapp.com/download"

    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun `locator Strategy CSS`() {
        driver.get(testUrl)
        val link = driver.findElement(By.cssSelector("a")).getAttribute("href")
        println(link)
    }

    @Test
    fun `locator Stategy Precise CSS`() {
        driver.get(testUrl)
        val link = driver.findElement(By.cssSelector("#content a")).getAttribute("href")
        println(link)
    }

    @Test
    fun `locator Strategy Exact CSS`() {
        driver.get(testUrl)
        val link = driver.findElement(By.cssSelector("#content .example a")).getAttribute("href")
        println(link)
    }

    @Test
    fun `locator Strategy XPATH`() {
        driver.get(testUrl)
        val link = driver.findElement(By.xpath("//*[@class='row']//a")).getAttribute("href")
        println(link)
        val linkPrecise = driver.findElement(By.xpath("//*[@id='content']//a")).getAttribute("href")
        println(linkPrecise)
        val linkList = driver.findElements(By.xpath("//*[@class='row']//a"))[0].getAttribute("href")
        println(linkList)
        val linkPreciseList = driver.findElements(By.xpath("//*[@id='content']//a"))[0].getAttribute("href")
        println(linkPreciseList)
    }
}