package util;


//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

//public class DBConnUtil {
 //   public static Connection getConnection(String connectionString) throws SQLException {
   //     return DriverManager.getConnection(connectionString);
    //}
//}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // Change this based on your database
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/PayrollManagementSystem"; // Change this with your database URL
    private static final String USER = "root"; // Change this with your database username
    private static final String PASSWORD = "K@13tubh"; // Change this with your database password

    static {
        try {
            // Load JDBC driver
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection(String string) throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }
}