import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

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
        assert (result);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}

public class GoogleSearch extends Base {

    WebDriver driver;

    By searchBox = By.id("gbqfq");
    By searchBoxSubmit = By.id("gbqfb");
    By topSearchResult = By.cssSelector("#rso .g");

    public GoogleSearch(WebDriver _driver) {
        super(_driver, "http://www.google.com");
        visit();
        verifyPage();
    }


    public void searchFor(String searchTerm) {
        clear(searchBox);
        type(searchBox, searchTerm);
        clickOn(searchBoxSubmit);
    }

    public boolean searchResultPresent(String searchResult) {
        waitFor().until(displayed(topSearchResult));
        return textOf(topSearchResult).contains(searchResult);
    }


    public void verifyPage() {
        assert (title().contains("Google"));
    }
}

class Base {

    private String BASE_URL;
    private final WebDriver driver;

    public Base(WebDriver _driver, String baseUrl) {
        this.driver = _driver;
        this.BASE_URL = baseUrl;
    }

    public void visit() {
        driver.get(BASE_URL);
    }

    public void visit(String url) {
        driver.get(BASE_URL + url);
    }

    public WebElement find(By locator) {
        return driver.findElement(locator);
    }

    public void clear(By locator) {
        find(locator).clear();
    }

    public void type(By locator, String input) {
        find(locator).sendKeys(input);
    }

    public void clickOn(By locator) {
        find(locator).click();
    }

    public ExpectedCondition displayed(By locator) {
        return ExpectedConditions.presenceOfElementLocated(locator);
    }

    public String textOf(By locator) {
        return find(locator).getText();
    }

    public String title() {
        return driver.getCurrentUrl();
    }

    public WebDriverWait waitFor() {
        return new WebDriverWait(driver, 5);
    }
}