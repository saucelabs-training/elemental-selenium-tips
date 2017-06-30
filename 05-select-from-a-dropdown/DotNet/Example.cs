/*Logic Copied from Dave Haufnerr's Select From a Dropdown Example.rb Ruby Test
 *Rewritten in C# using Selenium and MS Test
 *Jonathan Taylor 2/6/2015
*/
using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
//Note to use the to instantiate a new SelectElement use the Support.UI Assembly.  
using OpenQA.Selenium.Support.UI;

namespace Elemental_Selenium_Tips._05_select_from_a_dropdown.DotNet
{
    [TestClass]
    public class SelectFromDropdown
    {
        public static IWebDriver Driver { get; set; }
       

        [TestMethod]
        public void SelectDropDown()
        {
            IWebElement dropdown = Driver.FindElement(By.Id("dropdown"));
            var select_list = new SelectElement(dropdown);
            select_list.SelectByText("Option 1");

            string selected_option = select_list.SelectedOption.Text;
            Assert.AreEqual(selected_option, "Option 1");
            //select_list.SelectByValue("1");
        }

        [TestInitialize]
        public void Setup()
        {
            Driver = new FirefoxDriver();
            Driver.Manage().Window.Maximize();
            Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/dropdown");
        }

        [TestCleanup]
        public void Teardown()
        {
            Driver.Quit();
        }
    }
}
