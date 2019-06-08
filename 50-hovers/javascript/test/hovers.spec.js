const assert = require("assert");
const { Builder, By, Key } = require("selenium-webdriver");

describe("Hovers", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("firefox").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("displays caption on hover", async function() {
    await driver.get("http://the-internet.herokuapp.com/hovers");
    const avatar = await driver.findElement(By.css(".figure"));
    await driver
      .actions({ bridge: true })
      .move({ origin: avatar })
      .perform();
    const caption = await driver.findElement(By.css(".figcaption"));
    assert(caption.isDisplayed());
  });
});
