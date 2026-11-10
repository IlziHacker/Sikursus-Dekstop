package config;
<<<<<<< HEAD

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/db_sikursus?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jakarta";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASS = "";

    private Database() {}

    public static String getUrl() {
        return System.getProperty("sikursus.db.url", DEFAULT_URL);
    }

    public static String getUser() {
        return System.getProperty("sikursus.db.user", DEFAULT_USER);
    }

    public static String getPassword() {
        return System.getProperty("sikursus.db.password", DEFAULT_PASS);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUser(), getPassword());
    }
}
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Database {
private static final String URL =
"jdbc:mysql://localhost:3306/db_sikursus";
private static final String USER = "root";
private static final String PASS = "";
private Database() {
// Mencegah object Database dibuat sembarangan.
}
public static Connection getConnection() throws SQLException {
return DriverManager.getConnection(URL, USER, PASS);
}
}   
>>>>>>> 6bd52ef41db6b42a8147c8d43d7f520e89ae3d0d
