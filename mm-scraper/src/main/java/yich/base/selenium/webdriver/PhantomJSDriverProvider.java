package yich.base.selenium.webdriver;

public class PhantomJSDriverProvider extends AbsDriverProvider {
    @Override
    protected void getDriver0() {
        driver = WebDriverFactory.getPhantomJSDriver();
    }
}
