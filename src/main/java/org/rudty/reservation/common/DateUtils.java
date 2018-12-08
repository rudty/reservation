package org.rudty.reservation.common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    /**
     * 예약하기에 적절한지 분, 초 판별
     * @param dateTime 예약시간
     * @return true if 0분 0초 혹은 30분 0초
     */
    public static boolean checkReservationMMss(LocalDateTime dateTime){
        int minute = dateTime.getMinute();
        if(minute == 30 || minute == 0) {
            int second = dateTime.getSecond();
            return second == 0;
        }
        throw new IllegalArgumentException("check 0 or 30 min and 0 second");
    }

    /**
     * 만들긴 했는데 테스트 문제로 안쓰는중..
     *
     * @param dateTime 입력받은 시간
     * @return true 현재보다 이후의 시간, false 현재보다 이전의 시간
     */
    public static boolean isAfterThanNow(LocalDateTime dateTime) {
        if (dateTime != null) {
           return LocalDateTime.now().isBefore(dateTime);
        }
        return false;
    }

    /**
     *
     * @param beginDateTime 시작 시간
     * @param endDateTime 끝 시간
     * @param maxDiffDay 최대로 차이나는 날짜 수 maxDiffDay 이상으로 차이나면 에러
     * 사이에 있는 날짜 문자열인지 판별
     */
    public static void checkBetweenDates(LocalDateTime beginDateTime, LocalDateTime endDateTime, int maxDiffDay) {
        if(beginDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("check beginTime < endTime");
        }

        if(beginDateTime.equals(endDateTime)) {
            throw new IllegalArgumentException("check beginTime == endTime");
        }

        if(maxDiffDay == 0){
            if (beginDateTime.getDayOfMonth() != endDateTime.getDayOfMonth()) {
                if (endDateTime.getHour() == 0 && endDateTime.getMinute() == 0){
                    //같은날짜의 00시는 그 전날의 24시까지로 취급
                    //예외를 발생하지 않음.
                } else {
                    throw new IllegalArgumentException("require same date");
                }
            }

        } else if(beginDateTime.until(endDateTime, ChronoUnit.DAYS) > maxDiffDay) {
            throw new IllegalArgumentException("can not view more than a "+maxDiffDay+"day");
        }
    }
    
}
