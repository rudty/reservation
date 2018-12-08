package org.rudty.reservation.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringToLocalDateTimeConverter
        implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                .toFormatter();
        try {
            return LocalDateTime.parse(source, formatter);
        } catch (DateTimeParseException e) {
            //2011-01-01 24:00:00 이라면
            //2011-01-02 00:00:00 으로 바꿈
            LocalDateTime time = parse24HourString(source);
            if(time != null) {
                return time;
            }
            throw e;
        }
    }

    /**
     * 같은 날짜인데
     * @param source yyyy-MM-dd HH:mm:ss 문자열
     * @return 가능하다면 날짜 값 불가능하면 null
     */
    private static LocalDateTime parse24HourString(String source) {
        Pattern datePattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2}) 24:00\\:?0?0?");
        Matcher matcher = datePattern.matcher(source);
        if (matcher.find()) {
            int year = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int day = Integer.parseInt(matcher.group(3));

            return LocalDateTime.of(year, month, day + 1, 0, 0);
        }

        return null;
    }
}