package com.group18.cs446.spacequest.game.objects.player;

import com.group18.cs446.spacequest.game.objects.player.components.BasicLaser;

import java.io.Serializable;

public class PlayerInfo implements Serializable {

    private Weapon weapon;
    private Engine engine;
    private Shield shiled;
    private Hull hull;

    private int currentSector;
    private int money;

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Shield getShiled() {
        return shiled;
    }

    public void setShiled(Shield shiled) {
        this.shiled = shiled;
    }

    public Hull getHull() {
        return hull;
    }

    public void setHull(Hull hull) {
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
        this.shiled = playerInfo.shiled;
        this.hull = playerInfo.hull;
        this.currentSector = playerInfo.currentSector;
        this.money = playerInfo.money;
    }

}
