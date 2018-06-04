package ru.otus.shtyka.cache;

import ru.otus.shtyka.websocket.CacheWebSocket;

/**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(K key, V value);

    V get(K key);

    int getHitCount();

    int getMissCount();

    int getCurrentSize();

    int getMaxSize();

    void dispose();

    void register(CacheWebSocket ws);
}
