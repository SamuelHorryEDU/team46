package IPOS_Detailed_Design.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the single MySQL connection for IPOS-SA.
 * All DAO classes call DatabaseConnection.getConnection().
 *
 * SETUP: Change USER and PASSWORD to match your MySQL installation.
 */
public class DatabaseConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/ipos_sa_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER     = "root";   // your MySQL username
    private static final String PASSWORD = "T34m46My5ql!";       // your MySQL password

    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException(
                        "MySQL driver not found. Right-click project → Open Module Settings → " +
                                "Libraries → + → add mysql-connector-j jar.", e);
            }
        }
        return connection;
    }
}