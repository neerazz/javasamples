package com.neeraj.preperation.hibernate;

import com.neeraj.preperation.java.oops.imutability.Employee;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.*;
import java.io.*;

/**
 * Created on:  Jun 02, 2021
 * Ref: https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/
 */

@Data
@Entity
public class DepartmentEntity {

    @Id
    long id;
    String departmentId;

    @OneToMany
    @JoinColumn(name = "dept_id")
//            This will look for the field dept_id in the Employee Table.
    Set<EmployeeEntity> employees;
}
