package org.rudty.reservation.common;


/**
 * 음.. 예약 관련 작업을 하다가 뭔가 예외가 발생했습니다
 * 생성자에서 받는 message 는 에러 코드로 전달됩니다
 */
public class ReservationException extends RuntimeException {
    public ReservationException(String message) {
        super(message);
    }
}
