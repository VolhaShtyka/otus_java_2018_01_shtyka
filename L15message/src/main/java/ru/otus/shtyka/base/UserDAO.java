package ru.otus.shtyka.base;

import java.util.List;

public interface UserDAO<User> {
    List<User> loadByName(String name);

    String getUserNameById(long id);
}
