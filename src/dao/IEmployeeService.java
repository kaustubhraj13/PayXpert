package dao;
import java.util.List;

import entity.Employee;
import exception.EmployeeNotFoundException;

public interface IEmployeeService {
    Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException;
    List<Employee> getAllEmployees();
    void addEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void removeEmployee(int employeeId) throws EmployeeNotFoundException;
}
