const assert = require("assert");
const { Builder, By } = require("selenium-webdriver");

describe("Disabled Element", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("dropdown list should contain disabled and enabled elements", async function() {
    driver.get("http://the-internet.herokuapp.com/dropdown");
    const dropdownList = await driver.findElements(By.css("option"));
    assert((await dropdownList[0].isEnabled()) === false);
    assert(await dropdownList[1].isEnabled());
  });
});
