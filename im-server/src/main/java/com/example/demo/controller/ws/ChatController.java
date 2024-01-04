package com.example.demo.controller.ws;

import com.example.demo.controller.dto.chat.ChatDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo(value = "/topic/chat")
    public void heartbeat(ChatDTO chatDTO) {
        log.info("Recv chat: {}", chatDTO);
        //dispatch chat dto
        //from -> to
    }

}
