package ru.itis.sdk.protocol.producer;

import ru.itis.sdk.dto.Send;

import java.net.URI;

public interface JlqmProducer {
    void send(Send send);

    void start(URI uri, String queue);
}
