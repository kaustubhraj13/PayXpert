package main;

import dao.EmployeeService;
import dao.PayrollService;
import dao.TaxService;
import entity.Tax;
import dao.FinancialRecordService;
import exception.EmployeeNotFoundException;
import exception.PayrollGenerationException;
import exception.TaxCalculationException;
import exception.FinancialRecordException;
import exception.InvalidInputException;
import exception.DatabaseConnectionException;
import util.DBPropertyUtil;
import util.DBConnUtil;
import util.ValidationService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        MainModule mainModule = new MainModule();
//        mainModule.displayTaxInformation(1, 2022);
        

        try {
            // Establish database connection
            Connection connection = DBConnUtil.getConnection(DBPropertyUtil.getConnectionString("database.properties"));

            // Initialize services
            EmployeeService employeeService = new EmployeeService();
            PayrollService payrollService = new PayrollService();
            TaxService taxService = new TaxService();
            FinancialRecordService financialRecordService = new FinancialRecordService();

            // Menu-driven application
            while (true) {
                System.out.println("1. View Employee Details");
                System.out.println("2. Generate Payroll");
                System.out.println("3. Calculate Tax");
                System.out.println("4. Add Financial Record");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                switch (choice) {
                    case 1:
                        viewEmployeeDetails(employeeService, scanner);
                        break;
                    case 2:
                        generatePayroll(payrollService, employeeService, scanner);
                        break;
                    case 3:
                        calculateTax(taxService, employeeService, scanner);
                        break;
                    case 4:
					try {
						addFinancialRecord(financialRecordService, employeeService, scanner);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                        break;
                    case 5:
                        // Close the scanner and exit
                        scanner.close();
                        System.out.println("Exiting the application.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (SQLException e) {
            handleDatabaseException(e);
        }
    }

    private static void viewEmployeeDetails(EmployeeService employeeService, Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        try {
            System.out.println(employeeService.getEmployeeById(employeeId));
        } catch (EmployeeNotFoundException e) {
            System.out.println("Employee not found: " + e.getMessage());
        }
    }

    private static void generatePayroll(PayrollService payrollService, EmployeeService employeeService, Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        System.out.print("Enter Payroll Start Date (yyyy-MM-dd): ");
        String startDateStr = scanner.nextLine();
        System.out.print("Enter Payroll End Date (yyyy-MM-dd): ");
        String endDateStr = scanner.nextLine();

        try {
            payrollService.generatePayroll(employeeId, java.sql.Date.valueOf(startDateStr).toLocalDate(),
                    java.sql.Date.valueOf(endDateStr).toLocalDate());
            System.out.println("Payroll generated successfully.");
        } catch (PayrollGenerationException e) {
            System.out.println("Error generating payroll: " + e.getMessage());
        }
    }

    private static void calculateTax(TaxService taxService, EmployeeService employeeService, Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        System.out.print("Enter Tax Year: ");
        int taxYear = scanner.nextInt();

        try {
            taxService.calculateTax(employeeId, taxYear);
            System.out.println("Tax calculated successfully.");
        } catch (TaxCalculationException e) {
            System.out.println("Error calculating tax: " + e.getMessage());
        } catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static void addFinancialRecord(FinancialRecordService financialRecordService, EmployeeService employeeService, Scanner scanner) throws FinancialRecordException, InvalidInputException {
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter Record Type: ");
        String recordType = scanner.nextLine();

        financialRecordService.addFinancialRecord(employeeId, description, amount, recordType);
		System.out.println("Financial record added successfully.");
    }

    private static void handleDatabaseException(SQLException e) {
        // Log the exception or perform additional handling
        System.out.println("Database connection error: " + e.getMessage());
    }
    public void displayTaxInformation(int employeeId, int taxYear) {
    	TaxService taxService = new TaxService();
        try {
        	
            List<Tax> taxes = taxService.getTaxesForEmployee(employeeId);
            // Display taxes for the specified employee
            System.out.println("Tax Information for Employee ID " + employeeId + " for Tax Year " + taxYear + ":");
            for (Tax tax : taxes) {
                System.out.println("Tax ID: " + tax.getTaxID());
                System.out.println("Taxable Income: " + tax.getTaxableIncome());
                System.out.println("Tax Amount: " + tax.getTaxAmount());
                System.out.println("-----------------------------");
            }
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }
    }
}