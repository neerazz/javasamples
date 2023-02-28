package com.neeraj.preperation.spring.controller;

import com.neeraj.preperation.jackson.model.EmployeeDto;
import com.neeraj.preperation.spring.EmpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
@AllArgsConstructor
public class BaseController {

    private EmpService empService;

    @GetMapping
    public List<EmployeeDto> getEmployee(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "state", required = false) String state) {
        return empService.getEmployee(name, state);
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        return empService.createEmployee(employeeDto);
    }
}
