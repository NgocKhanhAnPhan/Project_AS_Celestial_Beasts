package com.example.project.Model;

import java.io.Serializable;
import java.util.Random;

public class Beast implements Serializable {
    private static final long serialVersionUID = 1L;
     String ele;
     String chara;
     private int imageResource;
    private int attack, defe, experience, heal, maxHeal;
    private int id;


    public Beast(String ele, String chara, int defe, int attack, int heal, int maxHeal, int imageResource){
        this.ele = ele;
        this.chara= chara;
        this.defe = defe;
        this.attack = attack;
        this.heal = heal;
        this.maxHeal = maxHeal;
        this.imageResource = imageResource;
        this.experience = 0 ;
    }
    //ID getter and setter
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
//element type
    public void setEle(String ele) {
        this.ele = ele;
    }

    public String getEle() {
        return ele;
    }
    //Character type
    public void setChara(String chara) {
        this.chara = chara;
    }
    public String getChara() {
        return chara;
    }

//Defense type
    public void setDefe(int defe) {
        this.defe = defe;
    }

    public int getDefe() {
        return defe;
    }
    //attack type
    public int getAttack() {return attack;}   public void setAttack(int attack) {this.attack = attack;}




//health and maxhealth
    public int getHeal() {
        return heal;
    }

    public int getMaxHeal() {
        return maxHeal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public void setMaxHeal(int maxHeal) {
        this.maxHeal = maxHeal;
    }
//image
    public int getImageResource() {return imageResource;}  public void setImageResource(String imageResource) {this.imageResource = Integer.parseInt(imageResource);}
//experience
    public void setExperience(int experience) {this.experience = experience;} public int getExperience() {return experience;}

    // Plus the experience when training:
    public void addExp(int amount) {
        this.experience += amount;
    }

     // restore the HP
      public void restoreHP(){
        heal = maxHeal;
      }

    public void heal(int amount) {
        // Heal the beast, but not exceeding max health
        this.heal = Math.min(maxHeal, this.heal + amount);
    }

      //battle
      public int attack() {
          int baseDamage = this.attack;
          int randomFactor = new Random().nextInt(5); // bonus random (0-4)
          return baseDamage + randomFactor;
      }

    public void defense(int damage) {
        int actualDamage = Math.max(damage - this.defe, 1); // tối thiểu 1
        this.heal -= actualDamage;
        if (this.heal < 0) this.heal = 0;
    }

    public boolean isAlive() {
        return this.heal > 0;
    }


    public void addExperience(int exp) {
        this.experience += exp;
    }

    //add lost
    private int losses = 0;

    public void addLoss() {
        losses++;
    }

    public int getLosses() {
        return losses;
    }

    //add win
    private int wins = 0;

    public void addWin() {
        wins++;
    }

    public int getWins() {
        return wins;
    }

    // add match and train
    private int matchCount = 0;
    private int trainCount = 0;

    public int getMatchCount() {
        return matchCount;
    }

    public void addMatchPlayed() {
        matchCount++;
    }

    public int getTrainCount() {
        return trainCount;
    }

    public void addTrainCount() {
        trainCount++;
    }
}
