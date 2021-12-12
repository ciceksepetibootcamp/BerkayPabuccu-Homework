package com.sample;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;


/**
 * The type Hooks.
 * This class sets driver, capabilities and some of options
 */
public class Hooks {

    protected static WebDriver driver;
    protected static Actions actions;
    public Logger logger = Logger.getLogger(getClass());

    /**
     * Selected Browser name and platform, initialize capabilities
     *
     * @param capabilities
     */
    String browserName = "chrome";
    String selectPlatform = "win";
    DesiredCapabilities capabilities;
    ChromeOptions chromeOptions;
    FirefoxOptions firefoxOptions;

    @Before
    public void beforeTest() {
        logger.info("* BeforeScenario *");
        logger.info("Local cihazda " + selectPlatform + " ortamında " + browserName + " browserında test ayağa kalkacak");
        if ("win".equalsIgnoreCase(selectPlatform)) {
            if ("chrome".equalsIgnoreCase(browserName)) {
                driver = new ChromeDriver(chromeOptions());
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            } else if ("firefox".equalsIgnoreCase(browserName)) {
                driver = new FirefoxDriver(firefoxOptions());
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            }
        } else if ("mac".equalsIgnoreCase(selectPlatform)) {
            if ("chrome".equalsIgnoreCase(browserName)) {
                driver = new ChromeDriver(chromeOptions());
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            } else if ("firefox".equalsIgnoreCase(browserName)) {
                driver = new FirefoxDriver(firefoxOptions());
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            }
        }
        actions = new Actions(driver);

    }


    /**
     * Set Chrome options
     *
     * @return the chrome options
     */
    public ChromeOptions chromeOptions() {
        chromeOptions = new ChromeOptions();
        capabilities = DesiredCapabilities.chrome();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addArguments("--kiosk");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--start-fullscreen");
        System.setProperty("webdriver.chrome.driver", "web_driver/chromedriver.exe");
        chromeOptions.merge(capabilities);
        return chromeOptions;
    }

    /**
     * Set Firefox options
     *
     * @return the firefox options
     */
    public FirefoxOptions firefoxOptions() {
        firefoxOptions = new FirefoxOptions();
        capabilities = DesiredCapabilities.firefox();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        firefoxOptions.addArguments("--kiosk");
        firefoxOptions.addArguments("--disable-notifications");
        firefoxOptions.addArguments("--start-fullscreen");
        FirefoxProfile profile = new FirefoxProfile();
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        capabilities.setCapability("marionette", true);
        firefoxOptions.merge(capabilities);
        System.setProperty("webdriver.gecko.driver", "web_driver/geckodriver");
        return firefoxOptions;
    }

    /**
     * After test.
     * Quit driver
     */
    @After
    public void afterTest() {
        driver.close();
        driver.quit();
    }

    /**
     * @return the web driver
     */
    public static WebDriver getWebDriver() {
        return driver;
    }

}
