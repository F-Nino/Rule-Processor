package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;

public interface ValidationRule {
    RuleViolation validate(Application application);
}
