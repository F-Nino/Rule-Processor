package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.enums.MaritalStatus;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.SpouseInfo;
import org.springframework.stereotype.Component;

@Component
public class MaritalStatusRule extends ValidationRule {

    @Override
    public RuleViolation validate(Application application) {
        MaritalStatus maritalStatus = application.getMaritalStatus();
        if (maritalStatus == null) {
            return createViolation("maritalStatus", "Marital status required");
        }
        
        if (maritalStatus == MaritalStatus.MARRIED) {
            SpouseInfo spouse = application.getSpouseInfo();
            
            if (spouse == null) {
                return createViolation("spouseInfo", "Spouse information is required for married students");
            }

            if (isNullOrEmpty(spouse.getFirstName()) || 
                isNullOrEmpty(spouse.getLastName()) || 
                isNullOrEmpty(spouse.getSsn())) {
                return createViolation("spouseInfo",
                    "Complete spouse information (first name, last name, and SSN) is required for married students"
                );
            }
        }
        
        return null;
    }
    
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
}
