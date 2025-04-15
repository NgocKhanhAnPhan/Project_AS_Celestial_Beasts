package com.example.project.Model;

public class CreateBeast {
    String customName;
    Beast beast;

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
}
