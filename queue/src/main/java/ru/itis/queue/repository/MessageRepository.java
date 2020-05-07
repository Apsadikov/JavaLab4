package ru.itis.queue.repository;

import ru.itis.queue.entity.Message;

public interface MessageRepository extends CrudRepository<Message, String> {

    void updateStatus(Message message);
}
