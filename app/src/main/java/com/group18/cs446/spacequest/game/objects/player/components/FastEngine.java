package com.group18.cs446.spacequest.game.objects.player.components;

import android.content.Context;
import android.graphics.Bitmap;

import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;
import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.Player;

public class FastEngine implements Engine {
    private static final String NAME = "Fast Engine";
    private static final String DESCRIPTION = "Fast Engine Description";
    private static final int PRICE = 200;

    private GameEntity owner;
    private Bitmap image;
    private int maxSpeed = 40;
    private int minSpeed = 17;
    private int speed = minSpeed;
    private int maxTurnSpeed = 8;
    private int minTurnSpeed = 3;
    private int turnSpeed = maxTurnSpeed;

    public FastEngine(Context context){}

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
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Bitmap getBitmap() {
        return this.image;
    }

    @Override
    public void registerOwner(GameEntity e) {
        this.owner = e;
    }

    @Override
    public int ID() {
        return ComponentFactory.FAST_ENGINE;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }
}
