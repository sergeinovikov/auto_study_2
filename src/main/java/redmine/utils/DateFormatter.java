package redmine.utils;

import java.time.LocalDateTime;
import java.util.Date;

public class DateFormatter {


    public static LocalDateTime convertDate(Date dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }
}
