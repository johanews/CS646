package com.group18.cs446.spacequest.game.objects.player.components;

import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.Player;

public class BasicEngine implements Engine {
    private Player owner;
    private int speed = 17;
    private int maxSpeed = 20;
    private int minSpeed = 17;
    private int turnSpeed = 7;

    public BasicEngine(Player owner){
        this.maxSpeed = 20;
        this.minSpeed = 17;
        this.speed = minSpeed;
        this.turnSpeed = 7;
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
        if(speed > minSpeed && gameTick%10 == 0){
            speed--;
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
    }

    @Override
    public void refresh(){
        speed = minSpeed;
    }
}
