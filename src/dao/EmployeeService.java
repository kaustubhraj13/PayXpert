//package dao;
//
//
//import entity.Employee;
//import exception.DatabaseConnectionException;
//import exception.EmployeeNotFoundException;
//import util.DatabaseContext;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EmployeeService implements IEmployeeService {
//
//    @Override
//    public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
//        // Implement database query to retrieve employee by ID
//        try (Connection connection = DatabaseContext.getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employee WHERE EmployeeID = ?")) {
//            statement.setInt(1, employeeId);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    // Map result set to Employee object
//                    return mapResultSetToEmployee(resultSet);
//                } else {
//                    throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
//                }
//            }
//        } catch (SQLException e) {
//            // Handle database-related exceptions
//            e.printStackTrace();
//            throw new EmployeeNotFoundException("Error fetching employee from the database.");
//        } catch (DatabaseConnectionException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return null;
//    }
//
//    @Override
//    public List<Employee> getAllEmployees() {
//        // Implement database query to retrieve all employees
//        List<Employee> employees = new ArrayList<>();
//
//        try (Connection connection = DatabaseContext.getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employee");
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                employees.add(mapResultSetToEmployee(resultSet));
//            }
//        } catch (SQLException | DatabaseConnectionException e) {
//            // Handle database-related exceptions
//            e.printStackTrace();
//        }
//
//        return employees;
//    }
//
//    // Other methods for adding, updating, and removing employees go here...
//
//    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
//        // Map result set columns to Employee object
//        return new Employee(
//                resultSet.getInt("EmployeeID"),
//                resultSet.getString("FirstName"),
//                resultSet.getString("LastName"), null, null, null, null, null, null, null, null
//        );
//    }
//
//	@Override
//	public void addEmployee(Employee employee) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateEmployee(Employee employee) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void removeEmployee(int employeeId) throws EmployeeNotFoundException {
//		// TODO Auto-generated method stub
//		
//	}
//}
package dao;

import entity.Employee;
import exception.DatabaseConnectionException;
import exception.EmployeeNotFoundException;
import util.DatabaseContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements IEmployeeService {

    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
        // Implement database query to retrieve employee by ID
        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employee WHERE EmployeeID = ?")) {
            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Map result set to Employee object
                    return mapResultSetToEmployee(resultSet);
                } else {
                    throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
                }
            }
        } catch (SQLException e) {
            // Handle database-related exceptions
            e.printStackTrace();
            throw new EmployeeNotFoundException("Error fetching employee from the database.");
        } catch (DatabaseConnectionException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        // Implement database query to retrieve all employees
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employee");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                employees.add(mapResultSetToEmployee(resultSet));
            }
        } catch (SQLException | DatabaseConnectionException e) {
            // Handle database-related exceptions
            e.printStackTrace();
        }

        return employees;
    }

    // Other methods for adding, updating, and removing employees go here...

    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        // Map result set columns to Employee object
        return new Employee(
                resultSet.getInt("EmployeeID"),
                resultSet.getString("FirstName"),
                resultSet.getString("LastName"), null, null, null, null, null, null, null, null
        );
    }

    @Override
    public void addEmployee(Employee employee) {
        // Implement database query to add employee
        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Employee (FirstName, LastName) VALUES (?, ?)")) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());

            statement.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            // Handle database-related exceptions
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        // Implement database query to update employee
        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE Employee SET FirstName = ?, LastName = ? WHERE EmployeeID = ?")) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setInt(3, employee.getEmployeeID());

            statement.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            // Handle database-related exceptions
            e.printStackTrace();
        }
    }

    
    @Override
    public void removeEmployee(int employeeId) throws EmployeeNotFoundException {
        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Employee WHERE EmployeeID = ?")) {
            statement.setInt(1, employeeId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
            }
        } catch (SQLException | DatabaseConnectionException e) {
            handleDatabaseException(e);
        }
    }

    private void handleDatabaseException(Exception e) {
    	e.printStackTrace();
    }
}