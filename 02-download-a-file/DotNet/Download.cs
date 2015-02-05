/*Logic Copied from Dave Haufnerr's Upload File Ruby Test
*Rewritten in C# using Selenium and MS Test
*Jonathan Taylor 1/21/2015
*/
using System;
using System.IO;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using System.Net;
namespace Selenium_Elemental
{
    [TestClass]
    public class Download
    {
        public static IWebDriver Driver { get; set; }
        private TestContext testContextInstance;
        public static string FileName = "MyDownloadedFile.txt";
        public static string FilePath = @"C:\Temp\";
        public string FullPath = FilePath + FileName;
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
        public void DownloadFile()
        {
            IWebElement HelloWolrd = Driver.FindElement(By.CssSelector("#content > div > a:nth-child(2)"));
            IWebElement MyUploadFile = Driver.FindElement(By.CssSelector("html.no-js body div.row div#content.large-12.columns div.example a"));
            string LinkToFile = MyUploadFile.GetAttribute("href");
            DownloadFile(LinkToFile, FullPath);
            Assert.IsTrue(File.Exists(FullPath), "The File Did Not Download");
        }
        void DownloadFile(string url, string localPath)
        {
            var Client = new WebClient();
            Client.DownloadFile(url, localPath);
        }
        [TestInitialize]
        public void StartTest()
        {
            Driver = new FirefoxDriver();
            Driver.Manage().Window.Maximize();
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/download");
        }
        [TestCleanup]
        public void EndTest()
        {
            Driver.Quit();
        }
    }
}
