package yich.base.jsoup;

import org.jsoup.nodes.Document;
import yich.base.selenium.loader.LoadWaiter;
import yich.base.selenium.webdriver.DriverProvider;

public class DefaultJsoupDOMExtractor implements JsoupDOMGetStrategy{
    private DriverProvider driverProvider;
    private LoadWaiter waiter;


    @Override
    public Document apply(String url) {
        return null;
    }

    public DriverProvider getDriverProvider() {
        return driverProvider;
    }

    public DefaultJsoupDOMExtractor setDriverProvider(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
        return this;
    }

    public LoadWaiter getWaiter() {
        return waiter;
    }

    public DefaultJsoupDOMExtractor setWaiter(LoadWaiter waiter) {
        this.waiter = waiter;
        return this;
    }
}
