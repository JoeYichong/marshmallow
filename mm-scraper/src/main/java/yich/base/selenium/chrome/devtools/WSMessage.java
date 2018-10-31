package yich.base.selenium.chrome.devtools;

import yich.base.resource.Resource;

public class WSMessage extends Resource {
    public static Item TEMPLATE_Page_enable
            = item("json/cdp/Page.enable.json");

    public static Item TEMPLATE_Page_navigate
            = item("json/cdp/Page.navigate.json");

    public static Item TEMPLATE_Page_addScriptToEvaluateOnNewDocument
            = item("json/cdp/Page.addScriptToEvaluateOnNewDocument.json");


    public static Item TEMPLATE_Network_enable
            = item("json/cdp/Network.enable.json");


    public static Item TEMPLATE_Network_setUserAgentOverride
            = item("json/cdp/Network.setUserAgentOverride.json");

    public static void main(String[] args) {
        System.out.println(TEMPLATE_Network_enable.str());
    }
}
