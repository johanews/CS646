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

public class TripleLaser implements Weapon {
    private static final String NAME = "Triple Laser";
    private static final String DESCRIPTION = "Three lasers";
    private static final int PRICE = 250;

    private static Bitmap bulletBitmap;
    private static Bitmap image;
    private Paint paint;
    private int fireRate = 5;
    private int shot;
    private long lastShot;
    private GameEntity owner;
    private int baseBulletSpeed = 20;
    private Context context;

    public TripleLaser(Context context){
        this.context = context;
        if(bulletBitmap == null) bulletBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_1);
        if(image == null) {
            image= BitmapFactory.decodeResource(context.getResources(), getImageID());
            image = Bitmap.createScaledBitmap(image, image.getWidth()/3, image.getHeight()/3, false);
        }
        shot = 0;
    }

    @Override
    public void refresh(){
        lastShot = 0;
    }

    @Override
    public void fire(long gameTick) {
        int bulletSpeed = baseBulletSpeed + owner.getSpeed();
        if(gameTick > lastShot + fireRate){
            SoundManager.playSound(SoundManager.FIRE_WEAPON, context);
            lastShot = gameTick;
            shot = (shot + 1) % 3;
            int displacement = getBitmap().getWidth()/2 * (shot-1);
            Point velocity = new Point((int)(bulletSpeed*(-Math.sin(owner.getAngle() * Math.PI / 180))),
                    (int)(bulletSpeed*(-Math.cos(owner.getAngle() * Math.PI / 180))));
            Point projectileStart = new Point((owner.getCoordinates().x - bulletBitmap.getWidth()/2)-(int)(Math.sin(owner.getAngle() * Math.PI / 180) * getBitmap().getHeight()-(int)(Math.cos(owner.getAngle() * Math.PI / 180) * displacement)),
                    (owner.getCoordinates().y - bulletBitmap.getHeight()/2)-(int)(Math.cos(owner.getAngle() * Math.PI / 180) * getBitmap().getHeight())-(int)(Math.sin(owner.getAngle() * Math.PI / 180) * displacement));
            BasicProjectile projectile = new BasicProjectile(
                    projectileStart,
                    velocity,
                    bulletBitmap,
                    owner,
                    owner.getCurrentSector());
            owner.getCurrentSector().addEntityFront(projectile);
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
        return Weapons.TRIPLE_LASER;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }

    @Override
    public int getImageID() {
        return R.drawable.item_weapon_triplelaser;
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
