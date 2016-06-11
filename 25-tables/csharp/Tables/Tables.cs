using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using System;
using System.Collections.Generic;

public class Tables
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
    public void TableWithNoHelpfulMarkup1()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/tables");

        // sort "Due" column in ascending order
        Driver.FindElement(By.CssSelector("#table1 thead tr th:nth-of-type(4)")).Click();

        // get values from "Due" column and clean up the data
        IReadOnlyCollection<IWebElement> Dues = Driver.FindElements(By.CssSelector("#table1 tbody tr td:nth-of-type(4)"));
        List<double> FormattedDues = new List<double>();
        foreach(IWebElement Due in Dues)
        {
            FormattedDues.Add(double.Parse(Due.Text.Replace("$", "")));
        }

        // check that the "Due" column is in ascending order
        Assert.That(FormattedDues, Is.Ordered);
    }

    [Test]
    public void TableWithNoHelpfulMarkup2()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/tables");

        // sort "Due" column in descending order
        Driver.FindElement(By.CssSelector("#table1 thead tr th:nth-of-type(4)")).Click();
        Driver.FindElement(By.CssSelector("#table1 thead tr th:nth-of-type(4)")).Click();

        // get values from "Due" column and clean up the data
        IReadOnlyCollection<IWebElement> Dues = Driver.FindElements(By.CssSelector("#table1 tbody tr td:nth-of-type(4)"));
        List<double> FormattedDues = new List<double>();
        foreach(IWebElement Due in Dues)
        {
            FormattedDues.Add(double.Parse(Due.Text.Replace("$", "")));
        }

        // check that the "Due" column is in ascending order
        Assert.That(FormattedDues, Is.Ordered.Descending);
    }

    [Test]
    public void TableWithNoHelpfulMarkup3()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/tables");

        // sort "Email" column in descending order
        Driver.FindElement(By.CssSelector("#table1 thead tr th:nth-of-type(3)")).Click();

        // get values from "Email" column
        IReadOnlyCollection<IWebElement> Emails = Driver.FindElements(By.CssSelector("#table1 tbody tr td:nth-of-type(3)"));
        List<string> FormattedEmails = new List<string>();
        foreach(IWebElement Email in Emails)
        {
            FormattedEmails.Add(Email.Text);
        }

        // check that the "Email" column is in ascending order
        Assert.That(FormattedEmails, Is.Ordered);
    }

    [Test]
    public void TableWithHelpfulMarkup()
    {
        Driver.Navigate().GoToUrl("http://the-internet.herokuapp.com/tables");
        Driver.FindElement(By.CssSelector("#table2 thead .dues")).Click();
        IReadOnlyCollection<IWebElement> Dues = Driver.FindElements(By.CssSelector("#table2 tbody .dues"));
        List<double> FormattedDues = new List<double>();
        foreach(IWebElement Due in Dues)
        {
            FormattedDues.Add(double.Parse(Due.Text.Replace("$", "")));
        }
        Assert.That(FormattedDues, Is.Ordered);
    }
}