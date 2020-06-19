package ru.itis.demo.repository.mapper;

import ru.itis.demo.entity.Note;
import ru.itis.demo.entity.User;
import ru.itis.demo.repository.mapper.base.AbstractJoinRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserJoinRowMapper extends AbstractJoinRowMapper<User, Long> {
    private UserRowMapper userRowMapper;
    private NoteRowMapper noteRowMapper;

    public UserJoinRowMapper() {
        userRowMapper = new UserRowMapper();
        noteRowMapper = new NoteRowMapper();
    }

    @Override
    protected void forEach(ResultSet rows, HashMap<Long, User> linkedHashMap) throws SQLException {
        User user = userRowMapper.mapRow(rows, User.class.getSimpleName().toLowerCase());
        Note note = noteRowMapper.mapRow(rows, Note.class.getSimpleName().toLowerCase());
        if (linkedHashMap.get(user.getId()) == null) {
            linkedHashMap.put(user.getId(), user);
            user.setNotes(new ArrayList<>());
        }
        if (!Long.valueOf(0L).equals(note.getId())) {
            linkedHashMap.get(user.getId()).getNotes().add(note);
        }
    }
}
