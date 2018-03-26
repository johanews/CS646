package com.group18.cs446.spacequest.game.objects.player.components.weapon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.enums.Weapons;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.player.Weapon;
import com.group18.cs446.spacequest.game.objects.player.components.projectile.BasicProjectile;
import com.group18.cs446.spacequest.game.vfx.CanvasComponent;
import com.group18.cs446.spacequest.io.SoundManager;

public class DualLaser implements Weapon {
    private static final String NAME = "Dual Laser";
    private static final String DESCRIPTION = "Twice the lasers";
    private static final int PRICE = 125;

    private static Bitmap bulletBitmap;
    private static Bitmap image;
    private Paint paint;
    private int fireRate = 30;
    private long lastShot;
    private GameEntity owner;
    private int baseBulletSpeed = 20;
    private Context context;

    public DualLaser(Context context){
        this.context = context;
        if(bulletBitmap == null) bulletBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_1);
        if(image == null) {
            image= BitmapFactory.decodeResource(context.getResources(), getImageID());
            image = Bitmap.createScaledBitmap(image, image.getWidth()/2, image.getHeight()/2, true);
        }
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
            Point projectileStart = new Point((owner.getCoordinates().x - bulletBitmap.getWidth()/2)-(int)(Math.sin(owner.getAngle() * Math.PI / 180) * getBitmap().getHeight()),
                    (owner.getCoordinates().y - bulletBitmap.getHeight()/2)-(int)(Math.cos(owner.getAngle() * Math.PI / 180) * getBitmap().getHeight()));
            BasicProjectile projectile = new BasicProjectile(
                    projectileStart,
                    velocity,
                    bulletBitmap,
                    owner,
                    owner.getCurrentSector());
            Point velocity2 = new Point((int)(bulletSpeed*(-Math.sin((owner.getAngle()+5) * Math.PI / 180))),
                    (int)(bulletSpeed*(-Math.cos((owner.getAngle()+5) * Math.PI / 180))));
            Point projectileStart2 = new Point((owner.getCoordinates().x - bulletBitmap.getWidth()/2)-(int)(Math.sin(owner.getAngle() * Math.PI / 180) * getBitmap().getHeight()),
                    (owner.getCoordinates().y - bulletBitmap.getHeight()/2)-(int)(Math.cos(owner.getAngle() * Math.PI / 180) * getBitmap().getHeight()));
            BasicProjectile projectile2 = new BasicProjectile(
                    projectileStart2,
                    velocity2,
                    bulletBitmap,
                    owner,
                    owner.getCurrentSector());
            SoundManager.playSound(SoundManager.FIRE_WEAPON, context);
            owner.getCurrentSector().addEntityFront(projectile);
            SoundManager.playSound(SoundManager.FIRE_WEAPON, context);
            owner.getCurrentSector().addEntityFront(projectile2);
        }
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
    public Weapons ID() {
        return Weapons.DUAL_LASER;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }

    @Override
    public int getImageID() {
        return R.drawable.item_weapon_duallaser;
    }

    @Override
    public void paint(CanvasComponent canvas, Point topLeftCorner){
        canvas.drawBitmap(
                getBitmap(),
                owner.getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2,
                owner.getCoordinates().y - topLeftCorner.y - getBitmap().getHeight() / 2,
                paint);
    }
}
