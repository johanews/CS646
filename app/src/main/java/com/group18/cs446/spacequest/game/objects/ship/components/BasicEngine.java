package com.group18.cs446.spacequest.game.objects.ship.components;

import com.group18.cs446.spacequest.game.objects.ship.Engine;

public class BasicEngine implements Engine {
    private int speed = 18;
    private int turnSpeed = 7;
    @Override
    public int getTurnSpeed() {
        return turnSpeed;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void update(long gameTick) {

    }
    @Override
    public void refresh(){

    }
}
