package com.neeraj.preperation.java.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String empId) {
        super(empId + "Not Found");
    }
}

class EmployeeModificationException extends Exception {
    String empId;
    String status;

    public EmployeeModificationException(String empId, String status) {
        super("Employee Id: " + empId + " is in " + status + ". No any Modification is allowed in this status.");
    }
}