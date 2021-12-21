using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Firefox;
using OpenQA.Selenium.Remote;
using OpenQA.Selenium.Support.UI;
using OpenQA.Selenium.Interactions;
using NUnit.Framework;


namespace CSharp_Selenium
{

    [TestFixture]
    public class MyTest : BasePage
    {

        HomePage home;
        Methods methods;

        String userNameString = "berkaypabuccu@test.com";
        String passwordString = "Test1234";


        [OneTimeSetUp]
        public void SetUp()
        {
            Initialize();
            home = new HomePage(driver);
            methods = new Methods(driver);

        }

        private By closePopUp = By.CssSelector("div[class='modal-close']");
        private By hoverToLoginSection = By.CssSelector("div[class='link account-user']");
        private By loginButton = By.CssSelector("div[class='login-button']");
        private By userName = By.CssSelector("input[id='login-email']");
        private By password = By.CssSelector("input[id='login-password-input']");
        private By submitLogIn = By.CssSelector("button[type='submit']");
        private By searchBox = By.CssSelector("input[class='search-box']");
        private By topBarMenCategory = By.CssSelector("ul li[class='tab-link']:nth-of-type(2) a[class='category-header']");
        private By menShoesSubCategory = By.CssSelector("li[class='tab-link']:nth-of-type(2) div[class='normal-column']:nth-of-type(2) ul li:nth-of-type(1) a");
        private By errorMessage = By.CssSelector("span[class='message']");
        private By stickyWrapper = By.CssSelector("div article[class='component-item']"); // returns El. list




        [Test, Order(1)]
        public void negativeLoginScen()
        {
            methods.click(closePopUp);
            methods.hoverElementByLocator(hoverToLoginSection);
            methods.click(hoverToLoginSection);
            methods.sendKeysToGivenKey(userNameString, userName);
            methods.sendKeysToGivenKey(passwordString, password);
            methods.click(submitLogIn);
            methods.assertionNegativeLoginCreds(errorMessage);
        }

        [Test, Order(2)]
        public void test2()
        {
            //methods.sendKeysToGivenKeyAndEnter("a4tech",searchBox);
            methods.click(topBarMenCategory);
            // methods.click(menShoesSubCategory);
            methods.randomPick(stickyWrapper);


        }

        [OneTimeTearDown]
        protected void Close()
        {
            TearDown();
        }

    }

}