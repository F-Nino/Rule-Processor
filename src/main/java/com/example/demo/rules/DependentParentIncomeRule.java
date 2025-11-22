package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.enums.DependencyStatus;
import com.example.demo.model.RuleViolation;
import org.springframework.stereotype.Component;

@Component
public class DependentParentIncomeRule extends ValidationRule {
    
    @Override
    public RuleViolation validate(Application application) {
        DependencyStatus dependencyStatus = application.getDependencyStatus();
        if (dependencyStatus == null) {
            return createViolation("dependencyStatus", "Dependency status is required");
        }
        
        if (dependencyStatus == DependencyStatus.DEPENDENT) {
            if (application.getIncome() == null || application.getIncome().getParentIncome() == null) {
                return createViolation("income.parentIncome",
                    "Parent income is required for students with dependent status"
                );
            }
        }
        
        return null;
    }
    
}
