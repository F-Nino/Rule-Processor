package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.StudentInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StudentAgeRuleTest {

    @InjectMocks
    private StudentAgeRule rule;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
    }

    @Test
    void whenStudentIsOldEnough_expectValidationPass() {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setDateOfBirth(LocalDate.now().minusYears(18));
        application.setStudentInfo(studentInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenStudentIsExactlyMinimumAge_expectValidationPass() {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setDateOfBirth(LocalDate.now().minusYears(14));
        application.setStudentInfo(studentInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenStudentIsTooYoung_expectValidationFailure() {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setDateOfBirth(LocalDate.now().minusYears(10));
        application.setStudentInfo(studentInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("studentInfo.dateOfBirth");
        assertThat(result.message()).isEqualTo("Student too young (10 years old, < 14)");
    }

    @Test
    void whenStudentInfoIsNull_expectValidationFailure() {
        application.setStudentInfo(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("studentInfo");
        assertThat(result.message()).isEqualTo("Student information is required");
    }

    @Test
    void whenDateOfBirthIsNull_expectValidationFailure() {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setDateOfBirth(null);
        application.setStudentInfo(studentInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("studentInfo.dateOfBirth");
        assertThat(result.message()).isEqualTo("Date of birth is required");
    }
}
