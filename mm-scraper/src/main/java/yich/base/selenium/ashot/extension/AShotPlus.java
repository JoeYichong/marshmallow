package yich.base.selenium.ashot.extension;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.coordinates.CoordsPreparationStrategy;
import ru.yandex.qatools.ashot.cropper.DefaultCropper;
import ru.yandex.qatools.ashot.cropper.ImageCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.SimpleShootingStrategy;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;
import static ru.yandex.qatools.ashot.coordinates.CoordsPreparationStrategy.intersectingWith;

public class AShotPlus {
    protected CoordsProviderPlus<WebElement> coordsProvider;
    protected ImageCropper cropper;
    protected Set<By> ignoredLocators;
    protected Set<Coords> ignoredAreas;
    protected ShootingStrategy shootingStrategy;

    public AShotPlus() {
        ignoredAreas = new HashSet<>();
        shootingStrategy = new SimpleShootingStrategy();
        ignoredLocators = new HashSet<>();
        cropper = new DefaultCropper();
        coordsProvider = new WebDriverCoordsProviderPlus();
    }

    public AShotPlus coordsProvider(final CoordsProviderPlus coordsProvider) {
        this.coordsProvider = coordsProvider;
        return this;
    }

    @SuppressWarnings("UnusedDeclaration")
    public AShotPlus imageCropper(ImageCropper cropper) {
        this.cropper = cropper;
        return this;
    }

    /**
     * Sets the list of locators to ignore during image comparison.
     *
     * @param ignoredElements list of By
     * @return this
     */
    @SuppressWarnings("UnusedDeclaration")
    public synchronized AShotPlus ignoredElements(final Set<By> ignoredElements) {
        this.ignoredLocators = ignoredElements;
        return this;
    }

    /**
     * Adds selector of ignored element.
     *
     * @param selector By
     * @return this
     */
    public synchronized AShotPlus addIgnoredElement(final By selector) {
        this.ignoredLocators.add(selector);
        return this;
    }

    /**
     * Sets a collection of wittingly ignored coords.
     * @param ignoredAreas Set of ignored areas
     * @return aShot
     */
    @SuppressWarnings("UnusedDeclaration")
    public synchronized AShotPlus ignoredAreas(final Set<Coords> ignoredAreas) {
        this.ignoredAreas = ignoredAreas;
        return this;
    }

    /**
     * Adds coordinated to set of wittingly ignored coords.
     * @param area coords of wittingly ignored coords
     * @return aShot;
     */
    @SuppressWarnings("UnusedDeclaration")
    public synchronized AShotPlus addIgnoredArea(Coords area) {
        this.ignoredAreas.add(area);
        return this;
    }

    /**
     * Sets the policy of taking screenshot.
     *
     * @param strategy shooting strategy
     * @return this
     * @see ShootingStrategy
     */
    public AShotPlus shootingStrategy(ShootingStrategy strategy) {
        this.shootingStrategy = strategy;
        return this;
    }


    public Screenshot takeScreenshot(WebDriver driver, List<WebElement> locators) {
        Set<Coords> coords = coordsProvider.ofLocators((JavascriptExecutor) driver, locators);
        BufferedImage shot = shootingStrategy.getScreenshot(driver, coords);
        Screenshot screenshot = cropper.crop(shot, shootingStrategy.prepareCoords(coords));
        Set<Coords> ignoredAreas = compileIgnoredAreas(driver, intersectingWith(screenshot));
        screenshot.setIgnoredAreas(shootingStrategy.prepareCoords(ignoredAreas));
        return screenshot;
    }

    /**
     * Takes the screenshot of given element
     *
     * @param driver WebDriver instance
     * @return Screenshot with cropped image and list of ignored areas on screenshot
     * @throws RuntimeException when something goes wrong
     * @see Screenshot
     */
    public Screenshot takeScreenshot(WebDriver driver, WebElement locator) {
        return takeScreenshot(driver, singletonList(locator));
    }

    /**
     * Takes the screenshot of whole page
     *
     * @param driver WebDriver instance
     * @return Screenshot with whole page image and list of ignored areas on screenshot
     * @see Screenshot
     */
    public Screenshot takeScreenshot(WebDriver driver) {
        Screenshot screenshot = new Screenshot(shootingStrategy.getScreenshot(driver));
        screenshot.setIgnoredAreas(compileIgnoredAreas(driver, CoordsPreparationStrategy.simple()));
        return screenshot;
    }

    protected synchronized Set<Coords> compileIgnoredAreas(WebDriver driver, CoordsPreparationStrategy preparationStrategy) {
        Set<Coords> ignoredCoords = new HashSet<>();
        for (By ignoredLocator : ignoredLocators) {
            List<WebElement> ignoredElements = driver.findElements(ignoredLocator);
            if (!ignoredElements.isEmpty()) {
                ignoredCoords.addAll(preparationStrategy.prepare(coordsProvider.ofLocators((JavascriptExecutor) driver, ignoredElements)));
            }

        }
        for (Coords ignoredArea : ignoredAreas) {
            ignoredCoords.addAll(preparationStrategy.prepare(singletonList(ignoredArea)));
        }
        return ignoredCoords;
    }

    @SuppressWarnings("UnusedDeclaration")
    public synchronized Set<By> getIgnoredLocators() {
        return ignoredLocators;
    }
}
