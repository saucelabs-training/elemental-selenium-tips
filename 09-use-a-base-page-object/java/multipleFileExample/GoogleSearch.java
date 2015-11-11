package num9;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleSearch extends Base {

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
