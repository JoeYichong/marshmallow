package yich.base.selenium.shot;

import org.openqa.selenium.remote.RemoteWebDriver;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.ViewportPastingDecorator;
import yich.base.selenium.ashot.extension.ZShot;

import java.awt.image.BufferedImage;
import java.util.List;

public class ZShotTaker implements ShotTaker {
    private ZShot shot;
    private ShootingStrategy strategy;

    public ZShotTaker() {
        strategy = ShootingStrategies.viewportPasting(100);
        this.shot = ZShot.get()
                   .shootingStrategy(strategy);
    }

    public ZShotTaker setScrollTimeout(int scrollTimeout) {
        if (strategy instanceof ViewportPastingDecorator) {
            ((ViewportPastingDecorator) strategy).withScrollTimeout(scrollTimeout);
        }
        return this;
    }

    @Override
    public BufferedImage getElementImage(RemoteWebDriver driver, RectArea rect) {
        return shot.takeScreenshotByCoords(driver, new Coords(rect.x, rect.y, rect.width, rect.height)).getImage();
    }

    @Override
    public BufferedImage getElementImage(RemoteWebDriver driver, String cssLocator) {
        return shot.takeScreenshotByCss(driver, cssLocator).getImage();
    }

    @Override
    public BufferedImage getElementGroupImage(RemoteWebDriver driver, String... cssLocators) {
        return shot.takeScreenshotByCss(driver, cssLocators).getImage();
    }

    @Override
    public BufferedImage getElementGroupImage(RemoteWebDriver driver, List<String> cssLocators) {
        return shot.takeScreenshotByCss(driver, cssLocators).getImage();
    }

    @Override
    public BufferedImage getFullPageImage(RemoteWebDriver driver) {
        return shot.takeScreenshot(driver).getImage();
    }

    @Override
    public BufferedImage getWindowImage(RemoteWebDriver driver) {
        BufferedImage image = shot.shootingStrategy(ShootingStrategies.simple())
                .takeScreenshot(driver).getImage();
        shot.shootingStrategy(ShootingStrategies.viewportPasting(100));
        return image;
    }
}
