package com.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.reflect.TypeToken;
import com.sample.model.ElementInfo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

/**
 * The type Steps.
 */
public class Steps {

    /**
     * The Web driver.
     */
    public WebDriver webDriver;
    /**
     * The Actions.
     */
    public Actions actions;
    /**
     * The Web driver wait.
     */
    public WebDriverWait webDriverWait;

    private final int timeOut = 30;
    private final int sleepTime = 150;
    /**
     * The constant DEFAULT_MAX_ITERATION_COUNT.
     */
    public static int DEFAULT_MAX_ITERATION_COUNT = 150;
    /**
     * The constant DEFAULT_MILLISECOND_WAIT_AMOUNT.
     */
    public static int DEFAULT_MILLISECOND_WAIT_AMOUNT = 100;

    /**
     * The Logger.
     */
    protected Logger logger = Logger.getLogger(getClass());
    private static final String DEFAULT_DIRECTORY_PATH = "elementValues";
    /**
     * The Element map list.
     */
    ConcurrentMap<String, Object> elementMapList = new ConcurrentHashMap<>();
    /**
     * The Users.
     */
    Map<String, String> users = new HashMap<>();

    /**
     * Instantiates a new Steps.
     */
    public Steps() {
        initMap(getFileList());
        this.webDriver = Hooks.getWebDriver();
        this.actions = new Actions(this.webDriver);
        this.webDriverWait = new WebDriverWait(webDriver, timeOut, sleepTime);
    }


    /**
     * Javascriptclicker.
     *
     * @param element the element
     */
    public void javascriptclicker(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click();", element);
    }

