//package dao;
//
//
//import entity.Payroll;
//import exception.DatabaseConnectionException;
//import exception.PayrollGenerationException;
//import util.DatabaseContext;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PayrollService implements IPayrollService {
//
//    @Override
//    public Payroll generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws PayrollGenerationException {
//        // Implement logic to generate payroll for the specified employee and date range
//        // You may need to calculate basic salary, overtime pay, deductions, net salary, etc.
//        // Update the database accordingly
//
//        try (Connection connection = DatabaseContext.getConnection()) {
//            // Implement payroll generation logic and database update here
//        } catch (SQLException | DatabaseConnectionException e) {
//            // Handle database-related exceptions
//            e.printStackTrace();
//            throw new PayrollGenerationException("Error generating payroll for employee with ID " + employeeId);
//        }
//		return null;
//    }
//
//    @Override
//    public Payroll getPayrollById(int payrollId) {
//        // Implement logic to retrieve payroll by ID
//        // You may need to fetch data from the database
//
//        try (Connection connection = DatabaseContext.getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Payroll WHERE PayrollID = ?")) {
//            statement.setInt(1, payrollId);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    // Map result set to Payroll object
//                    return mapResultSetToPayroll(resultSet);
//                } else {
//                    return null; // Payroll not found
//                }
//            }
//        } catch (SQLException | DatabaseConnectionException e) {
//            // Handle database-related exceptions
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // Other methods for getting payrolls for an employee, for a period, etc. go here...
//
//    private Payroll mapResultSetToPayroll(ResultSet resultSet) throws SQLException {
//        // Map result set columns to Payroll object
//        return new Payroll(
//                resultSet.getInt("PayrollID"),
//                resultSet.getInt("EmployeeID"),
//                resultSet.getDate("PayPeriodStartDate").toLocalDate(),
//                resultSet.getDate("PayPeriodEndDate").toLocalDate(),
//                resultSet.getDouble("BasicSalary"),
//                resultSet.getDouble("OvertimePay"),
//                resultSet.getDouble("Deductions"),
//                resultSet.getDouble("NetSalary")
//        );
//    
//    }
//
//	@Override
//	public List<Payroll> getPayrollsForEmployee(int employeeId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
package dao;

import entity.Payroll;
import exception.DatabaseConnectionException;
import exception.PayrollGenerationException;
import util.DatabaseContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollService implements IPayrollService {

    @Override
    public Payroll generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws PayrollGenerationException {
        // Implement logic to generate payroll for the specified employee and date range
        // You may need to calculate basic salary, overtime pay, deductions, net salary, etc.
        // Update the database accordingly

        try (Connection connection = DatabaseContext.getConnection()) {
            // Implement payroll generation logic and database update here

            // For simplicity, let's assume BasicSalary, OvertimePay, Deductions, and NetSalary are calculated elsewhere
            double basicSalary = 5000.00;  // Replace with actual calculation
            double overtimePay = 200.00;  // Replace with actual calculation
            double deductions = 300.00;  // Replace with actual calculation
            double netSalary = basicSalary + overtimePay - deductions;  // Replace with actual calculation

            // Insert the generated payroll into the database
            try (PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO Payroll (EmployeeID, PayPeriodStartDate, PayPeriodEndDate, BasicSalary, OvertimePay, Deductions, NetSalary) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertStatement.setInt(1, employeeId);
                insertStatement.setDate(2, java.sql.Date.valueOf(startDate));
                insertStatement.setDate(3, java.sql.Date.valueOf(endDate));
                insertStatement.setDouble(4, basicSalary);
                insertStatement.setDouble(5, overtimePay);
                insertStatement.setDouble(6, deductions);
                insertStatement.setDouble(7, netSalary);

                int rowsInserted = insertStatement.executeUpdate();

                if (rowsInserted > 0) {
                    // Retrieve the generated PayrollID
                    ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int payrollId = generatedKeys.getInt(1);
                        return new Payroll(payrollId, employeeId, startDate, endDate, basicSalary, overtimePay, deductions, netSalary);
                    }
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            // Handle database-related exceptions
            e.printStackTrace();
            throw new PayrollGenerationException("Error generating payroll for employee with ID " + employeeId);
        }
        return null;
    }

    @Override
    public Payroll getPayrollById(int payrollId) {
        // Implement logic to retrieve payroll by ID
        // You may need to fetch data from the database

        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Payroll WHERE PayrollID = ?")) {
            statement.setInt(1, payrollId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Map result set to Payroll object
                    return mapResultSetToPayroll(resultSet);
                } else {
                    return null; // Payroll not found
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            // Handle database-related exceptions
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Payroll> getPayrollsForEmployee(int employeeId) {
        // Implement logic to retrieve payrolls for a specific employee
        // You may need to fetch data from the database based on EmployeeID

        List<Payroll> payrolls = new ArrayList<>();

        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Payroll WHERE EmployeeID = ?")) {
            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    payrolls.add(mapResultSetToPayroll(resultSet));
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            // Handle database-related exceptions
            e.printStackTrace();
        }

        return payrolls;
    }

    @Override
    public List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate) {
        // Implement logic to retrieve payrolls for a specific date range
        // You may need to fetch data from the database based on PayPeriodStartDate and PayPeriodEndDate

        List<Payroll> payrolls = new ArrayList<>();

        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Payroll WHERE PayPeriodStartDate >= ? AND PayPeriodEndDate <= ?")) {
            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    payrolls.add(mapResultSetToPayroll(resultSet));
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            // Handle database-related exceptions
            e.printStackTrace();
        }

        return payrolls;
    }

    private Payroll mapResultSetToPayroll(ResultSet resultSet) throws SQLException {
        // Map result set columns to Payroll object
        return new Payroll(
                resultSet.getInt("PayrollID"),
                resultSet.getInt("EmployeeID"),
                resultSet.getDate("PayPeriodStartDate").toLocalDate(),
                resultSet.getDate("PayPeriodEndDate").toLocalDate(),
                resultSet.getDouble("BasicSalary"),
                resultSet.getDouble("OvertimePay"),
                resultSet.getDouble("Deductions"),
                resultSet.getDouble("NetSalary")
        );
    }
}