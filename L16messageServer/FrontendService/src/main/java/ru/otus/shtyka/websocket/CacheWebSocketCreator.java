package ru.otus.shtyka.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author v.chibrikov
 */
public class CacheWebSocketCreator implements WebSocketCreator {
    private Set<CacheWebSocket> users;

    public CacheWebSocketCreator() {
        this.users = Collections.newSetFromMap(new ConcurrentHashMap<CacheWebSocket, Boolean>());
        System.out.println("WebSocketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        CacheWebSocket socket = new CacheWebSocket(users);
        System.out.println("Socket created");
        return socket;
    }
}
