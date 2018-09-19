package com.flouis.npjt.pojo.rowMapper;

import com.flouis.npjt.pojo.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setAge(resultSet.getInt("age"));
        user.setGender(resultSet.getInt("gender"));
        user.setHomeAddress(resultSet.getString("home_address"));
        user.setCreateTime(resultSet.getString("create_time"));
        user.setUpdateTime(resultSet.getString("update_time"));
        return user;
    }
}
