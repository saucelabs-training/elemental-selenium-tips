/*Logic Copied from Dave Haufnerr's se-ide-example-exported Ruby Test
 *Rewritten in C# using Selenium and MS Test
 *Resolved wait issue derived from Selenium IDE Recording
 *Removed unnecessary code.  
 *Jonathan Taylor 2/9/2015
*/

using System;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Support.UI;

namespace SeleniumIDEFixed
{
    [TestClass]
    public class SeIdeExportedCSharp
    {
        private IWebDriver driver;
        private string baseURL;
        
        
        [TestInitialize]
        public void SetupTest()
        {
            driver = new FirefoxDriver();
            baseURL = "https://www.google.com/";
        }
        
        [TestCleanup]
        public void TeardownTest()
        {
           driver.Quit();
        }
        
        [TestMethod]
        public void IDEResolved()
        {            
            driver.Navigate().GoToUrl(baseURL);
            driver.FindElement(By.Id("gbqfq")).Clear();
            driver.FindElement(By.Id("gbqfq")).SendKeys("elemental selenium tips");
            driver.FindElement(By.Id("gbqfq")).SendKeys(Keys.Enter);

            //Create a new WebDriver Wait Variable that waits for Existence and Visibility in the DOM
            var WaitForElementalTips = new WebDriverWait (driver, TimeSpan.FromSeconds(10));
            WaitForElementalTips.Until(ExpectedConditions.ElementExists(By.LinkText("Elemental Selenium: Receive a Free, Weekly Tip on Using ...")));
            WaitForElementalTips.Until(ExpectedConditions.ElementIsVisible(By.LinkText("Elemental Selenium: Receive a Free, Weekly Tip on Using ...")));

            driver.FindElement(By.LinkText("Elemental Selenium: Receive a Free, Weekly Tip on Using ...")).Click();

            //User a basic Find to see if the Elemental Selenium Page Opened
            driver.FindElement(By.XPath("/html/body/header/div/div/h1"));
        }
       
        
        
       
    }
}
