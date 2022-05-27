package ro.unibuc.cookup.service.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private String url = "jdbc:mysql://localhost:3306/cookup";
    private String username = "admin";
    private String password = "password";

    private Connection connection;

    public DatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
    }

    public void close() throws SQLException {
        connection.close();
    }

    public PreparedStatement prepareStatement(String stmt) throws SQLException {
        if (connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password); // reconnect
        }
        return connection.prepareStatement(stmt);
    }
}
