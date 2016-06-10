using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using System.Threading;

namespace HighlightElements
{
    public class HighlightElements
    {
        IWebDriver Driver;
        IJavaScriptExecutor JSDriver;

        [SetUp]
        public void SetUp()
        {
            Driver = new FirefoxDriver();
            JSDriver = (IJavaScriptExecutor) Driver;
        }

        [TearDown]
        public void TearDown()
        {
            Driver.Quit();
        }

        [Test]
        public void HighlightElementExample()
        {
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/large");
            IWebElement TargetElement = Driver.FindElement(By.Id("sibling-2.3"));
            HighlightElement(TargetElement, 3);
        }

        private void HighlightElement(IWebElement Element, int Duration)
        {
            string OriginalStyle = Element.GetAttribute("style");

            JSDriver.ExecuteScript("arguments[0].setAttribute(arguments[1], arguments[2])",
                                   Element,
                                   "style",
                                   "border: 2px solid red; border-style: dashed;");

            Thread.Sleep(Duration * 1000);
            JSDriver.ExecuteScript("arguments[0].setAttribute(arguments[1], arguments[2])",
                                   Element,
                                   "style",
                                   OriginalStyle);
        }
    }
}