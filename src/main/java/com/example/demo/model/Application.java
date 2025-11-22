package com.example.demo.model;

import com.example.demo.model.enums.DependencyStatus;
import com.example.demo.model.enums.MaritalStatus;

public class Application {
    private StudentInfo studentInfo;
    private DependencyStatus dependencyStatus;
    private MaritalStatus maritalStatus;
    private SpouseInfo spouseInfo;
    private Household household;
    private Income income;
    private String stateOfResidence;

    public Application() {
    }

    public Application(StudentInfo studentInfo, DependencyStatus dependencyStatus,
                       MaritalStatus maritalStatus, SpouseInfo spouseInfo,
                       Household household, Income income, String stateOfResidence) {
        this.studentInfo = studentInfo;
        this.dependencyStatus = dependencyStatus;
        this.maritalStatus = maritalStatus;
        this.spouseInfo = spouseInfo;
        this.household = household;
        this.income = income;
        this.stateOfResidence = stateOfResidence;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public DependencyStatus getDependencyStatus() {
        return dependencyStatus;
    }

    public void setDependencyStatus(DependencyStatus dependencyStatus) {
        this.dependencyStatus = dependencyStatus;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public SpouseInfo getSpouseInfo() {
        return spouseInfo;
    }

    public void setSpouseInfo(SpouseInfo spouseInfo) {
        this.spouseInfo = spouseInfo;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }

    public String getStateOfResidence() {
        return stateOfResidence;
    }

    public void setStateOfResidence(String stateOfResidence) {
        this.stateOfResidence = stateOfResidence;
    }
}