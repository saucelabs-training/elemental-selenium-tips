using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Support.UI;

public class DisabledElements
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
    public void ElementDisabled()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/dropdown");
        var Dropdown = new SelectElement(Driver.FindElement(By.Id("dropdown")));
        // The SelectedOption we want is selected by default on page load
        Assert.False(Dropdown.SelectedOption.Enabled);
    }
}