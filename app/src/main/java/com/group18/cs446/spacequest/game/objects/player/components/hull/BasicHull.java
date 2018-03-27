package com.group18.cs446.spacequest.game.objects.player.components.hull;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.collision.Damage;
import com.group18.cs446.spacequest.game.enums.Hulls;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.player.Hull;
import com.group18.cs446.spacequest.game.vfx.DamageFilter;

public class BasicHull implements Hull {
    private static final String NAME = "Basic Hull";
    private static final String DESCRIPTION = "Its a hull";
    private static final int PRICE = 30;

    private int maxHealth, currentHealth;
    private int regenAmount;
    private int regenCooldown; // time to regen from last damage
    private long lastDamageTick;
    private boolean tookDamageThisTick;
    private GameEntity owner;
    private static Bitmap image;
    private Point[] smokeLocations = new Point[4];

    public BasicHull(Context context){
        this.maxHealth = 250;
        this.currentHealth = maxHealth;
        this.regenAmount = 1; // amount to increase
        this.regenCooldown = 150;
        this.lastDamageTick = 0;
        this.tookDamageThisTick = false;
        // These are aesthetic
        this.smokeLocations[0] = new Point(20, -20);
        this.smokeLocations[1] = new Point(-20, -20);
        this.smokeLocations[2] = new Point(15, -15);
        this.smokeLocations[3] = new Point(-15, -15);
        if(image == null) {
            image = BitmapFactory.decodeResource(context.getResources(), getImageID());
            image = Bitmap.createScaledBitmap(image, 2*image.getWidth()/3, 2*image.getHeight()/3, false);
        }
    }

    @Override
    public void update(long gameTick) {
        if(currentHealth <= 0) return;
        if(tookDamageThisTick){
            tookDamageThisTick = false;
            lastDamageTick = gameTick;
            owner.getCurrentSector().addFilter(new DamageFilter(owner.getCurrentSector()));
        } else if (lastDamageTick + regenCooldown < gameTick && gameTick % 15 == 0){
            if(currentHealth + regenAmount >= maxHealth){
                currentHealth = maxHealth;
            } else {
                currentHealth += regenAmount;
            }
        }
        if(currentHealth < maxHealth){ // extra trail when damaged
            int i = 0;
            for(Point p : smokeLocations) {
                i++;
                if(currentHealth >= maxHealth/i) break;
                int radius = (i > 8) ? 3 : 20-2*i;
                int ticks = (i > 10) ? 30 : 80 - i*5;
                int color = (i > 3) ? Color.RED : Color.DKGRAY;
                SmokeParticle smokeParticle = new SmokeParticle(owner.getCurrentSector(),
                        owner.getCoordinates().x + (int) (p.x * Math.cos(owner.getAngle() * Math.PI / 180)),
                        owner.getCoordinates().y + (int) (p.y * Math.sin(owner.getAngle() * Math.PI / 180)),
                        radius, color, ticks);
                owner.getCurrentSector().addEntityToBack(smokeParticle);
            }
        }
    }

    @Override
    public void refresh() {
        this.currentHealth = maxHealth;
        this.lastDamageTick = 0;
        this.tookDamageThisTick = false;
    }

    @Override
    public void takeDamage(Damage damage) {
        int damageAmount = damage.getAmount();
        switch (damage.getType()){
            case LASER:
                damageAmount *= 1.2;
                break;
            case PHYSICAL:
                damageAmount *= 1.1;
                break;
            default:
                break;
        }
        if(currentHealth - damageAmount <= 0){
            currentHealth = 0;
        } else {
            currentHealth -= damageAmount;
        }
        tookDamageThisTick = true;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
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
    public Hulls ID() {
        return Hulls.BASIC_HULL;
    }

    @Override
    public int getPrice() {
        return PRICE;
    }

    @Override
    public int getImageID() {
        return R.drawable.item_hull_basic;
    }
}
