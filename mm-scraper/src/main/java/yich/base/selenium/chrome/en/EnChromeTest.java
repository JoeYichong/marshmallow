package yich.base.selenium.chrome.en;

import org.openqa.selenium.chrome.ChromeDriverService;
import yich.base.logging.JUL;
import yich.base.selenium.common.SELENIUM_Config;
import yich.base.selenium.common.CDP_ScriptOnNewDocument;

public class EnChromeTest {
    final public static String CHROME = "webdriver.chrome.driver";

    public static void main(String[] args) throws InterruptedException {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, JUL.PATH + "/en_chromedriver.log");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, SELENIUM_Config.SELENIUM.getProperty(CHROME));


        EnChromeDriver driver = new EnChromeDriver();
        Object value = driver.addScriptToEvaluateOnNewDocument(CDP_ScriptOnNewDocument.get());

        System.out.println(value);

//        driver.get("https://intoli.com/blog/not-possible-to-block-chrome-headless/chrome-headless-test.html");
//        Thread.sleep(2000);

//        driver.get("https://www.baidu.com/");
//        Thread.sleep(2000);

//        driver.get("https://intoli.com/blog/not-possible-to-block-chrome-headless/chrome-headless-test.html");
//        Thread.sleep(3000);

        driver.quit();
    }
}
