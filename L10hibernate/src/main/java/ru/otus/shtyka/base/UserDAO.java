package ru.otus.shtyka.base;

import java.util.List;

public interface UserDAO<User> extends DBService<User> {
    List<User> loadByName(String name);

    String getUserNameById(long id);
}
