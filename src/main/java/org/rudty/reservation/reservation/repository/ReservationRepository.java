package org.rudty.reservation.reservation.repository;

import org.rudty.reservation.common.SQLSingleResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 예약 가능한지 확인
     *
     * @param beginDate 시작 시간
     * @param endDate   끝 시간
     * @param roomSn    방 번호
     * @param repeat    반복 횟수
     * @return true 가능 / false 불가능
     */
    public boolean availabilityReservation(
            LocalDateTime beginDate,
            LocalDateTime endDate,
            long roomSn,
            int repeat) {

        Integer result = jdbcTemplate.query("exec confirm_availability_reservation ?,?,?,?",
                SQLSingleResultMapper::intResultIfExists,
                beginDate, endDate, roomSn, repeat);

        if (result != null) {
            return result == 1;
        }

        return false;
    }

    /**
     * 예약 하기
     *
     * @param beginDate 시작 시간
     * @param endDate   끝 시간
     * @param roomSn    방 번호
     * @param repeat    반복 횟수
     */
    public void requestReservation(
            final LocalDateTime beginDate,
            final LocalDateTime endDate,
            final long roomSn,
            final long userSn,
            final int repeat) {
        jdbcTemplate.execute("exec request_reservation ?,?,?,?,?",
                newReservationCallback(beginDate, endDate, roomSn, userSn, repeat));
    }

    private final PreparedStatementCallback<Boolean> newReservationCallback(final LocalDateTime beginDate,
                                                              final LocalDateTime endDate,
                                                              final long roomSn,
                                                              final long userSn,
                                                              final int repeat) {
        return ps -> {
            ps.setObject(1, beginDate);
            ps.setObject(2, endDate);
            ps.setLong(3, roomSn);
            ps.setLong(4, userSn);
            ps.setInt(5, repeat);
            return ps.execute();
        };
    }
}
