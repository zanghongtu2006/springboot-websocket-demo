package com.example.clientdemo.config;

import com.example.clientdemo.controller.dto.Greeting;
import com.example.clientdemo.controller.dto.Heartbeat;
import com.example.clientdemo.controller.dto.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Date;

@Slf4j
public class ClientStompSessionHandler extends StompSessionHandlerAdapter {
    private final WebSocketStompClient stompClient;
    private final TaskScheduler taskScheduler;
    private final String url = "ws://localhost:8080/chatserver";

    @Autowired
    public ClientStompSessionHandler(WebSocketStompClient stompClient, TaskScheduler taskScheduler) {
        this.stompClient = stompClient;
        this.taskScheduler = taskScheduler;
        stompClient.connect(url, this);
    }
    @Override
    public void afterConnected(StompSession session, StompHeaders headers) {
        log.info("Client connected: headers {}", headers);
//        session.subscribe("/topic/heartbeat", new StompFrameHandler() {
//            @Override
//            public Type getPayloadType(StompHeaders headers) {
//                String contentType = headers.getFirst("content-type");
//                if (contentType != null && contentType.equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
//                    return Heartbeat.class;
//                }
//                return Object.class;
//            }
//
//            @Override
//            public void handleFrame(StompHeaders headers, Object payload) {
//                if (payload instanceof Heartbeat) {
//                    Heartbeat jsonMessage = (Heartbeat) payload;
//                    log.info(jsonMessage.toString());
//                }
//            }
//        });

        String message = "one-time message from client";
        log.info("Client sends: {}", message);
        session.send("/app/hello", new HelloMessage(message));

//        scheduleHeartbeat(session);

    }

//    private void scheduleHeartbeat(StompSession session) {
//        // Define the heartbeat interval in milliseconds (e.g., every 30 seconds)
//        long heartbeatInterval = 10 * 1000; // 30 seconds
//
//        // Schedule a task to send heartbeat messages periodically
//        Runnable heartbeatTask = () -> {
//            // Send a heartbeat message to the server
//            session.send("/app/heartbeat", System.currentTimeMillis());
//        };
//
//        Thread heartbeatThread = new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(heartbeatInterval);
//                    heartbeatTask.run();
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                    break;
//                }
//            }
//        });
//
//        // Start the heartbeat thread
//        heartbeatThread.start();
//    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("Client received: payload {}, headers {}", payload, headers);
    }

    @Override
    public void handleException(StompSession session, StompCommand command,
                                StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("Client error: exception {}, command {}, payload {}, headers {}",
                exception.getMessage(), command, payload, headers);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.error("Client transport error: error {}", exception.getMessage());
        if(!session.isConnected()) {
            // 如果连接断开，启动一个重连任务
            taskScheduler.schedule(this::reconnect, new Date(System.currentTimeMillis() + 10000));  // 10秒后尝试重连
        }
    }

    private void reconnect() {
        // 重连逻辑
        stompClient.connect(url, this);
    }
}
