package yich.base.predicate;


import yich.base.time.DefaultTimeInflater;
import yich.base.time.TimeInflater;
import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.time.LocalDateTime;
import java.util.logging.Logger;

abstract public class TimePredicate<T> extends AbsPredicate<T> {
    final private static Logger logger = JUL.getLogger(TimePredicate.class);

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

        if (start_temp != null && end_temp != null && end_temp.isBefore(start_temp)) {
            throw new RuntimeException("End_Time(" + end_temp + ") is before Start_Time(" + start_temp + ")");
//            Require.argumentWCM(!end_temp.isBefore(start_temp), "Argument 'End Time' is before 'Start Time'");
        }
        this.startTime = start_temp;
        this.endTime = end_temp;

//        logger.info("** Set Time Filter from " + startTime.toString() + " to " + endTime.toString());

        logger.info("*** Set TimeFilter(" + getName() + ") to " + startTime + " - " + endTime);
        return this;
    }

    public TimePredicate setTime(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null && end.isBefore(start)) {
            throw new RuntimeException("End_Time(" + end + ") is before Start_Time(" + start + ")");
//            Require.argumentWCM(!end.isBefore(start), "Argument 'End Time' is before 'Start Time'");
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
        return timeInflater.toDateTime(time);
    }

    abstract protected Object getTargetTime(T target);

    public boolean isTimeRight(T target) {
        Object time = getTargetTime(target);
        LocalDateTime postTime = null;
        if (time instanceof String) {
            postTime = parseDateTime((String) time);
        } else if (time instanceof LocalDateTime) {
            postTime = (LocalDateTime) time;
        }

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

//    public static void main(String[] args) {
//        LocalDateTime time = new DefaultTimeInflater().toDateTime("16:43:34");
//        System.out.println(time.format(DateTimeFormatter.BASIC_ISO_DATE));
//    }

}
