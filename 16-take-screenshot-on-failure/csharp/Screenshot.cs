using NUnit.Framework;
using NUnit.Framework.Interfaces;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

public class Screenshot
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
        if (TestContext.CurrentContext.Result.Outcome.Status.Equals(TestStatus.Failed))
            TakeScreenshot();

        Driver.Quit();
    }

    private void TakeScreenshot()
    {
        string TestName     = TestContext.CurrentContext.Test.FullName;
        string SaveLocation = System.Environment.CurrentDirectory + 
          $"/../../../failshot_{TestName}.png";
        ITakesScreenshot ScreenshotDriver = (ITakesScreenshot) Driver;
        ScreenshotDriver.GetScreenshot().SaveAsFile(SaveLocation);
    }

    [Test]
    public void ScreenShotOnFailure()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com");
        Assert.That(false.Equals(true));
    }
}
