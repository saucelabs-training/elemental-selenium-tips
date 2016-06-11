using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Interactions;
using OpenQA.Selenium.Support.UI;
using System;

public class Hovers
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
    public void MouseHoverDisplays()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/hovers");

        // Hover over desired element
        IWebElement Avatar = Driver.FindElement(By.ClassName("figure"));
        Actions Builder = new Actions(Driver);
        Builder.MoveToElement(Avatar).Build().Perform();

        // Wait until the hover appears
        By Hover = By.ClassName("figcaption");
        WebDriverWait Wait = new WebDriverWait(Driver, TimeSpan.FromSeconds(5));
        Wait.Until(ExpectedConditions.ElementIsVisible(Hover));

        Assert.That(Driver.FindElement(Hover).Displayed);
    }
}