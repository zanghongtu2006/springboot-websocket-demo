package com.example.demo.controller.dto.chat;

import lombok.Getter;

@Getter
public enum MessageType {
    CHAT,
    SYSTEM_NOTICE,
    FILE_TRANSFER
}
