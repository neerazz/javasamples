package com.neeraj.preperation.spring.controller;

import com.neeraj.preperation.spring.EmpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class UIController {

    private EmpService empService;
}
