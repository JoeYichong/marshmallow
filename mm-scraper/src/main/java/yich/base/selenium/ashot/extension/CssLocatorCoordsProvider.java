package yich.base.selenium.ashot.extension;

import org.openqa.selenium.JavascriptExecutor;
import ru.yandex.qatools.ashot.coordinates.Coords;
import yich.base.selenium.common.SELENIUM_JS;

import java.util.*;

public class CssLocatorCoordsProvider implements CoordsProviderPlus<String> {

    private Coords inflate(String flat) {
        //System.out.println("** " + flat);
        if (flat.contains("NaN")) {
            throw new RuntimeException("Error: NaN Returned");
        }
        String[] strs = flat.split(":");
        if (strs.length != 4) {
            throw new RuntimeException("Unknown Error");
        }
        return new Coords(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]),
                Integer.parseInt(strs[2]), Integer.parseInt(strs[3]));
    }

    @Override
    public Coords ofLocator(JavascriptExecutor driver, String cssLocator) {

        return inflate((String) driver.executeScript(SELENIUM_JS.ELEMENT_AREA_STR.str(), cssLocator));
    }

    @Override
    public Set<Coords> ofLocators(JavascriptExecutor driver, List<String> cssLocators) {
        Set<Coords> coords = new HashSet<>();
        for (String cssLocator : cssLocators) {
            Coords coord = ofLocator(driver, cssLocator);
            if (!coord.isEmpty()) {
                coords.add(coord);
            }
        }
        return Collections.unmodifiableSet(coords);
    }

    @Override
    public Set<Coords> ofLocators(JavascriptExecutor driver, String... cssLocator) {
        return ofLocators(driver, Arrays.asList(cssLocator));
    }

}
