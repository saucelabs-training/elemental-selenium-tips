import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class GoogleSearchPageObj(_driver: WebDriver) {
    private val testUrl = "http://www.google.com"
    private val driver = _driver
    private val searchBox = By.xpath("//input[@class='gLFyf gsfi']")
    private val topSearchResult = By.cssSelector("#rso .g")

    init {
        visit()
        assert(verifyPage())
    }

    private fun visit() {
        driver.get(this.testUrl)
    }

    fun searchFor(searchTerm: String) {
        driver.findElement(searchBox).clear()
        driver.findElement(searchBox).sendKeys(searchTerm)
        driver.findElement(searchBox).sendKeys(Keys.ENTER)
    }

    fun searchResultPresent(searchResult: String): Boolean {
        waitFor(topSearchResult)
        val str = driver.findElement(topSearchResult).text.contains(searchResult).toString()
        return driver.findElement(topSearchResult).text.contains(searchResult)
    }

    private fun waitFor(locator: By) {
        WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(locator))
    }

    private fun verifyPage(): Boolean {
        return driver.currentUrl.contains("google")
    }
}