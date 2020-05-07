package ru.itis.stomp.queue.repository;

import ru.itis.stomp.queue.entity.Message;

public interface MessageRepository extends CrudRepository<Message, String> {

    void updateStatus(Message message);
}
