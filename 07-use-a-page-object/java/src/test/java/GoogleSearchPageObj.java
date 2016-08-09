package src.test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearchPageObj {

    private final String BASE_URL = "http://www.google.com";
    WebDriver driver;

    By searchBox       = By.id("gbqfq");
    By searchBoxSubmit = By.id("gbqfb");
    By topSearchResult = By.cssSelector("#rso .g");

    public GoogleSearchPageObj(WebDriver _driver) {
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
