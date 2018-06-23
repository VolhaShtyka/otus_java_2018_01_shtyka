package ru.otus.shtyka.app;

import ru.otus.shtyka.messageSystem.Addressee;
import ru.otus.shtyka.websocket.CacheWebSocket;

public interface FrontendService<T> extends Addressee {
    void init();

    void load(Class<T> clazz, long id);

    void addUser(long id, String name);

    void save(T user);

    void send(String cacheInfo, long userSessionId);

    void addWebSocket(long sessionId, CacheWebSocket ws);

    void removeWebSocket(long sessionId);
}

