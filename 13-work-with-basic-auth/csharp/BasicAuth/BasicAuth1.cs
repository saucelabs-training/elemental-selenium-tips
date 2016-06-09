using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

namespace BasicAuth
{
    public class BasicAuth1
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
        public void ConnectToBasicAuthByURL()
        {
            Driver.Navigate().GoToUrl("http://admin:admin@the-internet.herokuapp.com/basic_auth");
            string PageMessage = Driver.FindElement(By.CssSelector("p")).Text;
            Assert.That(PageMessage.Contains("Congratulations!"));
        }
    }
}
