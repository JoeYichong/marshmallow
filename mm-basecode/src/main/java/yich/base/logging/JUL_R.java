package yich.base.logging;


import yich.base.resource.Resource;
import yich.base.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JUL_R extends Resource implements JULProvider {
//    final private Logger logger;

    final public Item LOGGING_CONFIG;
    final private AtomicBoolean config;

    public JUL_R() {
        // Note That:
        // "cfig\\logging.properties" or "cfig" + File.separator + "logging.properties" Not Working
        // in the 'single executable jar' mode
        LOGGING_CONFIG = item("cfig/logging.properties");

        // `config` field needs to be initialized before calling `applyLoggingConfigurations()`
        config = new AtomicBoolean(false);

        // In order to make configurations take effect in Logger instances,
        // they need to be applied before instantiate any Logger object.
        applyLoggingConfigurations();

//        // create '_logs' directory if not exists
//        String path = System.getProperty("user.dir") + File.separator + "_logs_";
//        FileUtil.dirChecker()
//                .afterMake("** Logging Directory '"+ path + "' Generated.")
//                .check(path);
//
//        // Note that a Logger object has already been instantiated in the FileUtil class
//        // if Logging Directory need to be created before instantiate the JUL Logger object .
//        logger = Logger.getLogger(JUL_R.class.getName());
//
//        //
//        logger.info("** Logging Configuration '"+ LOGGING_CONFIG.path() + "' Applied");
    }

    @Override
    public Logger getLogger(Class clazz) {
        Logger logger_ = Logger.getLogger(clazz.getName());
        //logger_.setUseParentHandlers(false);
        return logger_;
    }

    @Override
    public String path() {
        return LOGGING_CONFIG.path();
    }

    public void applyLoggingConfigurations() {
        if (config.compareAndSet(false, true)) {
            try (InputStream is = LOGGING_CONFIG.is()) {
                LogManager.getLogManager().readConfiguration(is);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void apply() {}

    public static void main(String[] args) {
//        System.out.println(new JUL_R().LOGGING_CONFIG.str());

        Logger logger = JUL.getLogger(JUL_R.class);
        logger.info("hello");

        //JUL.apply();
        //JUL.getLogger(Object.class);
        //JUL.applyLoggingConfigurations();
        //logger.info("** Hello, Logging!");
    }

}
