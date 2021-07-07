import org.apache.http.client.methods.HttpHead
import org.apache.http.impl.client.HttpClientBuilder
import org.openqa.selenium.By
import org.openqa.selenium.Cookie
import org.openqa.selenium.firefox.FirefoxDriver
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class DownloadSecureFiles {
    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    @Throws(Exception::class)
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    private fun contentType(file: String): String {
        val extension = file.substringAfterLast(".", "")
        return when(extension){
            "jpg" -> "image/jpeg"
            "pdf" -> "application/pdf"
            "xls" -> "application/vnd.ms-excel"
            "gif", "png", "m3u", "docx", "xlsx", "txt" -> "application/octet-stream"
            else  -> "Unknown file type"
        }
    }

    @Test
    fun download() {
        driver.get("http://admin:admin@the-internet.herokuapp.com/download_secure")
        driver.manage().addCookie(Cookie("rack.session", "true"))
        val link = driver.findElement(By.cssSelector(".example a")).getAttribute("href")
        val httpClient = HttpClientBuilder.create().build()
        val request = HttpHead(link)
        val response = httpClient.execute(request)
        val contentLength = Integer.parseInt(response.getFirstHeader("Content-Length").value)

        assertEquals(
            "image/jpeg",
            response.getFirstHeader("Content-Type").value
        )

        assertNotEquals(
            0,
            contentLength
        )
    }

    @Test
    fun downloadAll() {
        driver.get("http://admin:admin@the-internet.herokuapp.com/download_secure")
        driver.manage().addCookie(Cookie("rack.session", "true"))
        val links = driver.findElements(By.cssSelector(".example a"))
        for (link in links) {
            val linkFile = link.getAttribute("href")
            val httpClient = HttpClientBuilder.create().build()
            val request = HttpHead(linkFile)
            val response = httpClient.execute(request)
            val contentLength = Integer.parseInt(response.getFirstHeader("Content-Length").value)

            assertEquals(
                contentType(linkFile),
                response.getFirstHeader("Content-Type").value
            )

            assertNotEquals(
                0,
                contentLength
            )
        }

    }
}