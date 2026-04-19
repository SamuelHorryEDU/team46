package IPOS_Detailed_Design.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseConnection — manages the MySQL database connection for IPOS-SA.
 *
 * <p>Implements a singleton connection pattern, ensuring only one active
 * database connection exists at a time. All DAO classes obtain their
 * connection by calling {@link #getConnection()}.</p>
 *
 * <p>Database credentials are loaded from a {@code db.properties} file
 * at the project root, keeping sensitive information out of source control.
 * Copy {@code db.properties.example} to {@code db.properties} and fill in
 * your local MySQL credentials before running the application.
 * The {@code db.properties} file is listed in {@code .gitignore} and will
 * never be committed to the repository.</p>
 *
 * <p>Expected properties in {@code db.properties}:</p>
 * <pre>
 *   db.url      = jdbc:mysql://localhost:3306/ipos_sa_db?useSSL=false&amp;serverTimezone=UTC
 *   db.user     = root
 *   db.password = yourpassword
 * </pre>
 *
 */
public class DatabaseConnection {

    /** Path to the properties file containing database credentials. */
    private static final String PROPS_FILE = "db.properties";

    /** The single shared database connection instance. */
    private static Connection connection = null;

    /**
     * Private constructor — prevents instantiation.
     *
     * <p>This class is a utility class and should never be instantiated.
     * All access is via the static {@link #getConnection()} method.</p>
     */
    private DatabaseConnection() {}

    /**
     * Returns the active MySQL database connection, creating one if necessary.
     *
     * <p>If no connection exists, or the existing connection has been closed,
     * a new connection is established using credentials loaded from
     * {@code db.properties}. The MySQL JDBC driver is loaded via
     * reflection using {@code Class.forName()}.</p>
     *
     * <p>This method is called by every DAO class in the application.
     * DAOs should use try-with-resources to ensure connections are
     * returned properly after each operation.</p>
     *
     * @return an active {@link Connection} to the {@code ipos_sa_db} database
     * @throws SQLException if the properties file cannot be read,
     *                      the MySQL JDBC driver is not found on the classpath,
     *                      or the database connection cannot be established
     *                      (e.g. wrong credentials, MySQL not running)
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties props = new Properties();
            try (InputStream in = new FileInputStream(PROPS_FILE)) {
                props.load(in);
            } catch (IOException e) {
                throw new SQLException(
                        "Could not read " + PROPS_FILE + ". " +
                                "Copy db.properties.example to db.properties " +
                                "and fill in your credentials.", e);
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
                        "MySQL driver not found. " +
                                "Add mysql-connector-j jar to project libraries.", e);
            }
        }
        return connection;
    }
}