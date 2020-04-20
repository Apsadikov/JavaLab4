package ru.itis.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.itis.websocket.handler.WebSocketEventHandler;
import ru.itis.websocket.interseptor.AuthInterceptor;

@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final AuthInterceptor authInterceptor;
    private WebSocketEventHandler webSocketEventHandler;

    @Autowired
    public WebSocketConfiguration(WebSocketEventHandler webSocketEventHandler, AuthInterceptor authInterceptor) {
        this.webSocketEventHandler = webSocketEventHandler;
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(webSocketEventHandler, "/chat")
                .withSockJS()
                .setInterceptors(authInterceptor);
    }
}
