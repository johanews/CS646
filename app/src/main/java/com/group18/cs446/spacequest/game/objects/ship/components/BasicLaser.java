package com.group18.cs446.spacequest.game.objects.ship.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.Player;
import com.group18.cs446.spacequest.game.objects.Projectile;
import com.group18.cs446.spacequest.game.objects.ship.Weapon;

public class BasicLaser implements Weapon {
    private Bitmap bulletBitmap;
    private int fireRate = 20;
    private long lastShot;
    private Player owner;
    private int bulletSpeed = 30;

    public BasicLaser(Player player, Context context){
        this.owner = player;
        this.bulletBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_1);
    }

    @Override
    public void fire(long gameTick) {
        if(gameTick > lastShot + fireRate){
            lastShot = gameTick;
            Point velocity = new Point((int)(bulletSpeed*(-Math.sin(owner.getAngle() * Math.PI / 180))),
                    (int)(bulletSpeed*(-Math.cos(owner.getAngle() * Math.PI / 180))));
            Projectile projectile = new Projectile(
                    new Point(owner.getCoordinates().x - bulletBitmap.getWidth()/2, owner.getCoordinates().y - bulletBitmap.getHeight()/2),
                    velocity,
                    bulletBitmap,
                    owner,
                    owner.getCurrentSector());
            owner.getCurrentSector().addEntityFront(projectile);
        }
    }
}
