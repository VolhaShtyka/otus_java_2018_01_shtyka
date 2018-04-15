package ru.otus.shtyka.base;

import java.sql.SQLException;
import java.util.List;

public interface DBService<T> {

    void save(T user) throws SQLException;

    T load(Class<T> clazz, long id) throws SQLException;

    List<T> loadAll(Class<T> clazz);
}
