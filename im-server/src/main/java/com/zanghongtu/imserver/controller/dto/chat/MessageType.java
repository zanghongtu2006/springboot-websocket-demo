package com.zanghongtu.imserver.controller.dto.chat;

import lombok.Getter;

@Getter
public enum MessageType {
    CHAT,
    CHAT_REPLY,
    SYSTEM_NOTICE,
    FILE_TRANSFER
}
