package yich.base.selenium.webdriver;

import org.openqa.selenium.remote.RemoteWebDriver;
import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

abstract public class AbsDriverProvider implements DriverProvider {
    final private static Logger logger = JUL.getLogger(AbsDriverProvider.class);

    protected RemoteWebDriver driver = null;

    private int timeout = 60;

    private boolean headless = true;

    public AbsDriverProvider() {
    }

    public AbsDriverProvider(RemoteWebDriver driver) {
        Require.argumentNotNull(driver);
        this.driver = driver;
    }

    abstract protected void getDriver0();

    private void getDriver() {
        getDriver0();
        driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
    }


    @Override
    public RemoteWebDriver get() {
        if (driver == null) {
            getDriver();
        }
        return driver;
    }

    @Override
    public boolean reset() {
        if (driver != null) {
            logger.info("** Resetting Driver...");
            driver.quit();
            getDriver0();
            return true;
        }
        logger.warning("** DriverProvider is closed...");
        return false;
    }

    @Override
    public void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("** DriverProvider Closed.");
        }
    }

    public int getTimeout() {
        return timeout;
    }

    public AbsDriverProvider setTimeout(int timeout) {
        Require.argument(timeout > 0, timeout, "timeout > 0");
        this.timeout = timeout;
        return this;
    }

    public boolean isHeadless() {
        return headless;
    }

    public AbsDriverProvider setHeadless(boolean headless) {
        this.headless = headless;
        logger.info("** Set to Headless Mode: " + headless);
        return this;
    }

}
