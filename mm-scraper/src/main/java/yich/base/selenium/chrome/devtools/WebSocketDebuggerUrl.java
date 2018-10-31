package yich.base.selenium.chrome.devtools;

import yich.base.resource.OuterFiles;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSocketDebuggerUrl extends OuterFiles{
    private static Item WEB_SOCKET_URL = item("_logs_/chromedriver.log");

    public static String get() {
        Matcher matcher =
                Pattern.compile("webSocketDebuggerUrl\": \"(ws:\\/\\/.+?\\/\\w+?)\"")
                        .matcher(WEB_SOCKET_URL.str());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void main(String[] args) {
       System.out.println(get());
    }

}
