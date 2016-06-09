using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using System;

namespace Frames
{
    public class Frames
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
        public void NestedFrames()
        {
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/nested_frames");
            Driver.SwitchTo().Frame("frame-top");
            Driver.SwitchTo().Frame("frame-middle");
            Assert.That(Driver.FindElement(By.Id("content")).Text.Equals("MIDDLE"));
        }

        [Test]
        public void Iframes()
        {
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/tinymce");
            Driver.SwitchTo().Frame("mce_0_ifr");
            IWebElement Editor = Driver.FindElement(By.Id("tinymce"));
            string StartText = Editor.Text;
            Editor.Clear();
            Editor.SendKeys("Hello World!");
            string EndText = Editor.Text;
            Assert.AreNotEqual(EndText, StartText);

            Driver.SwitchTo().DefaultContent();
            string HeaderText = Driver.FindElement(By.CssSelector("h3")).Text;
            Assert.That(HeaderText.Equals("An iFrame containing the TinyMCE WYSIWYG Editor"));
        }
    }
}
