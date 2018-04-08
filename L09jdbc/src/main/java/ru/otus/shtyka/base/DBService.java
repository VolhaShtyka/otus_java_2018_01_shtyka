package ru.otus.shtyka.base;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {
    String getMetaData();

    void prepareTables() throws SQLException;

    String getUserName(long id) throws SQLException;

    <T extends UsersDataSet> void save(T user) throws SQLException;

    <T extends UsersDataSet> T load(long id, Class<T> clazz) throws SQLException;

    void deleteTables() throws SQLException;
}
