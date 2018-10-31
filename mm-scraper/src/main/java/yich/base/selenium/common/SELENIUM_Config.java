package yich.base.selenium.common;


import yich.base.resource.PropertyFiles;

public class SELENIUM_Config extends PropertyFiles {
    final public static Item SELENIUM = item("_config/selenium.properties");

    public static void main(String[] args) {
        System.out.println('\n' + SELENIUM.path() + '\n');
        SELENIUM.properties().forEach((k, v) -> System.out.println(k + ": " + v));
    }

}
