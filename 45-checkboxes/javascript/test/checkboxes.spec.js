const assert = require("assert");
const { Builder, By } = require("selenium-webdriver");

describe("Checkboxes", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("should list values for different approaches", async function() {
    await driver.get("http://the-internet.herokuapp.com/checkboxes");
    const checkboxes = await driver.findElements(
      By.css('input[type="checkbox"]')
    );

    console.log("With .getAttribute('checked')");
    for (let checkbox in checkboxes)
      console.log(await checkboxes[checkbox].getAttribute("checked"));

    console.log("\nWith .is_selected");
    for (let checkbox in checkboxes)
      console.log(await checkboxes[checkbox].isSelected());

    assert(checkboxes[checkboxes.length - 1].getAttribute("checked"));
    assert(checkboxes[checkboxes.length - 1].isSelected());
    assert(checkboxes[0].getAttribute("checked"));
    assert(checkboxes[0].isSelected());
  });
});
