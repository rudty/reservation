package org.rudty.reservation.common;

import org.springframework.dao.DataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLSingleResultMapper {
    public static Integer intResultIfExists(ResultSet rs) throws SQLException, DataAccessException {
        if(rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }
    public static Long longResultIfExists(ResultSet rs) throws SQLException, DataAccessException {
        if(rs.next()) {
            return rs.getLong(1);
        }
        return null;
    }
}
