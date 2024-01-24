package dao;
import java.util.List;
import java.time.LocalDate;
import entity.Payroll;
import exception.PayrollGenerationException;

public interface IPayrollService {
    Payroll generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws PayrollGenerationException;
    Payroll getPayrollById(int payrollId);
    List<Payroll> getPayrollsForEmployee(int employeeId);
    List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate);
}