package com.example.reminder;

public class ReminderInfo {
    public String name;
    public boolean important;

    public ReminderInfo(String name, boolean important) {
        this.name = name;
        this.important = important;
    }

    public String getName(){
        return this.name;
    }


}
