package yich.base.time;


import yich.base.logging.JUL;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class DefaultTimeInflater extends AbsTimeInflater{
    //private static Logger logger = JUL.getLogger(DefaultTimeInflater.class);

    private DateTimeFormatter dateFormatter;
    private DateTimeFormatter timeFormatter;
    private Pattern date_ptn;
    private Pattern datetime_ptn;

    public DefaultTimeInflater() {
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date_ptn = Pattern.compile("^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
        this.datetime_ptn = Pattern.compile("^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])" +
                "\\s([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");
    }

    @Override
    protected Pattern datePtn() {
        return date_ptn;
    }

    @Override
    protected Pattern datetimePtn() {
        return datetime_ptn;
    }

    @Override
    protected DateTimeFormatter timeFormatter() {
        return timeFormatter;
    }

    @Override
    protected DateTimeFormatter dateFormatter() {
        return dateFormatter;
    }

}
