package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;

import com.group18.cs446.spacequest.game.CollisionEvent;
import com.group18.cs446.spacequest.game.enums.PlayerCommand;
import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.objects.ship.Engine;
import com.group18.cs446.spacequest.game.objects.ship.Weapon;
import com.group18.cs446.spacequest.game.objects.ship.components.BasicEngine;
import com.group18.cs446.spacequest.game.objects.ship.components.BasicLaser;
import com.group18.cs446.spacequest.game.objects.ship.components.ChainLaser;
import com.group18.cs446.spacequest.game.objects.ship.components.DualLaser;
import com.group18.cs446.spacequest.game.vfx.DamageFilter;

import java.util.Random;

public class Player implements GameEntity{
    private Point coordinates; // Now represents the center of the character, not the top left
    private int maxHealth, currentHealth, regen, maxShield, currentShield, shieldRegen, heading;
    private Bitmap bitmap;
    private PlayerCommand currentCommand;
    private Rect bounds;
    private Sector currentSector;

    private Random random = new Random();

    private boolean controlledByPlayer = true;
    private Point target;
    private int updatesToTarget;

    private int timeToDeletion;
    private boolean alive = true;
    private boolean doingAction;
    private CollisionEvent collisionEvent;

    private boolean tookDamage;
    private long lastDamage;
    private long regenCooldown = 600;
    private long shieldRegenCooldown = 300;

    //Components
    private Weapon equipedWeapon;
    private Engine equipedEngine;

    public Player(Context context){

        coordinates = new Point((random.nextBoolean() ? 1 : -1 )*(3500+random.nextInt(1000)), (random.nextBoolean() ? 1 : -1 )*(3500+random.nextInt(1000)));
        maxHealth = 250;
        currentHealth = maxHealth;
        maxShield = 750;
        currentShield = maxShield;
        regen = 1;
        shieldRegen = 3;
        heading = 0; // Direction in degrees
        currentCommand = PlayerCommand.NONE;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        bounds = null;
        equipedEngine = new BasicEngine();
        //equipedWeapon = new BasicLaser(this, context);
        equipedWeapon = new DualLaser(this, context);
        //equipedWeapon = new ChainLaser(this, context);

        doingAction = false;
        collisionEvent = new CollisionEvent(CollisionEvent.DAMAGE, 100);
        tookDamage = false;
    }

    public void doAction(){
        doingAction = true;
    }
    public void stopAction(){
        doingAction = false;
    }
    public boolean getTookDamage(){
        return tookDamage;
    }

    public void setCurrentSector(Sector s){
        this.currentSector = s;
    }
    public Sector getCurrentSector(){
        return currentSector;
    }

    public void flyToTarget(Point p, int time){
        controlledByPlayer = false;
        target = p;
        updatesToTarget = time;
    }

    public void explode(int timeToDeletion){
        this.timeToDeletion = timeToDeletion;
        alive = false;
    }

