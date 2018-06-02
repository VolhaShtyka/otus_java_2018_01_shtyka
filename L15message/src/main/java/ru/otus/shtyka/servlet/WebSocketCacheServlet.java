package ru.otus.shtyka.servlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.shtyka.websocket.CacheWebSocketCreator;

/**
 * This class represents a servlet starting a webSocket application
 */
public class WebSocketCacheServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new CacheWebSocketCreator());

        factory.getExtensionFactory().unregister("permessage-deflate");
    }
}
