const assert = require("assert");
const { Builder, By } = require("selenium-webdriver");

describe("Frame Test", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("firefox").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("nested_frames", async function() {
    await driver.get("http://the-internet.herokuapp.com/nested_frames");
    await driver
      .switchTo()
      .frame(await driver.findElement(By.name("frame-top")));
    await driver
      .switchTo()
      .frame(await driver.findElement(By.name("frame-middle")));
    let content = await driver.findElement(By.id("content")).getText();
    assert.equal(content, "MIDDLE");
  });

  it("iframes", async function() {
    await driver.get("http://the-internet.herokuapp.com/tinymce");
    await driver.switchTo().frame(await driver.findElement(By.id("mce_0_ifr")));
    const editor = await driver.findElement(By.id("tinymce"));
    let beforeText = await editor.getText();
    await editor.clear();
    await editor.sendKeys("Hello World!");
    let afterText = await editor.getText();
    assert.notEqual(afterText, beforeText);
    await driver.switchTo().defaultContent();
    let txt = await driver.findElement(By.css("h3")).getText();
    assert.equal(txt, "An iFrame containing the TinyMCE WYSIWYG Editor");
  });
});
