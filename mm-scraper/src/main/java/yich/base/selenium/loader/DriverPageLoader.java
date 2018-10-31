package yich.base.selenium.loader;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;
import yich.base.dbc.Require;
import yich.base.logging.JUL;
import yich.base.util.UrlInfo;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DriverPageLoader implements PageLoader<RemoteWebDriver> {
    final private static Logger logger = JUL.getLogger(DriverPageLoader.class);
    private RemoteWebDriver driver;
    private String currentUrl;
    private boolean isPageLoaded;
    private boolean lazyLoad;
    private LoadWaiter waiter;

    private DriverPageLoader() {
        this.isPageLoaded = false;
        this.lazyLoad = false;
        waiter = new DefaultLoadWaiter();
    }

    public static DriverPageLoader get(RemoteWebDriver driver, String url) {
        return new DriverPageLoader().setDriver(driver).setUrl(url);
    }

    @Override
    public RemoteWebDriver page()  {
//        try {
//            checkPageState();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        checkPageState();
        return driver;
    }

    public DriverPageLoader setDriver(RemoteWebDriver driver) {
        Require.argumentNotNull(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        this.driver = driver;
        return this;
    }

    @Override
    public String url() {
        return currentUrl;
    }

    @Override
    public DriverPageLoader setUrl(String url) {
        Require.argumentWCM(UrlInfo.of(url).isValid(), "URL Not Valid");
        this.currentUrl = url;
        this.isPageLoaded = false;
        if (isPageLoaded()) {
            loadPage();
        }
        return this;
    }

    public boolean isLazyLoad() {
        return lazyLoad;
    }

    public DriverPageLoader setLazyLoad(boolean lazyLoad) {
        this.lazyLoad = lazyLoad;
        return this;
    }

    protected boolean isLoadCompleted(RemoteWebDriver driver) {
        return driver.executeScript("return document.readyState").equals("complete");
    }

    public boolean isPageLoaded() {
        if (currentUrl.equals(driver.getCurrentUrl()) && isLoadCompleted(driver)) {
            return true;
        }
        return false;
    }

    public boolean isPageLoaded(String url) {
        if (url == null || "".equals(url)) {
            return false;
        }
        if (!url.equals(currentUrl) || !url.equals(driver.getCurrentUrl())) {
            return false;
        }
        if (isLoadCompleted(driver)) {
            return true;
        }
        return false;
    }

    public LoadWaiter getWaiter() {
        return waiter;
    }

    public DriverPageLoader setWaiter(LoadWaiter waiter) {
        Require.argumentNotNull(waiter);
        this.waiter = waiter;
        return this;
    }

    public DriverPageLoader loadPage() {
        if (isPageLoaded) {
            return this;
        }
        logger.info("** Start to Load Page..");

        this.driver.manage().window().maximize();
        driver.get(currentUrl);

//        new WebDriverWait(driver, loadTimeoutSec)
//                .until(driver -> isLoadCompleted((RemoteWebDriver) driver));
        int count = 0;
        for(;;) {
            try {
                waiter.wait(driver);
                break;
            } catch (TimeoutException e) {
                logger.warning("** " + e.getMessage());
                if (++count == 3) {
                    logger.severe("** Failed to Load Page");
                    throw e;
                }
                logger.info("** Try to Load Again...");
                driver.navigate().refresh();
            }
        }

        isPageLoaded = true;

        logger.info("** Loading Page Ends");
        return this;
    }

    public DriverPageLoader refresh() {
        if (currentUrl != null) {
            driver.navigate().refresh();
//            this.isPageLoaded = false;
//            loadPage();
        }
        return this;
    }

    private void checkPageState() {
        if (!isPageLoaded) {
            loadPage();
        }
    }

//    public WebElement waitWebElementByJS(String jsGetElement) {
//        return (WebElement) new WebDriverWait(driver, loadTimeoutSec)
//                .until(webdriver -> ((JavascriptExecutor) webdriver)
//                        .executeScript("return " + jsGetElement));
//    }
//
//    public WebElement waitWebElementByCSS(String cssLocator) {
//        return waitWebElementByJS("document.querySelector(\"" + cssLocator +"\")");
//    }

    @Override
    public void close() {
        this.driver.quit();
        this.driver = null;
    }

    @Override
    public RemoteWebDriver get() {
        return page();
    }
}
