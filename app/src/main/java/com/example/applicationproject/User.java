package com.example.applicationproject;

import android.content.Intent;

public class User {
    // member data
    private String name;
    private Integer age;
    private Integer hoursCount;
    private Integer permissions;
    private Double phoneNumber;
    //picture data type?

    User(){ // new account is created
        permissions = 0; // 0 is the lowest permissions ie. volunteers
        hoursCount = 0; // account should initialize with 0 hours worked
    }

    // getters/setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHoursCount() {
        return hoursCount;
    }

    public void setHoursCount(Integer hoursCount) {
        this.hoursCount = hoursCount;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }

    public Double getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Double phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    boolean canMessage(){
        return permissions > 0;
    }

    boolean canEditVolunteers(){
        return permissions > 0;
    }

    boolean canEditInterns(){
        return permissions > 1;
    }
}
