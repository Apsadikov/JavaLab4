package ru.itis.demo.repository.mapper.base;

import ru.itis.demo.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface JoinRowMapper<T extends Entity> {
    T mapRow(ResultSet rows) throws SQLException;

    List<T> mapRows(ResultSet rows) throws SQLException;
}
