package yich.base.time;

import yich.base.logging.JUL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.regex.Pattern;

abstract public class AbsTimeInflater implements TimeInflater {
    final private static Logger logger = JUL.getLogger(AbsTimeInflater.class);

    abstract protected Pattern datePtn();

    abstract protected Pattern timePtn();

    abstract protected Pattern datetimePtn();

    abstract protected DateTimeFormatter datetimeFormatter();

    abstract protected DateTimeFormatter dateFormatter();

    abstract protected LocalDateTime parseTime(String time);

    @Override
    public LocalDateTime toDateTime(String strTime) {
        LocalDateTime ldTime;
        if (strTime == null) {
            return null;
        } else if (datePtn().matcher(strTime).matches()) {
            ldTime = LocalDate.parse(strTime, dateFormatter())
                    .atTime(0, 0 , 0);
        } else if (datetimePtn().matcher(strTime).matches()) {
            ldTime = LocalDateTime.parse(strTime, datetimeFormatter());
        } else if (timePtn().matcher(strTime).matches()){
            ldTime = parseTime(strTime);
        }else {
            logger.severe("Illegal Date-Time Format: '" + strTime + "'");
            throw new RuntimeException("Illegal Date-Time Format");
        }
        return ldTime;
    }

    @Override
    public long toMilli(String strTime) {
        return strTime == null ? 0 : toDateTime(strTime)
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
    }

}
