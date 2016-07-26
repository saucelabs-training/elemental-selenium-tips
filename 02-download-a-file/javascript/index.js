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
	var downloadDir = path.join(process.cwd(), "downloads");

	// create driver instance
	test.before(function() {

		// create download directory in root if not exist
		if (!fs.existsSync(downloadDir))
			fs.mkdirSync(downloadDir);

		var service = new chrome.ServiceBuilder(chromePath).build();
		chrome.setDefaultService(service);

		var options = new chrome.Options();
		options.addArguments("start-maximized");
		
		/*
		Below options does not work for chrome 
		https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/5546

		options.addArguments("download.prompt_for_download=false");
		options.addArguments("download.directory_upgrade=true");
		options.addArguments("download.default_directory=/Users/gulshansaini/elemental-selenium-tips/02-download-a-file/javascript/downloads/");
		*/

		driver = new webdriver.Builder()
		.setChromeOptions(options)
		.build();

		// Set downloads directory for chrome
		driver.get("chrome://settings-frame/");
		driver.findElement(By.id("advanced-settings-expander")).click();
		var script = "Preferences.setStringPref('download.default_directory', '" + downloadDir + "', true)";
		driver.executeScript(script);
	});

	// quit browser
	test.after(function() {
		driver.quit();
	});

	test.it('I should be able to download file', function(){
		driver.get("http://the-internet.herokuapp.com/download");
		driver.findElement(By.css(".example a")).click();
		// Wait 3 second for download to complete
		driver.sleep(3000);
		fs.readdir(downloadDir, function(err,list){
			assert(1).equalTo(list.length);
		});
	});

});