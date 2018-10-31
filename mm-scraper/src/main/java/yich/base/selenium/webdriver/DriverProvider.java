package yich.base.selenium.webdriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.function.Supplier;

public interface DriverProvider extends Supplier<RemoteWebDriver> {

    boolean reset();

    void quit();
}
