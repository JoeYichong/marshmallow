package yich.base.selenium.webdriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.*;
import yich.base.logging.JUL;
import yich.base.selenium.chrome.en.EnChromeDriver;
import yich.base.selenium.common.CDP_ScriptOnNewDocument;
import yich.base.selenium.common.SELENIUM_Config;

import java.util.logging.Logger;

public class WebDriverFactory {
    private static Logger logger = JUL.getLogger(WebDriverFactory.class);

    final public static String CHROME = "webdriver.chrome.driver";
    final public static String GECKO = "webdriver.gecko.driver";
    final public static String EDGE = "webdriver.edge.driver";
    final public static String FIREFOX_BIN = "webdriver.firefox.bin";
    final public static String PHANTOMJS_BIN = "phantomjs.binary.path";
    final public static String PHANTOMJS_USER_AGENT = "phantomjs.loader.settings.userAgent";

    static {
        setSysProperty(CHROME);
        setSysProperty(GECKO);
        setSysProperty(EDGE);
        setSysProperty(FIREFOX_BIN);
        setSysProperty(PHANTOMJS_BIN);
    }

    private static void setSysProperty(String property){
        System.setProperty(property, SELENIUM_Config.SELENIUM.getProperty(property));
    }

    public static RemoteWebDriver getEdgeDriver() {
        setSysProperty(EDGE);
        RemoteWebDriver driver = new EdgeDriver();
        return driver;
    }

    public static RemoteWebDriver getChromeDriver(boolean headless) {
        setSysProperty(CHROME);
        RemoteWebDriver webDriver;
        if (headless) {
            ChromeOptions cho = new ChromeOptions();
            cho.addArguments("headless");
            //cho.addArguments(Arrays.asList("--start-maximized"));
            //cho.addArguments("window-size=1200x600");
            webDriver = new ChromeDriver(cho);
            webDriver.manage().window().maximize();
        } else {
            webDriver = new ChromeDriver();
            //webDriver = new RemoteWebDriver();
        }
        return webDriver;
    }

    public static RemoteWebDriver getEnChromeDriver(boolean headless) {
        setSysProperty(CHROME);
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, JUL.PATH + "/en_chromedriver.log");
        logger.info("*** Set EnChromeDriver Log Path to " + JUL.PATH + "/en_chromedriver.log");

        ChromeOptions cho = new ChromeOptions();
        cho.addArguments("--start-maximized", "--enable-devtools-experiments");
        if (headless) {
            cho.addArguments("headless");
            logger.info("*** Set ChromeOptions To 'Headless' Mode");
        }
        EnChromeDriver driver = new EnChromeDriver(cho);
        driver.addScriptToEvaluateOnNewDocument(CDP_ScriptOnNewDocument.get());

        logger.info("*** EnChromeDriver Instance Created, ScriptToEvaluateOnNewDocument Added.");
        return driver;
    }

    //
    public static RemoteWebDriver getChromeDriverEx(boolean headless) {
        setSysProperty(CHROME);

        RemoteWebDriver webDriver;
        if (headless) {
            ChromeOptions cho = new ChromeOptions();
            cho.addArguments("headless");
            webDriver = new ChromeDriverEx(cho);
            webDriver.manage().window().maximize();
        } else {
            webDriver = new ChromeDriverEx();
        }

        return webDriver;
    }

    public static RemoteWebDriver getGeckoDriver(boolean headless) {
        System.setProperty(GECKO, SELENIUM_Config.SELENIUM.getProperty(GECKO));
        System.setProperty(FIREFOX_BIN, SELENIUM_Config.SELENIUM.getProperty(FIREFOX_BIN));
        RemoteWebDriver webDriver;
        if (headless) {
            FirefoxBinary firefoxBinary = new FirefoxBinary();
            firefoxBinary.addCommandLineOptions("--headless");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            webDriver =  new FirefoxDriver(firefoxOptions);
        } else {
            webDriver =  new FirefoxDriver();
        }
        return webDriver;
    }

    public static RemoteWebDriver getPhantomJSDriver() {
        System.out.println(PHANTOMJS_USER_AGENT);
        System.out.println(SELENIUM_Config.SELENIUM.getProperty(PHANTOMJS_USER_AGENT));
        System.setProperty(PHANTOMJS_BIN, SELENIUM_Config.SELENIUM.getProperty(PHANTOMJS_BIN));
        System.setProperty(PHANTOMJS_USER_AGENT, SELENIUM_Config.SELENIUM.getProperty(PHANTOMJS_USER_AGENT));
        // Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0
        return new PhantomJSDriver();
    }


}
