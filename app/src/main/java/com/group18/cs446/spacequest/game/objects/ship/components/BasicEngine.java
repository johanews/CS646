package com.group18.cs446.spacequest.game.objects.ship.components;

import com.group18.cs446.spacequest.game.objects.ship.Engine;

public class BasicEngine implements Engine {
    private int speed = 17;
    private int maxSpeed = 20;
    private int minSpeed = 17;
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
        if(speed > minSpeed && gameTick%10 == 0){
            speed--;
        }
    }

    @Override
    public void doSpecial(long gameTick) {
        if(speed < maxSpeed){
            speed++;
        }
    }

    @Override
    public void refresh(){
        speed = minSpeed;
    }
}
