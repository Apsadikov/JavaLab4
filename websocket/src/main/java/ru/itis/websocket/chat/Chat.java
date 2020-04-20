package ru.itis.websocket.chat;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Chat {
    private static final Map<String, Client> clients = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper;

}
