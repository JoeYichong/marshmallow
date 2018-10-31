package yich.base.selenium.loader;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.Set;

public class ElementLoadWaiter extends AbsLoadWaiter {
    private Set<String> cssSelectors = null;

    public Set<String> getCssSelectors() {
        return cssSelectors;
    }

    public ElementLoadWaiter addCssSelector(String cssSelector) {
        if (cssSelectors == null) {
            cssSelectors = new HashSet<>();
        }
        this.cssSelectors.add(cssSelector);
        return this;
    }

    @Override
    public void wait(RemoteWebDriver driver) {
        if (cssSelectors == null) {
            return;
        }
        WebDriverWait wait = new WebDriverWait(driver, getTimeoutSec());
        for (String cssSelector : cssSelectors) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
        }
    }

}
