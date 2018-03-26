package com.group18.cs446.spacequest.game.objects.player.components.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.player.Engine;

public class BasicEngine implements Engine {
    private static final String NAME = "Basic Engine";
    private static final String DESCRIPTION = "Gets you there";
    private static final int PRICE = 30;

    private GameEntity owner;
    private static Bitmap image;
    private int speed = 17;
    private int maxSpeed = 24;
    private int minSpeed = 17;
    private int turnSpeed = 7;

    public BasicEngine(Context context){
        this.speed = minSpeed;
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
        return Engines.BASIC_ENGINE;
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
