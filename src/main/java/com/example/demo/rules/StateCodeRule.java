package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StateCodeRule extends ValidationRule {
    
    private static final Set<String> VALID_STATE_CODES = Set.of(
        "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
        "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
        "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
        "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
        "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY",
        "DC", "PR", "VI", "GU", "AS", "MP"
    );
    
    @Override
    public RuleViolation validate(Application application) {
        String stateCode = application.getStateOfResidence();
        
        if (stateCode == null || stateCode.trim().isEmpty()) {
            return createViolation("stateOfResidence", "State of residence is required");
        }
        
        String normalizedState = stateCode.trim().toUpperCase();
        
        if (!VALID_STATE_CODES.contains(normalizedState)) {
            return createViolation("stateOfResidence",
                String.format("Invalid state code '%s'", stateCode)
            );
        }
        
        return null;
    }
    
}
