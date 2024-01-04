package com.example.demo.controller.dto.chat;

import lombok.Data;

@Data
public class UserDTO {
    /**
     * UUID
     */
    private String userId;

    private String userName;

    private String avatar;
}
