package com.neeraj.preperation.hibernate;

import javax.persistence.*;
import java.util.*;
import java.io.*;

@Entity
public class EmployeeEntity {

    @Id
    String empId;
    String firstName;
    String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
//    This will create a column with name dept_id, so that the department mapping can be done with employee.
    private DepartmentEntity departmentEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    AddressEntity addressEntity;
    String state;
}
