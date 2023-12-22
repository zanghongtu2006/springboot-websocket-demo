package com.example.demo.ws;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {
    private Session session;

    private String userId;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String, Session> sessionPool = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
        } catch (Exception e) {
            log.error("onOpen failed, session: {}, userId: {}", session, userId);
        }
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("Recv message: {}", message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("onError: Session:{}, error: {}", session, error.getMessage());
    }

    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("OnClose, userId: {}, websockets.size(): {}", this.userId, webSockets.spliterator());
        } catch (Exception e) {
            log.error("OnClose Error", e);
        }
    }

    //Send all
    public void sendAll(String message) {
        log.info("Send all : {}", message);
        for (WebSocket webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                log.error("Send All Error.", e);
            }
        }
    }

    //Send one
    public void sendOne(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                log.error("Send One Error.userId: {}, session: {}", userId, session, e);
            }
        }
    }

    //Send many
    public void sendMany(String[] userIds, String message) {
        for (String userId : userIds) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    log.error("Send Many Error. userId: {}, session: {}", userId, session, e);
                }
            }
        }
    }

}
