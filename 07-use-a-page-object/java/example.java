import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by andrew on 8/22/15.
 */

public class GoogleSearchTest {

    WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @Test
    public void workWithBasicAuthTest() {
        GoogleSearch google = new GoogleSearch(driver);
        google.searchFor("elemental selenium tips");
        boolean result = google.searchResultPresent("Recieve a Free, Weekly tip");
        assert (result == true);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}

class GoogleSearch {

    private final String BASE_URL = "http://www.google.com";
    WebDriver driver;

    By searchBox = By.id("gbqfq");
    By searchBoxSubmit = By.id("gbqfb");
    By topSearchResult = By.cssSelector("#rso .g");

    public GoogleSearch(WebDriver _driver) {
        this.driver = _driver;
        visit();
        assert (verifyPage() == true);
    }

    public void visit() {
        driver.get(this.BASE_URL);
    }

    public void searchFor(String searchTerm) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(searchTerm);
        driver.findElement(searchBoxSubmit).click();
    }

    public boolean searchResultPresent(String searchResult) {
        waitFor(topSearchResult);
        return driver.findElement(topSearchResult).getText().contains(searchResult);
    }
    
    public void waitFor(By locator) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean verifyPage() {
        return driver.getCurrentUrl().contains("Google");
    }
}