    public int getCurrentHealth(){
        return currentHealth;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public int getCurrentShield(){
        return currentShield;
    }
    public int getMaxShield(){
        return maxShield;
    }

    @Override
    public void takeDamage(int damage){
        if(!controlledByPlayer) return;
        tookDamage = true;
        currentSector.addFilter(new DamageFilter(currentSector));
        if(currentShield > 0){
            this.currentShield -= damage;
            if(this.currentShield < 0){
                currentShield = 0;
            }
        } else {
            this.currentHealth -= damage;
            if (currentHealth <= 0) {
                currentHealth = 0;
                currentSector.triggerDefeat();
            }
        }
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public CollisionEvent getCollisionEvent(GameEntity e){
        return collisionEvent;
    }

    @Override
    public Rect getBounds(){
        // If the spaceship was not rotated, then the bounds would be (x, y, width, height)
        // However, since the spaceship could be rotated we must return the smalled rect that the rotated
        // ship could fit inside
        // angle 0 = upright, means
        if(bounds != null){ // moving will set this to null
            return bounds;
        }
        // TODO currently doesnt work properly for rotated ship
        return new Rect(coordinates.x - bitmap.getWidth()/2, coordinates.y - bitmap.getHeight()/2,
                coordinates.x + bitmap.getWidth()/2, coordinates.y + bitmap.getHeight()/2);
        //return new Rect(coordinates.x-bitmap.getWidth()-bitmap.getHeight(), coordinates.y-bitmap.getWidth()-bitmap.getHeight(),
        //        coordinates.x+bitmap.getWidth()+bitmap.getHeight(), coordinates.y+bitmap.getWidth()+bitmap.getHeight());

    }

    @Override
    public void update(long gameTick) {
        if(tookDamage){
            tookDamage = false;
            lastDamage = gameTick;
        } else {
            if(gameTick > lastDamage + regenCooldown) {
                if (currentHealth > 0 && currentHealth < maxHealth) {
                    currentHealth = (currentHealth + regen > maxHealth) ? maxHealth : currentHealth + regen;
                }
            }
            if(gameTick > lastDamage + shieldRegenCooldown) {
                if (currentShield < maxShield) {
                    currentShield = (currentShield + shieldRegen > maxShield) ? maxShield : currentShield + shieldRegen;
                }
            }
        }
        if(alive) {
            if(doingAction){
                if(equipedWeapon != null){
                    equipedWeapon.fire(gameTick);
                }
            }
            if (controlledByPlayer) {
                switch (currentCommand) {
                    case RIGHT:
                        heading = (heading + 360 - equipedEngine.getTurnSpeed()) % 360;
                        break;
                    case LEFT:
                        heading = (heading + 360 + equipedEngine.getTurnSpeed()) % 360;
                        break;
                    case BOTH:
                    case NONE:
                        // Nothing needs to be done
                        break;
                }
                coordinates.y -= Math.cos(heading * Math.PI / 180) * getSpeed();
                coordinates.x -= Math.sin(heading * Math.PI / 180) * getSpeed();
            } else {
                if (updatesToTarget > 0) {
                    double normalized = Math.sqrt((coordinates.y - target.y) * (coordinates.y - target.y)
                            + (coordinates.x - target.x) * (coordinates.x - target.x));
                    if (Math.abs(coordinates.y - target.y) < equipedEngine.getSpeed()) {
                        coordinates.y = target.y;
                    } else {
                        coordinates.y -= ((coordinates.y - target.y) / normalized) * getSpeed();
                    }
                    if (Math.abs(coordinates.x - target.x) < equipedEngine.getSpeed()) {
                        coordinates.x = target.x;
                    } else {
                        coordinates.x -= ((coordinates.x - target.x) / normalized) * getSpeed();
                    }
                    if (heading > 180) {
                        heading--;
                    } else if (heading < 180) {
                        heading++;
                    }
                    updatesToTarget--;
                }
            }
            bounds = null; // invalidate bounds since we moved
        } else {
            // any updates to happen while dead
            timeToDeletion--;
        }

        // Add smoke effect

        if(gameTick%2 == 0) { // default trail
            SmokeParticle smokeParticle = new SmokeParticle(currentSector, coordinates.x+(int)(20*Math.sin(heading*Math.PI/180)), coordinates.y+(int)(20*Math.cos(heading*Math.PI/180)), 70);
            currentSector.addEntityToBack(smokeParticle);
        }
        if(gameTick%3 == 0 && currentHealth < maxHealth){ // extra trail when damaged
            SmokeParticle smokeParticle = new SmokeParticle(currentSector, coordinates.x+(int)(20*Math.cos(heading*Math.PI/180)), coordinates.y-(int)(20*Math.sin(heading*Math.PI/180)), 20, Color.DKGRAY, 80);
            currentSector.addEntityToBack(smokeParticle);
        }
        if(currentHealth < maxHealth/2){ // extra trail at 50% health
            SmokeParticle smokeParticle = new SmokeParticle(currentSector, coordinates.x+(int)(-20*Math.cos(heading*Math.PI/180)), coordinates.y-(int)(-20*Math.sin(heading*Math.PI/180)), 10, Color.RED, 100);
            currentSector.addEntityToBack(smokeParticle);
        }
    }

    public int getAngle(){
        return heading;
    }
    public int getSpeed() {return equipedEngine.getSpeed(); }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }

    public void removeCommand(PlayerCommand playerCommand) {
        if(currentCommand == PlayerCommand.BOTH){
            currentCommand = playerCommand == PlayerCommand.LEFT ? PlayerCommand.RIGHT : PlayerCommand.LEFT;
        } else if (currentCommand == playerCommand){
            currentCommand = PlayerCommand.NONE;
        }
    }

    public void addCommand(PlayerCommand playerCommand) {
        if(playerCommand == PlayerCommand.LEFT){
            switch (currentCommand){
                case NONE:
                    currentCommand = PlayerCommand.LEFT;
                    break;
                case RIGHT:
                    currentCommand = PlayerCommand.BOTH;
                    break;
                case BOTH:
                case LEFT:
                    return;
            }
        } else if(playerCommand == PlayerCommand.RIGHT){
            switch (currentCommand){
                case NONE:
                    currentCommand = PlayerCommand.RIGHT;
                    break;
                case LEFT:
                    currentCommand = PlayerCommand.BOTH;
                    break;
                case BOTH:
                case RIGHT:
                    return;
            }
        }
    }

    @Override
    public void paint(Canvas canvas, Paint paint, Point topLeftCorner) {
        //Alternatively, pass this to Player to paint themselves here
        canvas.save();
        canvas.rotate(-getAngle(), getCoordinates().x - topLeftCorner.x, getCoordinates().y - topLeftCorner.y);
        canvas.drawBitmap(
                getBitmap(),
                getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2,
                getCoordinates().y - topLeftCorner.y - getBitmap().getHeight() / 2,
                paint);
        if(!alive){ // Very ugly and simple explosion animation, TODO make this look nice - use a real animation
            paint.setColor(Color.rgb(150+random.nextInt(155), random.nextInt(50), random.nextInt(50)));
            canvas.drawCircle(getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2 - 10 + random.nextInt(getBitmap().getWidth()),
                    getCoordinates().y - topLeftCorner.y - getBitmap().getWidth() / 2 - 10 + random.nextInt(getBitmap().getHeight()),
                    random.nextInt(40), paint);
        }
        int shieldXRadi = getBitmap().getWidth() / 2 + 40;
        int shieldYRadi = getBitmap().getHeight() / 2 + 40;
        if(currentShield > 0){
            paint.setColor(Color.argb(50, 10, 100, 255));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawOval(getCoordinates().x - topLeftCorner.x - shieldXRadi,
                        getCoordinates().y - topLeftCorner.y - shieldYRadi,
                        getCoordinates().x - topLeftCorner.x + shieldXRadi,
                        getCoordinates().y - topLeftCorner.y +shieldYRadi,
                        paint);
            } else {
                canvas.drawCircle(getCoordinates().x,
                        getCoordinates().y,
                        Math.max(shieldXRadi, shieldYRadi),
                        paint);

            }
        }
        paint.reset();
        canvas.restore();
    }

