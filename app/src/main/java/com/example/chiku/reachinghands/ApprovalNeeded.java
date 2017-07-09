package com.example.chiku.reachinghands;


public class ApprovalNeeded {
    String check, name;
    ApprovalNeeded(String name, String check){
        this.check = check;
        this.name = name;
    }

    public String getCheck() {
        return check;
    }

    public String getName() {
        return name;
    }
}
