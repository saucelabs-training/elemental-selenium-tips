using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Interactions;

public class KeyboardKeys
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
    public void KeyboardKeysExample()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/key_presses");

        // Option 1
        Driver.FindElement(By.Id("target")).SendKeys(Keys.Space);
        Assert.That(Driver.FindElement(By.Id("result")).Text.Equals("You entered: SPACE"));

        // Option 2
        Actions Builder = new Actions(Driver);
        Builder.SendKeys(Keys.Left).Build().Perform();
        Assert.That(Driver.FindElement(By.Id("result")).Text.Equals("You entered: LEFT"));
    }
}