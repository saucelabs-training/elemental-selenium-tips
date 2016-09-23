using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

public class FileUpload
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
}
