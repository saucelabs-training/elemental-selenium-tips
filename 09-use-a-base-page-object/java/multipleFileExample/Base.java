package num9;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {

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
