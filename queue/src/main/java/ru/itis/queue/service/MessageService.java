package ru.itis.queue.service;

import ru.itis.queue.entity.Message;

import java.util.Optional;

public interface MessageService {
    void save(Message message);

    Optional<Message> getNewMessage();

    void complete(String uuid);

    void accept(String uuid);
}
