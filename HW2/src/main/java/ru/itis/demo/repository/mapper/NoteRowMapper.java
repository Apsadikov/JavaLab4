package ru.itis.demo.repository.mapper;

import ru.itis.demo.entity.Note;
import ru.itis.demo.repository.mapper.base.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteRowMapper extends AbstractRowMapper<Note> {

    @Override
    public Note mapRow(ResultSet row, String tablePrefix) throws SQLException {
        return Note.builder()
                .id(row.getLong(column(tablePrefix, "id")))
                .text(row.getString(column(tablePrefix, "text")))
                .userId(row.getLong(column(tablePrefix, "user_id")))
                .build();
    }
}