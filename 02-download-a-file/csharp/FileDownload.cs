using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using System.IO;
using System.Threading;

public class FileDownload
{
    IWebDriver Driver;
    string FolderPath;

    [SetUp]
    public void SetUp()
    {
        FolderPath = @"C:\Temp\" + System.Guid.NewGuid().ToString();
        Directory.CreateDirectory(FolderPath);

        FirefoxProfile profile = new FirefoxProfile();
        profile.SetPreference("browser.download.dir", FolderPath);
        profile.SetPreference("browser.download.folderList", 2);
        profile.SetPreference("browser.helperApps.neverAsk.saveToDisk",
                              "image/jpeg, application/pdf, application/octet-stream");
        profile.SetPreference("pdfjs.disabled", true);
        Driver = new FirefoxDriver(profile);
    }

    [Test]
    public void DownloadFileToDisk()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/download");
        Driver.FindElement(By.CssSelector(".example a")).Click();
        Thread.Sleep(2000);

        DirectoryInfo DownloadFolder = new DirectoryInfo(FolderPath);
        Assert.IsTrue(DownloadFolder.GetFiles().Length > 0, "File not downloaded");
        foreach(FileInfo file in DownloadFolder.GetFiles())
        {
            Assert.IsTrue(file.Length > 0, "File empty");
        }
    }

    [TearDown]
    public void TearDown()
    {
        Driver.Quit();
        Directory.Delete(FolderPath, true);
    }


}