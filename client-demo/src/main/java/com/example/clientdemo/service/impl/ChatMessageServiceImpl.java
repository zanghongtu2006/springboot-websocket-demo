package com.example.clientdemo.service.impl;

import com.example.clientdemo.controller.dto.chat.ChatDTO;
import com.example.clientdemo.service.IChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatMessageServiceImpl implements IChatMessageService {
    @Override
    public void dealChatReplyMessage(ChatDTO message) {

        // 在这里处理接收到的消息
        log.info("Received Reply message: {}", message);
    }
}
