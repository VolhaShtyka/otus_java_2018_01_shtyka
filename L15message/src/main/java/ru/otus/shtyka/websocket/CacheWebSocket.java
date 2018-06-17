package ru.otus.shtyka.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.app.SomeActions;
import ru.otus.shtyka.app.messages.MsgGetCacheEngineInfo;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messageSystem.MessageSystemContext;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebSocket
public class CacheWebSocket {

    private static final Logger logger = Logger.getLogger(CacheWebSocket.class.getName());
    private static final String ADMIN_LOGIN = "root";

    private Set<CacheWebSocket> users;

    private Session session;

    @Autowired
    private MessageSystemContext msgSystemContext;

    CacheWebSocket(Set<CacheWebSocket> users) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.users = users;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        logger.log(Level.INFO, data);
        if (checkPermission() || "cache".equals(data)) {
            Message msg = new MsgGetCacheEngineInfo(msgSystemContext.getFrontAddress(), msgSystemContext.getDbAddress(), this);
            msgSystemContext.getMessageSystem().sendMessage(msg);
        } else {
            logger.log(Level.INFO, "Permission denied");
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        users.add(this);
        setSession(session);
        logger.log(Level.INFO, "onOpen");
        new SomeActions().checkConnectDB();
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        users.remove(this);
        logger.log(Level.INFO, "onClose - status code - " + statusCode);
    }

    public void send(String s) {
        for (CacheWebSocket user : users) {
            try {
                user.getSession().getRemote().sendString(s);
                logger.log(Level.INFO, "Sending message: " + s);
            } catch (IOException e) {
                throw new AssertionError("WebSocket send error: ", e);
            }
        }
    }

    private Session getSession() {
        return session;
    }

    private void setSession(Session session) {
        this.session = session;
    }

    private boolean checkPermission() {
        List<HttpCookie> cookies = getSession().getUpgradeRequest().getCookies();
        if (cookies == null || cookies.isEmpty()) {
            return false;
        }
        for (HttpCookie cookie : cookies) {
            if (cookie.getValue().equals(ADMIN_LOGIN)) {
                return true;
            }
        }
        return false;
    }
}
