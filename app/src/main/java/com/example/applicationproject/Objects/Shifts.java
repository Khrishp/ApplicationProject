package com.example.applicationproject.Objects;

import java.util.ArrayList;

public class Shifts {


    ArrayList<String> shifts = new ArrayList<>();

    public Shifts(){
    }

    public Shifts(String newSlot){
        shifts.add(newSlot);
    }

    public Shifts(ArrayList<String> shifts) {
        this.shifts = shifts;
    }

    public ArrayList<String> getShifts() { return shifts; }
}
