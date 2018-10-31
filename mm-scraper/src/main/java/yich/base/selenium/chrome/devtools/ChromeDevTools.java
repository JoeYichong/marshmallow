package yich.base.selenium.chrome.devtools;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import yich.base.logging.JUL;
import yich.base.selenium.common.SELENIUM_Config;
import yich.base.selenium.common.CDP_ScriptOnNewDocument;

import java.io.IOException;

public class ChromeDevTools {
    final public static String CHROME = "webdriver.chrome.driver";
    final public static String CHROME_BIN = "webdriver.chrome.bin";

    private static WebSocket ws = null;

    private static void sendWSMessage(String url,String message)
            throws IOException, WebSocketException {
        if(ws == null) {
            ws = new WebSocketFactory()
                    .createSocket(url)
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket ws, String message) {
                            System.out.println(message);
                        }
                    })
                    .connect();
        }
        ws.sendText(message);
    }

    public static void main(String[] args) throws IOException, WebSocketException, InterruptedException {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, JUL.PATH + "/chromedriver.log");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, SELENIUM_Config.SELENIUM.getProperty(CHROME));

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--start-maximized", "--enable-devtools-experiments");
        options.setBinary(SELENIUM_Config.SELENIUM.getProperty(CHROME_BIN));

        DesiredCapabilities crcapabilities = DesiredCapabilities.chrome();
        crcapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        crcapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .withVerbose(true)
                .build();
        service.start();

        RemoteWebDriver driver = new RemoteWebDriver(service.getUrl(), crcapabilities);

        String wsURL = WebSocketDebuggerUrl.get();
        System.out.println(wsURL);

        String msgJSON;

        msgJSON = WSMessage.TEMPLATE_Page_enable.str();
        System.out.println(msgJSON);
        sendWSMessage(wsURL, msgJSON);


        msgJSON = WSMessage.TEMPLATE_Page_addScriptToEvaluateOnNewDocument.str();
        String script = CDP_ScriptOnNewDocument.get();
        msgJSON = msgJSON.replace("@script", script);
        System.out.println(msgJSON);
        sendWSMessage(wsURL, msgJSON);



//        msgJSON = WSMessage.TEMPLATE_Network_enable.str();
//        System.out.println(msgJSON);
//        sendWSMessage(wsURL, msgJSON);
//
//        msgJSON = WSMessage.TEMPLATE_Network_setUserAgentOverride.str();
//        System.out.println(msgJSON);
//        sendWSMessage(wsURL, msgJSON);

        driver.get("https://intoli.com/blog/not-possible-to-block-chrome-headless/chrome-headless-test.html");

//        msgJSON = WSMessage.TEMPLATE_Page_navigate.str();
//        System.out.println(msgJSON);
//        sendWSMessage(wsURL, msgJSON);

        Thread.sleep(3000);

        ws.disconnect();
        driver.close();
        driver.quit();
        service.stop();

    }

}
