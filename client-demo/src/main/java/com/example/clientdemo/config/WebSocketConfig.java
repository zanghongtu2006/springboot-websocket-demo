package com.example.clientdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSocket
public class WebSocketConfig {
    @Bean
    public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient, TaskScheduler taskScheduler) {
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(webSocketClient);
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        webSocketStompClient.setTaskScheduler(taskScheduler);
        webSocketStompClient.setDefaultHeartbeat(new long[]{10000, 10000});

        return webSocketStompClient;
    }

    @Bean
    public WebSocketClient webSocketClient() {
//        return new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        return new SockJsClient(transports);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        // 创建并返回一个ThreadPoolTaskScheduler
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        return scheduler;
    }

    @Bean
    public StompSessionHandler stompSessionHandler(WebSocketStompClient webSocketStompClient, TaskScheduler taskScheduler,
                                                   Config config) {
        return new ClientStompSessionHandler(webSocketStompClient, taskScheduler, config);
    }
}
