package ru.otus.shtyka.connection;

import ru.otus.shtyka.base.UsersDataSet;
import ru.otus.shtyka.executor.Executor;

import java.sql.SQLException;

public class DBServiceTransactional extends DBServiceConnection {
    private static final String INSERT_INTO_USER = "insert into User (name, age) values(?, ?)";
    private static final String SELECT_USER_NAME = "select name from User where id=%s";
    private static final String SELECT_USER_ID = "select id from User where name='%s'";
    private static final String SELECT_USER = "select * from User where id=%s";

    @Override
    public String getUserName(long id) throws SQLException {
        Executor execT = new Executor(getConnection());
        return execT.execQuery(String.format(SELECT_USER_NAME, id), result -> {
            result.next();
            return result.getString("name");
        });
    }

    private long getId(String name) throws SQLException {
        Executor execT = new Executor(getConnection());
        return execT.execQuery(String.format(SELECT_USER_ID, name), result -> {
            result.next();
            return result.getLong("id");
        });
    }

    @Override
    public <T extends UsersDataSet> void save(T user) throws SQLException {
        try {
            Executor exec = new Executor(getConnection());
            getConnection().setAutoCommit(false);
            exec.execUpdate(INSERT_INTO_USER, statement -> {
                statement.setString(1, user.getName());
                statement.setInt(2, user.getAge());
                statement.execute();
            });
            getConnection().commit();
            user.setId(getId(user.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
            getConnection().rollback();
        } finally {
            getConnection().setAutoCommit(true);
        }
    }

    @Override
    public <T extends UsersDataSet> T load(long userId, Class<T> clazz) throws SQLException {
        Executor executor = new Executor(getConnection());
        return executor.execQuery(String.format(SELECT_USER, userId), result -> {
            result.next();
            long id = result.getLong("id");
            String name = result.getString("name");
            int age = result.getInt("age");
            UsersDataSet user = new UsersDataSet(name, age);
            user.setId(id);
            return (T) user;
        });
    }
}
