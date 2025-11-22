package com.example.demo.model;

import java.util.List;

public class ValidationResponse {
    private boolean valid;
    private List<RuleViolation> violations;

    public ValidationResponse() {
    }

    public ValidationResponse(boolean valid, List<RuleViolation> violations, int totalRulesChecked) {
        this.valid = valid;
        this.violations = violations;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<RuleViolation> getViolations() {
        return violations;
    }

    public void setViolations(List<RuleViolation> violations) {
        this.violations = violations;
    }
}