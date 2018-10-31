package yich.base.selenium.loader;

import org.openqa.selenium.remote.RemoteWebDriver;

public interface LoadWaiter {

    int getTimeoutSec();

    LoadWaiter setTimeoutSec(int timeoutSec);

    void wait(RemoteWebDriver driver);
}
