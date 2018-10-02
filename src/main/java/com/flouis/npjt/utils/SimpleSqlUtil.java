package com.flouis.npjt.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Flouis
 * @date 2018/10/02
 * @description package JdbcTemplate operations
 **/
@Component
public class SimpleSqlUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @description generally used to insert with returning key id
     */
    public String getKeyUpdate(String sql, Object[] objs){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                if ((objs != null) && (objs.length > 0)){
                    for (int i = 1; i <= objs.length; i++){
                        ps.setObject(i, objs[i-1]);
                    }
                }
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().toString();
    }

}
