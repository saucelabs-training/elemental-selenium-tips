const assert = require("assert");
const { Builder, By, Key } = require("selenium-webdriver");

describe("Keyboard Keys", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("send keys", async function() {
    await driver.get("http://the-internet.herokuapp.com/key_presses");
    await driver.findElement(By.id("target")).sendKeys(Key.SPACE);
    assert(
      (await driver.findElement(By.id("result")).getText()) ===
        "You entered: SPACE"
    );
    await driver
      .actions({ bridge: true })
      .sendKeys(Key.TAB)
      .perform();
    assert(
      (await driver.findElement(By.id("result")).getText()) ==
        "You entered: TAB"
    );
  });
});
