package com.example.demo.service;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.ValidationResponse;
import com.example.demo.rules.ValidationRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ValidationService {
    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    private final List<ValidationRule> rules;

    public ValidationService(List<ValidationRule> rules) {
        this.rules = rules;
    }

    public ValidationResponse validateApplication(Application application) {
        logger.info("Starting Validation for application: {}", application);

        List<RuleViolation> violations = rules.stream()
                .map(rule -> rule.validate(application))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        boolean isValid = violations.isEmpty();

        if(isValid){
            logger.info("Validation completed successfully for application: {}", application);
        }else{
            List<String> failedFields = violations.stream()
                    .map(RuleViolation::fieldName)
                    .collect(Collectors.toList());
            logger.info("Validation completed. Valid: false, Failed fields: {}", failedFields);
            logger.debug("Violation details: {}", violations);
        }

        return new ValidationResponse(isValid, violations);
    }
}