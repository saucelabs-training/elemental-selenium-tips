package src.test.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by andrew on 8/22/15.
 */
public class RetryTestActions {
    public class WorkWithBasicAuth {
        WebDriver driver;

        @Before
        public void setUp() throws Exception {
            driver = new FirefoxDriver();
        }

        @Test
        public void retryTestActionsTest() {
            driver.get("http://the-internet.herokuapp.com/notification_message");
            assert retryIfNotificationMessageContains("please try again");
        }

        @After
        public void tearDown() throws Exception {
            driver.quit();
        }


        public String getNotificationMessage() {
            return driver.findElement(By.id("flash")).getText();
        }

        public boolean retryIfNotificationMessageContains(String message) {
            for (int count = 0; count < 3; count++) {
                if (getNotificationMessage().contains(message))
                    driver.navigate().refresh();
                else
                    return true;
            }
            return false;
        }
    }
}
