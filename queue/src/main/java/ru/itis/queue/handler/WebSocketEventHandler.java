package ru.itis.queue.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.queue.dto.*;
import ru.itis.queue.protocol.JlqmServer;

import java.io.IOException;

@Component
@EnableWebSocket
public class WebSocketEventHandler extends TextWebSocketHandler {
    private JlqmServer jlqmServer;
    private ObjectMapper objectMapper;

    @Autowired
    public WebSocketEventHandler(JlqmServer jlqmServer, ObjectMapper objectMapper) {
        this.jlqmServer = jlqmServer;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String command = objectMapper.readValue(message.getPayload().toString(), Command.class).getCommand();
        switch (command) {
            case "SUBSCRIBE":
                Subscribe subscribe = objectMapper.readValue(message.getPayload().toString(),
                        new TypeReference<Command<Subscribe>>() {
                        }).getPayload();
                jlqmServer.subscribe(subscribe, text -> {
                    if (session.isOpen()) {
                        try {
                            session.sendMessage(new TextMessage(text));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case "SEND":
                Send send = objectMapper.readValue(message.getPayload().toString(),
                        new TypeReference<Command<Send>>() {
                        }).getPayload();
                jlqmServer.send(send);
                break;
            case "COMPLETE":
                Complete complete = objectMapper.readValue(message.getPayload().toString(),
                        new TypeReference<Command<Complete>>() {
                        }).getPayload();
                jlqmServer.complete(complete);
                break;
            case "ACCEPT":
                Accept accept = objectMapper.readValue(message.getPayload().toString(),
                        new TypeReference<Command<Accept>>() {
                        }).getPayload();
                jlqmServer.accept(accept);
                break;
        }
    }

}
