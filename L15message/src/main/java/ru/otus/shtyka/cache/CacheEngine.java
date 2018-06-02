package ru.otus.shtyka.cache;

import org.springframework.stereotype.Service;
import ru.otus.shtyka.websocket.CacheWebSocket;

/**
 * Created by tully.
 */
@Service
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
