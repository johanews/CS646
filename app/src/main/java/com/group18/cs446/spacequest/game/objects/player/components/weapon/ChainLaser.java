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

public class ChainLaser implements Weapon {
    private static final String NAME = "Chain Laser";
    private static final String DESCRIPTION = "Chain Laser Description";
    private static final int PRICE = 150;

    private static Bitmap bulletBitmap;
    private static Bitmap image;
    private Paint paint;
    private int fireRate = 3;
    private int shotCapacity = 6;
    private int ticksPerReload = 20;
    private int shots;
    private long lastShot;
    private long lastReload;
    private GameEntity owner;
    private int baseBulletSpeed = 25;
    private Context context;

    public ChainLaser(Context context){
        this.context = context;
        if(bulletBitmap == null) bulletBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_1);
        this.lastReload = 0;
        this.shots = shotCapacity;
        if(image == null) {
            image= BitmapFactory.decodeResource(context.getResources(), getImageID());
            image = Bitmap.createScaledBitmap(image, image.getWidth()/2, image.getHeight()/2, false);
        }
        paint = new Paint();
    }

    @Override
    public void refresh(){
        lastShot = 0;
        shots = shotCapacity;
        lastReload = 0;
    }
    @Override
    public void fire(long gameTick) {
        int bulletSpeed = baseBulletSpeed + owner.getSpeed();
        long timeSinceLastReload = gameTick - lastReload;
        shots += timeSinceLastReload/ticksPerReload;
        lastReload = gameTick - (timeSinceLastReload%ticksPerReload);
        if(shots > shotCapacity) shots = shotCapacity;
        if(shots > 0 && gameTick > lastShot + fireRate){
            SoundManager.playSound(SoundManager.FIRE_WEAPON, context);
            lastShot = gameTick;
            Point velocity = new Point((int)(bulletSpeed*(-Math.sin((owner.getAngle()) * Math.PI / 180))),
                    (int)(bulletSpeed*(-Math.cos((owner.getAngle()) * Math.PI / 180))));
            Point projectileStart = new Point((owner.getCoordinates().x - bulletBitmap.getWidth()/2)-(int)(Math.sin(owner.getAngle() * Math.PI / 180) * getBitmap().getHeight()),
                    (owner.getCoordinates().y - bulletBitmap.getHeight()/2)-(int)(Math.cos(owner.getAngle() * Math.PI / 180) * getBitmap().getHeight()));
            BasicProjectile projectile = new BasicProjectile(
                    projectileStart,
                    velocity,
                    bulletBitmap,
                    owner,
                    owner.getCurrentSector());
            owner.getCurrentSector().addEntityFront(projectile);
            shots--;
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
        return Weapons.CHAIN_LASER;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }

    @Override
    public int getImageID() {
        return R.drawable.item_weapon_chainlaser;
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
