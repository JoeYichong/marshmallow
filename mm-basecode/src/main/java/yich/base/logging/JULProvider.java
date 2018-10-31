package yich.base.logging;

import java.util.logging.Logger;

public interface JULProvider {
    Logger getLogger(Class clazz);

    String path();
}
