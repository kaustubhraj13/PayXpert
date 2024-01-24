package dao;
import java.util.List;
import entity.Tax;
import exception.DatabaseConnectionException;
import exception.TaxCalculationException;

public interface ITaxService {
    Tax calculateTax(int employeeId, int taxYear) throws TaxCalculationException, DatabaseConnectionException;
    Tax getTaxById(int taxId) throws DatabaseConnectionException;
    List<Tax> getTaxesForEmployee(int employeeId);
    List<Tax> getTaxesForYear(int taxYear);
}