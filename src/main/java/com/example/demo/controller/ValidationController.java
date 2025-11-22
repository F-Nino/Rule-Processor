package com.example.demo.controller;

import com.example.demo.model.Application;
import com.example.demo.model.ValidationResponse;
import com.example.demo.service.ValidationService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/applications")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/validate")
    public ValidationResponse validateApplication(@RequestBody Application application) {
        return validationService.validateApplication(application);
    }
}

