package com.example.demo.model;

import com.example.demo.model.enums.DependencyStatus;
import com.example.demo.model.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    private StudentInfo studentInfo;
    private DependencyStatus dependencyStatus;
    private MaritalStatus maritalStatus;
    private SpouseInfo spouseInfo;
    private Household household;
    private Income income;
    private String stateOfResidence;
}