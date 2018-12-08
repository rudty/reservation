package org.rudty.reservation.reservation.repository;

import org.rudty.reservation.common.SQLSingleResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RoomRepository
 * 이 repository 에서는
 * 요청한 이름으로 sn을 가져옵니다
 */
@Repository
public class RoomRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Long> getRoomSN(String roomName) {
        Long value = null;
        try {
            value = jdbcTemplate.query("exec get_room_sn ?",
                    SQLSingleResultMapper::longResultIfExists,
                    roomName);
        } catch (Exception ignore) {
        }
        return Optional.ofNullable(value);
    }
}
