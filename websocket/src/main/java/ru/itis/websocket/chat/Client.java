package ru.itis.websocket.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import ru.itis.websocket.dto.UserDto;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Client {
    private UserDto userDto;
    private WebSocketSession session;
}
