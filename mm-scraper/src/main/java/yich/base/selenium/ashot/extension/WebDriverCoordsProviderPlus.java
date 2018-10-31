package yich.base.selenium.ashot.extension;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.coordinates.Coords;

import java.util.*;

public class WebDriverCoordsProviderPlus implements CoordsProviderPlus<WebElement> {
    @Override
    public Coords ofLocator(JavascriptExecutor driver, WebElement element) {
        Point point = element.getLocation();
        Dimension dimension = element.getSize();
        return new Coords(
                point.getX(),
                point.getY(),
                dimension.getWidth(),
                dimension.getHeight());
    }

    @Override
    public Set<Coords> ofLocators(JavascriptExecutor driver, List<WebElement> elements) {
        Set<Coords> elementsCoords = new HashSet<>();
        for (WebElement element : elements) {
            Coords elementCoords = ofLocator(driver, element);
            if (!elementCoords.isEmpty()) {
                elementsCoords.add(elementCoords);
            }
        }
        return Collections.unmodifiableSet(elementsCoords);
    }

    @Override
    public Set<Coords> ofLocators(JavascriptExecutor driver, WebElement... elements) {
        return ofLocators(driver, Arrays.asList(elements));
    }
}
