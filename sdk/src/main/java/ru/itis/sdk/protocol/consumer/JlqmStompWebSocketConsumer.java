package ru.itis.sdk.protocol.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import ru.itis.sdk.dto.Accept;
import ru.itis.sdk.dto.Command;
import ru.itis.sdk.dto.Complete;
import ru.itis.sdk.dto.Subscribe;
import ru.itis.sdk.entity.Message;
import ru.itis.sdk.handler.StompWebsocketMessageHandler;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class JlqmStompWebSocketConsumer implements JlqmConsumer {
    private StompSession stompSession;
    private StompSessionHandler stompSessionHandler;
    private ObjectMapper objectMapper;
    private JlqmWebSocketConsumer.OnReceive listener;
    private StompWebsocketMessageHandler stompWebsocketMessageHandler;

    public JlqmStompWebSocketConsumer() {
        stompWebsocketMessageHandler = new StompWebsocketMessageHandler(this);
        objectMapper = new ObjectMapper();
    }

    @Override
    public void subscribe(Subscribe subscribe) {
        stompSession.subscribe("/topic/message", stompSessionHandler);
    }

    @Override
    public void accept(Accept accept) {
        Command<Accept> acceptCommand = new Command<>("ACCEPT", accept);
        try {
            stompSession.send("/app/queue", objectMapper.writeValueAsString(acceptCommand));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void complete(Complete complete) {
        Command<Complete> completeCommand = new Command<>("COMPLETE", complete);
        try {
            stompSession.send("/app/queue", objectMapper.writeValueAsString(completeCommand));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receive(Message message) {
        listener.receive(message, this);
    }

    @Override
    public void start(URI uri, JlqmWebSocketConsumer.OnReceive listener, String queue) {
        this.listener = listener;
        org.springframework.web.socket.client.WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        try {
            stompSessionHandler = new StompSessionHandler() {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                }

                @Override
                public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                }

                @Override
                public void handleTransportError(StompSession session, Throwable exception) {
                }

                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return String.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    try {
                        stompWebsocketMessageHandler.handle(payload.toString());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            };
            stompSession = stompClient.connect("ws://localhost:8080/queue", stompSessionHandler).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public interface OnReceive {
        void receive(Message message, JlqmConsumer jlqmConsumer);
    }
}
