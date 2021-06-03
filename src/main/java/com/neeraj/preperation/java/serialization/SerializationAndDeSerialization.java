package com.neeraj.preperation.java.serialization;

import com.neeraj.preperation.java.oops.imutability.Employee;
import com.neeraj.preperation.java.oops.imutability.Address;

import java.nio.file.Path;
import java.util.*;
import java.io.*;

/**
 * Created on:  Jun 02, 2021
 * Ref: https://howtodoinjava.com/java/serialization/externalizable-vs-serializable/
 */

public class SerializationAndDeSerialization {

    public static void serializationAndDeSerialization() throws IOException, ClassNotFoundException {
        String fileName = "sample.txt";
        Address address = new Address("line1", "line2", "city", "33511");
        Employee employeeToFile = new Employee("10", "firstName", "lastName", address, "state");

        writeObjectToFile(fileName, employeeToFile);
        Employee employeeFromFile = getObjectFromFile(fileName);
        System.out.println("employeeToFile   = " + employeeToFile);
        System.out.println("employeeFromFile = " + employeeFromFile);
        if (employeeToFile.equals(employeeFromFile)) {
            System.out.println("Both the Objects are same.");
        } else {
            System.out.println("Both the Objects are Not-Same.");
        }
    }

    private static void writeObjectToFile(String fileName, Employee employee) {
//        Creates a FileOutputStream where objects from ObjectOutputStream are written
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream fileWriter = new ObjectOutputStream(fileOut);) {
//        Write the employee object to the file.
            fileWriter.writeObject(employee);
        } catch (IOException e) {
            System.out.println("Error While writing THe object to file.");
            e.printStackTrace();
        }
    }

    private static Employee getObjectFromFile(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream fileReader = new ObjectInputStream(fileIn);
        fileIn.close();
        fileReader.close();
        return (Employee) fileReader.readObject();
    }
}
