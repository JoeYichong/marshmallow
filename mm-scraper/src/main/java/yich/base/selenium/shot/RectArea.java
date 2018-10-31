package yich.base.selenium.shot;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import yich.base.dbc.Require;
import yich.base.selenium.common.SELENIUM_JS;

public class RectArea {
    public int x;
    public int y;
    public int width;
    public int height;

    private static long timeoutSec = 30;

    public RectArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static RectArea inflate(String flat) {
        if (flat.contains("NaN")) {
            throw new RuntimeException("Error: NaN Returned");
        }
        String[] strs = flat.split(":");
        if (strs.length != 4) {
            throw new RuntimeException("Unknown Error");
        }

        return new RectArea(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]),
                Integer.parseInt(strs[2]), Integer.parseInt(strs[3]));
    }

    public static long timeoutSec() {
        return timeoutSec;
    }

    public static void setTimeoutSec(long timeoutSec) {
        Require.argument(timeoutSec > 0, timeoutSec, "timeoutSec > 0");
        RectArea.timeoutSec = timeoutSec;
    }

    public static RectArea getByCSS(RemoteWebDriver driver, String cssDescriptor) {
        new WebDriverWait(driver, timeoutSec)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssDescriptor)));

        return inflate((String) driver.executeScript(SELENIUM_JS.ELEMENT_AREA_STR.str(), cssDescriptor));
        //return getByJS(driver, "document.querySelector(\"" + cssDescriptor +"\")");
    }

    public static RectArea getVerticalElementsGroup(RemoteWebDriver driver, String firstEleCss, String lastEleCss) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSec);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(firstEleCss)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(lastEleCss)));

        RectArea firstEle = getByCSS(driver, firstEleCss);
        RectArea lastEle = getByCSS(driver, lastEleCss);
        return  new RectArea(firstEle.x, firstEle.y, firstEle.width, lastEle.y - firstEle.y + lastEle.height);
    }

}
