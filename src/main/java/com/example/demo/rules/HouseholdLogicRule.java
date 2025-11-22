package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.Household;
import com.example.demo.model.RuleViolation;
import org.springframework.stereotype.Component;

@Component
public class HouseholdLogicRule extends ValidationRule {
    
    @Override
    public RuleViolation validate(Application application) {
        Household household = application.getHousehold();
        if (household == null) {
            return createViolation("household", "Household information required");
        }
        
        Integer numberInHousehold = household.getNumberInHousehold();
        Integer numberInCollege = household.getNumberInCollege();
        
        if (numberInHousehold == null || numberInCollege == null) {
            return createViolation("household", "Number in household and number in college are required");
        }
        
        if (numberInCollege > numberInHousehold) {
            return createViolation("household",
                String.format("Number in college (%d) cannot exceed number in household (%d)",
                    numberInCollege, numberInHousehold)
            );
        }
        
        return null;
    }

}
