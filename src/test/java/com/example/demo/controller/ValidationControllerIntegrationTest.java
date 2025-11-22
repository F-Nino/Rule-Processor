package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ValidationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnValidForCompleteValidApplication() throws Exception {
        String validApplication = """
                {
                  "studentInfo": {
                    "firstName": "Jane",
                    "lastName": "Smith",
                    "ssn": "123456789",
                    "dateOfBirth": "2003-05-15"
                  },
                  "dependencyStatus": "DEPENDENT",
                  "maritalStatus": "SINGLE",
                  "household": {
                    "numberInHousehold": 4,
                    "numberInCollege": 1
                  },
                  "income": {
                    "studentIncome": 5000,
                    "parentIncome": 65000
                  },
                  "stateOfResidence": "CA"
                }
                """;

        mockMvc.perform(post("/applications/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validApplication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.violations").isEmpty());
    }

    @Test
    void shouldReturnMultipleViolationsForInvalidApplication() throws Exception {
        String invalidApplication = """
                {
                  "studentInfo": {
                    "firstName": "John",
                    "lastName": "Doe",
                    "ssn": "invalid",
                    "dateOfBirth": "2015-01-01"
                  },
                  "dependencyStatus": "DEPENDENT",
                  "maritalStatus": "MARRIED",
                  "household": {
                    "numberInHousehold": 2,
                    "numberInCollege": 5
                  },
                  "income": {
                    "studentIncome": -1000
                  },
                  "stateOfResidence": "XX"
                }
                """;

        mockMvc.perform(post("/applications/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidApplication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(false))
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(greaterThanOrEqualTo(7))))
                // Student too young
                .andExpect(jsonPath("$.violations[*].fieldName", hasItem("studentInfo.dateOfBirth")))
                .andExpect(jsonPath("$.violations[?(@.fieldName == 'studentInfo.dateOfBirth')].message",
                        hasItem(containsString("too young"))))
                .andExpect(jsonPath("$.violations[*].fieldName", hasItem("studentInfo.ssn")))
                .andExpect(jsonPath("$.violations[?(@.fieldName == 'studentInfo.ssn')].message",
                        hasItem(containsString("9 digits"))))
                // Missing parent income
                .andExpect(jsonPath("$.violations[*].fieldName", hasItem("income.parentIncome")))
                .andExpect(jsonPath("$.violations[?(@.fieldName == 'income.parentIncome')].message",
                        hasItem(containsString("Parent income is required"))))
                // Missing spouse info
                .andExpect(jsonPath("$.violations[*].fieldName", hasItem("spouseInfo")))
                .andExpect(jsonPath("$.violations[?(@.fieldName == 'spouseInfo')].message",
                        hasItem(containsString("Spouse information"))))
                // Negative income
                .andExpect(jsonPath("$.violations[*].fieldName", hasItem("income")))
                .andExpect(jsonPath("$.violations[?(@.fieldName == 'income')].message",
                        hasItem(containsString("negative"))))
                // Household logic violation
                .andExpect(jsonPath("$.violations[*].fieldName", hasItem("household")))
                .andExpect(jsonPath("$.violations[?(@.fieldName == 'household')].message",
                        hasItem(containsString("cannot exceed"))))
                // Invalid state code
                .andExpect(jsonPath("$.violations[*].fieldName", hasItem("stateOfResidence")))
                .andExpect(jsonPath("$.violations[?(@.fieldName == 'stateOfResidence')].message",
                        hasItem(containsString("Invalid state code"))));
    }

    @Test
    void shouldHandleMarriedStudentWithSpouseInfo() throws Exception {
        String marriedApplication = """
                {
                  "studentInfo": {
                    "firstName": "Alice",
                    "lastName": "Johnson",
                    "ssn": "987654321",
                    "dateOfBirth": "2000-03-20"
                  },
                  "dependencyStatus": "INDEPENDENT",
                  "maritalStatus": "MARRIED",
                  "spouseInfo": {
                    "firstName": "Bob",
                    "lastName": "Johnson",
                    "ssn": "123987456"
                  },
                  "household": {
                    "numberInHousehold": 2,
                    "numberInCollege": 1
                  },
                  "income": {
                    "studentIncome": 30000
                  },
                  "stateOfResidence": "NY"
                }
                """;

        mockMvc.perform(post("/applications/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(marriedApplication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.violations").isEmpty());
    }

    @Test
    void shouldDetectMissingSpouseInfoForMarriedStudent() throws Exception {
        String marriedWithoutSpouse = """
                {
                  "studentInfo": {
                    "firstName": "Charlie",
                    "lastName": "Brown",
                    "ssn": "555666777",
                    "dateOfBirth": "2001-07-10"
                  },
                  "dependencyStatus": "INDEPENDENT",
                  "maritalStatus": "MARRIED",
                  "household": {
                    "numberInHousehold": 2,
                    "numberInCollege": 1
                  },
                  "income": {
                    "studentIncome": 25000
                  },
                  "stateOfResidence": "TX"
                }
                """;

        mockMvc.perform(post("/applications/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(marriedWithoutSpouse))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(false))
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].fieldName").value("spouseInfo"))
                .andExpect(jsonPath("$.violations[0].message", containsString("Spouse information is required")));
    }
}