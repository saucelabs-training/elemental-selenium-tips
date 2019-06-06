const assert = require("assert");
const { Builder, By, Key } = require("selenium-webdriver");
const firefox = require("selenium-webdriver/firefox");
const fs = require("fs");
const path = require("path");

describe("File download", function() {
  let driver;
  const tmpDir = path.join(__dirname, "tmp");

  beforeEach(async function() {
    if (!fs.existsSync(tmpDir)) fs.mkdirSync(tmpDir);
    let options = new firefox.Options()
      .setPreference("browser.download.dir", tmpDir)
      .setPreference("browser.download.folderList", 2)
      .setPreference(
        "browser.helperApps.neverAsk.saveToDisk",
        "images/jpeg, application/pdf, application/octet-stream"
      )
      .setPreference("pdfjs.disabled", true);

    driver = await new Builder()
      .forBrowser("firefox")
      .setFirefoxOptions(options)
      .build();
  });

  function cleanupTmpDir() {
    if (fs.existsSync(tmpDir)) {
      const files = fs.readdirSync(tmpDir).map(file => path.join(tmpDir, file));
      files.forEach(file => fs.unlinkSync(file));
      fs.rmdirSync(tmpDir);
    }
  }

  afterEach(async function() {
    await driver.quit();
    cleanupTmpDir();
  });

  it("should automatically download to local disk", async function() {
    await driver.get("http://the-internet.herokuapp.com/download");
    await driver.findElement(By.css(".example a")).click();
    const files = fs.readdirSync(tmpDir).map(file => path.join(tmpDir, file));
    assert(files.length);
    assert(fs.statSync(files[0]).size);
  });
});
