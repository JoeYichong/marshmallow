package yich.base.selenium.webdriver;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import yich.base.logging.JUL;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class ChromeDriverEx extends ChromeDriver {
    private static Logger logger = JUL.getLogger(ChromeDriverEx.class);

    public ChromeDriverEx(ChromeOptions cho) {
        super(cho);
    }

    public ChromeDriverEx() {
        super();
    }

    @Override
    public void setFileDetector(FileDetector detector) {
        if (detector == null) {
            throw new WebDriverException("You may not set a file detector that is null");
        }
        Field fileDetectorField;
        try {
            fileDetectorField = RemoteWebDriver.class.getDeclaredField("fileDetector");
            fileDetectorField.setAccessible(true);
            fileDetectorField.set(this, detector);

            logger.info("** MyChromeDriver::setFileDetector() Applied.");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
