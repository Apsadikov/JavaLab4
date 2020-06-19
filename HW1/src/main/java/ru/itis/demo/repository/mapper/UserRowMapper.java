package ru.itis.demo.repository.mapper;

import ru.itis.demo.entity.User;
import ru.itis.demo.repository.mapper.base.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper extends AbstractRowMapper<User> {

    @Override
    public User mapRow(ResultSet row, String tablePrefix) throws SQLException {
        return User.builder()
                .firstName(row.getString(column(tablePrefix, "first_name")))
                .lastName(row.getString(column(tablePrefix, "last_name")))
                .id(row.getLong(column(tablePrefix, "id")))
                .build();
    }
}
