package org.rudty.reservation.status.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomRepositoryTest {

    @Autowired
    ReservationStatusRepository reservationRepository;


    LocalDateTime newLocalDateTime(String time){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter();
        return LocalDateTime.parse(time, formatter);
    }
    @Test
    public void defaultFindByBeginTimeAndEndTime() {
        //테스트로 db에 값 넣어둔거 확인용
        List<RoomStatus> r = reservationRepository.findByBeginTimeAndEndTime(
                newLocalDateTime("2018-12-04 23:00:00"),
                newLocalDateTime("2018-12-04 23:31:00"),
                "테스트");

        Assert.assertFalse("테스트용으로 1개를 넣음", r.isEmpty());
        RoomStatus firstElem = r.get(0);
        r.forEach(System.out::println);

        Assert.assertEquals("db에 그냥 넣어둔 데이터와 다릅니다 beginTime", "2018-12-04 23:00:00.0", firstElem.beginTime );
        Assert.assertEquals("db에 그냥 넣어둔 데이터와 다릅니다 endTime", "2018-12-04 23:30:00.0", firstElem.endTime );
        Assert.assertEquals("db에 그냥 넣어둔 데이터와 다릅니다 userName", "테스트 유저", firstElem.userName );
    }

    @Test
    public void testFindRoomName() {
        //테스트로 db에 값 넣어둔거 확인용
        List<String> r = reservationRepository.findAllRoomName();

        Assert.assertFalse("테스트용으로 1개를 넣음", r.isEmpty());

        r.forEach(System.out::println);
    }

}
