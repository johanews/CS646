package com.group18.cs446.spacequest.game.objects.ship.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.objects.Player;
import com.group18.cs446.spacequest.game.objects.ship.Weapon;

public class ChainLaser implements Weapon {
    private Bitmap bulletBitmap;
    private int fireRate = 3;
    private int shotCapacity = 6;
    private int ticksPerReload = 20;
    private int shots;
    private long lastShot;
    private long lastReload;
    private Player owner;
    private int bulletSpeed = 30;

    public ChainLaser(Player player, Context context){
        this.owner = player;
        this.bulletBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_1);
        this.lastReload = 0;
        this.shots = shotCapacity;
    }

    @Override
    public void fire(long gameTick) {
        long timeSinceLastReload = gameTick - lastReload;
        shots += timeSinceLastReload/ticksPerReload;
        lastReload = gameTick - (timeSinceLastReload%ticksPerReload);
        if(shots > shotCapacity) shots = shotCapacity;
        if(shots > 0 && gameTick > lastShot + fireRate){
            lastShot = gameTick;
            Point velocity = new Point((int)(bulletSpeed*(-Math.sin((owner.getAngle()) * Math.PI / 180))),
                    (int)(bulletSpeed*(-Math.cos((owner.getAngle()) * Math.PI / 180))));
            BasicProjectile projectile = new BasicProjectile(
                    new Point(owner.getCoordinates().x - bulletBitmap.getWidth()/2, owner.getCoordinates().y - bulletBitmap.getHeight()/2),
                    velocity,
                    bulletBitmap,
                    owner,
                    owner.getCurrentSector());
            owner.getCurrentSector().addEntityToBack(projectile);
            shots--;
        }
    }
}
