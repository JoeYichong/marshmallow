package yich.base.logging;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class AugmenterLogFilter implements Filter{
    @Override
    public boolean isLoggable(LogRecord record) {
        return !record.getMessage()
                .contains("Augmenter should be applied to the instances of @Augmentable classes " +
                        "or previously augmented instances only");
    }
}
