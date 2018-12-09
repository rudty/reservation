package org.rudty.reservation.reservation.service;

import org.rudty.reservation.common.ReservationException;
import org.rudty.reservation.reservation.repository.ReservationRepository;
import org.rudty.reservation.reservation.repository.RoomRepository;
import org.rudty.reservation.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@Service
public class ReservationService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(UserRepository userRepository, RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }


    /**
     * 예약을 신청합니다
     * roomName -> roomsn
     * userName -> usersn
     * 예약 가능여부 확인
     * 예약
     * 의 순서로 진행됩니다.
     *
     * @param beginDate 시작 시간
     * @param endDate   끝 시간
     * @param roomName  방 이름
     * @param userName  사용자 이름
     * @param repeat    몇번 반복
     */
    @Transactional(rollbackFor=Exception.class, isolation = SERIALIZABLE)
    public void requestReservation(LocalDateTime beginDate,
                                   LocalDateTime endDate,
                                   String roomName,
                                   String userName,
                                   int repeat) {

        final long roomSn = roomRepository.getRoomSN(roomName)
                .orElseThrow(() -> new ReservationException("APPLE_PIE"));
        final long userSn = userRepository.getUserSN(userName)
                .orElseThrow(() -> new ReservationException("BANANA_BREAD"));


        //예약이 가능하다면 예약
        if (reservationRepository.availabilityReservation(beginDate, endDate, roomSn, repeat)) {
            reservationRepository.requestReservation(beginDate, endDate, roomSn, userSn, repeat);
        } else {
            throw new ReservationException("CUP_CAKE");
        }

    }
}
