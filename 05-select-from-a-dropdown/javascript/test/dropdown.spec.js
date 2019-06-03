const assert = require("assert");
const { Builder, By } = require("selenium-webdriver");

describe("Dropdown", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("select the hard way", async function() {
    await driver.get("http://the-internet.herokuapp.com/dropdown");
    const options = await driver.findElements(By.css("#dropdown option"));
    let selection;
    for (const option in options) {
      if ((await options[option].getText()) === "Option 1") {
        await options[option].click();
        break;
      }
    }
    for (const option in options) {
      if (await options[option].isSelected()) {
        selection = await options[option].getText();
        break;
      }
    }
    assert(selection === "Option 1");
  });

  it("select the simpler way", async function() {
    await driver.get("http://the-internet.herokuapp.com/dropdown");
    await driver
      .findElement(
        By.xpath("//*[@id='dropdown']/option[contains(text(),'Option 1')]")
      )
      .click();
    const selection = await driver
      .findElement(By.css('#dropdown option[selected="selected"]'))
      .getText();
    assert(selection === "Option 1");
  });
});
