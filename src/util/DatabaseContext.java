package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.DatabaseConnectionException;

public class DatabaseContext {
	public static Connection getConnection() throws DatabaseConnectionException {
	    try {
	        String url = "jdbc:mysql://127.0.0.1:3306/PayrollManagementSystem?useSSL=false&serverTimezone=UTC";
	        String username = "root";
	        String password = "K@13tubh";

	        return DriverManager.getConnection(url, username, password);
	    } catch (SQLException e) {
	        throw new DatabaseConnectionException("Error connecting to the database: " + e.getMessage());
	    }
	}
}