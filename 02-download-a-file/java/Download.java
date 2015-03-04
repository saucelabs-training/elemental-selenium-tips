import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class Download {
    WebDriver driver;
    File folder;

    @Before
    public void setUp() throws Exception {
        folder = new File(UUID.randomUUID().toString());
        folder.mkdir();
//        Firefox
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.dir", folder.getAbsolutePath());
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/jpeg, application/pdf");
        profile.setPreference("pdfjs.disabled", true);
        driver = new FirefoxDriver(profile);

//        Chrome
//        System.setProperty("webdriver.chrome.driver", "vendor/chrome-driver-2.14/chromedriver_linux64");
//        ChromeOptions options = new ChromeOptions();
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("profile.default_content_settings.popups", 0);
//        prefs.put("download.default_directory", folder.getAbsolutePath());
//        options.setExperimentalOption("prefs", prefs);
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        driver = new ChromeDriver(capabilities);
    }

    @Test
    public void download() throws Exception {
        driver.get("http://the-internet.herokuapp.com/download");
        driver.findElement(By.cssSelector(".example a")).click();
//        wait 2 seconds to download
        Thread.sleep(2000);
        File[] listOfFiles = folder.listFiles();
        assertThat(listOfFiles.length, greaterThan(0));
        for (File file : listOfFiles) {
            assertThat(file.length(), greaterThan((long) 0));
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}