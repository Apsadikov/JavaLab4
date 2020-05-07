package ru.itis.sdk.protocol.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import ru.itis.sdk.dto.Command;
import ru.itis.sdk.dto.Send;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class JlqmStompWebSocketProducer implements JlqmProducer {
    private StompSession session;
    private ObjectMapper objectMapper;
    private String queue;

    public JlqmStompWebSocketProducer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void send(Send send) {
        send.setQueueName(queue);
        Command<Send> sendCommand = new Command<>("SEND", send);
        try {
            session.send("/app/queue", objectMapper.writeValueAsString(sendCommand));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(URI uri, String queue) {
        this.queue = queue;
        org.springframework.web.socket.client.WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        try {
            session = stompClient.connect("ws://localhost:8080/queue", new StompSessionHandler() {
                @SneakyThrows
                @Override
                public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
                }

                @Override
                public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
                }

                @Override
                public void handleTransportError(StompSession stompSession, Throwable throwable) {
                }

                @Override
                public Type getPayloadType(StompHeaders stompHeaders) {
                    return String.class;
                }

                @Override
                public void handleFrame(StompHeaders stompHeaders, Object o) {
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
