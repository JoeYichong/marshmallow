package yich.base.selenium.webdriver;

import org.openqa.selenium.remote.RemoteWebDriver;
import yich.base.dbc.Require;
import yich.base.selenium.common.SELENIUM_JS;

public class BotDetector {

    public static RemoteWebDriver rmChromeDriverTrace(RemoteWebDriver driver) {
        Require.argumentNotNull(driver);
        driver.manage().window().maximize();
        driver.executeScript(SELENIUM_JS.RM_CHROME_DRIVER_TRACE.str());
        return driver;
    }

    public static boolean detectBot(RemoteWebDriver driver) {
        Require.argumentNotNull(driver);

        return (boolean) driver.executeScript(SELENIUM_JS.DETECT_BOT.str());
        //return (boolean) driver.executeScript("console.log('Hello!');return false;");
    }

}
