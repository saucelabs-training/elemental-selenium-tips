using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using System.Threading;

public class MultipleWindows
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
    public void MultipleWindowsExample1()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/windows");
        Driver.FindElement(By.CssSelector(".example a")).Click();
        var Windows = Driver.WindowHandles;

        Driver.SwitchTo().Window(Windows[0]);
        Assert.That(Driver.Title != "New Window");

        Driver.SwitchTo().Window(Windows[1]);
        Assert.That(Driver.Title.Equals("New Window"));
    }

    [Test]
    public void MultipleWindowsExample2()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/windows");
        string FirstWindow = Driver.CurrentWindowHandle;
        string SecondWindow = "";

        Driver.FindElement(By.CssSelector(".example a")).Click();
        Thread.Sleep(1000); // to account for window loading time

        var Windows = Driver.WindowHandles;
        foreach(var Window in Windows)
        {
            if (Window != FirstWindow)
                SecondWindow = Window;
        }

        Driver.SwitchTo().Window(FirstWindow);
        Assert.That(Driver.Title != "New Window");

        Driver.SwitchTo().Window(SecondWindow);
        Assert.That(Driver.Title.Equals("New Window"));
    }
}