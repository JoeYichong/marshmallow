package yich.base.selenium.webdriver;

//import org.openqa.selenium.remote.RemoteWebDriver;
import yich.base.dbc.Require;
import yich.base.selenium.common.SELENIUM_JS;

public class BotDetector {

    public static DriverProvider windowMaximize(DriverProvider driverProvider){
        driverProvider.get().manage().window().maximize();
        return driverProvider;
    }

    public static DriverProvider rmChromeDriverTrace(DriverProvider driverProvider) {
        Require.argumentNotNull(driverProvider);
        windowMaximize(driverProvider);
        driverProvider.get().executeScript(SELENIUM_JS.RM_CHROME_DRIVER_TRACE.str());
        return driverProvider;
    }

    public static boolean detectBot(DriverProvider driverProvider) {
        Require.argumentNotNull(driverProvider);

        return (boolean) driverProvider.get().executeScript(SELENIUM_JS.DETECT_BOT.str());
        //return (boolean) driver.executeScript("console.log('Hello!');return false;");
    }

}
