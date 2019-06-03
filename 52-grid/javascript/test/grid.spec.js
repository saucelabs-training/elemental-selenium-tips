const assert = require("assert");
const { Builder, By } = require("selenium-webdriver");

describe("Grid", function() {
  let driver;

  beforeEach(async function() {
    const url = "http://localhost:4444/wd/hub";
    const caps = {
      browserName: "chrome"
    };
    driver = await new Builder()
      .usingServer(url)
      .withCapabilities(caps)
      .build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("hello world", async function() {
    await driver.get("http://the-internet.herokuapp.com/");
    assert((await driver.getTitle()) === "The Internet");
  });
});
