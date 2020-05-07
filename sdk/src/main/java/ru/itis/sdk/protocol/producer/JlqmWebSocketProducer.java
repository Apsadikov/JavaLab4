package ru.itis.sdk.protocol.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import ru.itis.sdk.dto.Command;
import ru.itis.sdk.dto.Send;

import java.net.URI;

public class JlqmWebSocketProducer implements JlqmProducer {
    private WebSocketClient webSocketClient;
    private ObjectMapper objectMapper;
    private String queue;

    public JlqmWebSocketProducer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void send(Send send) {
        send.setQueueName(queue);
        Command<Send> sendCommand = new Command<>("SEND", send);
        try {
            webSocketClient.send(objectMapper.writeValueAsString(sendCommand));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(URI uri, String queue) {
        this.queue = queue;
        this.webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
            }

            @Override
            public void onMessage(String s) {
                System.out.println(s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
            }

            @Override
            public void onError(Exception e) {
            }
        };
        try {
            webSocketClient.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
