package ru.otus.shtyka.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.app.DBService;
import ru.otus.shtyka.app.FrontendService;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.front.FrontendServiceImpl;
import ru.otus.shtyka.messageSystem.MessageAddress;

import java.net.HttpCookie;
import java.util.List;
import java.util.Set;

@WebSocket
public class CacheWebSocket {

    private static final String ADMIN_LOGIN = "root";

    private Set<CacheWebSocket> users;

    private Session session;

    @Autowired
    private DBService dbService;

    CacheWebSocket(Set<CacheWebSocket> users) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        dbService.init();
        dbService.getCacheEngine().register(this);
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
        if (cookies.isEmpty()) {
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
        MessageAddress frontAddress = new MessageAddress("Frontend");
        FrontendService frontendService = new FrontendServiceImpl(frontAddress);
        frontendService.init();
        User sidorov = new User("Sidorov", 22);
        frontendService.save(sidorov);
        frontendService.load(User.class, sidorov.getId());

        try {
            Thread.sleep(5000);
        } catch (Exception u) {
            u.printStackTrace();
        }
        frontendService.load(User.class, sidorov.getId());
        frontendService.save(new User("Ivanov", 98));
        frontendService.load(User.class, sidorov.getId());
        frontendService.load(User.class, sidorov.getId());
        frontendService.load(User.class, 5);
    }
}
