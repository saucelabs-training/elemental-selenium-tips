using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Interactions;

public class RightClick
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
    public void RightClickExample()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/context_menu");
        IWebElement MenuArea = Driver.FindElement(By.Id("hot-spot"));
        Actions Builder = new Actions(Driver);
        Builder.ContextClick(MenuArea).Perform();
        IAlert Alert = Driver.SwitchTo().Alert();
        Assert.That(Alert.Text.Equals("You selected a context menu"));
    }
}