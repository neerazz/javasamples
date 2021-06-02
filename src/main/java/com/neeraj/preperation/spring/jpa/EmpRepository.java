package com.neeraj.preperation.spring.jpa;

import com.neeraj.preperation.hibernate.EmployeeEntity;
import com.neeraj.preperation.java.oops.imutability.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.io.*;

@Repository
public interface EmpRepository extends JpaRepository<EmployeeEntity, String> {

    List<EmployeeEntity> findByFirstName(String firstName);

    List<EmployeeEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
