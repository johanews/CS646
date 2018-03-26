package com.group18.cs446.spacequest.game.objects.player.components.shield;

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

public class BasicShield implements Shield {
    private static final String NAME = "Basic Shield";
    private static final String DESCRIPTION = "Protects your hull";
    private static final int PRICE = 30;

    private int maxShield, currentShield;
    private int regenAmount;
    private int regenCooldown; // time to regen from last damage
    private long lastDamageTick;
    private boolean tookDamageThisTick;
    private GameEntity owner;
    private static Bitmap image;

    public BasicShield(Context context){
        this.maxShield = 500;
        this.currentShield = maxShield;
        this.regenAmount = 1; // amount to increase
        this.regenCooldown = 150;
        this.lastDamageTick = 0;
        this.tookDamageThisTick = false;
        if(image == null) image = BitmapFactory.decodeResource(context.getResources(), getImageID());
    }

    @Override
    public void update(long gameTick) {
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
    public Damage takeDamage(Damage damage) {
        if(currentShield == 0){
            return damage;
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
            damageAmount = currentShield - damageAmount;
            currentShield = 0;
        } else {
            currentShield -= damageAmount;
            damageAmount = 0;
        }
        tookDamageThisTick = true;
        return new Damage(damage.getType(), damageAmount);
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
    public Shields ID() {
        return Shields.BASIC_SHIELD;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }

    @Override
    public int getImageID() {
        return R.drawable.item_shield_basic;
    }
}
