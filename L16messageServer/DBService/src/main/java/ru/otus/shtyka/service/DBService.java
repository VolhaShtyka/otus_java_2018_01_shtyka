package ru.otus.shtyka.service;

import org.springframework.stereotype.Service;
import ru.otus.shtyka.cache.CacheEngine;
import ru.otus.shtyka.entity.User;
import ru.otus.shtyka.messageSystem.Addressee;

import java.util.List;

public interface DBService<T> extends Addressee {

    void save(T user);

    T load(Class<T> clazz, long id);

    String getUserNameById(long id);

    CacheEngine getCacheEngine();

    void init();

    void shutdown();
}
