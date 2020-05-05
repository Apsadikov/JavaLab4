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
import ru.itis.queue.entity.Message;
import ru.itis.queue.entity.Status;
import ru.itis.queue.service.MessageService;
import ru.itis.queue.util.UuidGenerator;

import java.util.HashMap;
import java.util.Optional;

@Component
@EnableWebSocket
public class WebSocketEventHandler extends TextWebSocketHandler {
    private final MessageService messageService;
    private HashMap<String, WebSocketSession> consumers = new HashMap<>();
    private ObjectMapper objectMapper;

    @Autowired
    public WebSocketEventHandler(ObjectMapper objectMapper, MessageService messageService) {
        this.objectMapper = objectMapper;
        this.messageService = messageService;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String command = objectMapper.readValue(message.getPayload().toString(), Command.class).getCommand();

        switch (command) {
            case "SUBSCRIBE":
                Command<Subscribe> subscribe = objectMapper.readValue(message.getPayload().toString(),
                        new TypeReference<Command<Subscribe>>() {
                        });
                consumers.putIfAbsent(subscribe.getPayload().getQueueName(), session);
                break;
            case "GET":
                Get getCommand = objectMapper.readValue(message.getPayload().toString(),
                        new TypeReference<Command<Get>>() {
                        }).getPayload();
                if (consumers.get(getCommand.getQueueName()) != null) {
                    Optional<Message> optionalMessage = messageService.getNewMessage();
                    if (optionalMessage.isPresent()) {
                        Command<Message> messageCommand = new Command<>("RECEIVE", optionalMessage.get());
                        WebSocketMessage<String> webSocketMessage =
                                new TextMessage(objectMapper.writeValueAsString(messageCommand));
                        consumers.get(getCommand.getQueueName()).sendMessage(webSocketMessage);
                    }
                }
                break;
            case "SEND":
                Send sendCommand = objectMapper.readValue(message.getPayload().toString(),
                        new TypeReference<Command<Send>>() {
                        }).getPayload();
                Message newMessage = Message.builder()
                        .uuid(UuidGenerator.generate())
                        .status(Status.NEW)
                        .body(sendCommand.getBody())
                        .queueName(sendCommand.getQueueName())
                        .build();
                messageService.save(newMessage);
                if (consumers.get(sendCommand.getQueueName()) != null) {
                    Command<Message> messageCommand = new Command<>("RECEIVE", newMessage);
                    WebSocketMessage<String> webSocketMessage =
                            new TextMessage(objectMapper.writeValueAsString(messageCommand));
                    consumers.get(sendCommand.getQueueName()).sendMessage(webSocketMessage);
                }
                break;
            case "COMPLETE":
                CompleteMessage completeMessageCommand = objectMapper.readValue(message.getPayload().toString(),
                        new TypeReference<Command<CompleteMessage>>() {
                        }).getPayload();
                messageService.complete(completeMessageCommand.getUuid());
                break;
            case "ACCEPT":
                AcceptMessage acceptMessageCommand = objectMapper.readValue(message.getPayload().toString(),
                        new TypeReference<Command<AcceptMessage>>() {
                        }).getPayload();
                messageService.complete(acceptMessageCommand.getUuid());
                break;
        }
    }
}
