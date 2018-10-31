package yich.base.selenium.webdriver;

public class FirefoxDriverProvider extends AbsDriverProvider {
    public FirefoxDriverProvider() {
        super();
    }

    public FirefoxDriverProvider(boolean headless) {
        super();
        setHeadless(headless);
    }

    @Override
    protected void getDriver0() {
        driver = WebDriverFactory.getGeckoDriver(isHeadless());
    }
}
