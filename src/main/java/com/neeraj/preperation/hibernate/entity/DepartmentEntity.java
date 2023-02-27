package com.neeraj.preperation.hibernate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

/**
 * Created on:  Jun 02, 2021
 * Ref: https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/
 */

@Data
@Entity
@Table
public class DepartmentEntity {

    @Id
    long id;
    String departmentId;

    @OneToMany
    @JoinColumn(name = "dept_id")
//            This will look for the field dept_id in the Employee Table.
    Set<EmployeeEntity> employees;
}
