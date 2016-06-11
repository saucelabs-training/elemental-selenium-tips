using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using System;
using System.Collections.Generic;

public class Checkboxes
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
    public void CheckboxDiscovery()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/checkboxes");
        IReadOnlyCollection<IWebElement> Checkboxes = Driver.FindElements(By.CssSelector("input[type=\"checkbox\"]"));

        Console.Write("With .attribute('checked')");
        foreach(IWebElement Checkbox in Checkboxes)
        {
            Console.WriteLine(Checkbox.GetAttribute("checked"));
        }

        Console.WriteLine("With .selected?");
        foreach(IWebElement Checkbox in Checkboxes)
        {
            Console.WriteLine(Checkbox.Selected);
        }
    }

    [Test]
    public void GetCheckBoxStatusByAttribute()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/checkboxes");
        IWebElement Checkbox = Driver.FindElement(By.CssSelector("form input:nth-of-type(2)"));
        Assert.That(Checkbox.GetAttribute("checked") == "true");
    }

    [Test]
    public void GetCheckBoxesStatusByQuery()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/checkboxes");
        IWebElement Checkbox = Driver.FindElement(By.CssSelector("form input:nth-of-type(2)"));
        Assert.That(Checkbox.Selected);
    }
}