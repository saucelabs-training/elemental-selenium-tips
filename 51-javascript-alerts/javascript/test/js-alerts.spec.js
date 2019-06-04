const assert = require("assert");
const { Builder, By, Key } = require("selenium-webdriver");

describe("JS Alerts", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("general use", async function() {
    await driver.get("http://the-internet.herokuapp.com/javascript_alerts");
    await driver.findElement(By.css("ul > li:nth-child(2) > button")).click();
    const popup = await driver.switchTo().alert();
    popup.accept();
    const result = await driver.findElement(By.id("result")).getText();
    assert(result == "You clicked: Ok");
  });
});
