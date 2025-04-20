package com.example.project.Model;

import java.io.Serializable;

public class CreateBeast implements Serializable {
    private static final long serialVersionUID = 1L;
    private String customName;
    private Beast beast;


    public CreateBeast (String customName, Beast beast){
        this.customName = customName;
        this.beast = beast;
    }

    private Location location = Location.HOME;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public void setBeast(Beast beast) {
        this.beast = beast;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Beast getBeast() {
        return beast;
    }

    //Add value for index
    public void addExp(int value) {
        if (beast != null){
            beast.addExp(value);
        }
    }

    // full HP
    // restore the HP
    public void restoreHP(){
        beast.restoreHP();
    }

    // beast user controll
    private boolean isPlayerControlled;

    public boolean isPlayerControlled() {
        return isPlayerControlled;
    }

    public void setPlayerControlled(boolean isPlayerControlled) {
        this.isPlayerControlled = isPlayerControlled;
    }
}
