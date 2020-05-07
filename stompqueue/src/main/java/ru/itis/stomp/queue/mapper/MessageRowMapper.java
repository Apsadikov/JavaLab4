package ru.itis.stomp.queue.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.stomp.queue.entity.Message;
import ru.itis.stomp.queue.entity.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MessageRowMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Message.builder()
                .uuid(rs.getString("uuid"))
                .queueName(rs.getString("queue_name"))
                .status(Status.valueOf(rs.getString("status")))
                .body(rs.getString("body"))
                .build();
    }
}
