package com.example.clientdemo.service;

import com.example.clientdemo.config.MyStompSessionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class WebSocketService {
    private StompSession session;
    private String url = "ws://192.168.168.10:8080/websocket";

    @Autowired
    private WebSocketStompClient stompClient;

    public void connect() {
        try {
            this.session = stompClient.connect(url, new MyStompSessionHandler()).get();
        } catch (InterruptedException e) {
            log.error("Connect to ws failed.", e);
        } catch (ExecutionException e) {
            log.error("Connect to ws failed.", e);
        }
    }

    @Scheduled(fixedRate = 10000) // 10 seconds
    public void sendHeartbeat() {
        if (session != null && session.isConnected()) {
            session.send("/app/heartbeat", "Heartbeat message");
        }
    }

}
