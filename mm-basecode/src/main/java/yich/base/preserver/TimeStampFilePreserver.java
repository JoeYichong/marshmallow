package yich.base.preserver;


import yich.base.logging.JUL;
import yich.base.util.StrUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

abstract public class TimeStampFilePreserver<T> extends AbsFilePreserver<T> {
    final private static Logger logger = JUL.getLogger(TimeStampFilePreserver.class);

    private String getFileName_1(String nameSep, int randStrLen) {
        return getBasePath() + getAppendedPath()
                //+ TimeUtil.getLocalDateNow("") + File.separator
                + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + File.separator
                + (getTag() == null ? "" : getTag() + nameSep)
                //+ TimeUtil.getLocalTimeNow("")
                + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"))
                + nameSep + StrUtil.randomAlphaNumeric(randStrLen) + "." + getFormat();
    }

    private String getFileName_2(String nameSep, int randStrLen) {
        return getBasePath() + getAppendedPath()
                + (getTag() == null ? "" : getTag() + nameSep)
                //+ TimeUtil.getLocalDateNow("")
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                //+ TimeUtil.getLocalTimeNow("")
                + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"))
                + nameSep + StrUtil.randomAlphaNumeric(randStrLen) + "." + getFormat();
    }


    protected File getDestFile(String nameSep, int randStrLen, int type) {
        String filename;
        switch(type) {
            case 1:
                filename = getFileName_1(nameSep, randStrLen);
                break;
            case 2:
                filename = getFileName_2(nameSep, randStrLen);
                break;
            case 3:

            default: {
                filename = getFileName_1(nameSep, randStrLen);
            }
        }

        File file = new File(filename);
        File dirs = file.getParentFile();
        if (!dirs.exists()) {
            logger.info("** Making Directory... : " + dirs);
            dirs.mkdirs();
        }
        return file;
    }

    protected File getDestFile(String nameSep, int randStrLen) {
        return getDestFile(nameSep, randStrLen, 1);
    }

}
