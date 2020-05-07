package ru.itis.sdk.protocol.consumer;

import ru.itis.sdk.dto.Accept;
import ru.itis.sdk.dto.Complete;
import ru.itis.sdk.dto.Subscribe;
import ru.itis.sdk.entity.Message;

import java.net.URI;

public interface JlqmConsumer {
    void subscribe(Subscribe subscribe);

    void accept(Accept accept);

    void complete(Complete complete);

    void receive(Message message);

    void start(URI uri, JlqmWebSocketConsumer.OnReceive listener, String queue);
}
