package com.group18.cs446.spacequest.game.objects.player;

import java.io.Serializable;

public class PlayerInfo implements Serializable {

    private Weapon weapon;
    private Engine engine;
    private Shield shiled;
    private Hull hull;

    private int currentSector;

    public PlayerInfo() {

    }

}
