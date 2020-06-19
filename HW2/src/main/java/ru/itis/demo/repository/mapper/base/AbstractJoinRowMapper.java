package ru.itis.demo.repository.mapper.base;

import ru.itis.demo.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractJoinRowMapper<T extends Entity, ID> implements JoinRowMapper<T> {

    @Override
    public T mapRow(ResultSet rows) throws SQLException {
        return mapRows(rows).get(0);
    }

    @Override
    public List<T> mapRows(ResultSet rows) throws SQLException {
        HashMap<ID, T> linkedHashMap = new LinkedHashMap<>();
        do {
            forEach(rows, linkedHashMap);
        } while (rows.next());
        return linkedHashMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    protected abstract void forEach(ResultSet rows, HashMap<ID, T> linkedHashMap) throws SQLException;
}
