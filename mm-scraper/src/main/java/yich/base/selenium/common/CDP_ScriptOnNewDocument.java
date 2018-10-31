package yich.base.selenium.common;

import yich.base.resource.Resource;


public class CDP_ScriptOnNewDocument extends Resource{
    private static Item RM_ChromeDriver_Trace
            = item("js/cdp/rmChromeDriverTrace_CDP.js");

    public static String get() {
        return RM_ChromeDriver_Trace.trimStr().escapeStr().str();
    }

    public static void main(String[] args) {
        System.out.println(get());
    }

}
