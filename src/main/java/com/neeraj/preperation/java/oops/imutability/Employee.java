package com.neeraj.preperation.java.oops.imutability;

import lombok.Getter;

import java.util.*;
import java.io.*;

@Getter
public class Employee {

    String empId;
    String firstName;
    String lastName;
    Address address;
    String state;

    public Employee(String empId, String firstName, String lastName, Address address, String state) {
        this.empId = empId;
        this.state = state;
        this.firstName = firstName;
        this.lastName = lastName;
//        Inside the constructor, make sure to use a clone copy of the passed argument and never set your mutable field to the real instance passed through constructor.
        this.address = address.clone();
    }

    @Override
    protected Employee clone() {
        return new Employee(empId, firstName, lastName, address, state);
    }

    public Employee setFirstName(String firstName) {
        Employee employee = new Employee(empId, firstName, lastName, address, state);
        return employee;
    }

    public Address getAddress() {
//        Make sure to always return a clone copy of the field and never return the real object instance.
        return address.clone();
    }

    public String getState() {
        return state;
    }

    public String getEmpId() {
        return empId;
    }
}

@Getter
class Address {
    private String line1;
    private String line2;
    private String city;
    private String zip;

    //    Create a private constructor.
    public Address(String line1, String line2, String city, String zip) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.zip = zip;
    }

    //    Create a clone Method.
    @Override
    protected Address clone() {
        return new Address(line1, line2, city, zip);
    }
}