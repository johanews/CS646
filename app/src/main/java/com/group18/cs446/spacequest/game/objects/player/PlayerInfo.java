package com.group18.cs446.spacequest.game.objects.player;

import java.io.Serializable;

public class PlayerInfo implements Serializable {

    private int weapon;
    private int engine;
    private int shield;
    private int hull;

    private int currentSector;
    private int money;

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getHull() {
        return hull;
    }

    public void setHull(int hull) {
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
