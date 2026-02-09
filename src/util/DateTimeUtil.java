package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    public static LocalDateTime parse(String value) {
        return LocalDateTime.parse(value, FORMATTER);
    }

    public static String format(LocalDateTime value) {
        return value.format(FORMATTER);
    }
}
