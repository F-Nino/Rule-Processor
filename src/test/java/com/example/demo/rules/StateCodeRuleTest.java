package com.example.demo.rules;

import com.example.demo.model.Application;
import com.example.demo.model.RuleViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StateCodeRuleTest {

    @InjectMocks
    private StateCodeRule rule;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
    }

    @ParameterizedTest
    @ValueSource(strings = {"CA", "NY", "TX", "FL", "DC", "PR", "VI", "GU", "AS", "MP"})
    void whenValidStateCode_expectValidationPass(String stateCode) {
        application.setStateOfResidence(stateCode);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ca", "ny", "tx", "pr"})
    void whenLowercaseStateCode_expectValidationPass(String stateCode) {
        application.setStateOfResidence(stateCode);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {" CA ", " NY ", " TX "})
    void whenStateCodeWithWhitespace_expectValidationPass(String stateCode) {
        application.setStateOfResidence(stateCode);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"XX", "ZZ", "ABC", "1A"})
    void whenInvalidStateCode_expectValidationFailure(String stateCode) {
        application.setStateOfResidence(stateCode);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("stateOfResidence");
        assertThat(result.message()).contains("Invalid state code", stateCode);
    }

    @Test
    void whenStateCodeIsNull_expectValidationFailure() {
        application.setStateOfResidence(null);

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("stateOfResidence");
        assertThat(result.message()).isEqualTo("State of residence is required");
    }

    @Test
    void whenStateCodeIsEmpty_expectValidationFailure() {
        application.setStateOfResidence("");

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("stateOfResidence");
        assertThat(result.message()).isEqualTo("State of residence is required");
    }

    @Test
    void whenStateCodeIsBlank_expectValidationFailure() {
        application.setStateOfResidence(" ");

        RuleViolation result = rule.validate(application);

        assertThat(result).isNotNull();
        assertThat(result.fieldName()).isEqualTo("stateOfResidence");
        assertThat(result.message()).isEqualTo("State of residence is required");
    }
}
