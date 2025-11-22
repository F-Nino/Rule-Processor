package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.Household;
import com.example.demo.model.RuleViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HouseholdLogicRuleTest {

    @InjectMocks
    private HouseholdLogicRule rule;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
    }

    @Test
    void whenNumberInCollegeIsLessThanHousehold_expectValidationPass() {
        Household household = new Household(4, 2);
        application.setHousehold(household);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenNumberInCollegeEqualsHousehold_expectValidationPass() {
        Household household = new Household(3, 3);
        application.setHousehold(household);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenNumberInCollegeExceedsHousehold_expectValidationFailure() {
        Household household = new Household(2, 5);
        application.setHousehold(household);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("household");
        assertThat(result.message()).isEqualTo("Number in college (5) cannot exceed number in household (2)");
    }

    @Test
    void whenHouseholdIsNull_expectValidationFailure() {
        application.setHousehold(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("household");
        assertThat(result.message()).isEqualTo("Household information required");
    }

    @Test
    void whenNumberInHouseholdIsNull_expectValidationFailure() {
        Household household = new Household(null, 2);
        application.setHousehold(household);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("household");
        assertThat(result.message()).isEqualTo("Number in household and number in college are required");
    }

    @Test
    void whenNumberInCollegeIsNull_expectValidationFailure() {
        Household household = new Household(4, null);
        application.setHousehold(household);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("household");
        assertThat(result.message()).contains("Number in household and number in college are required");
    }

    @Test
    void whenBothNumbersAreNull_expectValidationFailure() {
        Household household = new Household(null, null);
        application.setHousehold(household);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("household");
        assertThat(result.message()).isEqualTo("Number in household and number in college are required");
    }
}
