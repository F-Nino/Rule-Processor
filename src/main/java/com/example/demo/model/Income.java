package com.example.demo.model;


public class Income {
    private Integer studentIncome;
    private Integer parentIncome;

    public Income() {
    }

    public Income(Integer studentIncome, Integer parentIncome) {
        this.studentIncome = studentIncome;
        this.parentIncome = parentIncome;
    }

    public Integer getStudentIncome() {
        return studentIncome;
    }

    public void setStudentIncome(Integer studentIncome) {
        this.studentIncome = studentIncome;
    }

    public Integer getParentIncome() {
        return parentIncome;
    }

    public void setParentIncome(Integer parentIncome) {
        this.parentIncome = parentIncome;
    }
}