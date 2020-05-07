package ru.itis.queue.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.queue.entity.Message;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepositoryJdbcTemplateImpl implements MessageRepository {
    private final RowMapper<Message> messageRowMapper;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate, RowMapper<Message> messageRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.messageRowMapper = messageRowMapper;
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Message> findById(String uuid) {
        return Optional.empty();
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public void save(Message message) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO message(uuid, queue_name, body, status) VALUE (?, ?, ?, ?)");
            statement.setString(1, message.getUuid());
            statement.setString(2, message.getQueueName());
            statement.setString(3, message.getBody());
            statement.setString(4, message.getStatus().name());
            return statement;
        });
    }

    @Override
    public void updateStatus(Message message) {
        jdbcTemplate.update("UPDATE message SET status = ? WHERE uuid = ?",
                new Object[]{message.getStatus().name(), message.getUuid()});
    }
}
