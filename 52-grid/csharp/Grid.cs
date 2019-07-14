using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Remote;
using System;

public class Grid
{
    IWebDriver Driver;

    [SetUp]
    public void SetUp()
    {
        var Options = new FirefoxOptions();
        string GridURL = "http://localhost:4444/wd/hub";
        Driver = new RemoteWebDriver(new Uri(GridURL), Options.ToCapabilities());
    }

    [TearDown]
    public void TearDown()
    {
        Driver.Quit();
    }

    [Test]
    public void BrowserLaunchesOnGrid()
    {
        // Before running make sure Selenium Grid is running and it has at least one node with desired browser
        // launch hub       java -jar ./vendor/selenium-server-standalone.jar -role hub
        // register node    java -jar ./vendor/selenium-server-standalone.jar -role node -hub http://localhost:4444/grid/register

        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com");
        Assert.That(Driver.Title.Equals("The Internet"));
    }
}