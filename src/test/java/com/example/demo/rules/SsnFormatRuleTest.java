package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.StudentInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SsnFormatRuleTest {

    @InjectMocks
    private SsnFormatRule rule;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456789", "987654321", "111223333"})
    void whenValidNineDigitSsn_expectValidationPass(String ssn) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setSsn(ssn);
        application.setStudentInfo(studentInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"123-45-6789", "987-65-4321"})
    void whenSsnWithDashes_expectValidationPass(String ssn) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setSsn(ssn);
        application.setStudentInfo(studentInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ABC123456", "12345678X", "invalid", "123", "1234567"})
    void whenNonNumericSsnOrInvalidLength_expectValidationFailure(String ssn) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setSsn(ssn);
        application.setStudentInfo(studentInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("studentInfo.ssn");
        assertThat(result.message()).contains("SSN must be exactly 9 digits", ssn);
    }

    @Test
    void whenSsnIsBlank_expectValidationFailure() {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setSsn(" ");
        application.setStudentInfo(studentInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("studentInfo.ssn");
        assertThat(result.message()).contains("SSN is required");
    }

    @Test
    void whenSsnIsNull_expectValidationFailure() {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setSsn(null);
        application.setStudentInfo(studentInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("studentInfo.ssn");
        assertThat(result.message()).contains("SSN is required");
    }

    @Test
    void whenStudentInfoIsNull_expectValidationFailure() {
        application.setStudentInfo(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("studentInfo");
        assertThat(result.message()).isEqualTo("Student information is required");
    }
}
