package org.example;


import WebAutomationBase.helper.ElementHelper;
import WebAutomationBase.helper.StoreHelper;
import WebAutomationBase.model.ElementInfo;
import com.asprise.ocr.Ocr;
import com.thoughtworks.gauge.Step;
import driver.Driver;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.imageio.ImageIO;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class StepImplementation extends Driver {

    private Logger logger = LoggerFactory.getLogger(StepImplementation.class);
    private final Actions action;

    public static int DEFAULT_MAX_ITERATION_COUNT = 150;
    public static int DEFAULT_MILLISECOND_WAIT_AMOUNT = 100;

    public StepImplementation() {
        action = new Actions(driver);
    }

    private WebElement findElement(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    @Step("<key> li elementin <attributeName> attribute değerinin <value> e eşit olduğunu kontrol et ")
    public void equalsAttributeValue(String key, String attributeName, String value) {
        Assert.assertEquals(value, getAttribute(key, attributeName));
        logger.info(key + " li elementin " + attributeName + " attribute değerinin " + value + " e eşit olduğu kontrol edildi");
    }

    String getAttribute(String key, String attributeName) {
        return findElement(key).getAttribute(attributeName);
    }
    @Step("<key> li elementin <attribute> değerini <saveKey> olarak sakla")
    public void saveAttributeValueByKey(String key,String attribute, String saveKey) {
        StoreHelper.INSTANCE.saveValue(saveKey, findElement(key).getAttribute(attribute));
        logger.info(saveKey + " variable is stored with: " + findElement(key).getAttribute(attribute));
        System.out.println(saveKey + " variable is stored with: " + findElement(key).getAttribute(attribute));
    }
    @Step({"<key> li elementi bul ve değerini <saveKey> olarak sakla",
            "Find element by <key> and save text <saveKey>"})
    public void saveTextByKey(String key, String saveKey) {
        StoreHelper.INSTANCE.saveValue(saveKey, findElement(key).getText());
        logger.info(saveKey + " variable is stored with: " + findElement(key).getText());
        System.out.println(saveKey + " variable is stored with: " + findElement(key).getText());
        System.out.println("test");
    }

    @Step("<key> li elementi bul ve değerini <saveKey1> ve <saveKey2> saklanan değer ile karşılaştır")
    public void equalsSaveTextByKey2(String key, String saveKey1,String saveKey2) {
        Assert.assertEquals(StoreHelper.INSTANCE.getValue(saveKey1).trim()+StoreHelper.INSTANCE.getValue(saveKey2).trim(), findElement(key).getText().trim());
        //logger.info("Element by " + key + " is equal to " + StoreHelper.INSTANCE.getValue(saveKey1));
        //logger.info("Element by " + key + " contains: " + findElement(key).getText() + " and Equals to " + saveKey1 + ": " + StoreHelper.INSTANCE.getValue(saveKey1));
    }
    @Step({"<key> li elementi bul ve değerini <saveKey> saklanan değer ile karşılaştır",
            "Find element by <key> and compare saved key <saveKey>"})
    public void equalsSaveTextByKey(String key, String saveKey) {
        System.out.println(findElement(key).getText().trim());
        System.out.println(StoreHelper.INSTANCE.getValue(saveKey).trim());
        Assert.assertEquals(StoreHelper.INSTANCE.getValue(saveKey).trim(), findElement(key).getText().trim());
        logger.info("Element by " + key + " is equal to " + StoreHelper.INSTANCE.getValue(saveKey));
        logger.info("Element by " + key + " contains: " + findElement(key).getText() + " and Equals to " + saveKey + ": " + StoreHelper.INSTANCE.getValue(saveKey));
    }
    private List<WebElement> findElements(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        return driver.findElements(infoParam);
    }


    private void clickElement(WebElement element) {
        element.click();
    }

    private void clickElementBy(String key) {
        findElement(key).click();
    }


    private void sendKeys(String text, String key) {
        if (!key.equals("")) {
            findElement(key).sendKeys(text);
            logger.info(key + " elementine " + text + " texti yazıldı.");
        }
    }

    private void clearTextArea(String key) {
        findElement(key).clear();
        logger.info("Text area cleared ");
    }

    private String getText(String key) {
        logger.info("Getting text from given locator ");
        return findElement(key).getText();
    }


    private String getTitle() {
        return driver.getTitle();
    }

    /**
     * Javascript driverın başlatılması
     */
    private JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) driver;
    }

    /**
     * Javascript scriptlerinin çalışması için gerekli fonksiyon
     */
    private Object executeJS(String script, boolean wait) {
        return wait ? getJSExecutor().executeScript(script, "") : getJSExecutor().executeAsyncScript(script, "");
    }


    /**
     * Belirli bir locasyona sayfanın kaydırılması
     */
    private void scrollTo(int x, int y) {
        String script = String.format("window.scrollTo(%d, %d);", x, y);
        executeJS(script, true);
    }


    /**
     * Belirli bir elementin olduğu locasyona websayfasının kaydırılması
     */
    private WebElement scrollToElementToBeVisible(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        WebElement webElement = driver.findElement(ElementHelper.getElementInfoToBy(elementInfo));
        if (webElement != null) {
            scrollTo(webElement.getLocation().getX(), webElement.getLocation().getY() - 100);
        }
        return webElement;
    }

    private void hoverElementBy(String key) {
        WebElement webElement = findElement(key);
        action.moveToElement(webElement).build().perform();
    }

    private WebElement findElementWithKey(String key) {
        return findElement(key);
    }

    private Boolean isEnabled(String key) {
        logger.info("Checking is element enable on the page ");

        return findElement(key).isEnabled();
    }

    private boolean isDisplayedBy(By by) {
        return driver.findElement(by).isDisplayed();
    }

    private void selectByValue(String key, String text) {
        Select select = new Select(findElement(key));
        select.selectByValue(text);
        logger.info("Selected from dropdown");
    }

    /**
     * Dynamic sleep
     */
    private void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
            logger.info("Waited " + seconds + " seconds ");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * Navigate Home Page
     */
    @Step("Navigate to home page")
    public void navigateToHomePage() {
        driver.navigate().to(System.getenv("APP_URL"));
        logger.info("Navigated to homepage ");
    }

    /**
     * Scroll to given element
     */
    @Step({"<key> alanına kaydır"})
    public void scrollToElement(String key) {
        scrollToElementToBeVisible(key);
        logger.info(key + " elementinin olduğu alana kaydırıldı");

    }

    /**
     * Check if element "key" contains text "expectedText"
     */
    @Step({"Check if element <key> contains text <expectedText>",
            "<key> elementi <text> değerini içeriyor mu kontrol et"})
    public void checkElementContainsText(String key, String expectedText) {
        boolean containsText = findElement(key).getText().contains(expectedText);
        assertTrue("Expected text is not contained", containsText);
        logger.info(key + " elementi" + expectedText + "değerini içeriyor.");
    }
    @Step("<key> li elemente <text> değerini yazdır ve enter'a bas")
    public void sendKeysToGivenKeyAndEnter(String text,String key) {
        findElement(key).sendKeys(text,Keys.ENTER);
    }
    @Step("<key> li elemente <text> değerini yazdır")
    public void sendKeysToGivenKey(String key,String text) {
        findElement(key).sendKeys(text);
    }


    /**
     * check title contains given text
     */
    @Step({"Check if title contains text <expectedText>",
            "Title <text> değerini içeriyor mu kontrol et"})
    public void checkElementContainsText(String expectedText) {
        boolean containsText = getTitle().contains(expectedText);
        assertTrue("Expected text is not contained", containsText);
        logger.info(" Title" + expectedText + "değerini içeriyor.");
    }

    /**
     * js click with xpath
     */
    @Step({"Click with javascript to xpath <xpath>",
            "Javascript ile xpath tıkla <xpath>"})
    public void javascriptClickerWithXpath(String xpath) {
        assertTrue("Element bulunamadı", isDisplayedBy(By.xpath(xpath)));
        javaScriptClicker(driver, driver.findElement(By.xpath(xpath)));
        logger.info("Javascript ile " + xpath + " tıklandı.");
    }

    /**
     * js click with css locator
     */
    @Step({"Click with javascript to css <css>",
            "Javascript ile css tıkla <css>"})
    public void javascriptClickerWithCss(String css) {
        assertTrue("Element bulunamadı", isDisplayedBy(By.cssSelector(css)));
        javaScriptClicker(driver, driver.findElement(By.cssSelector(css)));
        logger.info("Javascript ile " + css + " tıklandı.");
    }


    /**
     * check is display given element on page
     */
    @Step({"Element with <key> is displayed on website",
            "<key> li element sayfada görüntüleniyor mu kontrol et "})
    public void isDisplayedSection(String key) {
        WebElement element = findElementWithKey(key);
        assertTrue("Section bulunamadı", element.isDisplayed());
        logger.info(key + "li section sayfada bulunuyor.");
    }

    /**
     * check is enable given element on page
     */
    @Step({"Element with <key> is enable on website",
            "<key> li element sayfada enable mı kontrol et "})
    public void isEnableSection(String key) {
        WebElement element = findElementWithKey(key);
        assertTrue("Section bulunamadı", element.isEnabled());
        logger.info(key + "li section sayfada bulunuyor.");
    }

    /**
     *Belirtilen key li frame e geçiş yapar
     */
    @Step({"Switch to frame given <key>",
            "verilen <key> frame ine geçiş yap"})
    public void switchToFrameWithGivenKey(String key) {
        driver.switchTo().frame(findElement(key));

    }

    /**
     * default frame e geçiş yapar
     */
    @Step({"Switch to default frame",
            "default frame e geçiş yap"})
    public void switchToFrameWithGivenKey() {
        driver.switchTo().defaultContent();
    }

    /**
     * Choose Random Element
     */
    @Step("<key> menu listesinden rasgele seç")
    public void chooseRandomElementFromList(String key) {
        randomPick(key);
    }

    /**
     * js clicker
     */
    public void javaScriptClicker(WebDriver driver, WebElement element) {

        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
                + "arguments[0].dispatchEvent(evt);", element);
    }

    /**
     * switch tabs and verify
     */
    @Step("Check if new tab has verified url <key>")
    public void switchTabsUsingPartOfUrl(String key) {
        String currentHandle = null;
        try {
            final Set<String> handles = driver.getWindowHandles();
            if (handles.size() > 1) {
                currentHandle = driver.getWindowHandle();
            }
            if (currentHandle != null) {
                for (final String handle : handles) {
                    driver.switchTo().window(handle);
                    if (driver.getCurrentUrl().contains(key) && !currentHandle.equals(handle)) {
                        break;
                    }
                }
            } else {
                for (final String handle : handles) {
                    driver.switchTo().window(handle);
                    if (driver.getCurrentUrl().contains(key)) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Switching tabs failed");
        }
    }


    /**
     * scroll to given field
     */
    @Step({"Scroll to <key> field",
            "<key> alanına js ile kaydır"})
    public void scrollToElementWithJs(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        WebElement element = driver.findElement(ElementHelper.getElementInfoToBy(elementInfo));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * check element isExist on page
     */
    @Step({"Check if element <key> exists else print message <message>",
            "Element <key> var mı kontrol et yoksa hata mesajı ver <message>"})
    public void getElementWithKeyIfExistsWithMessage(String key, String message) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By by = ElementHelper.getElementInfoToBy(elementInfo);

        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() > 0) {
                logger.info(key + " elementi bulundu.");
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail(message);
    }

    /**
     * find given text on List and click on it
     */
    public void findTextAndClick(String key, String text) {

        List<WebElement> searchText = findElements(key);
        for (int i = 0; i < searchText.size(); i++) {
            if (searchText.get(i).getText().trim().contains(text)) {
                searchText.get(i).click();
                logger.info("Bulunan elemente tıklandı.");
                break;
            }
        }
    }

    /**
     * hover to given el
     */
    private void hoverElement(WebElement element) {
        action.moveToElement(element).build().perform();
    }

    @Step({"Hover to element <key>",
            "<key> elementinin üzerine gel"})
    public void hoverToGivenElement(String key) {
        hoverElement(findElement(key));

    }

    @Step({"Click to element <key>",
            "Elementine tıkla <key>"})
    public void clickElement(String key) {
        if (!key.equals("")) {
            WebElement element = findElement(key);
            hoverElement(element);
            waitByMilliSeconds(500);
            clickElement(element);
            logger.info(key + " elementine tıklandı.");
        }
    }

    @Step("Set captcha code")
    public void setCaptcha() throws IOException {
       /* String imageUrl = driver.findElement(By.xpath("//img[@id='img-captcha']")).getAttribute("src");
        URL url = new URL(imageUrl);
        Ocr.setUp();
        Ocr ocr = new Ocr();
        ocr.startEngine("eng", Ocr.SPEED_FASTEST);
        String imageToText = ocr.recognize(new URL[] {new URL(imageUrl)},
                Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
        System.out.println("Result: " + imageToText);
        imageToText.split("=")[0].replaceAll("[^a-zA-Z0-9]","");
        ocr.stopEngine();*/
    }

    @Step({"Wait <value> milliseconds",
            "<long> milisaniye bekle"})
    public void waitByMilliSeconds(long milliseconds) {
        try {
            logger.info(milliseconds + " milisaniye bekleniyor.");
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void randomPick(String key) {
        List<WebElement> elements = findElements(key);
        Random random = new Random();
        int index = random.nextInt(elements.size());
        elements.get(index).click();
    }

    @Step("Check if <key> element's attribute value <active>")
    public void asdf(String key, String active) {
        WebElement element = findElementWithKey(key);
        String a = element.getAttribute(active);
        assertEquals("active", a);
    }

    @Step("Check position <key1> relative to <key2>")
    public Boolean checkRelativePosition(String key1, String key2) {
        WebElement parent = findElementWithKey(key1);
        WebElement child = findElementWithKey(key2);
        boolean isAbove = false;
        if (!(parent.getLocation() == null && child.getLocation() == null)) {
            if (parent.getLocation().getY() - child.getLocation().getY() < 0) {
                isAbove = true;
                logger.info(key1 + " element is above the" + key2);
            }

            if (parent.getLocation().getY() - child.getLocation().getY() > 0) {
                isAbove = false;
                logger.info(key1 + " element is under the " + key2);
            }
        }
        return isAbove;
    }


    @Step({"Check if element <key> has attribute <attribute>",
            "<key> elementi <attribute> niteliğine sahip mi"})
    public void checkElementAttributeExists(String key, String attribute) {
        WebElement element = findElement(key);
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (element.getAttribute(attribute) != null) {
                logger.info(key + " elementi " + attribute + " niteliğine sahip.");
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }

        Assert.fail("Element DOESN't have the attribute: '" + attribute + "'");
    }
}
