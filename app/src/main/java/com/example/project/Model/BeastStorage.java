package com.example.project.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeastStorage {

    private static BeastStorage instance;

    private HashMap<Integer, CreateBeast> createdBeastMap = new HashMap<>();

    private HashMap<Integer, CreateBeast> trainingMap = new HashMap<>();

    private int nextId = 1;

    private BeastStorage() {}
    //singeton instance
    public static BeastStorage getInstance() {
        if (instance == null) {
            instance = new BeastStorage();
        }
        return instance;
    }
    //Add new Beast
    public void addBeast(CreateBeast beast) {
        beast.getBeast().setId(nextId);
        createdBeastMap.put(nextId, beast);
        nextId++;
    }

    //Getallbeast:
    public Map<Integer, CreateBeast> getAllBeasts() {
        return createdBeastMap;
    }

    // Get beast by id:
    public CreateBeast getBeastById(int id) {
        return createdBeastMap.get(id);
    }

    //remove by id:
    public void removeBeast(int id) {
        createdBeastMap.remove(id);
        trainingMap.remove(id);
    }

    // move training:
    public void moveToTraining(int id) {
        CreateBeast beast = createdBeastMap.get(id);
        if (beast != null) {
            beast.setLocation(Location.TRAINING);
            trainingMap.put(id, beast);
        }
    }

    //get beast map in training
    public Map<Integer, CreateBeast> getTrainingBeasts() {
        return trainingMap;
    }

    //move to home
    public void moveToHome(int id) {
        CreateBeast beast = trainingMap.get(id);
        if (beast != null) {
            beast.setLocation(Location.HOME);
            trainingMap.remove(id);
        }
    }
//count in home
    public int countBeastsInHome() {
        int count = 0;
        for (CreateBeast beast : createdBeastMap.values()) {
            if (beast.getLocation() == Location.HOME) {
                count++;
            }
        }
        return count;
    }
// count in training
    public int countBeastsInTraining() {
        int count = 0;
        for (CreateBeast beast : createdBeastMap.values()) {
            if (beast.getLocation() == Location.TRAINING) {
                count++;
            }
        }
        return count;
    }
//count in battle
    public int countBeastsInBattle() {
        int count = 0;
        for (CreateBeast beast : createdBeastMap.values()) {
            if (beast.getLocation() == Location.BATTLE) {
                count++;
            }
        }
        return count;
    }
    // Selected beast in location
    public List<CreateBeast> getBeastsByLocation(Location location) {
        List<CreateBeast> list = new ArrayList<>();
        for (CreateBeast beast : createdBeastMap.values()) {
            if (beast.getLocation() == location) {
                list.add(beast);
            }
        }
        return list;
    }

    public void moveToConvert(int id) {
        CreateBeast beast = createdBeastMap.get(id);
        if (beast != null) {
            beast.setLocation(Location.CONVERT);

            int beastEXP = beast.getBeast().getExperience();
        }
    }








}
