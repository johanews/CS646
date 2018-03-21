package com.group18.cs446.spacequest.game.objects.player.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.collision.Damage;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;
import com.group18.cs446.spacequest.game.objects.player.Player;
import com.group18.cs446.spacequest.game.objects.player.Shield;
import com.group18.cs446.spacequest.game.vfx.DamageFilter;

public class BasicShield implements Shield {
    private static final String NAME = "Basic Shield";
    private static final String DESCRIPTION = "Basic Shield Description";
    private static final int PRICE = 30;

    private int maxShield, currentShield;
    private int regenAmount;
    private int regenCooldown; // time to regen from last damage
    private long lastDamageTick;
    private boolean tookDamageThisTick;
    private GameEntity owner;
    private Bitmap image;

    public BasicShield(Context context){
        this.maxShield = 500;
        this.currentShield = maxShield;
        this.regenAmount = 1; // amount to increase
        this.regenCooldown = 150;
        this.lastDamageTick = 0;
        this.tookDamageThisTick = false;
        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_basic_shield_image);
    }

    @Override
    public void update(long gameTick) {
        if(currentShield <= 0) return;
        if(tookDamageThisTick){
            tookDamageThisTick = false;
            lastDamageTick = gameTick;
            owner.getCurrentSector().addFilter(new DamageFilter(owner.getCurrentSector()));
        } else if (lastDamageTick + regenCooldown < gameTick && gameTick % 5 == 0){
            if(currentShield + regenAmount >= maxShield){
                currentShield = maxShield;
            } else {
                currentShield += regenAmount;
            }
        }
    }

    @Override
    public void refresh() {
        this.currentShield = maxShield;
        this.lastDamageTick = 0;
        this.tookDamageThisTick = false;
    }

    @Override
    public boolean takeDamage(Damage damage) {
        if(currentShield == 0){
            return false;
        }
        int damageAmount = damage.getAmount();
        switch (damage.getType()){
            case LASER:
                damageAmount *= 0.8;
                break;
            case PHYSICAL:
                damageAmount *= 1.2;
                break;
            default:
                break;
        }
        if(currentShield - damageAmount <= 0){
            currentShield = 0;
        } else {
            currentShield -= damageAmount;
        }
        tookDamageThisTick = true;
        return true;
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
    public void paint(Canvas canvas, Paint paint, Point topLeftCorner) {
        int shieldXRadi = owner.getBitmap().getWidth() / 2 + 40;
        int shieldYRadi = owner.getBitmap().getHeight() / 2 + 40;
        paint.setColor(Color.argb(50, 10, 100, 255));
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
    public int ID() {
        return ComponentFactory.BASIC_SHIELD;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }
}
