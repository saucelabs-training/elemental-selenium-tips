const assert = require("assert");
const { Builder, By, Key } = require("selenium-webdriver");

describe("Tables", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("should sort number column in ascending order", async function() {
    await driver.get("http://the-internet.herokuapp.com/tables");
    await driver
      .findElement(By.css("#table1 thead tr th:nth-of-type(4)"))
      .click();
    const due_column = await driver.findElements(
      By.css("#table1 tbody tr td:nth-of-type(4)")
    );
    let dues = [];
    for (const entry in due_column) {
      const text = await due_column[entry].getText();
      dues.push(Number(text.replace("$", "")));
    }
    assert(dues === dues.sort());
  });

  it("should sort number column in descending order", async function() {
    await driver.get("http://the-internet.herokuapp.com/tables");
    await driver
      .findElement(By.css("#table1 thead tr th:nth-of-type(4)"))
      .click();
    await driver
      .findElement(By.css("#table1 thead tr th:nth-of-type(4)"))
      .click();
    const due_column = await driver.findElements(
      By.css("#table1 tbody tr td:nth-of-type(4)")
    );
    let dues = [];
    for (const entry in due_column) {
      const text = await due_column[entry].getText();
      dues.push(Number(text.replace("$", "")));
    }
    assert(dues === dues.sort().reverse());
  });

  it("should sort text column in ascending", async function() {
    await driver.get("http://the-internet.herokuapp.com/tables");
    await driver
      .findElement(By.css("#table1 thead tr th:nth-of-type(3)"))
      .click();
    const email_column = await driver.findElements(
      By.css("#table1 tbody tr td:nth-of-type(3)")
    );
    let emails = [];
    for (const entry in email_column) {
      emails.push(await email_column[entry].getText());
    }
    assert(emails === emails.sort());
  });

  it("sort number column in ascending order with helpful locators", async function() {
    await driver.get("http://the-internet.herokuapp.com/tables");
    driver.findElement(By.css("#table2 thead .dues")).click();
    const due_column = await driver.findElements(By.css("#table2 tbody .dues"));
    let dues = [];
    for (const entry in due_column) {
      const text = await due_column[entry].getText();
      dues.push(Number(text.replace("$", "")));
    }
    assert(dues === dues.sort());
  });
});
