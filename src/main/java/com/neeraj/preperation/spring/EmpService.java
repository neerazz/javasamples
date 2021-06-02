package com.neeraj.preperation.spring;

import com.neeraj.preperation.hibernate.EmployeeEntity;
import com.neeraj.preperation.java.exceptions.EmployeeNotFoundException;
import com.neeraj.preperation.spring.jpa.EmpRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpService {

    EmpRepository empRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @Cacheable(cacheNames = "searchByFirstName", key = "#firstName")
    List<EmployeeEntity> searchByName(String firstName) {
        List<EmployeeEntity> employees = empRepository.findByFirstName(firstName);
        return employees;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Cacheable(cacheNames = "searchByFirstNameAndLastName", keyGenerator = "customFirstLastNameKeyGenerator")
    List<EmployeeEntity> searchByName(String firstName, String lastName) {
        List<EmployeeEntity> employees = empRepository.findByFirstNameAndLastName(firstName, lastName);
        return employees;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Cacheable(cacheNames = "findById", key = "#empId")
    EmployeeEntity getByEmpId(String empId) {
        return empRepository.findById(empId).orElseThrow(() -> new EmployeeNotFoundException(empId));
    }
}
