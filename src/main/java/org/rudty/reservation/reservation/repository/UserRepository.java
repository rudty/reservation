package org.rudty.reservation.reservation.repository;

import org.rudty.reservation.common.SQLSingleResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


/**
 * UserRepository
 * 이 repository 에서는
 * 요청한 이름으로 sn을 가져옵니다
 */
@Repository
public class UserRepository  {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Long> getUserSN(String userName) {
        Long value = null;
        try {
            value = jdbcTemplate.query("exec get_user_sn ?",
                    SQLSingleResultMapper::longResultIfExists,
                    userName);
        } catch (Exception ignore) {
        }
        return Optional.ofNullable(value);
    }
}
