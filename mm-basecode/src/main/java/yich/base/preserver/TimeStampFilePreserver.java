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

    private int type;

    public TimeStampFilePreserver() {
        this.type = 1;
    }

    private String getFileName_1() {
        return getBasePath() + getAppendedPath()
                //+ TimeUtil.getLocalDateNow("") + File.separator
                + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + File.separator
                + (tag() == null ? "" : tag() + nameSep())
                //+ TimeUtil.getLocalTimeNow("")
                + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"))
                + nameSep() + StrUtil.randomAlphaNumeric(randStrLen()) + "." + format();
    }

    private String getFileName_2() {
        return getBasePath() + getAppendedPath()
                + (tag() == null ? "" : tag() + nameSep())
                //+ TimeUtil.getLocalDateNow("")
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                //+ TimeUtil.getLocalTimeNow("")
                + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"))
                + nameSep() + StrUtil.randomAlphaNumeric(randStrLen()) + "." + format();
    }

    @Override
    public File getDestFile() {
        String filename;
        switch(type) {
            case 1:
                filename = getFileName_1();
                break;
            case 2:
                filename = getFileName_2();
                break;
            case 3:

            default: {
                filename = getFileName_1();
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

    public int getType() {
        return type;
    }

    public TimeStampFilePreserver setType(int type) {
        this.type = type;
        return this;
    }
}
