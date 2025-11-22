package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.Income;
import com.example.demo.model.RuleViolation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IncomeValidationRule implements ValidationRule {
    
    @Override
    public RuleViolation validate(Application application) {
        Income applicationIncome = application.getIncome();
        if (applicationIncome == null) {
            return createViolation("Income information required");
        }
        
        List<String> violations = collectIncomeViolations(applicationIncome);
        
        if (!violations.isEmpty()) {
            return createViolation(String.join("; ", violations));
        }
        
        return null;
    }
    
    private List<String> collectIncomeViolations(Income applicationIncome) {
        List<String> violations = new ArrayList<>();
        
        Integer studentIncome = applicationIncome.getStudentIncome();
        if (studentIncome != null && studentIncome < 0) {
            violations.add("Student income cannot be negative (value: " + studentIncome + ")");
        }
        
        Integer parentIncome = applicationIncome.getParentIncome();
        if (parentIncome != null && parentIncome < 0) {
            violations.add("Parent income cannot be negative (value: " + parentIncome + ")");
        }
        
        return violations;
    }

    
    private RuleViolation createViolation(String message) {
        return new RuleViolation("income", message);
    }
}
