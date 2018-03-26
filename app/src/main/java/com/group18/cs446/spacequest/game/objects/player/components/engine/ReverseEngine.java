package com.group18.cs446.spacequest.game.objects.player.components.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.player.Engine;

public class ReverseEngine implements Engine {
    private static final String NAME = "Reverse Engine";
    private static final String DESCRIPTION = "Whiplash machine";
    private static final int PRICE = 80;

    private GameEntity owner;
    private static Bitmap image;
    private int max = 20;
    private int speed = max;
    private int turnSpeed = 9;
    private boolean forwards = true;
    private boolean updated = false;
    private int maxSwitchTime = 5;
    private int switchTime = maxSwitchTime;


    public ReverseEngine(Context context){
        if(image == null) image = BitmapFactory.decodeResource(context.getResources(), getImageID());
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
        // Add smoke effect
        SmokeParticle basicSmokeParticle = new SmokeParticle(owner.getCurrentSector(),
                owner.getCoordinates().x+(int)(20*Math.sin(owner.getAngle()*Math.PI/180)),
                owner.getCoordinates().y+(int)(20*Math.cos(owner.getAngle()*Math.PI/180)), 70);
        owner.getCurrentSector().addEntityToBack(basicSmokeParticle);
        if(updated){
            updated = false;
            switchTime = maxSwitchTime;
        } else {
            switchTime--;
            if(switchTime < 0) {
                forwards = true;
            }
        }
        if(forwards){
            if(speed < max){
                speed+=5;
            }
        } else {
            if(speed > -max){
                speed-=5;
            }
        }

    }

    @Override
    public void doSpecial(long gameTick) {
        forwards = false;
        updated = true;
    }

    @Override
    public void refresh(){
        forwards = true;
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
    public Engines ID() {
        return Engines.REVERSE_ENGINE;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }

    @Override
    public int getImageID() {
        return R.drawable.item_engine_basic;
    }
}
