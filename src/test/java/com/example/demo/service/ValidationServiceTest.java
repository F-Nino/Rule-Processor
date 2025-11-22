package com.example.demo.service;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import com.example.demo.model.ValidationResponse;
import com.example.demo.rules.ValidationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @Mock
    private ValidationRule firstMockRule;

    @Mock
    private ValidationRule secondMockRule;

    private ValidationService validationService;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
    }

    @Test
    void whenAllRulesPass_expectValidResponse() {
        List<ValidationRule> rules = Arrays.asList(firstMockRule, secondMockRule);
        validationService = new ValidationService(rules);
        when(firstMockRule.validate(any(Application.class))).thenReturn(null);
        when(secondMockRule.validate(any(Application.class))).thenReturn(null);

        ValidationResponse response = validationService.validateApplication(application);

        assertThat(response.isValid()).isTrue();
        assertThat(response.getViolations()).isEmpty();
    }

    @Test
    void whenOneRuleFails_expectInvalidResponseWithViolation() {
        List<ValidationRule> rules = Arrays.asList(firstMockRule, secondMockRule);
        validationService = new ValidationService(rules);

        RuleViolation violation = new RuleViolation("fieldName", "Field is invalid");

        when(firstMockRule.validate(any(Application.class))).thenReturn(null);
        when(secondMockRule.validate(any(Application.class))).thenReturn(violation);

        ValidationResponse response = validationService.validateApplication(application);

        assertThat(response.isValid()).isFalse();
        assertThat(response.getViolations()).hasSize(1);
        assertThat(response.getViolations().get(0).fieldName()).isEqualTo("fieldName");
        assertThat(response.getViolations().get(0).message()).isEqualTo("Field is invalid");
    }

    @Test
    void whenMultipleRulesFail_expectInvalidResponseWithMultipleViolations() {
        List<ValidationRule> rules = Arrays.asList(firstMockRule, secondMockRule);
        validationService = new ValidationService(rules);

        RuleViolation firstViolation = new RuleViolation("field1", "Field 1 is invalid");
        RuleViolation secondViolation = new RuleViolation("field2", "Field 2 is invalid");

        when(firstMockRule.validate(any(Application.class))).thenReturn(firstViolation);
        when(secondMockRule.validate(any(Application.class))).thenReturn(secondViolation);
        ValidationResponse response = validationService.validateApplication(application);

        assertThat(response.isValid()).isFalse();
        assertThat(response.getViolations()).hasSize(2);
        assertThat(response.getViolations()).extracting(RuleViolation::fieldName)
                .containsExactly("field1", "field2");
    }
}
