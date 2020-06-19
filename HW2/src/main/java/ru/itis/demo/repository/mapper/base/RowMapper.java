package ru.itis.demo.repository.mapper.base;

import ru.itis.demo.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowMapper<T extends Entity> {
    T mapRow(ResultSet row) throws SQLException;

    T mapRow(ResultSet row, String tablePrefix) throws SQLException;

    List<T> mapRows(ResultSet rows) throws SQLException;
}