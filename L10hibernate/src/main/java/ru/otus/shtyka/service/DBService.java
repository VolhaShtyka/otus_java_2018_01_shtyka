package ru.otus.shtyka.service;

import ru.otus.shtyka.cache.CacheEngine;
import ru.otus.shtyka.entity.User;

import java.util.List;

public interface DBService<T> {

    void save(T user);

    T load(Class<T> clazz, long id);

    List<User> loadAll();

    String getUserNameById(long id);

    CacheEngine getCacheEngine();

    void shutdown();
}
