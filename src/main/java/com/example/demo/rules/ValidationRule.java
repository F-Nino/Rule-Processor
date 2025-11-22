package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;

public abstract class ValidationRule {
    
    public abstract RuleViolation validate(Application application);
    
    protected RuleViolation createViolation(String fieldName, String message) {
        return new RuleViolation(fieldName, message);
    }
}
