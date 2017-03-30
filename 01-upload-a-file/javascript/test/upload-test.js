'use strict';

const assert = require('assert');
const {Builder, By, promise, until} = require('selenium-webdriver');
const {fs} = require('fs-extra');
const path = require('path');

promise.USE_PROMISE_MANAGER = false;

describe('Upload Test', function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser('chrome').build();
  });

  afterEach(async function() {
    await driver.quit();
  });

  it('upload a file', async function() {
    let filename = 'some-filejs.txt';
 		let filePath = path.join(process.cwd(), filename);

    await driver.get('http://the-internet.herokuapp.com/upload');
    await driver.findElement(By.id("file-upload")).sendKeys(filePath);
    await driver.findElement(By.id("file-submit")).click();

    let text = await driver.findElement(By.id("uploaded-files")).getText();
    assert.equal(text, filename);
  });
});
