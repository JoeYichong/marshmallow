package yich.base.selenium.shot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import yich.base.dbc.Require;
import yich.base.logging.JUL;
import yich.base.preserver.FilePreserver;
import yich.base.preserver.ShotFilePreserver;
import yich.base.selenium.loader.DriverPageLoader;
import yich.base.selenium.webdriver.WebDriverFactory;
import yich.base.util.FileUtil;
import yich.base.util.UrlInfo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class WebShot implements ShotFileTaker {
    final private static Logger logger = JUL.getLogger(WebShot.class);
    protected DriverPageLoader loader;
    protected FilePreserver<BufferedImage> preserver;
    protected ShotTaker shotTaker;

    private WebShot(RemoteWebDriver driver, String url, String basePath) {
        this.loader = DriverPageLoader.get(driver, url);
        this.preserver = new ShotFilePreserver(basePath).setUrl(url);
        this.shotTaker = new ZShotTaker();
    }

    private WebShot(RemoteWebDriver driver, String url) {
        this(driver, url, getBasePath());
    }

    private static String getBasePath() {
        String path = System.getProperty("user.dir") + File.separator + "_screenshots_";

        FileUtil.dirChecker()
                .beforeMake("** Screenshots Output Directory '" + path + "' Not Found...")
                .afterMake("** Directory '"+ path + "' Generated.")
                .check(path);

        return path + File.separator;
    }

    private WebShot(String url, String basePath) {
        this((RemoteWebDriver) (WebDriverFactory.getChromeDriver(true)), url, basePath);
    }

    private WebShot(String url) {
        this(url, getBasePath());
    }

    public static WebShot get(String url) {
        return new WebShot(url);
    }

    public static WebShot get(String url, String basePath) {
        return new WebShot(url, basePath);
    }

    public static WebShot get(RemoteWebDriver driver, String url) {
        return new WebShot(driver, url);
    }

    public static WebShot get(RemoteWebDriver driver, String url, String basePath) {
        return new WebShot(driver, url, basePath);
    }

    public RemoteWebDriver page() {
        return loader.page();
    }

    public WebShot setDriver(RemoteWebDriver driver) {
        WebDriver wd = loader.page();
        this.loader.setDriver(driver);
        wd.quit();
        return this;
    }

    public DriverPageLoader loader() {
        return loader;
    }

    public WebShot setLoader(DriverPageLoader loader) {
        Require.argumentNotNull(loader);
        this.loader = loader;
        return this;
    }

    public String url() {
        return loader.url();
    }

    public WebShot setUrl(String url) throws IOException {
        UrlInfo urlInfo = UrlInfo.of(url);
        Require.argumentWCM(urlInfo.isValid(), "URL Not Valid");
        String oldUrl = this.loader.url();
        this.loader.setUrl(url);
        preserver.removeAppendedDir(oldUrl);
        preserver.appendDir(urlInfo.domainName());
        return this;
    }

    public String basePath() {
        return preserver.getBasePath();
    }

    public WebShot saveTo(String basePath) {
        preserver.setBasePath(basePath);
        return this;
    }

    public FilePreserver<BufferedImage> getPreserver() {
        return preserver;
    }

    public WebShot setPreserver(FilePreserver<BufferedImage> preserver) {
        Require.argumentNotNull(preserver);
        this.preserver = preserver;
        return this;
    }

    public WebShot refresh() throws IOException {
        loader.refresh();
        return this;
    }

    @Override
    public void close() {
        loader.close();
        loader = null;
        preserver = null;
        shotTaker = null;
    }

    public ShotTaker getShotTaker() {
        return shotTaker;
    }

    public WebShot setShotTaker(ShotTaker shotTaker) {
        Require.argumentNotNull(shotTaker);
        this.shotTaker = shotTaker;
        return this;
    }

    public String saveImage(BufferedImage image, String tag) {
        return (String) preserver.setTag(tag).apply(image);
    }


    @Override
    public String getElementShotFile(String tag, RectArea rect) {
        return (String) preserver.setTag(tag).apply(shotTaker.getElementImage(loader.page(), rect));
    }

    @Override
    public String getElementShotFile(String tag, String cssLocator) {
        return (String) preserver.setTag(tag).apply(shotTaker.getElementImage(loader.page(), cssLocator));
    }

    @Override
    public String getElementGroupShotFile(String tag, String... locators) {
        return (String) preserver.setTag(tag).apply(shotTaker.getElementGroupImage(loader.page(), locators));
    }

    @Override
    public String getElementGroupShotFile(String tag, List<String> locators) {
        return (String) preserver.setTag(tag).apply(shotTaker.getElementGroupImage(loader.page(), locators));
    }

    @Override
    public String getFullPageShotFile(String tag) {
        return (String) preserver.setTag(tag).apply(shotTaker.getFullPageImage(loader.page()));
    }

    @Override
    public String getWindowShotFile(String tag) {
        return (String) preserver.setTag(tag).apply(shotTaker.getWindowImage(loader.page()));
    }

}
