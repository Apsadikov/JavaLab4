package ru.itis.websocket.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
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
import java.util.List;
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
            messageEvent.getPayload().setName(clients.get(session.getId()).getUserDto().getName());
            TextMessage jsonMessage = new TextMessage(objectMapper.writeValueAsString(messageEvent));

            String roomId = messageEvent.getPayload().getRoomId();
            Optional<Client> optionalClient = rooms.get(roomId).getClients().stream()
                    .filter(client -> client.getSession().getId().equals(session.getId())).findFirst();
            if (optionalClient.isPresent()) {
                rooms.get(roomId).getClients().stream()
                        .filter(client -> !client.getSession().getId().equals(session.getId()))
                        .forEach(client -> {
                            try {
                                client.getSession().sendMessage(jsonMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } else if (header.equals("room")) {
            Event<Room> roomEvent = objectMapper.readValue(event, new TypeReference<Event<Room>>() {
            });
            rooms.putIfAbsent(roomEvent.getPayload().getId(), Room.builder()
                    .id(roomEvent.getPayload().getId())
                    .clients(new ArrayList<>())
                    .build());
            List<Client> clients = rooms.get(roomEvent.getPayload().getId()).getClients();
            if (!clients.contains(WebSocketEventHandler.clients.get(session.getId()))) {
                clients.add(WebSocketEventHandler.clients.get(session.getId()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        rooms.forEach((roomId, room) -> {
            Optional<Client> optionalClient = room.getClients().stream()
                    .filter(client -> client.getSession().getId().equals(session.getId())).findFirst();
            optionalClient.ifPresent(client -> room.getClients().remove(client));
        });
        clients.remove(session.getId());
    }
}
