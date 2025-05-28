package ru.master.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // frontend будет подключаться сюда
                .setAllowedOriginPatterns("*")
                .withSockJS(); // SockJS fallback для старых браузеров
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // канал рассылки событий
        registry.setApplicationDestinationPrefixes("/app"); // префикс для клиентских сообщений (если надо)
    }
}
