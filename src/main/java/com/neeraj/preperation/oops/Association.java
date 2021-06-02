package com.neeraj.preperation.oops;

import com.neeraj.preperation.oops.imutability.Employee;

import java.util.*;
import java.io.*;

public class Association {

}

class Aggregation {
    /**
     * Aggregation is a Weak association, one object can leave without other object.
     * Like in the below example, a player can exists with out team, and vive-versa.
     * Aggregation relation is “has-a”.
     */
    class Team {
        String name;
        List<Player> players;
    }

    class Player {
        String fName;
        String lName;
    }
}

class Composition {
    /**
     * Composition is a Strong association, one object cannot live without other object.
     * Like in the below example, an Employee cannot exits with out a department, and a department cant exists without Employees.
     * Composition relation is “part-of”
     */

    class Department {
        String departmentId;
        List<Employee> employees;
    }
}