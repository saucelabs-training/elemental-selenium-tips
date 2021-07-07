import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import kotlin.test.assertEquals
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertNotEquals
import org.apache.http.client.methods.HttpHead
import org.apache.http.impl.client.HttpClientBuilder

class DownloadFileRevisited {
    private val testUrl = "http://the-internet.herokuapp.com/download"

    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    @Throws(Exception::class)
    fun downloadFileRevisitedTest() {
        driver.get(testUrl)
        val link = driver.findElement(By.cssSelector(".example a:nth-of-type(1)")).getAttribute("href")
        val httpClient = HttpClientBuilder.create().build()
        val request = HttpHead(link)
        val response = httpClient.execute(request)
        val contentType = response.getFirstHeader("Content-Type").getValue()
        val contentLength = Integer.parseInt(response.getFirstHeader("Content-Length").getValue())

        assertEquals(
            "application/octet-stream",
            contentType
        )

        assertNotEquals(
            0,
            contentLength
        )
    }
}