package yich.base.util;

import yich.base.logging.JUL;

import java.io.File;
import java.util.logging.Logger;

public class FileUtil {
    final private static Logger logger = JUL.getLogger(FileUtil.class);

    public static class DirChecker {
        String beforeMsg = null;
        String afterMsg = null;

        public DirChecker beforeMake(String msg) {
            this.beforeMsg = "".equals(msg) ? null : msg;
            return this;
        }

        public DirChecker afterMake(String msg) {
            this.afterMsg = "".equals(msg) ? null : msg;
            return this;
        }

        public void check(String path) {
            File dirs = new File(path);
            if (!dirs.exists()) {
                if (beforeMsg != null) {
                    logger.info(beforeMsg);
                }
                dirs.mkdirs();
                if (afterMsg != null) {
                    logger.info(afterMsg);
                }
            }
        }
    }

    public static DirChecker dirChecker() {
        return new DirChecker();
    }


}
