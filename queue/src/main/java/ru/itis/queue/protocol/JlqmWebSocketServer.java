package ru.itis.queue.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.queue.dto.*;
import ru.itis.queue.entity.Message;
import ru.itis.queue.entity.Status;
import ru.itis.queue.service.MessageService;
import ru.itis.queue.util.UuidGenerator;

import java.io.IOException;
import java.util.HashMap;

@Component
public class JlqmWebSocketServer implements JlqmServer {
    private ObjectMapper objectMapper;
    private MessageService messageService;
    private HashMap<String, Client> consumers;

    @Autowired
    public JlqmWebSocketServer(MessageService messageService, ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.consumers = new HashMap<>();
        this.objectMapper = objectMapper;
    }

    @Override
    public void subscribe(Subscribe subscribe, Client client) {
        consumers.put(subscribe.getQueueName(), client);
    }

    @Override
    public void accept(Accept accept) {
        messageService.accept(accept.getUuid());
    }

    @Override
    public void complete(Complete complete) {
        messageService.complete(complete.getUuid());
    }

    @Override
    public void send(Send send) {
        Message newMessage = Message.builder()
                .uuid(UuidGenerator.generate())
                .status(Status.NEW)
                .body(send.getBody())
                .queueName(send.getQueueName())
                .build();
        messageService.save(newMessage);
        if (consumers.get(send.getQueueName()) != null) {
            Command<Message> messageCommand = new Command<>("RECEIVE", newMessage);
            try {
                consumers.get(send.getQueueName()).sendMessage(objectMapper.writeValueAsString(messageCommand));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
