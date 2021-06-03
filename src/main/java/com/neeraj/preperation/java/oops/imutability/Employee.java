package com.neeraj.preperation.java.oops.imutability;

import lombok.Getter;

import java.util.*;
import java.io.*;

/**
 * Created on:  Jun 03, 2021
 * Ref: https://howtodoinjava.com/java/serialization/custom-serialization-readobject-writeobject/
 */

@Getter
public class Employee implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!Objects.equals(empId, employee.empId)) return false;
        if (!Objects.equals(firstName, employee.firstName)) return false;
        if (!Objects.equals(lastName, employee.lastName)) return false;
        if (!Objects.equals(address, employee.address)) return false;
        return Objects.equals(state, employee.state);
    }

    @Override
    public int hashCode() {
        int result = empId != null ? empId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", state='" + state + '\'' +
                '}';
    }
}