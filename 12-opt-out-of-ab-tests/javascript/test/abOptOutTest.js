'use strict';

const assert = require('assert');
const {Builder, By, promise, until} = require('selenium-webdriver');
const {fs} = require('fs-extra');
const path = require('path');

promise.USE_PROMISE_MANAGER = false;

describe('AB Opt Out Tests', function() {
  let driver;

  beforeEach(async function() {
    driver = await new Builder().forBrowser('chrome').build();
  });

  after(async function() {
    await driver.quit();
  });

  it('withCookieAfterVisitingPage', async function() {
      async function startsWith(str, match) {
          return str.lastIndexOf(match, 0) === 0;
      }
    await driver.get("http://the-internet.herokuapp.com/abtest");
    let headingText = await driver.findElement(By.css("h3")).getText();
    await startsWith(headingText, "A/B Test");
    await driver.manage().addCookie({name: 'optimizelyOptOut', value: 'true'});
    await driver.navigate().refresh();
    headingText = await driver.findElement(By.css("h3")).getText();
    assert.equal(headingText, "No A/B Test");
  });

  it('withCookieBeforeVisitingPage', async function() {
  await driver.get("http://the-internet.herokuapp.com");
  await driver.manage().addCookie({name: 'optimizelyOptOut', value: 'true'});
  await driver.get("http://the-internet.herokuapp.com/abtest");
  assert.equal( await driver.findElement(By.css("h3")).getText(), ("No A/B Test"));
  });

  it('withOptOutUrl', async function() {
  await driver.get("http://the-internet.herokuapp.com/abtest?optimizely_opt_out=true");
  await driver.switchTo().alert().dismiss();
  assert.equal( await driver.findElement(By.css("h3")).getText(), ("No A/B Test"));
  });


});
