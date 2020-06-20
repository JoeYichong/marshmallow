package yich.base.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Capabilities;
import yich.base.dbc.Require;
import yich.base.logging.JUL;
import yich.base.selenium.loader.LoadWaiter;
import yich.base.selenium.webdriver.BotDetector;
import yich.base.selenium.webdriver.DriverProvider;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocUtil {
    final private static Logger logger = JUL.getLogger(DocUtil.class);

    public static Document getJsoupDoc(String url) {
        logger.info("** Start to get Jsoup Document(No Browser): " + url);
        Document doc = null;
        for (;;) {
            try {
                doc = Jsoup.connect(url)
                        .cookie("auth", "token")
                        .timeout(50000)
                        .get();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("** End of Getting Jsoup Document.");
        return doc;
    }

    public static Document getJsoupDoc(DriverProvider driverProvider) {
        Require.argumentNotNull(driverProvider.get());
        Document doc = null;
        Capabilities cap = driverProvider.get().getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        logger.info("** Start to get Jsoup Document(" + browserName + ") from 'CurrentUrl': " +
                driverProvider.get().getCurrentUrl());
        try {
            doc = Jsoup.parse(driverProvider.get().getPageSource());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error Occurred When Parsing Page Source", e);
        }
        logger.info("** End of Getting Jsoup Document.");
        return doc;
    }


    public static void detectAndRmBot(DriverProvider driverProvider) {
        if (BotDetector.detectBot(driverProvider)) {
            logger.info("*** Bot Detected");
            BotDetector.rmChromeDriverTrace(driverProvider);
            logger.info("*** Bot Removing Script Executed.");
        }
    }

    public static Document getJsoupDoc(DriverProvider driverProvider, String url, LoadWaiter waiter) {
        Require.argumentNotNull(driverProvider.get(), "WebDriver driver");
        Require.argumentNotNull(url, "String url");
        Document doc = null;
        Capabilities cap = driverProvider.get().getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();

        detectAndRmBot(driverProvider);
        logger.info("** Start to get Jsoup Document (" + browserName + "): " + url);
        for (int i = 0; i < 3; i++) {
            try {

                if (i == 0) {
//                    logger.info("** driver get page - i = " + i);
                    driverProvider.get().get(url);
                } else {
                    //driver.navigate().refresh();
                    logger.info("** Using JSoup-Http to get page...");
                    doc = getJsoupDoc(url);
                    if (doc != null) {
                        break;
                    }
                }
                if (waiter != null) {
                    waiter.wait(driverProvider.get());
                }

                doc = Jsoup.parse(driverProvider.get().getPageSource());
                break;
            } catch (Exception e) {
                logger.log(Level.WARNING, "** Error Occurred When Getting Page " + i, e);
                if (i < 2) {
                    logger.info("** Try to get page '" + url + "' again... i = " + i);
                    driverProvider.reset();
                    logger.info("** DriverProvider Reset...");
                    detectAndRmBot(driverProvider);
                }
            }
        }

        logger.info("** End of Getting Jsoup Document.");
        return doc;
    }

}
