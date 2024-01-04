package com.example.clientdemo.service;

import com.example.clientdemo.controller.dto.chat.ChatDTO;

public interface IChatMessageService {
    void dealChatReplyMessage(ChatDTO message);
}
