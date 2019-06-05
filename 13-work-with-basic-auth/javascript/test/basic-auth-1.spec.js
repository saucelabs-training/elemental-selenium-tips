const assert = require("assert");
const { Builder, By, Key } = require("selenium-webdriver");

describe("Basic Auth", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("should visit basic auth secured page directly", async function() {
    await driver.get(
      "http://admin:admin@the-internet.herokuapp.com/basic_auth"
    );
    const page_message = await driver
      .findElement(By.css(".example p"))
      .getText();
    assert(
      page_message === "Congratulations! You must have the proper credentials."
    );
  });
});
