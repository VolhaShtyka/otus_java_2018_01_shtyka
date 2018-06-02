package ru.otus.shtyka.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.cache.CacheEngine;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.service.DBService;

import java.net.HttpCookie;
import java.util.List;
import java.util.Set;

import static ru.otus.shtyka.servlet.LoginServlet.ADMIN_LOGIN;

@WebSocket
public class CacheWebSocket {

    private Set<CacheWebSocket> users;

    private Session session;

    @Autowired
    private DBService dbService;

    @Autowired
    private CacheEngine cacheEngine;

    CacheWebSocket(Set<CacheWebSocket> users) {
        this.users = users;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        for (CacheWebSocket user : users) {
            try {
                user.getSession().getRemote().sendString(data);
                System.out.println("Sending message: " + data);
            } catch (Exception e) {
                System.out.print(e.toString());
            }
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        users.add(this);
        setSession(session);
        System.out.println("onOpen");
        if (checkPermission()) {
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
            cacheEngine.register(this);
            checkConnectDB();
        } else {
            onMessage("Permission denied");
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        users.remove(this);
        System.out.println("onClose");
    }


    private Session getSession() {
        return session;
    }

    private void setSession(Session session) {
        this.session = session;
    }

    private boolean checkPermission() {
        List<HttpCookie> cookies = getSession().getUpgradeRequest().getCookies();
        if (cookies.isEmpty()){
            return false;
        }
        for (HttpCookie cookie : cookies) {
            if (cookie.getValue().equals(ADMIN_LOGIN)) {
                return true;
            }
        }
        return false;
    }

    private void checkConnectDB() {
        User sidorov = new User("Sidorov", 22);
        dbService.save(sidorov);
        dbService.save(new User("Ivanov", 98));
        dbService.load(User.class, sidorov.getId());
        try {
            Thread.sleep(5000);
        } catch (Exception u) {
            u.printStackTrace();
        }
        dbService.load(User.class, sidorov.getId());
        dbService.load(User.class, 5);
        dbService.loadAll();
    }
}
