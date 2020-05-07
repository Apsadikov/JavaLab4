package ru.itis.stomp.queue.protocol;

public interface Client {
    void sendMessage(String message);
}
