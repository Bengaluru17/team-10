package com.example.chiku.reachinghands;

public class InventorySingle {
    String name;
    int current_count,threshold_count;
    InventorySingle(String name, int current_count){
        this.name=name;
        this.current_count=current_count;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrent_count() {
        return current_count;
    }

    public void setCurrent_count(int current_count) {
        this.current_count = current_count;
    }

    public int getThreshold_count() {
        return threshold_count;
    }

    public void setThreshold_count(int threshold_count) {
        this.threshold_count = threshold_count;
    }
}
