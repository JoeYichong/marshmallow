package yich.base.util;

import java.time.format.DateTimeFormatter;

public class TimeUtil {


//    public static String getLocalDateNow(String sep) {
//        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy" + sep + "MM" + sep + "dd"));
//    }

//    public static String getLocalTimeNow(String sep) {
//        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH" + sep + "mm" + sep + "ss"));
//    }

    public static DateTimeFormatter dFormatter(String sep) {
        return DateTimeFormatter.ofPattern("yyyy" + sep + "MM" + sep + "dd");
    }

    public static DateTimeFormatter tFormatter(String sep) {
        return DateTimeFormatter.ofPattern("HH" + sep + "mm" + sep + "ss");
    }


    public static void main(String[] args) {

//        System.out.println(date);
//        System.out.println(getLocalTimeNow(" "));

    }

}
