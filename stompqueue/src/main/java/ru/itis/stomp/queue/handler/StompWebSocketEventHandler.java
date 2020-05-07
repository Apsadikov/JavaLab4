package ru.itis.stomp.queue.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.stomp.queue.dto.Accept;
import ru.itis.stomp.queue.dto.Command;
import ru.itis.stomp.queue.dto.Complete;
import ru.itis.stomp.queue.dto.Send;
import ru.itis.stomp.queue.protocol.JlqmServer;

@Component
public class StompWebSocketEventHandler {
    private JlqmServer jlqmServer;
    private ObjectMapper objectMapper;

    @Autowired
    public StompWebSocketEventHandler(JlqmServer jlqmServer, ObjectMapper objectMapper) {
        this.jlqmServer = jlqmServer;
        this.objectMapper = objectMapper;
    }

    public void handleMessage(String message) throws Exception {
        String command = objectMapper.readValue(message, Command.class).getCommand();
        switch (command) {
            case "SEND":
                Send send = objectMapper.readValue(message,
                        new TypeReference<Command<Send>>() {
                        }).getPayload();
                jlqmServer.send(send);
                break;
            case "COMPLETE":
                Complete complete = objectMapper.readValue(message,
                        new TypeReference<Command<Complete>>() {
                        }).getPayload();
                jlqmServer.complete(complete);
                break;
            case "ACCEPT":
                Accept accept = objectMapper.readValue(message,
                        new TypeReference<Command<Accept>>() {
                        }).getPayload();
                jlqmServer.accept(accept);
                break;
        }
    }

}
