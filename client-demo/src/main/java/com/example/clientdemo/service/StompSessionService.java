package com.example.clientdemo.service;

import lombok.Getter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;

@Getter
@Service
public class StompSessionService {
    private StompSession stompSession;

    public void setStompSession(StompSession stompSession) {
        this.stompSession = stompSession;
    }

    public void send(String destination, Object payload) {
        if (stompSession != null && stompSession.isConnected()) {
            stompSession.send(destination, payload);
        } else {
            // Handle the case where session is null or not connected
            // Possibly by throwing an exception or attempting to reconnect
        }
    }
}
