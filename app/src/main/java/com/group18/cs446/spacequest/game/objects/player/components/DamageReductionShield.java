package com.group18.cs446.spacequest.game.objects.player.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.collision.Damage;
import com.group18.cs446.spacequest.game.enums.Shields;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.player.Shield;
import com.group18.cs446.spacequest.game.vfx.CanvasComponent;
import com.group18.cs446.spacequest.game.vfx.DamageFilter;

public class DamageReductionShield implements Shield {
    private static final String NAME = "Damage Reduction Shield";
    private static final String DESCRIPTION = "Doesn't block damage, reduces damage taken";
    private static final int PRICE = 80;

    private int maxShield, currentShield;
    private boolean tookDamageThisTick;
    private long mostRecentDamage;
    private GameEntity owner;
    private static Bitmap image;
    public DamageReductionShield(Context context){
        this.maxShield = 10;
        this.currentShield = maxShield;
        if(image == null) image = BitmapFactory.decodeResource(context.getResources(), getImageID()); // TODO
        tookDamageThisTick = false;
        mostRecentDamage = 0;
    }

    @Override
    public void update(long gameTick) {
        if(tookDamageThisTick){
            tookDamageThisTick = false;
            mostRecentDamage = gameTick;
        } else if (currentShield < maxShield){
            if(gameTick > mostRecentDamage + 200 && gameTick%5 == 0){
                currentShield++;
                if(currentShield > maxShield) currentShield = maxShield;
            }
        }
    }

    @Override
    public void refresh() {
        this.currentShield = maxShield;
        tookDamageThisTick = false;
        mostRecentDamage = 0;
    }

    @Override
    public Damage takeDamage(Damage damage) {
        if(currentShield == 0){
            return damage;
        }
        int newDamage = damage.getAmount()/(currentShield+1);
        currentShield--;
        tookDamageThisTick = true;
        return new Damage(damage.getType(), newDamage);
    }

    @Override
    public int getCurrentShield() {
        return currentShield;
    }

    @Override
    public int getMaxShield() {
        return maxShield;
    }

    @Override
    public void paint(CanvasComponent canvas, Paint paint, Point topLeftCorner) {
        int shieldXRadi = owner.getBitmap().getWidth() / 2 + 40;
        int shieldYRadi = owner.getBitmap().getHeight() / 2 + 40;
        paint.setColor(Color.argb(50, 10, 240, 50));
        if(currentShield > 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawOval(owner.getCoordinates().x - topLeftCorner.x - shieldXRadi,
                        owner.getCoordinates().y - topLeftCorner.y - shieldYRadi,
                        owner.getCoordinates().x - topLeftCorner.x + shieldXRadi,
                        owner.getCoordinates().y - topLeftCorner.y +shieldYRadi,
                        paint);
            } else {
                canvas.drawCircle(owner.getCoordinates().x,
                        owner.getCoordinates().y,
                        Math.max(shieldXRadi, shieldYRadi),
                        paint);

            }
            if(currentShield == maxShield){
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.WHITE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    canvas.drawOval(owner.getCoordinates().x - topLeftCorner.x - shieldXRadi,
                            owner.getCoordinates().y - topLeftCorner.y - shieldYRadi,
                            owner.getCoordinates().x - topLeftCorner.x + shieldXRadi,
                            owner.getCoordinates().y - topLeftCorner.y +shieldYRadi,
                            paint);
                } else {
                    canvas.drawCircle(owner.getCoordinates().x,
                            owner.getCoordinates().y,
                            Math.max(shieldXRadi, shieldYRadi),
                            paint);

                }
            }
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
    public Shields ID() {
        return Shields.BASIC_SHIELD;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }

    @Override
    public int getImageID() {
        return R.drawable.item_basic_shield_image;
    }
}
