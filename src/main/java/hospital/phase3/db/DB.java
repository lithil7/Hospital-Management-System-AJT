
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

            if (input == null) {
                throw new IllegalStateException(
                    "database.properties was not found"
                );
            }

            properties.load(input);

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(
            url,
            username,
            password
        );
    }

    public static String value(String key) {
        return properties.getProperty(key);
    }
}
