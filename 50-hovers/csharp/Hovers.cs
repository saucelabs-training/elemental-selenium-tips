using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Interactions;

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

        IWebElement Avatar = Driver.FindElement(By.ClassName("figure"));
        Actions Builder = new Actions(Driver);
        Builder.MoveToElement(Avatar).Build().Perform();

        By Hover = By.ClassName("figcaption");
        Assert.That(Driver.FindElement(Hover).Displayed);
    }
}