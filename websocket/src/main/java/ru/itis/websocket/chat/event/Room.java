package ru.itis.websocket.chat.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.websocket.chat.Client;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Room {
    private String id;
    private List<Client> clients;
}
