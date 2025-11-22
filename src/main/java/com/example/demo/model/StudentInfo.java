package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfo {
    private String firstName;
    private String lastName;
    private String ssn;
    private LocalDate dateOfBirth;
}



