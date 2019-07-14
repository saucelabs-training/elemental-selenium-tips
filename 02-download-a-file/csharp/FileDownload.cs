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
        FolderPath = System.Environment.CurrentDirectory + 
            $"/../../../{System.Guid.NewGuid().ToString()}";
        Directory.CreateDirectory(FolderPath);

        FirefoxProfile Profile = new FirefoxProfile();
        Profile.SetPreference("browser.download.dir", FolderPath);
        Profile.SetPreference("browser.download.folderList", 2);
        Profile.SetPreference("browser.helperApps.neverAsk.saveToDisk",
                              "image/jpeg, application/pdf, application/octet-stream");
        Profile.SetPreference("pdfjs.disabled", true);
        FirefoxOptions Options = new FirefoxOptions();
        Options.Profile = Profile;
        Driver = new FirefoxDriver(Options);
    }

    [TearDown]
    public void TearDown()
    {
        Driver.Quit();
        Directory.Delete(FolderPath, true);
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
}
