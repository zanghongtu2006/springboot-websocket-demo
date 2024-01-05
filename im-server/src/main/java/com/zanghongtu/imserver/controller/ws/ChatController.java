package com.zanghongtu.imserver.controller.ws;

import com.zanghongtu.imserver.controller.dto.chat.AdditionalInfoDTO;
import com.zanghongtu.imserver.controller.dto.chat.ChatDTO;
import com.zanghongtu.imserver.controller.dto.chat.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo(value = "/topic/chat-reply")
    public ChatDTO heartbeat(ChatDTO chatDTO) {
        log.info("Recv chat: {}", chatDTO);
        //dispatch chat dto
        //from -> to
        AdditionalInfoDTO additionalInfo = new AdditionalInfoDTO();
        additionalInfo.setReplayToMessageId(chatDTO.getMessageId());
        ChatDTO reply = new ChatDTO();
        reply.setAdditionalInfo(additionalInfo);
        reply.setMessageType(MessageType.CHAT_REPLY);
        return reply;
    }

}
