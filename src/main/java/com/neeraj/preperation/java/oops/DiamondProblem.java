package com.neeraj.preperation.java.oops;

public class DiamondProblem {

}

interface A {
    default String print() {
        return "Printing from A";
    }
}

interface B {
    default String print() {
        return "Printing from B";
    }
}

class Printer implements A, B {
    @Override
    public String print() {
//        Since both A, B interface has print method. JVM will not know which interface print method to call.
//          To solve this, in your class you have override the print so that JVM knows which interface print to call, when the print method is called on a object.
        return B.super.print();
    }
}