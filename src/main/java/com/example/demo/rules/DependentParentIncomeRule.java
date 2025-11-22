package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.StudentInfo;
import com.example.demo.model.enums.DependencyStatus;
import com.example.demo.model.RuleViolation;
import org.springframework.stereotype.Component;

@Component
public class DependentParentIncomeRule implements ValidationRule {
    
    @Override
    public RuleViolation validate(Application application) {
        DependencyStatus dependencyStatus = application.getDependencyStatus();
        if (dependencyStatus == null) {
            return createViolation("Dependency status is required");
        }
        
        if (dependencyStatus == DependencyStatus.DEPENDENT) {
            if (application.getIncome() == null || application.getIncome().getParentIncome() == null) {
                return createViolation(
                    "Parent income is required for students with dependent status"
                );
            }
        }
        
        return null;
    }
    
    private RuleViolation createViolation(String message) {
        return new RuleViolation("income.parentIncome", message);
    }
}
