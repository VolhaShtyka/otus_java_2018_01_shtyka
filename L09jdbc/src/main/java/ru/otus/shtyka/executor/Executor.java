package ru.otus.shtyka.executor;

import java.sql.*;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }

    public void execQuery(String update) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(update);
        }
    }

    public void execUpdate(String update, ExecuteHandler prepare) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(update)) {
            prepare.accept(stmt);
        }
    }

    public long execUpdate(String update, ExecuteHandler prepare, int generateColumnIndex) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {
            prepare.accept(stmt);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(generateColumnIndex);
            }
        }
        throw new IllegalArgumentException("Not found generated key");
    }

    Connection getConnection() {
        return connection;
    }
}
