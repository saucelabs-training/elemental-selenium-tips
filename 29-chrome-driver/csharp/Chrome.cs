using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Remote;
using System;
using System.IO;

public class Chrome
{
    IWebDriver Driver;
    string VendorDirectory = Directory.GetParent(
        Path.GetDirectoryName(typeof(Chrome).Assembly.Location)).
            Parent.FullName + @"\Vendor";

    [SetUp]
    public void SetUp()
    {
        Driver = new ChromeDriver(VendorDirectory);
        //DesiredCapabilities Capabilities = new DesiredCapabilities();
        //DesiredCapabilities.Chrome();
        //Driver = new RemoteWebDriver(new Uri("http://localhost:9515"), Capabilities);
    }

    [TearDown]
    public void TearDown()
    {
        Driver.Quit();
    }

    [Test]
    public void PageLoads()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com");
        Assert.That(Driver.Title.Equals("The Internet"));
    }
}