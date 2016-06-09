using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Support.UI;
using System;
using System.Collections.Generic;

namespace Dropdown
{
    public class Dropdown
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
        public void SelectFromDropdownTheHardWay()
        {
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/dropdown");
            IWebElement Dropdown = Driver.FindElement(By.Id("dropdown"));
            IReadOnlyCollection<IWebElement> DropdownOptions = Dropdown.FindElements(By.TagName("option"));
            foreach(IWebElement Option in DropdownOptions)
            {
                if(Option.Text.Equals("Option 1"))
                    Option.Click();
            }
            string SelectedOption = "";
            foreach (IWebElement Option in DropdownOptions)
            {
                if (Option.Selected)
                    SelectedOption = Option.Text;
            }
            Assert.That(SelectedOption.Equals("Option 1"));
        }

        [Test]
        public void SelectFromDropdownTheEasyWay()
        {
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/dropdown");
            SelectElement Dropdown = new SelectElement(Driver.FindElement(By.Id("dropdown")));
            Dropdown.SelectByText("Option 1");
            Assert.That(Dropdown.SelectedOption.Text.Equals("Option 1"));
        }

    }
}