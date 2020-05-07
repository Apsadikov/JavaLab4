package ru.itis.sdk.handler;

import ru.itis.sdk.protocol.consumer.JlqmConsumer;

public class StompWebsocketMessageHandler extends WebSocketMessageHandler {
    public StompWebsocketMessageHandler(JlqmConsumer jlqmConsumer) {
        super(jlqmConsumer);
    }
}
