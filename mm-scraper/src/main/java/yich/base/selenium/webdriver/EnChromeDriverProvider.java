package yich.base.selenium.webdriver;

public class EnChromeDriverProvider extends AbsDriverProvider {
    public EnChromeDriverProvider() {
        super();
    }

    public EnChromeDriverProvider(boolean headless) {
        super();
        setHeadless(headless);
    }

    @Override
    protected void getDriver0() {
        driver = WebDriverFactory.getEnChromeDriver(isHeadless());
    }

}
