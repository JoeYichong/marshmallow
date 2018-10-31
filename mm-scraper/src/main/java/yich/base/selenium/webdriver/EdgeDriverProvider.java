package yich.base.selenium.webdriver;

public class EdgeDriverProvider extends AbsDriverProvider {
    @Override
    protected void getDriver0() {
        driver = WebDriverFactory.getEdgeDriver();
    }
}
