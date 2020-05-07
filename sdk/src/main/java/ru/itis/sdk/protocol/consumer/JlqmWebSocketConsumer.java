package ru.itis.sdk.protocol.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import ru.itis.sdk.dto.Accept;
import ru.itis.sdk.dto.Command;
import ru.itis.sdk.dto.Complete;
import ru.itis.sdk.dto.Subscribe;
import ru.itis.sdk.entity.Message;
import ru.itis.sdk.handler.WebSocketMessageHandler;

import java.net.URI;

public class JlqmWebSocketConsumer implements JlqmConsumer {
    private WebSocketClient webSocketClient;
    private ObjectMapper objectMapper;
    private OnReceive listener;
    private WebSocketMessageHandler webSocketMessageHandler;

    public JlqmWebSocketConsumer() {
        webSocketMessageHandler = new WebSocketMessageHandler(this);
        objectMapper = new ObjectMapper();
    }


    @Override
    public void subscribe(Subscribe subscribe) {
        Command<Subscribe> subscribeCommand = new Command<>("SUBSCRIBE", subscribe);
        try {
            webSocketClient.send(objectMapper.writeValueAsString(subscribeCommand));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(Accept accept) {
        Command<Accept> acceptCommand = new Command<>("ACCEPT", accept);
        try {
            webSocketClient.send(objectMapper.writeValueAsString(acceptCommand));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void complete(Complete complete) {
        Command<Complete> completeCommand = new Command<>("COMPLETE", complete);
        try {
            webSocketClient.send(objectMapper.writeValueAsString(completeCommand));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receive(Message message) {
        listener.receive(message, this);
    }

    @Override
    public void start(URI uri, OnReceive listener, String queue) {
        this.listener = listener;
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
            }

            @Override
            public void onMessage(String s) {
                try {
                    webSocketMessageHandler.handle(s);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
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
        subscribe(Subscribe.builder().queueName(queue).build());
    }

    public interface OnReceive {
        void receive(Message message, JlqmConsumer jlqmConsumer);
    }
}
