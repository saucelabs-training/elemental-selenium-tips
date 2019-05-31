const assert = require("assert");
const { Builder, By } = require("selenium-webdriver");
const path = require("path");

describe("Upload Test", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("firefox").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("upload a file", async function() {
    let filename = "some-file.txt";
    let filePath = path.join(process.cwd(), filename);

    await driver.get("http://the-internet.herokuapp.com/upload");
    await driver.findElement(By.id("file-upload")).sendKeys(filePath);
    await driver.findElement(By.id("file-submit")).click();

    let text = await driver.findElement(By.id("uploaded-files")).getText();
    assert.equal(text, filename);
  });
});
