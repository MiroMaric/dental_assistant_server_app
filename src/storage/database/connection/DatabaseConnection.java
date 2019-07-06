package storage.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.SettingsLoader;

public class DatabaseConnection {

    private final Connection connection;
    private static DatabaseConnection instance;

    private DatabaseConnection() throws SQLException {
        String dbUrl = SettingsLoader.getInstance().getDatabaseProperty("url");
        String dbUser = SettingsLoader.getInstance().getDatabaseProperty("user");
        String dbPassword = SettingsLoader.getInstance().getDatabaseProperty("password");
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        connection.setAutoCommit(false);
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
    }

}
