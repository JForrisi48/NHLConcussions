import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection initializeDatabase() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/NHL_Concussion_Data", "pace", "123456");
    }
}

