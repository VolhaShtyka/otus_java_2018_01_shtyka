package ru.otus.shtyka.cache;

public interface CacheEngine<K, V> {

    void put(K key, V value);

    V get(K key);

    int getHitCount();

    int getMissCount();

    int getCurrentSize();

    int getMaxSize();

    void dispose();

    String getCacheInfo();
}