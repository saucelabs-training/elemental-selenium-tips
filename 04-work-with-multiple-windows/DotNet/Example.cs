/*Logic Copied from Dave Haufnerr's Working with Windows Example.rb Ruby Test
 *Rewritten in C# using Selenium and MS Test
 *Jonathan Taylor 2/5/2015
*/
using System;
using System.Collections;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

namespace _01_upload_file
{
    [TestClass]
    public class Example
    {
        public static IWebDriver Driver { get; set; }
        private TestContext testContextInstance;

        #region Test Context Setup
        public TestContext TestContext
        {
            get
            {
                return testContextInstance;
            }
            set
            {
                testContextInstance = value;
            }
        }
        #endregion


        [TestMethod]
        public void WorkingWithWindows()
        {
            string first_window = Driver.CurrentWindowHandle;
            Driver.FindElement(By.CssSelector(".example a")).Click();
            
            IList <string> all_windows = Driver.WindowHandles;

            Driver.SwitchTo().Window(first_window);
            Assert.IsTrue(Driver.Title != "New Window");
            Driver.SwitchTo().Window(all_windows[1].ToString());
            Assert.IsTrue(Driver.Title == "New Window");
        }

        [TestInitialize]
        public void StartTest()
        {
            Driver = new FirefoxDriver();
            Driver.Manage().Window.Maximize();
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/windows");
        }

        [TestCleanup]
        public void EndTest()
        {
            Driver.Quit();
        }



    }
}
