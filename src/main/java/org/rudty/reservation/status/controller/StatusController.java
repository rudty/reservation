package org.rudty.reservation.status.controller;

import org.rudty.reservation.status.dto.ReservationStatusDTO;
import org.rudty.reservation.status.service.ReservationStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 현재 예약 상황을 볼 수 있게 하는 컨트롤러
 * 관련된 기능들을 제공합니다.
 */
@RestController
@RequestMapping("/status")
public class StatusController {

    private static final String RESULT_FAIL = "{result:\"FAIL\"}";
    private static final String RESULT_ERROR_MESSAGE = "{\"result\":\"ERROR\", \"reason\":\"%s\"}";

    private static final HttpHeaders JSON_HEADER = new HttpHeaders();
    static {
        JSON_HEADER.setContentType(MediaType.APPLICATION_JSON);
    }

    @Autowired
    private ReservationStatusService reservationStatusService;


    /**
     * 인자가 없음
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String emptyRequest() {
        return RESULT_FAIL;
    }


    /**
     * @param beginDate 요청 시작 시간 yyyy-mm-dd hh:mm:ss
     * @param beginDate 요청 끝 시간 yyyy-mm-dd hh:mm:ss
     * @return JSON 예약정보
     */
    @RequestMapping(method = RequestMethod.GET,params = {"beginDate", "endDate"})
    public List<ReservationStatusDTO> status(@RequestParam(name = "beginDate")
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginDate,

                                             @RequestParam(name = "endDate")
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {

        return reservationStatusService.findReservations(beginDate, endDate);
    }

    /**
     * @param e 유효성 검증 에러
     * @return 에러메세지
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> validateHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(String.format(RESULT_ERROR_MESSAGE, e.getMessage()), JSON_HEADER, HttpStatus.BAD_REQUEST); }

    /**
     *
     * @param e 날짜 유효성 검증 에러
     * @return 에러메세지
     */
    @ExceptionHandler(value = ConversionFailedException.class)
    public ResponseEntity<String> validateExceptionHandler(ConversionFailedException e){
        return new ResponseEntity<>(String.format(RESULT_ERROR_MESSAGE, "date convert fail"), JSON_HEADER, HttpStatus.BAD_REQUEST);
    }


}
