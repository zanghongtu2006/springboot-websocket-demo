package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("heartbeat")
    @SendTo("/topic/heartbeat-replay")
    public String handleHeartbeatMessage(String message) {
        System.out.println(message);
        return "test";
    }
}
