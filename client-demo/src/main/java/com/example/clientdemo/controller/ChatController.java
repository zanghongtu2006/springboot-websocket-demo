package com.example.clientdemo.controller;

import com.example.clientdemo.controller.dto.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "chat")
public class ChatController {
//    @Autowired
//    private WebSocketService webSocketService;

    @PostMapping(path = "")
    public void send(@RequestBody ChatDTO chatDTO) {
        String destination = "/app/message";
//        webSocketService.sendMessage(destination, chatDTO.getMessage());
    }

}
