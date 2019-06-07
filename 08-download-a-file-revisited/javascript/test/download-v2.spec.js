const assert = require("assert");
const { Builder, By } = require("selenium-webdriver");
const http = require("http");
const url = require("url");

describe("File download v2", function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser("chrome").build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  function getHttpOptions(target) {
    const _url = url.parse(target);
    return {
      method: "HEAD",
      protocol: _url.protocol,
      hostname: _url.hostname,
      path: _url.path
    };
  }

  it("verify a file download by inspecting its head request", async function() {
    await driver.get("http://the-internet.herokuapp.com/download");
    const download_url = await driver
      .findElement(By.css(".example a"))
      .getAttribute("href");
    const request = http.request(getHttpOptions(download_url), response => {
      assert(response.headers["content-type"] === "application/octet-stream");
      assert(Number(response.headers["content-length"]) > 0);
    });
    request.end();
  });
});
