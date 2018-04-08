package ru.otus.shtyka.connection;

import ru.otus.shtyka.base.DBService;
import ru.otus.shtyka.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBServiceConnection implements DBService {
    private static final String CREATE_TABLE_USER = "CREATE TABLE if not exists User (id bigint(20) NOT NULL AUTO_INCREMENT, name varchar(255), age int(3), PRIMARY KEY (id));";
    private static final String DELETE_TABLE_USER = "DROP TABLE User";
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
    public void prepareTables() throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execQuery(CREATE_TABLE_USER);
        System.out.println("Table created");
    }

    @Override
    public void deleteTables() throws SQLException {
        Executor exec = new Executor(getConnection());
        exec.execQuery(DELETE_TABLE_USER);
        System.out.println("Table dropped");
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }

    Connection getConnection() {
        return connection;
    }
}
