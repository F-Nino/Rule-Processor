package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.SpouseInfo;
import com.example.demo.model.enums.MaritalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MaritalStatusRuleTest {

    @InjectMocks
    private MaritalStatusRule rule;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
    }

    @Test
    void whenSingleWithNoSpouseInfo_expectValidationPass() {
        application.setMaritalStatus(MaritalStatus.SINGLE);
        application.setSpouseInfo(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenMarriedWithCompleteSpouseInfo_expectValidationPass() {
        application.setMaritalStatus(MaritalStatus.MARRIED);
        SpouseInfo spouseInfo = new SpouseInfo("Jane", "Doe", "123456789");
        application.setSpouseInfo(spouseInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenMaritalStatusIsNull_expectValidationFailure() {
        application.setMaritalStatus(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("maritalStatus");
        assertThat(result.message()).isEqualTo("Marital status required");
    }

    @Test
    void whenMarriedWithNoSpouseInfo_expectValidationFailure() {
        application.setMaritalStatus(MaritalStatus.MARRIED);
        application.setSpouseInfo(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("spouseInfo");
        assertThat(result.message()).contains("Spouse information is required");
    }

    @Test
    void whenMarriedWithMissingFirstName_expectValidationFailure() {
        application.setMaritalStatus(MaritalStatus.MARRIED);
        SpouseInfo spouseInfo = new SpouseInfo(null, "Doe", "123456789");
        application.setSpouseInfo(spouseInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("spouseInfo");
        assertThat(result.message()).isEqualTo("Complete spouse information (first name, last name, and SSN) is required for married students");
    }

    @Test
    void whenMarriedWithEmptyFirstName_expectValidationFailure() {
        application.setMaritalStatus(MaritalStatus.MARRIED);
        SpouseInfo spouseInfo = new SpouseInfo("", "Doe", "123456789");
        application.setSpouseInfo(spouseInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("spouseInfo");
        assertThat(result.message()).isEqualTo("Complete spouse information (first name, last name, and SSN) is required for married students");
    }

    @Test
    void whenMarriedWithMissingLastName_expectValidationFailure() {
        application.setMaritalStatus(MaritalStatus.MARRIED);
        SpouseInfo spouseInfo = new SpouseInfo("Jane", null, "123456789");
        application.setSpouseInfo(spouseInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("spouseInfo");
        assertThat(result.message()).contains("Complete spouse information");
    }

    @Test
    void whenMarriedWithMissingSsn_expectValidationFailure() {
        application.setMaritalStatus(MaritalStatus.MARRIED);
        SpouseInfo spouseInfo = new SpouseInfo("Jane", "Doe", null);
        application.setSpouseInfo(spouseInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("spouseInfo");
        assertThat(result.message()).contains("Complete spouse information");
    }

    @Test
    void whenMarriedWithBlankSpouseFields_expectValidationFailure() {
        application.setMaritalStatus(MaritalStatus.MARRIED);
        SpouseInfo spouseInfo = new SpouseInfo("   ", "   ", "   ");
        application.setSpouseInfo(spouseInfo);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("spouseInfo");
        assertThat(result.message()).contains("Complete spouse information");
    }
}
