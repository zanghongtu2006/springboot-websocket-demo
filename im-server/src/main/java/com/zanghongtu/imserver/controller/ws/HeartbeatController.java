package com.zanghongtu.imserver.controller.ws;

import com.zanghongtu.imserver.controller.dto.Heartbeat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class HeartbeatController {

    @MessageMapping("/heartbeat")
    @SendTo(value = "/topic/heartbeat" )
    public Heartbeat heartbeat(Long timestamp) throws Exception {
        log.info("Recv heartbeat {}", timestamp);
        Thread.sleep(1000); // simulated delay
        return new Heartbeat();
    }

}
