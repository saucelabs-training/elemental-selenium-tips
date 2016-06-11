using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

class BasicAuth2
{
    IWebDriver Driver;

    [SetUp]
    public void SetUp()
    {
        Driver = new FirefoxDriver();
        Driver.Navigate().GoToUrl("http://admin:admin@the-internet.herokuapp.com/basic_auth");
    }

    [TearDown]
    public void TearDown()
    {
        Driver.Quit();
    }

    [Test]
    public void AccessBasicAuthPageAfterAuthentication()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/basic_auth");
        string PageMessage = Driver.FindElement(By.CssSelector("p")).Text;
        Assert.That(PageMessage.Contains("Congratulations!"));
    }

}