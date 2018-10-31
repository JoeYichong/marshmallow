package yich.base.selenium.chrome.en;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.html5.RemoteLocationContext;
import org.openqa.selenium.remote.html5.RemoteWebStorage;
import org.openqa.selenium.remote.mobile.RemoteNetworkConnection;
import yich.base.selenium.chrome.devtools.Page;

import java.io.IOException;
import java.util.Map;

public class EnChromeDriver extends RemoteWebDriver
        implements LocationContext, WebStorage, HasTouchScreen, NetworkConnection, Page {

    private RemoteLocationContext locationContext;
    private RemoteWebStorage webStorage;
    private TouchScreen touchScreen;
    private RemoteNetworkConnection networkConnection;

    /**
     * Creates a new ChromeDriver using the {@link ChromeDriverService#createDefaultService default}
     * server configuration.
     *
     */
    public EnChromeDriver() {
        this(ChromeDriverService.createDefaultService(), defaultChromeOptions());
    }

    /**
     * Creates a new ChromeDriver instance. The {@code service} will be started along with the driver,
     * and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @see RemoteWebDriver#RemoteWebDriver(org.openqa.selenium.remote.CommandExecutor, Capabilities)
     */
    public EnChromeDriver(ChromeDriverService service) {
        this(service, defaultChromeOptions());
    }

    /**
     * Creates a new ChromeDriver instance. The {@code capabilities} will be passed to the
     * chromedriver service.
     *
     * @param capabilities The capabilities required from the ChromeDriver.
     * @see #EnChromeDriver(ChromeDriverService, Capabilities)
     * @deprecated Use {@link ChromeDriver (ChromeOptions)} instead.
     */
    @Deprecated
    public EnChromeDriver(Capabilities capabilities) {
        this(ChromeDriverService.createDefaultService(), capabilities);
    }

    /**
     * Creates a new ChromeDriver instance with the specified options.
     *
     * @param options The options to use.
     */
    public EnChromeDriver(ChromeOptions options) {
        this(ChromeDriverService.createDefaultService(), options);
    }

    /**
     * Creates a new ChromeDriver instance with the specified options. The {@code service} will be
     * started along with the driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @param options The options to use.
     */
    public EnChromeDriver(ChromeDriverService service, ChromeOptions options) {
        this(service, (Capabilities) options);
    }

    /**
     * Creates a new ChromeDriver instance. The {@code service} will be started along with the
     * driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @param capabilities The capabilities required from the ChromeDriver.
     */
    public EnChromeDriver(ChromeDriverService service, Capabilities capabilities) {
        super(new EnChromeDriverCommandExecutor(service), capabilities);
        locationContext = new RemoteLocationContext(getExecuteMethod());
        webStorage = new RemoteWebStorage(getExecuteMethod());
        touchScreen = new RemoteTouchScreen(getExecuteMethod());
        networkConnection = new RemoteNetworkConnection(getExecuteMethod());
    }


    public static ChromeOptions defaultChromeOptions() {
        return new ChromeOptions()
                .addArguments("--start-maximized", "--enable-devtools-experiments");
    }

    @Override
    public LocalStorage getLocalStorage() {
        return webStorage.getLocalStorage();
    }

    @Override
    public SessionStorage getSessionStorage() {
        return webStorage.getSessionStorage();
    }

    @Override
    public Location location() {
        return locationContext.location();
    }

    @Override
    public void setLocation(Location location) {
        locationContext.setLocation(location);
    }

    @Override
    public TouchScreen getTouch() {
        return touchScreen;
    }

    @Override
    public ConnectionType getNetworkConnection() {
        return networkConnection.getNetworkConnection();
    }

    @Override
    public ConnectionType setNetworkConnection(ConnectionType type) {
        return networkConnection.setNetworkConnection(type);
    }

    /**
     * Launches Chrome app specified by id.
     *
     * @param id chrome app id
     */
    public void launchApp(String id) {
        execute(EnChromeDriverCommand.LAUNCH_APP, ImmutableMap.of("id", id));
    }

    //
    public Object send(String cmd, Map<String, Object> params) throws IOException {
        Map<String, Object> exe = ImmutableMap.of("cmd", cmd, "params", params);
        Command xc = new Command(this.getSessionId(), EnChromeDriverCommand.SEND_COMMAND_WITH_RESULT, exe);
        Response response = this.getCommandExecutor().execute(xc);

        Object value = response.getValue();
        if(response.getStatus() == null || response.getStatus().intValue() != 0) {
            throw new RuntimeException("Command '" + cmd + "' failed: " + value +
                    " - Response: [" + String.valueOf(value)+"]");
        }
        if(null == value) {
            throw new RuntimeException("Null response value to command '" + cmd + "'" +
                    " - Response: [" + String.valueOf(value)+"]");
        }
        return value;
    }


    @Override
    public Object addScriptToEvaluateOnNewDocument(String source) {
        try {
            return this.send("Page.addScriptToEvaluateOnNewDocument", ImmutableMap.of("source", source));
        } catch (IOException e) {
            throw new RuntimeException("** Fail to Execute 'Page.addScriptToEvaluateOnNewDocument'", e);
        }
    }
}
