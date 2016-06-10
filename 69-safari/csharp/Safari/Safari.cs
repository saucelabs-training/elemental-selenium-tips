using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Safari;

public class Safari
{
    IWebDriver Driver;

    [SetUp]
    public void SetUp()
    {
        Driver = new SafariDriver();
    }

    [TearDown]
    public void TearDown()
    {
        Driver.Quit();
    }

    [Test]
    public void SimpleTest()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com");
        Assert.That(Driver.Title.Equals("The Internet"));
    }
}