/*Logic Copied from Dave Haufnerr's 07 Page Object Ruby Test
 *Rewritten in C# using Selenium and MS Test
 *Used Separate Class for Google Search Page
 *Jonathan Taylor 2/18/2015
*/
using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

namespace PageObjectModel
{
    [TestClass]
    public class PageObjectExample
    {
        public static IWebDriver Driver { get; set; }
        private TestContext testContextInstance;

        [TestMethod]
        public void GoogleSearchTest()
        {
            GoogleSearch.SearchFor("Elemental Selenium");
            GoogleSearch.WaitFive();
            Assert.IsTrue(GoogleSearch.VerifyPage());            
        }

        [TestInitialize]
        public void StartTest()
        {
            Driver = new FirefoxDriver();
            Driver.Manage().Window.Maximize();
            Driver.Navigate().GoToUrl("http://www.google.com");
        }

        [TestCleanup]
        public void EndTest()
        {
            Driver.Quit();
        }
 
    }
    
}
