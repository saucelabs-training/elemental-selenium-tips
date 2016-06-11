using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using System;

public class ABTestOptOut
{
    IWebDriver Driver;

    [SetUp]
    public void SetUp()
    {
        Driver = new FirefoxDriver();
    }

    [TearDown]
    public void TearDown()
    {
        Driver.Quit();
    }

    [Test]
    public void OptOutWithCookieAfterVisitingPage()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/abtest");
        string HeadingText = Driver.FindElement(By.TagName("h3")).Text;
        Assert.That(HeadingText.StartsWith("A/B Test"));
        Driver.Manage().Cookies.AddCookie(new Cookie("optimizelyOptOut", "true"));
        Driver.Navigate().Refresh();
        HeadingText = Driver.FindElement(By.TagName("h3")).Text;
        Assert.That(HeadingText.StartsWith("No A/B Test"));
    }

    [Test]
    public void OptOutWithCookieBeforeVisitingPage()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com");
        Driver.Manage().Cookies.AddCookie(new Cookie("optimizelyOptOut", "true"));
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/abtest");
        string HeadingText = Driver.FindElement(By.TagName("h3")).Text;
        Assert.That(HeadingText.StartsWith("No A/B Test"));
    }

    [Test]
    public void OptOutWithURL()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/abtest?optimizely_opt_out=true");
        Driver.SwitchTo().Alert().Dismiss();
        string HeadingText = Driver.FindElement(By.TagName("h3")).Text;
        Assert.That(HeadingText.StartsWith("No A/B Test"));
    }
}