package com.group18.cs446.spacequest.game.objects.player;

import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.enums.Hulls;
import com.group18.cs446.spacequest.game.enums.Shields;
import com.group18.cs446.spacequest.game.enums.Weapons;

import java.io.Serializable;

public class PlayerInfo implements Serializable {

    private Weapons weapon;
    private Engines engine;
    private Shields shield;
    private Hulls hull;

    private int currentSector;
    private int money;

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
        currentSector = 1;
    }

    public PlayerInfo(PlayerInfo playerInfo){
        this.weapon = playerInfo.weapon;
        this.engine = playerInfo.engine;
        this.shield = playerInfo.shield;
        this.hull = playerInfo.hull;
        this.currentSector = playerInfo.currentSector;
        this.money = playerInfo.money;
    }

}
