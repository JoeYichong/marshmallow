package yich.base.preserver;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FilePreserver<T> extends Consumer<T>, Function<T, String> {
    String getBasePath();

    FilePreserver setBasePath(String basePath);

    String getTag();

    FilePreserver setTag(String... tags);

    FilePreserver appendTag(String... tags);

    FilePreserver removeTag(String... tags);

    String getFormat();

    FilePreserver setFormat(String format);

    FilePreserver appendDir(String dirName);

    FilePreserver removeAppendedDir(String dirName);

    String getAppendedPath();

    // override this to change output style
    File getDestFile(String nameSep, int randStrLen);
}
