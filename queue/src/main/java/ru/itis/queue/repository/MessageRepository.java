package ru.itis.queue.repository;

import ru.itis.queue.entity.Message;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, String> {
    Optional<Message> getNewMessage();

    void updateStatus(Message message);
}