    public void reset() { // Everything thats required for moving to a new sector
        coordinates = new Point((random.nextBoolean() ? 1 : -1 )*(1000+random.nextInt(4000)), (random.nextBoolean() ? 1 : -1 )*(1000+random.nextInt(4000)));
        heading = 0; // Direction in degrees
        currentCommand = PlayerCommand.NONE;
        controlledByPlayer = true;
        if(equipedWeapon != null){
            equipedWeapon.refresh();
        }
        if(equipedEngine != null){
            equipedEngine.refresh();
        }
        tookDamage = false;
        lastDamage = Long.MIN_VALUE;
    }

    @Override
    public boolean intersects(GameEntity e){
        if(!controlledByPlayer){
            return false;
        }
        Rect intersect = getBounds();
        if(intersect.intersect(e.getBounds())){
            // This just means the rectangle bounds intersect
            // now we will do a pixel by pixel search to see if they actually overlap
            for(int x = intersect.left; x < intersect.right; x++){
                for(int y = intersect.top; y < intersect.bottom; y++){
                    if(bitmap.getPixel(x - getBounds().left, y-getBounds().top) != Color.TRANSPARENT){
                        if(e.getBitmap().getPixel(x - e.getBounds().left, y - e.getBounds().top) != Color.TRANSPARENT){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
