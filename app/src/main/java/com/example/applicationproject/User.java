package com.example.applicationproject;

import android.content.Intent;
import android.util.Log;

/**
 * Used to store the data that we need for each user as he or she signs up for the app.
 * The data will eventually be stored in FireStore in its collections.
 */
public class User {
    // member data
    private String name;
    private Integer age;
    private Integer hoursCount;
    private Integer job;
    private String phoneNumber;

    /**
     * Default constructor will create a user with the lowest permissions and hours set to 0
     */
    User(){
        name = "N/A";
        age = 0;
        hoursCount = 0;
        phoneNumber = "(111)111-1111";
        job = 1; // 0 is the lowest permissions ie. volunteers
        hoursCount = 0; // account should initialize with 0 hours worked
    }



    /**
     * Non-Default constructor takes in the data from a new account that is created.
     * @param n
     * @param a
     * @param h
     * @param p
     */
    User(String n, Integer a, Integer h, String p, Integer pm){
        name = n;
        age = a;
        hoursCount = h;
        phoneNumber = p;
        Log.d("User","Thisis what pm is: " + pm);
        job = pm;
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

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * These are checkers to verify if a user has permission to access an item or see UI
     * @return true if the user has the correct permission
     */
    boolean canMessage(){
        return job > 1;
    }

    boolean canEditVolunteers(){
        return job > 1;
    }

    boolean canEditInterns(){
        return job > 2;
    }
}
