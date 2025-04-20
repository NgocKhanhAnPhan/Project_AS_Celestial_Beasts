package com.example.project.Model;

import android.content.Context;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeastStorage {

    private static BeastStorage instance;

    private HashMap<Integer, CreateBeast> createdBeastMap = new HashMap<>();

    private HashMap<Integer, CreateBeast> trainingMap = new HashMap<>();

    private HashMap<Integer, CreateBeast> battleMap = new HashMap<>();

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
        battleMap.remove(id);
    }

    //move battle
    public void moveToBattle(int id){
        CreateBeast beast = createdBeastMap.get(id);
        if (beast != null){
            beast.setLocation(Location.BATTLE);
        }
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

    //get beast from battle
    public Map<Integer, CreateBeast> getBattleBeasts() {
        return battleMap;
    }

    //move to home
    public void moveToHome(int id) {
        CreateBeast beast = createdBeastMap.get(id);
        if (beast != null) {
            beast.setLocation(Location.HOME);
            trainingMap.remove(id);
            battleMap.remove(id);
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

    public void updateBeast(CreateBeast createBeast) {
        // Update Beast in Hash Map by Id  is key.
        if (createdBeastMap.containsKey(createBeast.getBeast().getId())) {
            createdBeastMap.put(createBeast.getBeast().getId(), createBeast);
        } else {
            // Add if none
            createdBeastMap.put(createBeast.getBeast().getId(), createBeast);
        }
    }

    public void saveData(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("beasts.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(createdBeastMap);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(Context context) {
        try {
            FileInputStream fis = context.openFileInput("beasts.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            createdBeastMap = (HashMap<Integer, CreateBeast>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
