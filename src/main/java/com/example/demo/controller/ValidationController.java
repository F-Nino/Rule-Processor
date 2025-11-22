package com.example.demo.controller;


import com.example.demo.model.Application;
import com.example.demo.model.ValidationResponse;
import com.example.demo.service.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/applications")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }


    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validateApplication(
            @RequestBody Application application) {

        ValidationResponse response = validationService.validateApplication(application);

        return ResponseEntity.ok(response);
    }
}

