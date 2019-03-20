package com.example.applicationproject;

import android.content.Intent;

/**
 * Used to store the data that we need for each user as he or she signs up for the app.
 * The data will eventually be stored in FireStore in its collections.
 */
public class User {
    // member data
    private String name;
    private Integer age;
    private Integer hoursCount;
    private Integer permissions;
    private Double phoneNumber;
    //picture data type?

    /**
     * Default constructor will create a user with the lowest permissions and hours set to 0
     */
    User(){
        permissions = 0; // 0 is the lowest permissions ie. volunteers
        hoursCount = 0; // account should initialize with 0 hours worked
    }

    /**
     * Non-Default constructor takes in the data from a new account that is created.
     * @param n
     * @param a
     * @param h
     * @param p
     */
    User(String n, Integer a, Integer h, Double p){
        name = n;
        age = a;
        hoursCount = h;
        phoneNumber = p;
        permissions = 0;
    }

    /**
     * GETTERS / SETTERS
     * @return
     */
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

    /**
     * These are checkers to verify if a user has permission to access an item or see UI
     * @return true if the user has the correct permission
     */
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
