package com.example.clientdemo.config;

import com.example.clientdemo.controller.dto.Greeting;
import com.example.clientdemo.controller.dto.Heartbeat;
import com.example.clientdemo.controller.dto.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.util.MimeTypeUtils;

import java.lang.reflect.Type;

@Slf4j
public class ClientStompSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders headers) {
        log.info("Client connected: headers {}", headers);

//        session.subscribe("/app/subscribe", this);
//        session.subscribe("/queue/responses", this);
//        session.subscribe("/queue/errors", this);
//        session.subscribe("/topic/periodic", this);
        session.subscribe("/topic/greetings", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                String contentType = headers.getFirst("content-type");
                if (contentType != null && contentType.equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
                    return Greeting.class; // Replace with your JSON message class
                } else if (contentType != null && contentType.equals(MimeTypeUtils.TEXT_PLAIN_VALUE)) {
                    return String.class; // Treat as plain text String
                } else {
                    return Object.class; // Handle other content types or unknown formats
                }
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                if (payload instanceof Greeting) {
                    // Handle JSON message
                    Greeting jsonMessage = (Greeting) payload;
                    log.info(jsonMessage.toString());
                    // Process JSON message here
                } else if (payload instanceof String) {
                    // Handle plain text message
                    String textMessage = (String) payload;
                    log.info(textMessage);
                    // Process text message here
                } else {
                    // Handle other content types or unknown formats
                }
            }
        });
        session.subscribe("/topic/heartbeat", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                String contentType = headers.getFirst("content-type");
                if (contentType != null && contentType.equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
                    return Heartbeat.class; // Replace with your JSON message class
                }
                return Object.class; // Handle other content types or unknown formats
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                if (payload instanceof Heartbeat) {
                    // Handle JSON message
                    Heartbeat jsonMessage = (Heartbeat) payload;
                    log.info(jsonMessage.toString());
                    // Process JSON message here
                }
            }
        });

        String message = "one-time message from client";
        log.info("Client sends: {}", message);
        session.send("/app/hello", new HelloMessage(message));

        scheduleHeartbeat(session);

    }

    private void scheduleHeartbeat(StompSession session) {
        // Define the heartbeat interval in milliseconds (e.g., every 30 seconds)
        long heartbeatInterval = 10 * 1000; // 30 seconds

        // Schedule a task to send heartbeat messages periodically
        Runnable heartbeatTask = () -> {
            // Send a heartbeat message to the server
            session.send("/app/heartbeat", System.currentTimeMillis());
        };

        // Use a scheduler (e.g., ScheduledExecutorService) to execute the task
        // You may need to inject a scheduler or use Spring's TaskScheduler
        // Here, we use a simple Thread for demonstration purposes
        Thread heartbeatThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(heartbeatInterval);
                    heartbeatTask.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        // Start the heartbeat thread
        heartbeatThread.start();
    }

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
    }
}
