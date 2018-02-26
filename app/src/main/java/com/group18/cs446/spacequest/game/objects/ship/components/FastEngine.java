package com.group18.cs446.spacequest.game.objects.ship.components;

import com.group18.cs446.spacequest.game.objects.ship.Engine;

public class FastEngine implements Engine {
    private int speed = 25;
    private int maxSpeed = 40;
    private int minSpeed = 20;
    private int maxTurnSpeed = 10;
    private int minTurnSpeed = 3;
    private int turnSpeed = maxTurnSpeed;
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
        if(speed > minSpeed && gameTick%3 == 0){
            speed--;
        }
        if(turnSpeed < maxTurnSpeed && gameTick%3 == 0){
            turnSpeed++;
        }
    }

    @Override
    public void doSpecial(long gameTick) {
        if(speed < maxSpeed){
            speed++;
        }
        if(turnSpeed > minTurnSpeed){
            turnSpeed--;
        }
    }

    @Override
    public void refresh(){
        speed = minSpeed;
    }
}
