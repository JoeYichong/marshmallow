package yich.base.selenium.loader;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DefaultLoadWaiter extends AbsLoadWaiter {

    @Override
    public void wait(RemoteWebDriver driver) {

        //driver.manage().timeouts().implicitlyWait(getTimeoutSec(), TimeUnit.SECONDS);

        new WebDriverWait(driver, getTimeoutSec())
                .until(wdriver -> ((JavascriptExecutor) wdriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

}
