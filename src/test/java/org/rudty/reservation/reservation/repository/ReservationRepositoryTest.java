package org.rudty.reservation.reservation.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void developTest() {
        //개발용
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        LocalDateTime beginTime = LocalDateTime.parse("2018-12-07 13:00:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("2018-12-07 23:00:00", formatter);
        System.out.println(beginTime.toString());
        System.out.println(endTime.toString());

        System.out.println(reservationRepository.availabilityReservation(beginTime, endTime, 1, 2));
    }

    @Test
    public void 예약가능한_날짜_앞() {

        jdbcTemplate.execute("exec request_reservation '2015-01-01 13:00:00','2015-01-01 14:00:00',1,1,0 ");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        LocalDateTime beginTime = LocalDateTime.parse("2015-01-01 00:00:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("2015-01-01 13:00:00", formatter);
        System.out.println(beginTime.toString());
        System.out.println(endTime.toString());

//        System.out.println(reservationRepository.availabilityReservation(beginTime, endTime, 1, 1));
        Assert.assertTrue(reservationRepository.availabilityReservation(beginTime, endTime, 1, 1));
        jdbcTemplate.execute("delete from reservation where beginTime < '2017-01-01'");
    }


    @Test
    public void 예약가능한_날짜_뒤() {
//        db에 이렇게 들어 갔음
//        begin 2018-12-07 19:00:00.000 / end 2018-12-07 20:00:00.000
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        LocalDateTime beginTime = LocalDateTime.parse("2018-12-07 20:00:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("2018-12-07 21:00:00", formatter);
        System.out.println(beginTime.toString());
        System.out.println(endTime.toString());

//        System.out.println(reservationRepository.availabilityReservation(beginTime, endTime, 1, 1));
        Assert.assertTrue(reservationRepository.availabilityReservation(beginTime, endTime, 1, 1));
    }

    @Test
    public void 중간에_걸침() {
//        db에 이렇게 들어 갔음
//        begin 2018-12-07 19:00:00.000 / end 2018-12-07 20:00:00.000
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        LocalDateTime beginTime = LocalDateTime.parse("2018-12-07 19:30:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("2018-12-07 21:00:00", formatter);
        System.out.println(beginTime.toString());
        System.out.println(endTime.toString());

//        System.out.println(reservationRepository.availabilityReservation(beginTime, endTime, 1, 1));
        Assert.assertFalse(reservationRepository.availabilityReservation(beginTime, endTime, 1, 1));
    }

    @Test
    public void 저번주_반복으로_걸침() {
//        db에 이렇게 들어 갔음
//        begin 2018-12-07 19:00:00.000 / end 2018-12-07 20:00:00.000
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        LocalDateTime beginTime = LocalDateTime.parse("2018-11-30 19:30:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("2018-11-30 21:00:00", formatter);
        System.out.println(beginTime.toString());
        System.out.println(endTime.toString());

//        System.out.println(reservationRepository.availabilityReservation(beginTime, endTime, 1, 1));
        Assert.assertFalse(reservationRepository.availabilityReservation(beginTime, endTime, 1, 2));
    }


    @Test
    public void 동일한_시간_저번주() {
//        db에 이렇게 들어 갔음
//        begin 2018-12-07 19:00:00.000 / end 2018-12-07 20:00:00.000
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        LocalDateTime beginTime = LocalDateTime.parse("2018-11-30 19:00:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("2018-11-30 20:00:00", formatter);
        System.out.println(beginTime.toString());
        System.out.println(endTime.toString());

//        System.out.println(reservationRepository.availabilityReservation(beginTime, endTime, 1, 1));
        Assert.assertFalse(reservationRepository.availabilityReservation(beginTime, endTime, 1, 2));
    }


    @Test
    public void 예약_꼬일수도있어서_주의바람() {
//        db에 이렇게 들어 갔음
//        begin 2018-12-07 19:00:00.000 / end 2018-12-07 20:00:00.000
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        LocalDateTime beginTime = LocalDateTime.parse("2017-01-30 19:00:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("2017-01-30 20:00:00", formatter);
        System.out.println(beginTime.toString());
        System.out.println(endTime.toString());

//        System.out.println(reservationRepository.availabilityReservation(beginTime, endTime, 1, 1));
        reservationRepository.requestReservation(beginTime, endTime, 1, 1,2);
        jdbcTemplate.execute("delete from reservation where beginTime < '2018-01-01'");
    }
}
