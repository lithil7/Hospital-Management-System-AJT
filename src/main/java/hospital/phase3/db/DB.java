
package hospital.phase3.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                DB.class.getClassLoader()
                    .getResourceAsStream("database.properties")) {

            if (input != null) {
                properties.load(input);
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String value(String key) {

        String envName;

        switch (key) {
            case "db.url":
                envName = "DB_URL";
                break;
            case "db.user":
                envName = "DB_USER";
                break;
            case "db.password":
                envName = "DB_PASSWORD";
                break;
            default:
                return properties.getProperty(key);
        }

        String envValue = System.getenv(envName);

        if (envValue != null && !envValue.isEmpty()) {
            return envValue;
        }

        return properties.getProperty(key);
    }

    public static Connection getConnection()
            throws SQLException {

        String url = value("db.url");
        String user = value("db.user");
        String password = value("db.password");

        if (url == null || url.isEmpty()) {
            throw new SQLException(
                "Database URL not configured"
            );
        }

        return DriverManager.getConnection(
            url, user, password
        );
    }
}
