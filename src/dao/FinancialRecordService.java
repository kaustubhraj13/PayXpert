//package dao;
//
//import entity.FinancialRecord;
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
//public class FinancialRecordService implements IFinancialRecordService {
//
//    @Override
//    public void addFinancialRecord(int employeeId, String description, double amount, String recordType) {
//        // Implement logic to add a financial record for the specified employee
//        // You may need to update the database with the new financial record
//
//        try (Connection connection = DatabaseContext.getConnection();
//             PreparedStatement statement = connection.prepareStatement(
//                     "INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) " +
//                             "VALUES (?, ?, ?, ?, ?)")) {
//
//            statement.setInt(1, employeeId);
//            statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
//            statement.setString(3, description);
//            statement.setDouble(4, amount);
//            statement.setString(5, recordType);
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            // Handle database-related exceptions
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public FinancialRecord getFinancialRecordById(int recordId) {
//        // Implement logic to retrieve financial record by ID
//        // You may need to fetch data from the database
//
//        try (Connection connection = DatabaseContext.getConnection();
//             PreparedStatement statement = connection.prepareStatement(
//                     "SELECT * FROM FinancialRecord WHERE RecordID = ?")) {
//            statement.setInt(1, recordId);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    // Map result set to FinancialRecord object
//                    return mapResultSetToFinancialRecord(resultSet);
//                } else {
//                    return null; // Financial record not found
//                }
//            }
//        } catch (SQLException e) {
//            // Handle database-related exceptions
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // Other methods for getting financial records for an employee, for a date, etc. go here...
//
//    private FinancialRecord mapResultSetToFinancialRecord(ResultSet resultSet) throws SQLException {
//        // Map result set columns to FinancialRecord object
//        return new FinancialRecord(
//                resultSet.getInt("RecordID"),
//                resultSet.getInt("EmployeeID"),
//                resultSet.getDate("RecordDate").toLocalDate(),
//                resultSet.getString("Description"),
//                resultSet.getDouble("Amount"),
//                resultSet.getString("RecordType")
//        );
//    }
//
//	@Override
//	public List<entity.FinancialRecord> getFinancialRecordsForEmployee(int employeeId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<entity.FinancialRecord> getFinancialRecordsForDate(dao.LocalDate recordDate) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<entity.FinancialRecord> getFinancialRecordsForDate(dao.LocalDate recordDate) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<entity.FinancialRecord> getFinancialRecordsForDate(dao.LocalDate recordDate) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<FinancialRecord> getFinancialRecordsForDate(dao.LocalDate recordDate) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<FinancialRecord> getFinancialRecordsForDate(dao.LocalDate recordDate) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<FinancialRecord> getFinancialRecordsForDate(LocalDate recordDate) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
package dao;

import entity.FinancialRecord;
import exception.DatabaseConnectionException;
import util.DatabaseContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancialRecordService implements IFinancialRecordService {

    @Override
    public void addFinancialRecord(int employeeId, String description, double amount, String recordType) {
        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) " +
                             "VALUES (?, ?, ?, ?, ?)")) {

            statement.setInt(1, employeeId);
            statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            statement.setString(3, description);
            statement.setDouble(4, amount);
            statement.setString(5, recordType);

            statement.executeUpdate();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FinancialRecord getFinancialRecordById(int recordId) {
        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM FinancialRecord WHERE RecordID = ?")) {
            statement.setInt(1, recordId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToFinancialRecord(resultSet);
                } else {
                    return null; // Financial record not found
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId) {
        List<FinancialRecord> financialRecords = new ArrayList<>();

        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM FinancialRecord WHERE EmployeeID = ?")) {
            statement.setInt(1, employeeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    financialRecords.add(mapResultSetToFinancialRecord(resultSet));
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }

        return financialRecords;
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForDate(LocalDate recordDate) {
        List<FinancialRecord> financialRecords = new ArrayList<>();

        try (Connection connection = DatabaseContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM FinancialRecord WHERE RecordDate = ?")) {
            statement.setDate(1, java.sql.Date.valueOf(recordDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    financialRecords.add(mapResultSetToFinancialRecord(resultSet));
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }

        return financialRecords;
    }

    private FinancialRecord mapResultSetToFinancialRecord(ResultSet resultSet) throws SQLException {
        return new FinancialRecord(
                resultSet.getInt("RecordID"),
                resultSet.getInt("EmployeeID"),
                resultSet.getDate("RecordDate").toLocalDate(),
                resultSet.getString("Description"),
                resultSet.getDouble("Amount"),
                resultSet.getString("RecordType")
        );
    }
}
