package com.zanghongtu.imserver.controller.dto;

import lombok.Data;

@Data
public class Heartbeat {
    public Heartbeat() {
        this.timestamp = System.currentTimeMillis();
    }

    public Heartbeat(Long timestamp) {
        this.timestamp = timestamp;
    }

    private Long timestamp;
}
