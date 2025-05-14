package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbcDB";
    private static final String USERNAME = "veronika";
    private static final String PASSWORD = "veronika";
    private static Connection connection;

    private Util() {
    }

    public static Connection open() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
            }
        }
    }

}
