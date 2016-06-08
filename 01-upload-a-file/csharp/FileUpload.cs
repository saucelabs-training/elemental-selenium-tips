using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

namespace FileUpload
{
    public class FileUpload
    {
        IWebDriver Driver;

        [SetUp]
        public void SetUp()
        {
            Driver = new FirefoxDriver();
        }

        [Test]
        public void UploadFileFromDisk()
        {
            string File = "SomeFile.txt";
            string FilePath = @"C:\Temp\" + File;

            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/upload");
            Driver.FindElement(By.Id("file-upload")).SendKeys(FilePath);
            Driver.FindElement(By.Id("file-submit")).Click();

            IWebElement FileUploaded = Driver.FindElement(By.Id("uploaded-files"));
            Assert.IsTrue(FileUploaded.Text == File, "The File Did Not Upload Correctly");
        }

        [TearDown]
        public void TearDown()
        {
            Driver.Quit();
        }
    }
}
