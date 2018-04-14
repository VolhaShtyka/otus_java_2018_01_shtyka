package ru.otus.shtyka.connection;

import ru.otus.shtyka.base.DBService;
import ru.otus.shtyka.executor.Executor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBServiceConnection implements DBService {
    private static final String DELETE_TABLE_USER = "DROP TABLE %s";
    private final Connection connection;

    DBServiceConnection() {
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                    "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void prepareTables(Class clazz) throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execQuery(generateCreateSQL(clazz));
        System.out.println("Table created");
    }

    @Override
    public void deleteTables(Class clazz) throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execQuery(String.format(DELETE_TABLE_USER, clazz.getSimpleName()));
        System.out.println("Table dropped");
    }

    @Override
    public void close() throws Exception {
        connection.setAutoCommit(true);
        connection.close();
        System.out.println("Connection closed. Bye!");
    }

    Connection getConnection() throws SQLException {
        connection.setAutoCommit(false);
        return connection;
    }

    private String generateCreateSQL(Class clazz) {
        StringBuilder insert = new StringBuilder("CREATE TABLE if not exists ");
        insert.append(clazz.getSimpleName()).append(" (id bigint(20) NOT NULL AUTO_INCREMENT,");
        for (Field field : clazz.getDeclaredFields()) {
            if ("id".equals(field.getName())) {
                continue;
            }
            insert.append(field.getName()).append(getFieldAttribute(field)).append(",");
        }
        insert.append("PRIMARY KEY (id));");
        return insert.toString();
    }

    private String getFieldAttribute(Field field) {
        if (field.getType().isAssignableFrom(Integer.class)) {
            return " int(3)";
        } else if (field.getType().isAssignableFrom(String.class)) {
            return " varchar(255)";
        } else {
            throw new IllegalArgumentException("Unknown type");
        }
    }
}
