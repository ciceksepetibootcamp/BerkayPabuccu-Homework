
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
using SeleniumExtras.PageObjects;


namespace CSharp_Selenium
{

    public class Methods : BasePage
    {

        Actions actions;
        IWebElement tempElement = null;
        public Methods(IWebDriver driver)
        {
            Methods.driver = driver;
            actions = new Actions(driver);
            PageFactory.InitElements(driver, this);
        }

        public IWebElement findElement(By locator)
        {
            WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            tempElement = wait.Until(SeleniumExtras.WaitHelpers.ExpectedConditions.ElementExists(locator));
            ((IJavaScriptExecutor)driver).ExecuteScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                    tempElement);
            return tempElement;
        }

        private List<IWebElement> findElements(By locator)
        {
            return driver.FindElements(locator).ToList();
        }
        public void findElementsBy(By locator)
        {
            List<IWebElement> elements = findElements(locator);
        }
        public void randomPick(By locator)
        {
            List<IWebElement> elements = findElements(locator);
            Random random = new Random();
            int index = random.Next(elements.Count());
            IWebElement element = elements.ElementAt(index);
            clickByElement(element);
        }

        public void clickByElement(IWebElement element)
        {

            if (element.Displayed && element.Enabled)
            {
                tempElement.Click();
            }

        }
        public void click(By locator)
        {
            tempElement = findElement(locator);
            if (tempElement.Displayed && tempElement.Enabled)
            {
                tempElement.Click();
            }
            tempElement = null;
        }
        private void hoverElement(IWebElement elementParam)
        {
            actions.MoveToElement(elementParam).Build().Perform();
            Console.WriteLine("hovered");
        }

        public void hoverElementByLocator(By locator)
        {
            IWebElement webElement = findElement(locator);
            actions.MoveToElement(webElement).Build().Perform();


        }
        public void testHover(By locator)
        {
            WebDriverWait waits = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            waits.Until(SeleniumExtras.WaitHelpers.ExpectedConditions.VisibilityOfAllElementsLocatedBy(locator));

            IWebElement webElement = driver.FindElement(locator);
            actions.MoveToElement(webElement).Build().Perform();

        }

        public void retryingFindHover(By by)
        {
            Boolean result = false;
            int attempts = 0;
            while (attempts < 4)
            {
                try
                {
                    actions.MoveToElement(findElement(by)).Build().Perform();
                    result = true;
                    break;
                }
                catch (StaleElementReferenceException e)
                {
                    Console.WriteLine(e.Message);
                }
                attempts++;
            }
            Console.WriteLine("ress" + result);
        }



        private void sendKeys(String text, By locator)
        {
            if (!locator.Equals(""))
            {
                findElement(locator).SendKeys(text);
            }
        }
        public void sendKeysToGivenKey(String text, By locator)
        {
            sendKeys(text, locator);
        }

        private String getText(By locator)
        {
            tempElement = findElement(locator);
            String text = tempElement.Text;
            tempElement = null;
            return text;
        }
        public void assertionNegativeLoginCreds(By locator)
        {
            tempElement = findElement(locator);
            String expected = "E-posta adresiniz ve/veya şifreniz hatalı.";
            Assert.AreEqual(getText(locator), expected);
            tempElement = null;
        }
        public void sendKeysToGivenKeyAndEnter(String text, By locator)
        {
            sendKeys(text, locator);
            findElement(locator).SendKeys(Keys.Enter);
        }

        private Boolean isEnabledBy(By loc)
        {
            return findElement(loc).Enabled;
        }

        private Boolean isDisplayedBy(By by)
        {
            return findElement(by).Displayed;
        }
        public void isDisplayed(By loc)
        {
            Assert.True(isDisplayedBy(loc), loc + " element not displayed");
        }
        public void isEnabled(By loc)
        {
            Assert.True(isEnabledBy(loc), loc + " element not enable");
        }

    }
}