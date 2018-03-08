package com.group18.cs446.spacequest.game.objects.player.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.player.Weapon;

public class DualLaser implements Weapon {

    private String name = "Dual Laser";
    private Bitmap bulletBitmap;
    private int fireRate = 30;
    private long lastShot;
    private GameEntity owner;
    private int baseBulletSpeed = 20;

    public DualLaser(GameEntity owner, Context context){
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
        if(gameTick > lastShot + fireRate){
            lastShot = gameTick;
            Point velocity = new Point((int)(bulletSpeed*(-Math.sin((owner.getAngle()-5) * Math.PI / 180))),
                    (int)(bulletSpeed*(-Math.cos((owner.getAngle()-5) * Math.PI / 180))));
            BasicProjectile projectile = new BasicProjectile(
                    new Point(owner.getCoordinates().x - bulletBitmap.getWidth()/2, owner.getCoordinates().y - bulletBitmap.getHeight()/2),
                    velocity,
                    bulletBitmap,
                    owner,
                    owner.getCurrentSector());
            Point velocity2 = new Point((int)(bulletSpeed*(-Math.sin((owner.getAngle()+5) * Math.PI / 180))),
                    (int)(bulletSpeed*(-Math.cos((owner.getAngle()+5) * Math.PI / 180))));
            BasicProjectile projectile2 = new BasicProjectile(
                    new Point(owner.getCoordinates().x - bulletBitmap.getWidth()/2, owner.getCoordinates().y - bulletBitmap.getHeight()/2),
                    velocity2,
                    bulletBitmap,
                    owner,
                    owner.getCurrentSector());
            owner.getCurrentSector().addEntityToBack(projectile);
            owner.getCurrentSector().addEntityToBack(projectile2);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
