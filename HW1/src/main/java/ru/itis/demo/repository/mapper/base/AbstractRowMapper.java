package ru.itis.demo.repository.mapper.base;

import ru.itis.demo.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRowMapper<T extends Entity> implements RowMapper<T> {

    @Override
    public T mapRow(ResultSet row) throws SQLException {
        return mapRow(row, "");
    }

    protected String column(String prefix, String column) {
        return prefix.isEmpty() ? column : prefix + "." + column;
    }

    @Override
    public List<T> mapRows(ResultSet rows) throws SQLException {
        List<T> userList = new ArrayList<>();
        do {
            userList.add(mapRow(rows));
        } while (rows.next());
        return userList;
    }
}
