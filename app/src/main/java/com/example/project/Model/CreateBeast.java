package com.example.project.Model;

public class CreateBeast {
    String customName;
    Beast beast;
    int trainingCount;

    public CreateBeast (String customName, Beast beast){
        this.customName = customName;
        this.beast = beast;
        this.trainingCount = 0;
    }

    private Location location = Location.HOME;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getTrainingCount() {
        return trainingCount;
    }

    public void setTrainingCount(int trainingCount) {
        this.trainingCount = trainingCount;
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

    public void addExp(int value) {
        if (beast != null){
            beast.addExp(value);
        }
    }
}
