/*Logic Copied from Dave Haufnerr's Upload File Ruby Test
*Rewritten in C# using Selenium and MS Test
*Jonathan Taylor 1/28/2015
*/
using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
namespace Selenium_Elemental
{
    [TestClass]
    public class Frames
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
        public void NestedFrames()
        {
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/nested_frames");
            Driver.SwitchTo().Frame("frame-top");
            Driver.SwitchTo().Frame("frame-middle");
            Assert.IsTrue(Driver.FindElement(By.Id("content")).Text == "MIDDLE", "The Middle Frame Did Not Render");
        }
        [TestMethod]
        public void iFrames()
        {
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/tinymce");
            Driver.SwitchTo().Frame("mce_0_ifr");
            IWebElement Editor = Driver.FindElement(By.Id("tinymce"));
            string BeforeText = Editor.Text;
            Editor.Clear();
            Editor.SendKeys("Hello World");
            string AfterText = Editor.Text;
            Assert.AreNotEqual(AfterText, BeforeText, "The Before Text and After Text Equal Each Other");
            Driver.SwitchTo().DefaultContent();
            Assert.IsFalse(Driver.FindElement(By.CssSelector("h3")).Text == "", "The Page Did not Refresh");
        }
        [TestInitialize]
        public void StartTest()
        {
            Driver = new FirefoxDriver();
            Driver.Manage().Window.Maximize();
        }
        [TestCleanup]
        public void EndTest()
        {
            Driver.Quit();
        }
    }
}
