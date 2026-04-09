package IPOS_Detailed_Design.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manages the single MySQL connection for IPOS-SA.
 * All DAO classes call DatabaseConnection.getConnection().
 *
 * SETUP: Copy db.properties.example to db.properties at the project root
 *        and fill in your local MySQL credentials. db.properties is gitignored
 *        so it will never be committed.
 */
public class DatabaseConnection {

    private static final String PROPS_FILE = "db.properties";
    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties props = new Properties();
            try (InputStream in = new FileInputStream(PROPS_FILE)) {
                props.load(in);
            } catch (IOException e) {
                throw new SQLException(
                        "Could not read " + PROPS_FILE + ". " +
                        "Copy db.properties.example to db.properties and fill in your credentials.", e);
            }
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        props.getProperty("db.url"),
                        props.getProperty("db.user"),
                        props.getProperty("db.password")
                );
            } catch (ClassNotFoundException e) {
                throw new SQLException(
                        "MySQL driver not found. Add mysql-connector-j jar to project libraries.", e);
            }
        }
        return connection;
    }
}