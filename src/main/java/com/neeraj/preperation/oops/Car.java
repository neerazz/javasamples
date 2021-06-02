package com.neeraj.preperation.oops;

import java.util.*;
import java.io.*;

public abstract class Car {

    String make;
    String model;
    String name;
    String plateNumber;

    //    You have schema of car define, You can use this schema\blue-print and build you own type of car.
    public Car(String name, String plateNumber) {
        this.name = name;
        this.plateNumber = plateNumber;
    }

//    final keyword can be added to a method so that, no any sub-class can modify it.
    public final String driveCar(String driver) {
        String message = String.format("Hello World, I am %s and I am driving %s car with number : %s", driver, name, plateNumber);
        System.out.println(message);
        return message;
    }

    public String getProperties() {
        String properties = String.format("make : %s \n model : %s \n name : %s \n plateNumber : %s", make, model, name, plateNumber);
        System.out.println("properties = " + properties);
        return properties;
    }

    public abstract int getCost();
}

class Audi extends Car {

    String interiors;

    public Audi(String plateNumber, String interiors) {
        super("Audi", plateNumber);
        this.interiors = interiors;
    }

    @Override
    public String getProperties() {
//        This method is utilizing the decorator pattern, where you calling the super class method, also appending your own car properties to it.
        String properties = super.getProperties();
        properties += "\n interiors : " + interiors;
        return properties;
    }

    @Override
    public int getCost() {
        return interiors.equals("Leather") ? 40_000 : 35_000;
    }
}