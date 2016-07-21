'use strict';

var fs = require('fs'),
    chromePath = require('chromedriver').path,
    webdriver = require('selenium-webdriver'),
    chrome = require('selenium-webdriver/chrome'),
    test = require('selenium-webdriver/lib/test'),
    assert = require('selenium-webdriver/testing/assert'),
    path = require('path'),
    By = webdriver.By,
    until = webdriver.until;

describe('Using Selenium', function(){

	var driver;

	// create driver instance
	test.before(function() {
		var service = new chrome.ServiceBuilder(chromePath).build();
		chrome.setDefaultService(service);

		driver = new webdriver.Builder()
		.withCapabilities(webdriver.Capabilities.chrome())
		.build();
	});

	// quit browser
	test.after(function() {
		driver.quit();
	});

	test.it('I should be able to upload file', function(){
		var filename = 'some-file.txt';
		var filePath = path.join(process.cwd(), filename);
		fs.writeFile(filename, 'w');

		driver.get("http://the-internet.herokuapp.com/upload");
		driver.findElement(By.id("file-upload")).sendKeys(filePath);
		driver.findElement(By.id("file-submit")).click();

		var label = driver.findElement(By.id("uploaded-files"));
		assert(label.getText()).equalTo(filename);
	});
});