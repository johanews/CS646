package com.group18.cs446.spacequest.game.objects.player.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.player.Weapon;

public class BasicLaser implements Weapon {

    private String name = "Basic Laser";
    private Bitmap bulletBitmap;
    private int fireRate = 20;
    private long lastShot;
    private GameEntity owner;
    private int baseBulletSpeed = 25;

    public BasicLaser(GameEntity owner, Context context){
        this.owner = owner;
        this.bulletBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_1);
    }

    @Override
    public void refresh(){
        lastShot = 0;
    }
    @Override
    public void fire(long gameTick) {
        int bulletSpeed = baseBulletSpeed + owner.getSpeed();
        if(gameTick > lastShot + fireRate || gameTick < lastShot){
            lastShot = gameTick;
            Point velocity = new Point((int)(bulletSpeed*(-Math.sin(owner.getAngle() * Math.PI / 180))),
                    (int)(bulletSpeed*(-Math.cos(owner.getAngle() * Math.PI / 180))));
            BasicProjectile projectile = new BasicProjectile(
                    new Point(owner.getCoordinates().x - bulletBitmap.getWidth()/2, owner.getCoordinates().y - bulletBitmap.getHeight()/2),
                    velocity,
                    bulletBitmap,
                    owner,
                    owner.getCurrentSector());
            owner.getCurrentSector().addEntityToBack(projectile);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
