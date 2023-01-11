package com.neeraj.preperation.spring.jpa;

import com.neeraj.preperation.hibernate.EmployeeEntity;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends DataTablesRepository<EmployeeEntity, String> {

    List<EmployeeEntity> findByFirstName(String firstName);

    List<EmployeeEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
