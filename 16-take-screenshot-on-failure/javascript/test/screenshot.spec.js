const assert = require("assert");
const { Builder, By } = require("selenium-webdriver");
const fs = require("fs");
const path = require("path");

describe("Screenshot", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    if (this.currentTest.state !== "passed") {
      const testName = this.currentTest.fullTitle().replace(/\s/g, "-");
      const fileName = path.join(__dirname, `screenshot_${testName}.jpg`);
      fs.writeFileSync(fileName, await driver.takeScreenshot(), "base64");
    }
    await driver.quit();
  });

  it("on failure", async function() {
    await driver.get("http://the-internet.herokuapp.com");
    assert(true === false);
  });
});
