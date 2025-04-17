package com.example.project.Model;

public class TrainingSelected {
    private String name;
    private String descrip;
    private boolean isSelected;

    public TrainingSelected(String name, String descrip){
        this.name = name;
        this.descrip = descrip;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getDescrip() {
        return descrip;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

