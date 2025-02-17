import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String url = System.getenv("MYSQL_URL");
    private static final String user = System.getenv("MYSQL_USERNAME");
    private static final String password = System.getenv("MYSQL_PASSWORD");

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Failed to setup connection in Database.java");
            throw new RuntimeException(e);
        }
        return conn;
    }

}
