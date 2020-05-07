package ru.itis.sdk.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.sdk.dto.Command;
import ru.itis.sdk.entity.Message;
import ru.itis.sdk.protocol.consumer.JlqmConsumer;

public class WebSocketMessageHandler {
    private ObjectMapper objectMapper;
    private JlqmConsumer jlqmConsumer;

    public WebSocketMessageHandler(JlqmConsumer jlqmConsumer) {
        objectMapper = new ObjectMapper();
        this.jlqmConsumer = jlqmConsumer;
    }

    public void handle(String message) throws JsonProcessingException {
        String command = objectMapper.readValue(message, Command.class).getCommand();
        switch (command) {
            case "RECEIVE":
                Message receive = objectMapper.readValue(message,
                        new TypeReference<Command<Message>>() {
                        }).getPayload();
                jlqmConsumer.receive(receive);
                break;
        }
    }
}
