package ru.otus.shtyka.app;

import ru.otus.shtyka.messageSystem.Addressee;

public interface FrontendService<T> extends Addressee {
    void init();

    void load(Class<T> clazz, long id);

    void addUser(long id, String name);

    void save(T user);
}

