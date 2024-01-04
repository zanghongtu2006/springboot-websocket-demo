package com.example.clientdemo.controller;

import com.example.clientdemo.controller.dto.chat.ChatDTO;
import com.example.clientdemo.service.StompSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "chat")
public class ChatController {
    private final StompSessionService stompSessionService;

    @Autowired
    public ChatController(StompSessionService stompSessionService) {
        this.stompSessionService = stompSessionService;
    }

    @PostMapping(path = "")
    public void send(@RequestBody ChatDTO chatDTO) {
        String destination = "/app/chat";
        stompSessionService.send(destination, chatDTO);
    }

}
