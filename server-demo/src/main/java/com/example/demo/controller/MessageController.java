package com.example.demo.controller;

import com.example.demo.ws.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "messages")
public class MessageController {
    private final WebSocket webSocket;

    @Autowired
    public MessageController(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    @PostMapping(value = "sendAll")
    public void sendAll() {
        webSocket.sendAll("Hello World");
    }
}
