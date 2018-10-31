package yich.base.selenium.common;


import yich.base.logging.JUL;
import yich.base.resource.Resource;

import java.util.logging.Logger;

public final class SELENIUM_JS extends Resource {
    // Require a string argument as CSS Selector
    final public static Item ELEMENT_AREA_STR = item("js/element_area.js");

    final public static Item PAGE_HEIGHT_LONG = item("js/page_height.js");

    final public static Item VIEWPORT_HEIGHT_LONG = item("js/viewport_height.js");

    final public static Item VIEWPORT_WIDTH_LONG = item("js/viewport_width.js");

    final public static Item RM_CHROME_DRIVER_TRACE = item("js/selenium/rmChromeDriverTrace.js");

    final public static Item DETECT_BOT = item("js/selenium/runBotDetection.js");


    private SELENIUM_JS() {}

    public static void main(String[] args) {
        Logger logger = JUL.getLogger(SELENIUM_JS.class);
        logger.info("hello");

//        System.out.println(SELENIUM_JS.ELEMENT_AREA_STR.str());
//
//        System.out.println(SELENIUM_JS.RM_CHROME_DRIVER_TRACE.str());

        System.out.println(SELENIUM_JS.DETECT_BOT.str());
    }

}
