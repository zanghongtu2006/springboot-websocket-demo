package com.example.clientdemo.config;

import com.example.clientdemo.controller.dto.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Date;

@Slf4j
public class ClientStompSessionHandler extends StompSessionHandlerAdapter {
    private final WebSocketStompClient stompClient;
    private final TaskScheduler taskScheduler;

    private final Config config;

    public ClientStompSessionHandler(WebSocketStompClient stompClient, TaskScheduler taskScheduler, Config config) {
        this.stompClient = stompClient;
        this.taskScheduler = taskScheduler;
        this.config = config;
        reconnect();
    }

    @Override
    public void afterConnected(StompSession session, @NonNull StompHeaders headers) {
        log.info("{}, {}", session.getSessionId(), session.isConnected());
        log.info("Client connected: headers {}", headers);

        String message = "one-time message from client";
        log.info("Client sends: {}", message);
        session.send("/app/hello", new HelloMessage(message));
    }

    @Override
    public void handleFrame(@NonNull StompHeaders headers, Object payload) {
        log.info("Client received: payload {}, headers {}", payload, headers);
    }

    @Override
    public void handleException(@NonNull StompSession session, StompCommand command,
                                @NonNull StompHeaders headers, @NonNull byte[] payload, Throwable exception) {
        log.error("Client error: exception {}, command {}, payload {}, headers {}",
                exception.getMessage(), command, payload, headers);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.error("Client transport error: error {}", exception.getMessage());
        if (!session.isConnected()) {
            // 如果连接断开，启动一个重连任务
            taskScheduler.schedule(this::reconnect, new Date(System.currentTimeMillis() + 30000).toInstant());  // 10秒后尝试重连
        }
    }

    private void reconnect() {
        // 重连逻辑
        stompClient.connectAsync(config.getUrl(), this);
    }
}
