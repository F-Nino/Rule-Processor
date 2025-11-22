package com.example.demo.model;


import java.time.LocalDate;


public class StudentInfo {
    private String firstName;
    private String lastName;
    private String ssn;
    private LocalDate dateOfBirth;

    public StudentInfo() {
    }

    public StudentInfo(String firstName, String lastName, String ssn, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}



