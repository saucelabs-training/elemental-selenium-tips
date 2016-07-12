using System.Threading;
using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;

namespace DragAndDrop
{
    public class DragAndDrop
    {
        IWebDriver Driver;
        IJavaScriptExecutor JSDriver;

        [SetUp]
        public void SetUp()
        {
            Driver = new FirefoxDriver();
            JSDriver = (IJavaScriptExecutor)Driver;
        }

        [TearDown]
        public void TearDown()
        {
            Driver.Quit();
        }

        [Test]
        public void DragAndDropExample()
        {
            Driver.Navigate().GoToUrl("https://the-internet.herokuapp.com/drag_and_drop");

            var source = Driver.FindElement(By.Id("column-a"));
            var target = Driver.FindElement(By.Id("column-b"));
            var dnd_javascript = Properties.Resources.dnd;

            JSDriver.ExecuteScript(dnd_javascript + "$('#column-a').simulateDragDrop({ dropTarget: '#column-b'});");

            Thread.Sleep(1500);
            Assert.That(source.Text, Is.EqualTo("B"), "Source text was not 'B'");
            Assert.That(target.Text, Is.EqualTo("A"), "Target text was not 'A'");
        }
    }
}