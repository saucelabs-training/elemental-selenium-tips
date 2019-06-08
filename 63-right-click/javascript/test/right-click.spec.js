const assert = require("assert");
const { Builder, By, Key } = require("selenium-webdriver");

describe("Right click", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("firefox").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("displays browser context menu", async function() {
    await driver.get("http://the-internet.herokuapp.com/context_menu");
    const menuArea = await driver.findElement(By.id("hot-spot"));
    await driver
      .actions({ bridge: true })
      .contextClick(menuArea)
      .perform();
    const alertText = await driver
      .switchTo()
      .alert()
      .getText();
    assert(alertText === "You selected a context menu");
  });
});
