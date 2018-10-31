package yich.base.selenium.webdriver;

public class ChromeDriverProvider extends AbsDriverProvider {
    public ChromeDriverProvider() {
        super();
    }

    public ChromeDriverProvider(boolean headless) {
        super();
        setHeadless(headless);
    }

    @Override
    protected void getDriver0() {
        driver = WebDriverFactory.getChromeDriver(isHeadless());
    }

}
