const assert = require("assert");
const { Builder, By } = require("selenium-webdriver");

describe("A/B opt-out", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("with cookie after visiting page", async function() {
    await driver.get("http://the-internet.herokuapp.com/abtest");
    let headingText = await driver.findElement(By.css("h3")).getText();
    if (headingText.startsWith("A/B Test")) {
      await driver
        .manage()
        .addCookie({ name: "optimizelyOptOut", value: "true" });
      await driver.navigate().refresh();
      headingText = await driver.findElement(By.css("h3")).getText();
    }
    assert.equal(headingText, "No A/B Test");
  });

  it("with cookie before visiting page", async function() {
    await driver.get("http://the-internet.herokuapp.com");
    await driver
      .manage()
      .addCookie({ name: "optimizelyOptOut", value: "true" });
    await driver.get("http://the-internet.herokuapp.com/abtest");
    assert.equal(
      await driver.findElement(By.css("h3")).getText(),
      "No A/B Test"
    );
  });

  it("with opt-out URL", async function() {
    await driver.get(
      "http://the-internet.herokuapp.com/abtest?optimizely_opt_out=true"
    );
    await driver
      .switchTo()
      .alert()
      .dismiss();
    assert.equal(
      await driver.findElement(By.css("h3")).getText(),
      "No A/B Test"
    );
  });
});
