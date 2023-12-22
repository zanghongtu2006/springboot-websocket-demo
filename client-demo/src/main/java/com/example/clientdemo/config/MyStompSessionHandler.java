package com.example.clientdemo.config;

import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        String destination = "/topic/heartbeat-reply";
        session.subscribe(destination, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                String msg = (String) payload;
                // 处理从 /topic/heartbeat-reply 接收到的消息
                System.out.println("Received message from /topic/heartbeat-reply: " + msg);
            }
        });
        System.out.println("Subscribed to " + destination);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        // 处理异常
        exception.printStackTrace();
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        // 处理传输错误
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        // 返回消息载荷的类型
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        // 处理接收到的消息
        System.out.println("Received: " + payload);
    }
}
