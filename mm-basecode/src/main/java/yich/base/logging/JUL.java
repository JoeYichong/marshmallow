package yich.base.logging;


import yich.base.util.FileUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class JUL {
    final static private Logger logger;

    private static JULProvider provider;

    public static String PATH;

    static {
        if (Files.exists(Paths.get(System.getProperty("user.dir") +
                File.separator + "_config/logging.properties"))) {
            //System.out.println("_config/logging.properties");
            provider = new JUL_P();
        } else {
            //System.out.println("cfig/logging.properties");
            provider = new JUL_R();
        }

        // create '_logs' directory if not exists
        PATH = System.getProperty("user.dir") + File.separator + "_logs_";
        FileUtil.dirChecker()
                .afterMake("** Logging Directory '"+ PATH + "' Generated.")
                .check(PATH);

        // Note that a Logger object has already been instantiated in the FileUtil class
        // if Logging Directory need to be created before instantiate the JUL Logger object .
        logger = Logger.getLogger(JUL.class.getName());

        //
        logger.info("** Logging Configuration '"+ provider.path() + "' Applied");


    }


    public static Logger getLogger(Class clazz) {
        return provider.getLogger(clazz);
    }

    public static void main(String[] args) {
        //System.out.println("Hello");
    }
}
