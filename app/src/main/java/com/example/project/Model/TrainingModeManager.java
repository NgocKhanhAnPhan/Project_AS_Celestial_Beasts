package com.example.project.Model;

import java.util.ArrayList;
import java.util.List;

public class TrainingModeManager {

    private static TrainingModeManager instance;
    private List<String> trainingModes;
    private String selectedMode;

    // Private constructor
    private TrainingModeManager() {
        trainingModes = new ArrayList<>();
        trainingModes.add("+1 Experince");
        trainingModes.add("+2 Exp");
        trainingModes.add("+3 Exp");
    }

    // Get instance of TrainingModeManager
    public static synchronized TrainingModeManager getInstance() {
        if (instance == null) {
            instance = new TrainingModeManager();
        }
        return instance;
    }

    // get the list of mode
    public List<String> getTrainingModes() {
        return trainingModes;
    }

    // select the mode
    public String getSelectedMode() {
        return selectedMode;
    }

    // Update the mode selected
    public void setSelectedMode(String mode) {
        this.selectedMode = mode;
    }
}

