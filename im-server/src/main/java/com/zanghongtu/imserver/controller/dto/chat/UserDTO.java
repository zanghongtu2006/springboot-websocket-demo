package com.zanghongtu.imserver.controller.dto.chat;

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
