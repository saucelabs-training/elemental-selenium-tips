using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using System.Threading;

namespace Growl
{
    public class Growl
    {
        IWebDriver Driver;
        IJavaScriptExecutor JSDriver;

        [SetUp]
        public void SetUp()
        {
            Driver = new FirefoxDriver();
            JSDriver = (IJavaScriptExecutor) Driver;
        }

        [TearDown]
        public void TearDown()
        {
            Driver.Quit();
        }

        [Test]
        public void GrowlNotificationExample()
        {
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com");

            JSDriver.ExecuteScript("if (!window.jQuery) {" +
                                   "var jquery = document.createElement('script'); jquery.type = 'text/javascript';" +
                                   "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';" +
                                   "document.getElementsByTagName('head')[0].appendChild(jquery);" +
                                   "}");
            JSDriver.ExecuteScript("$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");
            JSDriver.ExecuteScript("$('head').append(\"<link rel='stylesheet' " +
                                   "href='http://the-internet.herokuapp.com/css/jquery.growl.css' " +
                                   "type='text/css' />\");");
            JSDriver.ExecuteScript("$.growl({ title: 'GET', message: '/' });");
            JSDriver.ExecuteScript("$.growl.error({ title: 'ERROR', message: 'your message goes here' });");
            JSDriver.ExecuteScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
            JSDriver.ExecuteScript("$.growl.notice({ title: 'Warning!', message: 'your warning message goes here' });");
            Thread.Sleep(5000);
        }
    }
}