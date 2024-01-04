package com.example.demo.controller.dto.chat;

import lombok.Data;

@Data
public class ContentDTO {
    private ContentType type;

    /**
     * chat message content
     */
    private String text;
}