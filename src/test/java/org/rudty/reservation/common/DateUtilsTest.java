package org.rudty.reservation.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateUtilsTest {

    private LocalDateTime newLocalDateTime(String s){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                .toFormatter();

        return LocalDateTime.parse(s, formatter);
    }

    @Test
    public void 앞의_날짜가_더_크다(){
        LocalDateTime beginDate = newLocalDateTime("2018-02-01 23:59:59");
        LocalDateTime endDate = newLocalDateTime("2018-01-01 23:59:59");
        try {
            DateUtils.checkBetweenDates(beginDate, endDate, 0);
            Assert.fail("에러를 던짐");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void 같은날짜(){
        LocalDateTime beginDate = newLocalDateTime("2018-02-01 23:59:59");
        LocalDateTime endDate = newLocalDateTime("2018-02-01 23:59:59");
        try {
            DateUtils.checkBetweenDates(beginDate, endDate, 0);
            Assert.fail("에러를 던짐");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void 다음날0시는_전날24시_취급() {
        LocalDateTime beginDate = newLocalDateTime("2018-02-01 00:00:00");
        LocalDateTime endDate = newLocalDateTime("2018-02-02 00:00:00");
        try {
            DateUtils.checkBetweenDates(beginDate, endDate, 0);
        }catch (Exception e){
            Assert.fail("에러없음" + e.getMessage());
        }
    }

    @Test
    public void 최대1일차이남(){
        LocalDateTime beginDate = newLocalDateTime("2018-02-01 23:59:59");
        LocalDateTime endDate = newLocalDateTime("2018-02-02 23:59:59");
        try {
            DateUtils.checkBetweenDates(beginDate, endDate, 1);
        }catch (Exception e){
            Assert.fail("에러없음" + e.getMessage());
        }
    }

    @Test
    public void 최대2일차이남(){
        LocalDateTime beginDate = newLocalDateTime("2018-02-01 23:59:59");
        LocalDateTime endDate = newLocalDateTime("2018-02-03 23:59:59");
        try {
            DateUtils.checkBetweenDates(beginDate, endDate, 2);
        }catch (Exception e){
            Assert.fail("에러없음" + e.getMessage());
        }
    }

    @Test
    public void 최대2일차이나야하는데3일차이남(){
        LocalDateTime beginDate = newLocalDateTime("2018-02-01 23:59:59");
        LocalDateTime endDate = newLocalDateTime("2018-02-04 23:59:59");
        try {
            DateUtils.checkBetweenDates(beginDate, endDate, 2);
            Assert.fail("에러");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void 현재보다_이전에_예약(){
        LocalDateTime dateTime = newLocalDateTime("2018-01-01 00:00:00");
        Assert.assertFalse("이전임",DateUtils.isAfterThanNow(dateTime));
    }

    @Test
    public void 현재보다_이후_예약(){
        LocalDateTime dateTime = newLocalDateTime("2020-01-01 00:00:00");
        Assert.assertTrue("이후임",DateUtils.isAfterThanNow(dateTime));
    }
}
