package yich.base.time;

import java.time.LocalDateTime;

public interface TimeInflater {

    LocalDateTime toDateTime(String strTime);

    long toMilli(String strTime);

}
