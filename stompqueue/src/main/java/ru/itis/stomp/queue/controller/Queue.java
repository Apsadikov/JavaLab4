package ru.itis.stomp.queue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.itis.stomp.queue.handler.StompWebSocketEventHandler;

@Controller
public class Queue {
    private final StompWebSocketEventHandler handler;

    @Autowired
    public Queue(StompWebSocketEventHandler handler) {
        this.handler = handler;
    }

    @MessageMapping("/queue")
    @SendTo("/topic/message")
    public String queue(Message<String> message) throws Exception {
        handler.handleMessage(message.getPayload());
        return message.getPayload();
    }
}
