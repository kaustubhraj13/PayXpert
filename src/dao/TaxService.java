package dao;


import entity.Tax;
import exception.DatabaseConnectionException;
import exception.TaxCalculationException;
import util.DatabaseContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaxService implements ITaxService {

//    @Override
//    public Tax calculateTax(int employeeId, int taxYear) throws TaxCalculationException, DatabaseConnectionException {
//        // Implement logic to calculate tax for the specified employee and tax year
//        // You may need to fetch employee income and other data from the database
//        // Update the database with the calculated tax information
//
//        try (Connection connection = DatabaseContext.getConnection()) {
//            // Implement tax calculation logic and database update here
//        } catch (SQLException e) {
//            // Handle database-related exceptions
//            e.printStackTrace();
//            throw new TaxCalculationException("Error calculating tax for employee with ID " + employeeId);
//        }
//		return null;
//    }
	private static double getRandomTax(double[] array,Random random) {
		int randomIndex = random.nextInt(array.length);
		return array[randomIndex];
	}
	@Override
	public Tax calculateTax(int employeeId, int taxYear) throws TaxCalculationException, DatabaseConnectionException {
	    try (Connection connection = DatabaseContext.getConnection()) {
	        // Fetch employee income and other relevant data from the database
//	        double taxableIncome = getTaxableIncomeFromDatabase(employeeId, connection);
	    	Random random = new Random(); 
	    	double[] taxableArray = {1000.00,500.00,800.00,5000.00,7000.00,8000.00};
	    	double taxableIncome = getRandomTax(taxableArray,random);
	        // Implement your tax calculation logic here (replace the placeholder)
	        double calculatedTax = calculateTaxBasedOnIncome(taxableIncome);

	        // Update the database with the calculated tax information
	        updateTaxInformationInDatabase(employeeId, taxYear, taxableIncome, calculatedTax, connection);

	        // Create a Tax object with the calculated values
	        Tax tax = new Tax();
	        tax.setEmployeeID(employeeId);
	        tax.setTaxYear(taxYear);
	        tax.setTaxableIncome(taxableIncome);
	        tax.setTaxAmount(calculatedTax);

	        return tax;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new TaxCalculationException("Error calculating tax for employee with ID " + employeeId);
	    }
	}

	// Placeholder method to fetch taxable income from the database
	private double getTaxableIncomeFromDatabase(int employeeId, Connection connection) throws SQLException {
	    // Implement logic to retrieve taxable income from the database based on employeeId
	    // Replace this with your actual database query
	    try (PreparedStatement statement = connection.prepareStatement("SELECT TaxableIncome FROM Employee WHERE EmployeeID = ?")) {
	        statement.setInt(1, employeeId);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getDouble("TaxableIncome");
	            } else {
	                throw new SQLException("Taxable income not found for employee with ID " + employeeId);
	            }
	        }
	    }
	}

	// Placeholder method for tax calculation logic
	private double calculateTaxBasedOnIncome(double taxableIncome) {
	    // Implement your actual tax calculation logic based on taxable income
	    // Replace this placeholder with your business logic
	    return taxableIncome * 0.2; // Placeholder calculation, replace with actual formula
	}

	// Placeholder method to update tax information in the database
	private void updateTaxInformationInDatabase(int employeeId, int taxYear, double taxableIncome, double calculatedTax, Connection connection) throws SQLException {
	    // Implement logic to update tax information in the database
	    // Replace this with your actual database update query
	    try (PreparedStatement statement = connection.prepareStatement(
	            "INSERT INTO Tax (EmployeeID, TaxYear, TaxableIncome, TaxAmount) VALUES (?, ?, ?, ?)")) {
	        statement.setInt(1, employeeId);
	        statement.setInt(2, taxYear);
	        statement.setDouble(3, taxableIncome);
	        statement.setDouble(4, calculatedTax);

	        statement.executeUpdate();
	    }
	}

    @Override
    public Tax getTaxById(int taxId) throws DatabaseConnectionException {
        // Implement logic to retrieve tax by ID
        // You may need to fetch data from the database

        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Tax WHERE TaxID = ?")) {
            statement.setInt(1, taxId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Map result set to Tax object
                    return mapResultSetToTax(resultSet);
                } else {
                    return null; // Tax not found
                }
            }
        } catch (SQLException e) {
            // Handle database-related exceptions
            e.printStackTrace();
            return null;
        }
    }

    // Other methods for getting taxes for an employee, for a tax year, etc. go here...



    // Method to get taxes for a specific employee
//    public List<Tax> getTaxesForEmployee(int employeeId) {
//        List<Tax> taxesForEmployee = new ArrayList<>();
//        Tax[] taxes = null;
//		for (Tax tax : taxes) {
//            if (tax.getEmployeeID() == employeeId) {
//                taxesForEmployee.add(tax);
//            }
//        }
//        return taxesForEmployee;
//    }
//
//    // Method to get taxes for a specific year
//    public List<Tax> getTaxesForYear(int taxYear) {
//        List<Tax> taxesForYear = new ArrayList<>();
//        Tax[] taxes = null;
//		for (Tax tax : taxes) {
//            if (tax.getTaxYear() == taxYear) {
//                taxesForYear.add(tax);
//            }
//        }
//        return taxesForYear;
   // }




        @Override
        public List<Tax> getTaxesForEmployee(int employeeId) {
            List<Tax> taxes = new ArrayList<>();

            try (Connection connection = DatabaseContext.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM Tax WHERE EmployeeID = ?")) {
                statement.setInt(1, employeeId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        taxes.add(mapResultSetToTax(resultSet));
                    }
                }
            } catch (SQLException | DatabaseConnectionException e) {
                e.printStackTrace(); // Handle exceptions based on your application's logging strategy
            }

            return taxes;
        }

        @Override
        public List<Tax> getTaxesForYear(int taxYear) {
            List<Tax> taxes = new ArrayList<>();

            try (Connection connection = DatabaseContext.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM Tax WHERE TaxYear = ?")) {
                statement.setInt(1, taxYear);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        taxes.add(mapResultSetToTax(resultSet));
                    }
                }
            } catch (SQLException | DatabaseConnectionException e) {
                e.printStackTrace(); // Handle exceptions based on your application's logging strategy
            }

            return taxes;
        }

        // Helper method to map ResultSet to Tax object
        private Tax mapResultSetToTax(ResultSet resultSet) throws SQLException {
            return new Tax(
                    resultSet.getInt("TaxID"),
                    resultSet.getInt("EmployeeID"),
                    resultSet.getInt("TaxYear"),
                    resultSet.getDouble("TaxableIncome"),
                    resultSet.getDouble("TaxAmount")
            );
        }
    
}