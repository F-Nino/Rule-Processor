package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.StudentInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class StudentAgeRule extends ValidationRule {
    
    private static final int MINIMUM_AGE = 14;

    public RuleViolation validate(Application application) {
        StudentInfo studentInformation = application.getStudentInfo();
        if (studentInformation == null) {
            return createViolation("studentInfo", "Student information is required");
        }
        
        LocalDate dob = studentInformation.getDateOfBirth();
        if (dob == null) {
            return createViolation("studentInfo.dateOfBirth", "Date of birth is required");
        }
        
        int age = Period.between(dob, LocalDate.now()).getYears();
        
        if (age < MINIMUM_AGE) {
            return createViolation("studentInfo.dateOfBirth",
                String.format("Student too young (%d years old, < %d)", age, MINIMUM_AGE)
            );
        }
        
        return null;
    }
    
}
