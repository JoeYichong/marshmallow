package yich.base.selenium.ashot.extension;

import org.openqa.selenium.JavascriptExecutor;
import ru.yandex.qatools.ashot.coordinates.Coords;

import java.util.List;
import java.util.Set;

interface CoordsProviderPlus<T> {

    Coords ofLocator(JavascriptExecutor driver, T locator);

    Set<Coords> ofLocators(JavascriptExecutor driver, List<T> locators);

    Set<Coords> ofLocators(JavascriptExecutor driver, T... locators);

}
