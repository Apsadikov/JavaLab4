package ru.itis.stomp.queue.service;

import ru.itis.stomp.queue.entity.Message;

public interface MessageService {
    void save(Message message);

    void complete(String uuid);

    void accept(String uuid);
}
