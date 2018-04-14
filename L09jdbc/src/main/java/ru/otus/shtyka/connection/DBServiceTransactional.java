package ru.otus.shtyka.connection;

import ru.otus.shtyka.base.UsersDataSet;
import ru.otus.shtyka.executor.Executor;
import ru.otus.shtyka.reflection.ReflectionHelper;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBServiceTransactional extends DBServiceConnection {
    private static final String SELECT_USER_NAME = "select name from %s where id=%s";
    private static final String SELECT_USER = "select * from %s where id=%s";

    @Override
    public String getUserName(String tableName, long id) throws SQLException {
        Executor execT = new Executor(getConnection());
        return execT.execQuery(String.format(SELECT_USER_NAME, tableName, id), result -> {
            result.next();
            return result.getString("name");
        });
    }


    @Override
    public <T extends UsersDataSet> void save(T user) throws SQLException {
        try {
            Executor exec = new Executor(getConnection());
            long generatedId = exec.execUpdate(generateInsertSQL(user),
                    PreparedStatement::execute, 1);
            user.setId(generatedId);
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        }
    }

    @Override
    public <T extends UsersDataSet> T load(long userId, Class<T> clazz) throws SQLException {
        Executor executor = new Executor(getConnection());
        return executor.execQuery(String.format(SELECT_USER, clazz.getSimpleName(), userId), result -> {
            result.next();
            T user = ReflectionHelper.instantiate(clazz);
            for (Field field : clazz.getDeclaredFields()) {
                ReflectionHelper.setFieldValue(user, field, result.getObject(field.getName()));
            }
            return user;
        });
    }

    private String generateInsertSQL(Object o) {
        StringBuilder insert = new StringBuilder("insert into ");
        insert.append(o.getClass().getSimpleName()).append(" (");
        StringBuilder values = new StringBuilder();
        for (Field field : o.getClass().getDeclaredFields()) {
            if ("id".equals(field.getName())) {
                continue;
            }
            insert.append(field.getName()).append(",");
            values.append("\'").append(ReflectionHelper.getFieldValue(o, field)).append("\'").append(", ");
        }
        insert.deleteCharAt(insert.lastIndexOf(","));
        insert.append(") values (");
        insert.append(values);
        insert.deleteCharAt(insert.lastIndexOf(","));
        insert.append(")");
        return insert.toString();
    }
}
