package com.example.applicationproject.Objects;

import java.util.ArrayList;

public class Date {


    ArrayList<String> shifts = new ArrayList<>();

    public Date(){
    }

    public Date(String newSlot){
        shifts.add(newSlot);
    }

    public Date(ArrayList<String> shifts) {
        this.shifts = shifts;
    }

    public ArrayList<String> getShifts() { return shifts; }
}
