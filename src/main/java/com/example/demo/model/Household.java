package com.example.demo.model;


public class Household {
    private Integer numberInHousehold;
    private Integer numberInCollege;

    public Household() {
    }

    public Household(Integer numberInHousehold, Integer numberInCollege) {
        this.numberInHousehold = numberInHousehold;
        this.numberInCollege = numberInCollege;
    }

    public Integer getNumberInHousehold() {
        return numberInHousehold;
    }

    public void setNumberInHousehold(Integer numberInHousehold) {
        this.numberInHousehold = numberInHousehold;
    }

    public Integer getNumberInCollege() {
        return numberInCollege;
    }

    public void setNumberInCollege(Integer numberInCollege) {
        this.numberInCollege = numberInCollege;
    }
}