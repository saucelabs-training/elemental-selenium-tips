const assert = require("assert");
const { Builder, By, Key } = require("selenium-webdriver");

describe("Highlight elements", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("firefox").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  async function highlight(element, duration = 2000) {
    // store original style so it can be reset later
    const originalStyle = await element.getAttribute("style");

    // style element with callout (e.g., dashed red border)
    await driver.executeScript(
      "arguments[0].setAttribute(arguments[1], arguments[2])",
      element,
      "style",
      "border: 2px solid red; border-style: dashed;"
    );

    // keep element highlighted for the duration and then revert
    await driver.sleep(duration);
    await driver.executeScript(
      "arguments[0].setAttribute(arguments[1], arguments[2])",
      element,
      "style",
      originalStyle
    );
  }

  it("highlights target element", async function() {
    await driver.get("http://the-internet.herokuapp.com/large");
    await highlight(await driver.findElement(By.id("sibling-2.3")));
  });
});
