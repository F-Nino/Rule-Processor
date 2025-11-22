package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.Income;
import com.example.demo.model.RuleViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class IncomeValidationRuleTest {

    @InjectMocks
    private IncomeValidationRule rule;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
    }

    @Test
    void whenAllIncomesArePositive_expectValidationPass() {
        Income income = new Income(30000, 65000);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenIncomesAreZero_expectValidationPass() {
        Income income = new Income(0, 0);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenStudentIncomeIsNull_expectValidationPass() {
        Income income = new Income(null, 50000);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenParentIncomeIsNull_expectValidationPass() {
        Income income = new Income(20000, null);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @Test
    void whenStudentIncomeIsNegative_expectValidationFailure() {
        Income income = new Income(-1000, 50000);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("income");
        assertThat(result.message()).contains("Student income cannot be negative", "-1000");
    }

    @Test
    void whenParentIncomeIsNegative_expectValidationFailure() {
        Income income = new Income(20000, -5000);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("income");
        assertThat(result.message()).contains("Parent income cannot be negative", "-5000");
    }

    @Test
    void whenBothIncomesAreNegative_expectValidationFailure() {
        Income income = new Income(-1000, -2000);
        application.setIncome(income);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("income");
        assertThat(result.message()).contains("Student income cannot be negative");
        assertThat(result.message()).contains("Parent income cannot be negative");
    }

    @Test
    void whenIncomeIsNull_expectValidationFailure() {
        application.setIncome(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("income");
        assertThat(result.message()).isEqualTo("Income information required");
    }
}
