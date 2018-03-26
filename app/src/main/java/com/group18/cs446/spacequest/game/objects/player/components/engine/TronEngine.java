package com.group18.cs446.spacequest.game.objects.player.components.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.components.projectile.TrailProjectile;

public class TronEngine implements Engine {
    private static final String NAME = "Tron Engine";
    private static final String DESCRIPTION = "Leave a laser trail";
    private static final int PRICE = 120;

    private GameEntity owner;
    private static Bitmap image;
    private static Bitmap trailImage;
    private int max = 20;
    private int speed = max;
    private int turnSpeed = 9;
    private int fireMaxTime = 20;
    private int fire = 0;


    public TronEngine(Context context){
        if(image == null) image = BitmapFactory.decodeResource(context.getResources(), getImageID());
        if(trailImage == null) trailImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_trail);
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
        if(fire > 0){
            //Point backPoint = new Point((owner.getCoordinates().x)+(int)(owner.getBitmap().getHeight()/2*Math.sin(owner.getAngle()*Math.PI/180)),
            //        (owner.getCoordinates().y)+(int)(owner.getBitmap().getHeight()/2*Math.cos(owner.getAngle()*Math.PI/180)));

            GameEntity projectile = new TrailProjectile(new Point(owner.getCoordinates()), trailImage, owner, owner.getCurrentSector());
            owner.getCurrentSector().addEntityToBack(projectile);
            fire--;
        }

    }

    @Override
    public void doSpecial(long gameTick) {
        fire = fireMaxTime;
    }

    @Override
    public void refresh(){
        fire = 0;
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
        return Engines.TRON_ENGINE;
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
