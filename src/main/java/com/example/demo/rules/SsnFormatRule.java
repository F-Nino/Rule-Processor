package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.StudentInfo;
import org.springframework.stereotype.Component;

@Component
public class SsnFormatRule extends ValidationRule {
    private static final String SSN_PATTERN = "^\\d{9}$";
    
    @Override
    public RuleViolation validate(Application application) {
        StudentInfo studentInformation = application.getStudentInfo();
        if (studentInformation == null) {
            return createViolation("studentInfo", "Student information is required");
        }
        
        String ssn = studentInformation.getSsn();
        if (ssn == null || ssn.trim().isEmpty()) {
            return createViolation("studentInfo.ssn", "SSN is required");
        }
        
        String cleanSsn = ssn.replaceAll("[\\s-]", "");
        
        if (!cleanSsn.matches(SSN_PATTERN)) {
            return createViolation("studentInfo.ssn",
                String.format("SSN must be exactly 9 digits (provided: '%s')", ssn)
            );
        }
        
        return null;
    }
    
}
