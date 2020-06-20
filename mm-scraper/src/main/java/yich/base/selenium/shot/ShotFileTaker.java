package yich.base.selenium.shot;

import java.util.List;

public interface ShotFileTaker extends AutoCloseable {
    String getElementShotFile(String tag, RectArea rect);

    String getElementShotFile(String tag, String cssLocator);

    String getElementGroupShotFile(String tag, String... locators);

    String getElementGroupShotFile(String tag, List<String> locators);

    String getFullPageShotFile(String tag);

    String getWindowShotFile(String tag);
}
