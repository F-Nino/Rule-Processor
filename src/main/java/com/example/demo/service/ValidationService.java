package com.example.demo.service;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.ValidationResponse;
import com.example.demo.rules.ValidationRule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ValidationService {

    private final List<ValidationRule> rules;

    public ValidationService(List<ValidationRule> rules) {
        this.rules = rules;
    }

    public ValidationResponse validateApplication(Application application) {
        List<RuleViolation> violations = rules.stream()
                .map(rule -> rule.validate(application))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        boolean isValid = violations.isEmpty();

        return new ValidationResponse(isValid, violations, rules.size());
    }
}