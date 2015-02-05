/*Logic Copied from Dave Haufnerr's Upload File Ruby Test
 * Rewritten in C# using Selenium and MS Test
*/
using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

namespace _01_upload_file
{
    [TestClass]
    public class UploadAFile
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
        public void UploadFile()
        {
            string File = "MyUploadFile.txt";
            string FileName = @"C:\Temp\" + File;

            IWebElement BrowseBtn = Driver.FindElement(By.Id("file-upload"));
            IWebElement UploadBtn = Driver.FindElement(By.Id("file-submit"));

            BrowseBtn.SendKeys(FileName);
            UploadBtn.Click();

            IWebElement FileUploaded = Driver.FindElement(By.Id("uploaded-files"));
            Assert.IsTrue(FileUploaded.Text == File, "The File Did Not Upload Correctly");

        }

        [TestInitialize]
        public void StartTest()
        {
            Driver = new FirefoxDriver();
            Driver.Manage().Window.Maximize();
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/upload");
        }

        [TestCleanup]
        public void EndTest()
        {
            Driver.Quit();
        }



    }
}
