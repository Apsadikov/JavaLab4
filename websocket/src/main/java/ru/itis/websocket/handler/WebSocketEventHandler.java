package ru.itis.websocket.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.websocket.chat.Client;
import ru.itis.websocket.chat.event.Event;
import ru.itis.websocket.chat.event.Message;
import ru.itis.websocket.chat.event.Room;
import ru.itis.websocket.dto.UserDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableWebSocket
public class WebSocketEventHandler extends TextWebSocketHandler {
    private static final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private static final Map<String, Client> clients = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper;

    @Autowired
    public WebSocketEventHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clients.put(session.getId(), Client.builder()
                .userDto((UserDto) session.getAttributes().get("user"))
                .session(session)
                .build()
        );
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String event = message.getPayload().toString();
        String header = objectMapper.readValue(event, Event.class).getHeader();
        if (header.equals("message")) {
            Event<Message> messageEvent = objectMapper.readValue(event, new TypeReference<Event<Message>>() {
            });
            rooms.forEach((key, value) -> {
                if (value.getId().equals(messageEvent.getPayload().getRoomId())) {
                    value.getClients().forEach(client -> {
                        try {
                            client.getSession().sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        } else if (header.equals("room")) {
            Event<Room> roomEvent = objectMapper.readValue(event, new TypeReference<Event<Room>>() {
            });
            rooms.putIfAbsent(roomEvent.getPayload().getId(), Room.builder()
                    .id(roomEvent.getPayload().getId())
                    .clients(new ArrayList<>(Collections.singleton(clients.get(session.getId()))))
                    .build());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        for (Map.Entry<String, Room> room : rooms.entrySet()) {
            Optional<Client> optionalClient = room.getValue().getClients().stream()
                    .filter(client -> client.getSession().getId().equals(session.getId())).findFirst();
            optionalClient.ifPresent(client -> room.getValue().getClients().remove(client));
        }
        clients.remove(session.getId());
    }
}
