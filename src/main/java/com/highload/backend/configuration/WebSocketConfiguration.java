package com.highload.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 1. Добавляем префикс "/exchange", чтобы Spring перенаправлял такие сообщения в RabbitMQ
        // Также полезно добавить "/topic" и "/queue" для стандартных функций RabbitMQ
        config.enableStompBrokerRelay("/exchange", "/topic", "/queue")
                .setRelayHost("rabbitmq")
                .setRelayPort(61613) // Порт STOMP плагина в RabbitMQ
                .setClientLogin("guest")
                .setClientPasscode("guest");
        config.setApplicationDestinationPrefixes("/app"); // Префикс для входящих сообщений
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Этот останется для фронтенда с SockJS
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();

        // Этот будет для Postman (без SockJS)
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    }
}