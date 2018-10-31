package yich.base.predicate;


import yich.base.time.DefaultTimeInflater;
import yich.base.time.TimeInflater;
import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.time.LocalDateTime;
import java.util.logging.Logger;

abstract public class TimePredicate<T> extends AbsPredicate<T> {
    final private static Logger logger = JUL.getLogger(TimePredicate.class);

//    private Pattern date_ptn;
//    private Pattern datetime_ptn;
//    private DateTimeFormatter dateFormatter;
//    private DateTimeFormatter timeFormatter;

    private TimeInflater timeInflater;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimePredicate(String name) {
        super(name);
        init(null);
    }

    public TimePredicate(String name, int level) {
        super(name, level);
        init(null);
    }

    public TimePredicate(String name, TimeInflater timeInflater) {
        super(name);
        init(timeInflater);
    }

    public TimePredicate(String name, int level, TimeInflater timeInflater) {
        super(name, level);
        init(timeInflater);
    }

    private void init(TimeInflater inflater) {
//        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        this.timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        this.date_ptn = Pattern.compile("^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");
//        this.datetime_ptn = Pattern.compile("^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])" +
//                "\\s([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");

        this.timeInflater = inflater == null ? new DefaultTimeInflater() : inflater;
        setTime("1970-01-01 00:00:00", null);
    }

    public TimeInflater getTimeInflater() {
        return timeInflater;
    }

    public TimePredicate setTimeInflater(TimeInflater timeInflater) {
        Require.argumentNotNull(timeInflater, "TimeInflater timeInflater");
        this.timeInflater = timeInflater;
        return this;
    }

    public TimePredicate setTime(String start, String end) {
        LocalDateTime start_temp = parseDateTime(start);
        LocalDateTime end_temp = parseDateTime(end);
//        LocalDateTime start_temp = (start == null) ? null : LocalDateTime.parse(start, dateFormatter);
//        LocalDateTime end_temp = (end == null) ? null : LocalDateTime.parse(end, dateFormatter);
        if (start_temp != null && end_temp != null) {
            Require.argumentWCM(!end_temp.isBefore(start_temp), "Argument 'End Time' is before 'Start Time'");
        }
        this.startTime = start_temp;
        this.endTime = end_temp;

//        logger.info("** Set Time Filter from " + startTime.toString() + " to " + endTime.toString());

        logger.info("*** Set TimeFilter(" + getName() + ") to " + startTime + " - " + endTime);
        return this;
    }

    public TimePredicate setTime(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null) {
            Require.argumentWCM(!end.isBefore(start), "Argument 'End Time' is before 'Start Time'");
        }
        this.startTime = start;
        this.endTime = end;

        logger.info("*** Set TimeFilter(" + getName() + ") to " + startTime + " - " + endTime);
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    private LocalDateTime parseDateTime(String time) {
//        LocalDateTime postTime;
//        if (time == null) {
//            return null;
//        } else if (date_ptn.matcher(time).matches()) {
//            postTime = LocalDate.parse(time, this.dateFormatter)
//                    .atTime(0, 0 , 0);
//        } else if (datetime_ptn.matcher(time).matches()) {
//            postTime = LocalDateTime.parse(time, this.timeFormatter);
//        } else {
//            logger.severe("Illegal Date-Time Format: '" + time + "'");
//            throw new RuntimeException("Illegal Date-Time Format");
//        }
//        return postTime;

        return timeInflater.toDateTime(time);
    }

    abstract protected String getTargetTimeAsString(T target);

    public boolean isTimeRight(T target) {
        LocalDateTime postTime = parseDateTime(getTargetTimeAsString(target));
        if (postTime == null) {
            return false;
        }
        if (this.startTime != null && postTime.isBefore(this.startTime)) {
            return false;
        }
        if (this.endTime != null && postTime.isAfter(this.endTime)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean test(T target) {
        return isTimeRight(target);
    }

}
