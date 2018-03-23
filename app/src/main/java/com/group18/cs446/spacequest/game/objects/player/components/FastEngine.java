package com.group18.cs446.spacequest.game.objects.player.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;
import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.Player;

public class FastEngine implements Engine {
    private static final String NAME = "Fast Engine";
    private static final String DESCRIPTION = "Very fast, great acceleration, hard to turn while accelerating";
    private static final int PRICE = 160;

    private GameEntity owner;
    private static Bitmap image;
    private int maxSpeed = 40;
    private int minSpeed = 17;
    private int speed = minSpeed;
    private int maxTurnSpeed = 8;
    private int minTurnSpeed = 3;
    private int turnSpeed = maxTurnSpeed;

    public FastEngine(Context context){
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
        if(speed > minSpeed && gameTick%3 == 0){
            speed--;
        }
        if(turnSpeed < maxTurnSpeed && gameTick%3 == 0){
            turnSpeed++;
        }

        // Add smoke effect
        SmokeParticle basicSmokeParticle = new SmokeParticle(owner.getCurrentSector(),
                owner.getCoordinates().x+(int)(20*Math.sin(owner.getAngle()*Math.PI/180)),
                owner.getCoordinates().y+(int)(20*Math.cos(owner.getAngle()*Math.PI/180)),30, Color.GRAY, 70);
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
    public Engines ID() {
        return Engines.FAST_ENGINE;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }

    @Override
    public int getImageID() {
        return R.drawable.item_engine_fast;
    }
}
