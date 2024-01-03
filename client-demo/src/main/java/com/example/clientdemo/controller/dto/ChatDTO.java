package com.example.clientdemo.controller.dto;

import lombok.Data;

@Data
public class ChatDTO {

    private Long from;

    private Long to;
    
    private String message;
}
