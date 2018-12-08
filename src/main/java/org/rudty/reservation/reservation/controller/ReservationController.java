package org.rudty.reservation.reservation.controller;

import org.rudty.reservation.common.ReservationException;
import org.rudty.reservation.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private static final String RESULT_OK = "{result:\"OK\"}";
    private static final String RESULT_FAIL = "{result:\"FAIL\"}";
    private static final String RESULT_ERROR_MESSAGE = "{\"result\":\"ERROR\", \"reason\":\"%s\"}";
    private static final String RESERVATION_ERROR_MESSAGE = "{\"result\":\"RESERVATION_ERROR\", \"reason\":\"%s\"}";

    private static final HttpHeaders JSON_HEADER = new HttpHeaders();
    static {
        JSON_HEADER.setContentType(MediaType.APPLICATION_JSON);
    }

    @Autowired
    private ReservationService reservationService;


    @RequestMapping(method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public String emptyRequest(){
        return RESULT_FAIL;
    }

    @RequestMapping(method = RequestMethod.POST,
            params = {"beginDate", "endDate", "roomName", "userName", "repeat"},
            produces = "application/json; charset=utf-8")
    public String doReservation (
            @RequestParam(name = "beginDate")
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime beginDate,

            @RequestParam(name = "endDate")
            LocalDateTime endDate,

            String roomName,
            String userName,
            int repeat) {

            reservationService.requestReservation(beginDate, endDate, roomName, userName, repeat);

        return RESULT_OK;
    }

    /**
     *
     * @param e 날짜 유효성 검증 에러
     * @return 에러메세지
     */
    @ExceptionHandler(value = ConversionFailedException.class)
    public ResponseEntity<String> validateExceptionHandler(ConversionFailedException e){
        return new ResponseEntity<>(String.format(RESULT_ERROR_MESSAGE, "date convert fail"), JSON_HEADER, HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param e 날짜 오류
     * @return 에러메세지
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> badDateHandler(IllegalArgumentException e){
        return new ResponseEntity<>(String.format(RESULT_ERROR_MESSAGE, e.getMessage()), JSON_HEADER, HttpStatus.BAD_REQUEST);
    }
    /**
     *
     * @param e 예약 오류
     * @return 에러메세지
     */
    @ExceptionHandler(value = ReservationException.class)
    public ResponseEntity<String> reservationHandler(ReservationException e){
        return new ResponseEntity<>(String.format(RESERVATION_ERROR_MESSAGE, e.getMessage()), JSON_HEADER, HttpStatus.BAD_REQUEST);
    }
}
