package ru.otus.shtyka.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultHandler <T>{
    T handle(ResultSet resultSet) throws SQLException;
}
