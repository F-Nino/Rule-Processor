package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.Income;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.enums.DependencyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DependentParentIncomeRuleTest {

    @InjectMocks
    private DependentParentIncomeRule rule;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
    }

    @Test
    void whenDependentWithParentIncome_expectValidationPass() {
        application.setDependencyStatus(DependencyStatus.DEPENDENT);
        Income income = new Income(5000, 65000);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenIndependentWithoutParentIncome_expectValidationPass() {
        application.setDependencyStatus(DependencyStatus.INDEPENDENT);
        Income income = new Income(30000, null);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenIndependentWithNoIncome_expectValidationPass() {
        application.setDependencyStatus(DependencyStatus.INDEPENDENT);
        application.setIncome(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenDependencyStatusIsNull_expectValidationFailure() {
        application.setDependencyStatus(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("dependencyStatus");
        assertThat(result.message()).isEqualTo("Dependency status is required");
    }

    @Test
    void whenDependentWithoutParentIncome_expectValidationFailure() {
        application.setDependencyStatus(DependencyStatus.DEPENDENT);
        Income income = new Income(5000, null);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("income.parentIncome");
        assertThat(result.message()).isEqualTo("Parent income is required for students with dependent status");
    }

    @Test
    void whenDependentWithNoIncomeObject_expectValidationFailure() {
        application.setDependencyStatus(DependencyStatus.DEPENDENT);
        application.setIncome(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("income.parentIncome");
        assertThat(result.message()).isEqualTo("Parent income is required for students with dependent status");
    }

    @Test
    void whenDependentWithZeroParentIncome_expectValidationPass() {
        application.setDependencyStatus(DependencyStatus.DEPENDENT);
        Income income = new Income(0, 0);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }
}