    /**
     * Find element web element.
     *
     * @param key the key
     * @return the web element
     */
    WebElement findElement(String key) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        By infoParam = getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 60);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    /**
     * Find elements list.
     *
     * @param key the key
     * @return the list
     */
    List<WebElement> findElements(String key) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        By infoParam = getElementInfoToBy(elementInfo);
        return webDriver.findElements(infoParam);
    }

    /**
     * Gets element info to by.
     *
     * @param elementInfo the element info
     * @return the element info to by
     */
    public static By getElementInfoToBy(ElementInfo elementInfo) {
        By by = null;
        if (elementInfo.getType().equals("css")) {
            by = By.cssSelector(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("name"))) {
            by = By.name(elementInfo.getValue());
        } else if (elementInfo.getType().equals("id")) {
            by = By.id(elementInfo.getValue());
        } else if (elementInfo.getType().equals("xpath")) {
            by = By.xpath(elementInfo.getValue());
        } else if (elementInfo.getType().equals("linkText")) {
            by = By.linkText(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("partialLinkText"))) {
            by = By.partialLinkText(elementInfo.getValue());
        }
        return by;
    }

    /**
     * Init map.
     *
     * @param fileList the file list
     */
    public void initMap(File[] fileList) {
        Type elementType = new TypeToken<List<ElementInfo>>() {
        }.getType();
        Gson gson = new Gson();
        List<ElementInfo> elementInfoList = null;
        for (File file : fileList) {
            try {
                elementInfoList = gson
                        .fromJson(new FileReader(file), elementType);
                elementInfoList.parallelStream()
                        .forEach(elementInfo -> elementMapList.put(elementInfo.getKey(), elementInfo));
            } catch (FileNotFoundException e) {
                logger.warn("{} not found", e);
            }
        }
    }

    /**
     * Get file list file [ ].
     *
     * @return the file [ ]
     */
    public File[] getFileList() {
        File[] fileList = new File(
                this.getClass().getClassLoader().getResource(DEFAULT_DIRECTORY_PATH).getFile())
                .listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));
        if (fileList == null) {
            logger.warn(
                    "File Directory Is Not Found! Please Check Directory Location. Default Directory Path = {}" +
                            DEFAULT_DIRECTORY_PATH);
            throw new NullPointerException();
        }
        return fileList;
    }

    /**
     * Find element info by key element info.
     *
     * @param key the key
     * @return the element info
     */
    public ElementInfo findElementInfoByKey(String key) {
        return (ElementInfo) elementMapList.get(key);
    }

    /**
     * Save value.
     *
     * @param key   the key
     * @param value the value
     */
    public void saveValue(String key, String value) {
        elementMapList.put(key, value);
    }

    /**
     * Gets value.
     *
     * @param key the key
     * @return the value
     */
    public String getValue(String key) {
        return elementMapList.get(key).toString();
    }

    /**
     * Find element with key web element.
     *
     * @param key the key
     * @return the web element
     */
    public WebElement findElementWithKey(String key) {
        return findElement(key);
    }


    /**
     * Check element exists then click.
     *
     * @param key the key
     */
    @And("Elementi bekle ve sonra tıkla {string}")
    public void checkElementExistsThenClick(String key) {
        logger.info("Elementi bekle ve sonra tıkla " + key);
        getElementWithKeyIfExists(key);
        clickElement(key);
        logger.info(key + " elementine tıklandı.");
    }


    /**
     * Elementine degerini yaz.
     *
     * @param key  the key
     * @param text the text
     */
    @And("{string} elementine {string} degerini yaz")
    public void elementineDegeriniYaz(String key, String text) {
        logger.info(key + " elementine " + text + " degerini yaz");
        findElement(key).sendKeys(text);
    }

    /**
     * Elementinin bulunduğunu kontrol et.
     *
     * @param key the key
     */
    @Then("{string} elementinin bulunduğunu kontrol et")
    public void elementininBulunduğunuKontrolEt(String key) {
        logger.info(key + " elementinin bulunduğunu kontrol et");
        Assert.assertTrue("Element Görünür durumda değil", findElement(key).isDisplayed());
    }

    /**
     * Elementine tıkla.
     *
     * @param key the key
     */
    @And("Click {string} element")
    public void elementineTıkla(String key) {
        logger.info(key + " elementine tıkla");
        if (findElement(key).isDisplayed()) {
            hoverElement(findElement(key));
            clickElement(findElement(key));
        }
    }

    /**
     * Saniye bekle.
     *
     * @param milliSecond the second
     */
    @Then("Wait {int} millisecond")
    public void saniyeBekle(int milliSecond) {
        try {
            logger.info(milliSecond + " saniye bekle");
            TimeUnit.MILLISECONDS.sleep(milliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sayfasına git.
     *
     * @param url the url
     */
    @Given("Open {string} URL")
    public void sayfasınaGit(String url) {
        logger.info(url + " sayfasına git");
        webDriver.get(url);
    }


    @Then("Check if title contains text {string}")
    public void checkElementContainsText(String expectedText) {
        boolean containsText = getTitle().contains(expectedText);
        assertTrue("Expected text is not contained", containsText);
        logger.info(" Title" + expectedText + "değerini içeriyor.");
    }

    @When("Scroll to {string} field")
    public void scrollToElementWithJs(String key) {
        WebElement element = findElementWithKey(key);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
        waitByMilliSeconds(300);
    }

    @Then("Element with {string} is displayed on website")
    public void isDisplayedSection(String key) {
        WebElement element = findElementWithKey(key);
        assertTrue("Section bulunamadı", element.isDisplayed());
        logger.info(key + "li section sayfada bulunuyor.");
    }

    @And("Element with {string} is enable on website")
    public void isEnableSection(String key) {
        WebElement element = findElementWithKey(key);
        assertTrue("Section bulunamadı", element.isEnabled());
        logger.info(key + "li section sayfada bulunuyor.");
    }

    @And("Check if element {string} has attribute {string}")
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

    @And("Check if {string} element's attribute value {string} equal to {string}")
    public void asdf(String key, String active, String expected) {
        WebElement element = findElementWithKey(key);
        String actual = element.getAttribute(active);
        assertEquals(expected, actual);
    }

    @And("Check if element {string} exists else print message {string}")
    public void getElementWithKeyIfExistsWithMessage(String key, String message) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        By by = getElementInfoToBy(elementInfo);
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (webDriver.findElements(by).size() > 0) {
                logger.info(key + " elementi bulundu.");
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail(message);
    }

    @And("Check position {string} relative to {string}")
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

    @And("Check if new tab has verified url {string}")
    public void switchTabsUsingPartOfUrl(String key) {
        String currentHandle = null;
        try {
            final Set<String> handles = webDriver.getWindowHandles();
            if (handles.size() > 1) {
                currentHandle = webDriver.getWindowHandle();
            }
            if (currentHandle != null) {
                for (final String handle : handles) {
                    webDriver.switchTo().window(handle);
                    if (webDriver.getCurrentUrl().contains(key) && !currentHandle.equals(handle)) {
                        break;
                    }
                }
            } else {
                for (final String handle : handles) {
                    webDriver.switchTo().window(handle);
                    if (webDriver.getCurrentUrl().contains(key)) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Switching tabs failed");
        }
    }


    /**
     * Gets element with key if exists.
     *
     * @param key the key
     * @return the element with key if exists
     */
    @Given("Element var mı kontrol et {string}")
    public WebElement getElementWithKeyIfExists(String key) {
        logger.info("Element var mı kontrol et" + key);
        WebElement webElement;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            try {
                webElement = findElementWithKey(key);
                logger.info(key + " elementi bulundu.");
                return webElement;
            } catch (WebDriverException e) {
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        assertFalse(Boolean.parseBoolean("Element: '" + key + "' doesn't exist."));
        return null;
    }


    /**
     * Click element.
     *
     * @param key the key
     */
    public void clickElement(String key) {
        if (!key.isEmpty()) {
            hoverElement(findElement(key));
            clickElement(findElement(key));
            logger.info(key + " elementine tıklandı.");
        }
    }

    /**
     * hoverElement
     *
     * @param element hover
     */
    private void hoverElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    private String getTitle() {
        return webDriver.getTitle();
    }

    /**
     * clickElement
     *
     * @param element click
     */
    private void clickElement(WebElement element) {
        element.click();
    }

    /**
     * Wait by milli seconds.
     *
     * @param milliseconds the milliseconds
     */
    public void waitByMilliSeconds(long milliseconds) {
        try {
            logger.info(milliseconds + " milisaniye bekleniyor.");
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
