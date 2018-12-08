package org.rudty.reservation.status.service;

import org.rudty.reservation.common.DateUtils;
import org.rudty.reservation.status.dto.ReservationStatusDTO;
import org.rudty.reservation.status.repository.ReservationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 예약 확인 서비스
 * 예약한 목록들을 확인.
 */
@Service
public class ReservationStatusService {

    private static final int MAX_DIFF_DAY = 7;

    private final ReservationStatusRepository reservationRepository;

    @Autowired
    public ReservationStatusService(ReservationStatusRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public List<ReservationStatusDTO> findReservations(LocalDateTime beginTime, LocalDateTime endTime) {
        DateUtils.checkBetweenDates(beginTime, endTime, MAX_DIFF_DAY);
        List<String> roomNames = reservationRepository.findAllRoomName();

        // 획득한 roomNames 로 쿼리를 날려서
        // 예약 목록을 가져옴
        return roomNames.parallelStream()
                .map(roomName -> new ReservationStatusDTO(roomName,
                        reservationRepository.findByBeginTimeAndEndTime(beginTime, endTime, roomName)))
                .collect(Collectors.toList());
    }
}
