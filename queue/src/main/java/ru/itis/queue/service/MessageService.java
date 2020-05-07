package ru.itis.queue.service;

import ru.itis.queue.entity.Message;

public interface MessageService {
    void save(Message message);

    void complete(String uuid);

    void accept(String uuid);
}
