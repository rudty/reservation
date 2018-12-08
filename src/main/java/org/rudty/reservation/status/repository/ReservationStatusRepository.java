package org.rudty.reservation.status.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationStatusRepository extends Repository<RoomStatus, String> {

    @Query(value = "exec get_reservation_status ?,?,?", nativeQuery = true)
    List<RoomStatus> findByBeginTimeAndEndTime(LocalDateTime beginTime, LocalDateTime endTime, String roomName);

    @Query(value = "exec get_room_names", nativeQuery = true)
    List<String> findAllRoomName();
}
