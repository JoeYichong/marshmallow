package yich.base.selenium.ashot.extension;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import yich.base.dbc.Require;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.yandex.qatools.ashot.coordinates.CoordsPreparationStrategy.intersectingWith;

public class ZShot extends AShotPlus {

    private CssLocatorCoordsProvider cssLocatorCoordsProvider;

    private ZShot() {
        super();
        cssLocatorCoordsProvider = new CssLocatorCoordsProvider();
    }

    public static ZShot get() {
        return new ZShot();
    }

    @Override
    public ZShot shootingStrategy(ShootingStrategy strategy) {
        this.shootingStrategy = strategy;
        return this;
    }


    public ZShot cssLocatorCoordsProvider(CssLocatorCoordsProvider cssLocatorCoordsProvider) {
        this.cssLocatorCoordsProvider = cssLocatorCoordsProvider;
        return this;
    }

    public Screenshot takeScreenshotByCss(WebDriver driver, List<String> cssLocators) {
        Require.stateNotNull(cssLocatorCoordsProvider, "CssLocatorCoordsProvider");

        Set<Coords> elementCoords = cssLocatorCoordsProvider.ofLocators((RemoteWebDriver) driver, cssLocators);
        BufferedImage shot = shootingStrategy.getScreenshot(driver, elementCoords);
        Screenshot screenshot = cropper.crop(shot, shootingStrategy.prepareCoords(elementCoords));
        Set<Coords> ignoredAreas = compileIgnoredAreas(driver, intersectingWith(screenshot));
        screenshot.setIgnoredAreas(shootingStrategy.prepareCoords(ignoredAreas));
        return screenshot;
    }

    public Screenshot takeScreenshotByCss(WebDriver driver, String... cssLocators) {
        return takeScreenshotByCss(driver, Arrays.asList(cssLocators));
    }

    public Screenshot takeScreenshotByCoords(WebDriver driver, Coords coords) {
        Require.stateNotNull(cssLocatorCoordsProvider, "CssLocatorCoordsProvider");
        Require.argumentNotNull(coords, "Coords coord");

        Set<Coords> elementCoords = new HashSet<>();
        elementCoords.add(coords);
        BufferedImage shot = shootingStrategy.getScreenshot(driver, elementCoords);
        Screenshot screenshot = cropper.crop(shot, shootingStrategy.prepareCoords(elementCoords));
        Set<Coords> ignoredAreas = compileIgnoredAreas(driver, intersectingWith(screenshot));
        screenshot.setIgnoredAreas(shootingStrategy.prepareCoords(ignoredAreas));
        return screenshot;
    }


}
