using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
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
        Driver = new ChromeDriver();
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