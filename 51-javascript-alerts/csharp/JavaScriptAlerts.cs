using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

public class JavaScriptAlerts
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
    public void JavaScriptAlertAppearsAndAccepted()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/javascript_alerts");
        Driver.FindElement(By.CssSelector(".example li:nth-child(2) button")).Click();
        IAlert Popup = Driver.SwitchTo().Alert();
        Popup.Accept();
        string Result = Driver.FindElement(By.Id("result")).Text;
        Assert.That(Result.Equals("You clicked: Ok"));
    }

}