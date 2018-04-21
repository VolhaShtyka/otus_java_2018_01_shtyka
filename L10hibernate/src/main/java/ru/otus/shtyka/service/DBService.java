package ru.otus.shtyka.service;

import java.util.List;

public interface DBService<T> {

    void save(T user);

    T load(Class<T> clazz, long id);

    List<T> loadAll(Class<T> clazz);

    String getUserNameById(long id);

    void shutdown();
}
