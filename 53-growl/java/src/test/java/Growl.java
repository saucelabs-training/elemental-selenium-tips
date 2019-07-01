package src.test.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Growl {
    WebDriver driver;
    JavascriptExecutor js;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void growlTest() throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com/");

        // Check for jQuery on the page, add it if need be
        js.executeScript("if (!window.jQuery) {" +
                "var jquery = document.createElement('script'); jquery.type = 'text/javascript';" +
                "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';" +
                "document.getElementsByTagName('head')[0].appendChild(jquery);" +
                "}");

        // Use jQuery to add jquery-growl to the page
        js.executeScript("$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

        // Use jQuery to add jquery-growl styles to the page
        js.executeScript("$('head').append('<link rel=\"stylesheet\" " +
                         "href=\"http://the-internet.herokuapp.com/css/jquery.growl.css\" " +
                         "type=\"text/css\" />');");

        // Add small delay for jquery-growl loading
        Thread.sleep(1000);

        // jquery-growl w/ no frills
        js.executeScript("$.growl({ title: 'GET', message: '/' });");

        // jquery-growl w/ colorized output
        js.executeScript("$.growl.error({ title: 'ERROR', message: 'your error message goes here' });");
        js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
        js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
        Thread.sleep(5000);
    }

}