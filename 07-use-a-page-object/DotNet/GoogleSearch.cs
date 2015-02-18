using OpenQA.Selenium;
using OpenQA.Selenium.Support.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace PageObjectModel
{
    public class GoogleSearch
    {
        public static void SearchFor(string Search_Term)
        {
            IWebElement SEARCH_BOX = PageObjectExample.Driver.FindElement(By.Id("gbqfq"));
            IWebElement SEARCH_BOX_SUBMIT = PageObjectExample.Driver.FindElement(By.Id("gbqfq"));
            

            SEARCH_BOX.Clear();
            SEARCH_BOX.SendKeys(Search_Term);
            SEARCH_BOX_SUBMIT.Click();

        }

        public static bool SearchResultPresent(string Search_Result)
        {
            var WaitForTopResult = new WebDriverWait(PageObjectExample.Driver, TimeSpan.FromSeconds(10));
            WaitForTopResult.Until(ExpectedConditions.ElementExists(By.CssSelector("#rso .g")));
            WaitForTopResult.Until(ExpectedConditions.ElementIsVisible(By.CssSelector("#rso .g")));

            try
            {
                IWebElement TOP_SEARCH_RESULT = PageObjectExample.Driver.FindElement(By.CssSelector("#rso .g"));
                return true;
            }
            catch (OpenQA.Selenium.NoSuchElementException)
            {

                return false;
            }

        }

        public static bool VerifyPage()
        {
            if(PageObjectExample.Driver.Title.Contains("Google"))
            {return true;}
            return false;
        }

        public static void WaitFive()
        {
            Thread.Sleep(5000);
        }

        
    }
}
