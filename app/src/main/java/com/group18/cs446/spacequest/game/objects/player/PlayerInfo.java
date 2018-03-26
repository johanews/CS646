package com.group18.cs446.spacequest.game.objects.player;

import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.enums.Hulls;
import com.group18.cs446.spacequest.game.enums.Shields;
import com.group18.cs446.spacequest.game.enums.Weapons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerInfo implements Serializable {

    private Weapons weapon;
    private Engines engine;
    private Shields shield;
    private Hulls hull;

    private int currentSector;
    private int money;

    private List<Weapons> ownedWeapons;
    private List<Engines> ownedEngines;
    private List<Shields> ownedShields;
    private List<Hulls> ownedHulls;

    public boolean ownsWeapon(Weapons weapon){
        return ownedWeapons.contains(weapon);
    }
    public void addWeapon(Weapons weapon){
        this.ownedWeapons.add(weapon);
    }
    public void addEngine(Engines engine){
        this.ownedEngines.add(engine);
    }
    public boolean ownsEngine(Engines engine){
        return ownedEngines.contains(engine);
    }
    public void addShield(Shields shield){
        this.ownedShields.add(shield);
    }
    public boolean ownsShield(Shields shield){
        return ownedShields.contains(shield);
    }
    public void addHull(Hulls hull){
        this.ownedHulls.add(hull);
    }
    public boolean ownsHull(Hulls hull){
        return ownedHulls.contains(hull);
    }

    public Weapons getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapons weapon) {
        this.weapon = weapon;
    }

    public Engines getEngine() {
        return engine;
    }

    public void setEngine(Engines engine) {
        this.engine = engine;
    }

    public Shields getShield() {
        return shield;
    }

    public void setShield(Shields shield) {
        this.shield = shield;
    }

    public Hulls getHull() {
        return hull;
    }

    public void setHull(Hulls hull) {
        this.hull = hull;
    }

    public int getCurrentSector() {
        return currentSector;
    }

    public void setCurrentSector(int currentSector) {
        this.currentSector = currentSector;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public PlayerInfo() {
        this.ownedWeapons = new ArrayList<>();
        this.ownedShields = new ArrayList<>();
        this.ownedHulls = new ArrayList<>();
        this.ownedEngines = new ArrayList<>();
        this.engine = Engines.BASIC_ENGINE;
        this.weapon = Weapons.BASIC_LASER;
        this.hull = Hulls.BASIC_HULL;
        this.shield = Shields.BASIC_SHIELD;
        ownedEngines.add(engine);
        ownedHulls.add(hull);
        ownedShields.add(shield);
        ownedWeapons.add(weapon);
        currentSector = 1;
    }


    public PlayerInfo(PlayerInfo playerInfo){
        this.weapon = playerInfo.weapon;
        this.engine = playerInfo.engine;
        this.shield = playerInfo.shield;
        this.hull = playerInfo.hull;
        this.currentSector = playerInfo.currentSector;
        this.money = playerInfo.money;
        this.ownedEngines = playerInfo.ownedEngines;
        this.ownedHulls = playerInfo.ownedHulls;
        this.ownedShields = playerInfo.ownedShields;
        this.ownedWeapons = playerInfo.ownedWeapons;
    }

}
