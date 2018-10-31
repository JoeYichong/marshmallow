package yich.base.selenium.chrome.en;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.service.DriverCommandExecutor;
import org.openqa.selenium.remote.service.DriverService;

public class EnChromeDriverCommandExecutor extends DriverCommandExecutor {

    private static final ImmutableMap<String, CommandInfo> CHROME_COMMAND_NAME_TO_URL = ImmutableMap.of(
            EnChromeDriverCommand.LAUNCH_APP,
            new CommandInfo("/session/:sessionId/chromium/launch_app", HttpMethod.POST),
            EnChromeDriverCommand.GET_NETWORK_CONDITIONS,
            new CommandInfo("/session/:sessionId/chromium/network_conditions", HttpMethod.GET),
            EnChromeDriverCommand.SET_NETWORK_CONDITIONS,
            new CommandInfo("/session/:sessionId/chromium/network_conditions", HttpMethod.POST),
            EnChromeDriverCommand.DELETE_NETWORK_CONDITIONS,
            new CommandInfo("/session/:sessionId/chromium/network_conditions", HttpMethod.DELETE),
            EnChromeDriverCommand.SEND_COMMAND_WITH_RESULT,
            new CommandInfo("/session/:sessionId/chromium/send_command_and_get_result", HttpMethod.POST)

    );

    public EnChromeDriverCommandExecutor(DriverService service) {
        super(service, CHROME_COMMAND_NAME_TO_URL);
    }

}