package com.group18.cs446.spacequest.game.objects.player.components;

import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.Player;

public class FastEngine implements Engine {
    private Player owner;
    private int maxSpeed = 40;
    private int minSpeed = 17;
    private int speed = minSpeed;
    private int maxTurnSpeed = 8;
    private int minTurnSpeed = 3;
    private int turnSpeed = maxTurnSpeed;

    public FastEngine(Player owner){
        this.owner = owner;
    }
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

        // Add smoke effect
        SmokeParticle basicSmokeParticle = new SmokeParticle(owner.getCurrentSector(),
                owner.getCoordinates().x+(int)(20*Math.sin(owner.getAngle()*Math.PI/180)),
                owner.getCoordinates().y+(int)(20*Math.cos(owner.getAngle()*Math.PI/180)), 70);
        owner.getCurrentSector().addEntityToBack(basicSmokeParticle);
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
