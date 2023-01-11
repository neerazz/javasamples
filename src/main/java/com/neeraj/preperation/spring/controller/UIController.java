package com.neeraj.preperation.spring.controller;

import com.neeraj.preperation.hibernate.EmployeeEntity;
import com.neeraj.preperation.spring.EmpService;
import com.neeraj.preperation.spring.jpa.EmpRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class UIController {

    private EmpService empService;
    private EmpRepository empRepository;

    @GetMapping(value = "/employees")
    public DataTablesOutput<EmployeeEntity> getAssets(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
        input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList(
                "ownerBusiness",
                "applicationName",
                "provider",
                "environment",
                "status",
                "assetType"));
        return empRepository.findAll(input);
    }
}
