package ru.otus.shtyka.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.shtyka.channel.ClientSocketMsgWorker;
import ru.otus.shtyka.channel.MsgWorker;
import ru.otus.shtyka.channel.SocketMsgWorker;
import ru.otus.shtyka.messageSystem.Addressee;
import ru.otus.shtyka.messageSystem.Message;
import ru.otus.shtyka.messages.MsgSetDefaultWorker;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebSocket
public class CacheWebSocket implements Addressee {

    private static final Logger logger = Logger.getLogger(CacheWebSocket.class.getName());

    private Set<CacheWebSocket> users;

    private Session session;

    private MsgWorker worker;

    private String sender;

    CacheWebSocket(Set<CacheWebSocket> users) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.users = users;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        logger.log(Level.INFO, "WebSocket onMessage: " + data);
        try {
            Message message = SocketMsgWorker.getMsgFromJSON(data);
            message.setFrom(sender);
            logger.info(message.toString());
            worker.accept(message);
        } catch (Exception e) {
            logger.log(Level.INFO, "Permission denied");
            e.printStackTrace();
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        users.add(this);
        setSession(session);

        logger.info("WebSocket connect: " + session.getRemoteAddress().toString() +
                ", session: " + session.toString());
        this.sender = "WebSocketSession" + String.valueOf(session.hashCode());
        try {
            worker = new ClientSocketMsgWorker("localhost", 4050, sender);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        logger.info("Client worker created " + worker.toString());
        worker.setEndPointService(this);
        worker.init();

    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        users.remove(this);
        logger.log(Level.INFO, "onClose - status code - " + statusCode);
    }

    @Override
    public void execute(Message message) {
        if (message instanceof MsgSetDefaultWorker) {
            worker.accept(new MsgSetDefaultWorker(this.sender, ""));
        } else {
            send(new Gson().toJson(message));
        }
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

}
