package ru.itis.queue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.itis.queue.handler.WebSocketEventHandler;

@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {
    private WebSocketEventHandler webSocketEventHandler;

    @Autowired
    public WebSocketConfiguration(WebSocketEventHandler webSocketEventHandler) {
        this.webSocketEventHandler = webSocketEventHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(webSocketEventHandler, "/queue")
                .withSockJS();
    }
}
