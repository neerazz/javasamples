package com.neeraj.preperation.exceptions;

import com.neeraj.preperation.oops.imutability.Employee;

import java.util.*;
import java.io.*;

//https://www.baeldung.com/java-checked-unchecked-exceptions
public class EmployeeFunction {

    Map<String, Employee> employees = new HashMap<>();

    Employee getEmployee(String empId) {
        Employee employee = employees.get(empId);
//        This is an un-checked exception, means the exception is thrown based on few condition.
        return Optional.ofNullable(employee).orElseThrow(() -> new EmployeeNotFoundException(empId));
    }

    Employee editEmployee(String empId, String newFirstName) {
        Employee employee = getEmployee(empId);
        try {
//            Since editName method throws an Exception of Exception class that will be a checked exception, and that needs to be handled using try-catch
            Employee editedEmployee = editName(employee, newFirstName);
//            Since the employee object is immutable, changing a value creates a new instance of Employee. THe new instance needs to be save.
            employees.put(empId, editedEmployee);
            return editedEmployee;
        } catch (EmployeeModificationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Employee editName(Employee employee, String newFirstName) throws EmployeeModificationException {
        if (employee.getState().equalsIgnoreCase("Locked"))
            throw new EmployeeModificationException(employee.getEmpId(), employee.getState());
        return employee.setFirstName(newFirstName);
    }
}
