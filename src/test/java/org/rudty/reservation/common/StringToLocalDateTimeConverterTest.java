package org.rudty.reservation.common;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class StringToLocalDateTimeConverterTest {
    private StringToLocalDateTimeConverter converter = new StringToLocalDateTimeConverter();

    private LocalDateTime newLocalDateTime(String s){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                .toFormatter();

        return LocalDateTime.parse(s, formatter);
    }

    @Test
    public void 변환_일반_날짜_문자() {
        try {
            converter.convert("2018-10-11 00:00");
        } catch (Exception ignore) {
            Assert.fail("이건 잘되어야 합니다");
        }
    }

    @Test
    public void 변환_일반_날짜_문자2() {
        try {
            converter.convert("2018-10-11 00:00:00");
        } catch (Exception ignore) {
            Assert.fail("이건 잘되어야 합니다");
        }
    }

    @Test
    public void 변환24시문자열() {
        try {
            LocalDateTime result = converter.convert("2018-10-11 24:00:00");
             LocalDateTime compare = newLocalDateTime("2018-10-12 00:00:00");
             Assert.assertEquals(result, compare);
        } catch (Exception ignore) {
            Assert.fail("이건 잘되어야 합니다");
        }
    }

    @Test
    public void 변환24시문자열2() {
        try {
            LocalDateTime result = converter.convert("2018-10-11 24:00");
            LocalDateTime compare = newLocalDateTime("2018-10-12 00:00:00");
            Assert.assertEquals(result, compare);
        } catch (Exception ignore) {
            Assert.fail("이건 잘되어야 합니다");
        }
    }
}
