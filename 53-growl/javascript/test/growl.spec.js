const assert = require("assert");
const { Builder, By, Key } = require("selenium-webdriver");

describe("Growl", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("firefox").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it("runs and shows growl debugging output", async function() {
    await driver.get("http://the-internet.herokuapp.com");

    // check for jQuery on the page, add it if needbe
    await driver.executeScript(
      `if (!window.jQuery) { var jquery = document.createElement('script'); jquery.type = 'text/javascript'; jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js'; document.getElementsByTagName('head')[0].appendChild(jquery);}`
    );

    // use jQuery to add jquery-growl to the page
    await driver.executeScript(
      "$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js');"
    );

    // use jQuery to add jquery-growl styles to the page
    await driver.executeScript(
      `$('head').append("<link rel=stylesheet href=http://the-internet.herokuapp.com/css/jquery.growl.css type=text/css />");`
    );

    await driver.sleep(1000);

    await driver.executeScript("$.growl({ title: 'GET', message: '/' });");
    await driver.executeScript(
      "$.growl.error({ title: 'ERROR', message: 'your error message goes here' });"
    );
    await driver.executeScript(
      "$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });"
    );
    await driver.executeScript(
      "$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });"
    );

    await driver.sleep(3000);
  });
});
