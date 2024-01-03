package com.example.clientdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
@EnableWebSocket
public class WebSocketConfig {
    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient,
                                                     StompSessionHandler stompSessionHandler) {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        webSocketStompClient.connect("ws://localhost:8080/chatserver", stompSessionHandler);
        return webSocketStompClient;
    }

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
//        List<Transport> transports = new ArrayList<>();
//        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//        transports.add(new RestTemplateXhrTransport());
//        return new SockJsClient(transports);
    }

    @Bean
    public StompSessionHandler stompSessionHandler() {
        return new ClientStompSessionHandler();
    }
}
