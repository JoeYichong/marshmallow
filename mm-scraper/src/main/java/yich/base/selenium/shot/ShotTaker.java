package yich.base.selenium.shot;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ShotTaker {
//    RemoteWebDriver getCurrentDriver();
//
//    ShotTaker setCurrentDriver(RemoteWebDriver driver);

    BufferedImage getElementImage(RemoteWebDriver driver, RectArea rect);

    BufferedImage getElementImage(RemoteWebDriver driver, String cssLocator);

    BufferedImage getElementGroupImage(RemoteWebDriver driver, String... cssLocators);

    BufferedImage getElementGroupImage(RemoteWebDriver driver, List<String> cssLocators);

    BufferedImage getFullPageImage(RemoteWebDriver driver);

    BufferedImage getWindowImage(RemoteWebDriver driver);
}
