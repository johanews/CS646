package com.group18.cs446.spacequest.game.objects.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.game.collision.CollisionEvent;
import com.group18.cs446.spacequest.game.collision.Damage;
import com.group18.cs446.spacequest.game.collision.DamageType;
import com.group18.cs446.spacequest.game.enums.PlayerCommand;
import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.player.components.BasicEngine;
import com.group18.cs446.spacequest.game.objects.player.components.BasicHull;
import com.group18.cs446.spacequest.game.objects.player.components.BasicLaser;
import com.group18.cs446.spacequest.game.objects.player.components.BasicShield;
import com.group18.cs446.spacequest.game.vfx.DamageFilter;

import java.io.Serializable;
import java.util.Random;

public class Player implements GameEntity, Serializable {
    private Point coordinates; // Now represents the center of the character, not the top left
    private int heading;
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

    //Components
    private Weapon equipedWeapon;
    private Engine equipedEngine;
    private Hull equipedHull;
    private Shield equipedShield;

    public Player(Context context) {

        coordinates = new Point(
                (random.nextBoolean() ? 1 : -1)*(3500+random.nextInt(1000)),
                (random.nextBoolean() ? 1 : -1)*(3500+random.nextInt(1000)));
        heading = 0; // Direction in degrees
        currentCommand = PlayerCommand.NONE;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        bounds = null;

        equipedEngine = new BasicEngine(this);
        equipedWeapon = new BasicLaser(this, context);
        equipedShield = new BasicShield(this);
        equipedHull = new BasicHull(this);

        doingAction = false;
        Damage collisionDamage = new Damage(DamageType.PHYSICAL, 100);
        collisionEvent = new CollisionEvent(CollisionEvent.DAMAGE, collisionDamage);
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

    public Weapon getWeapon() {
        return equipedWeapon;
    }

    public Engine getEngine() {
        return equipedEngine;
    }

    public Shield getShield() {
        return equipedShield;
    }

    public Hull getHull() {
        return equipedHull;
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
        return equipedHull.getCurrentHealth();
    }
    public int getMaxHealth(){
        return equipedHull.getMaxHealth();
    }
    public int getCurrentShield(){
        return (equipedShield != null) ? equipedShield.getCurrentShield() : 0;
    }
    public int getMaxShield(){
        return (equipedShield != null) ? equipedShield.getMaxShield() : 0;
    }

    @Override
    public void takeDamage(Damage damage){
        if(!controlledByPlayer) return;
        tookDamage = true;
        currentSector.addFilter(new DamageFilter(currentSector));
        if(equipedShield == null || !equipedShield.takeDamage(damage)) {
            // Shield takeDamage returns false if it doesn't handle the damage
            equipedHull.takeDamage(damage);
        }
        if(equipedHull.getCurrentHealth() <= 0){
            currentSector.triggerDefeat();
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
        if(bounds != null){
            // moving will set this to null
            return bounds;
        }
        // TODO currently doesnt work properly for rotated ship
        return new Rect(
                coordinates.x - bitmap.getWidth()/2,coordinates.y - bitmap.getHeight()/2,
                coordinates.x + bitmap.getWidth()/2, coordinates.y + bitmap.getHeight()/2);
        //return new Rect(coordinates.x-bitmap.getWidth()-bitmap.getHeight(), coordinates.y-bitmap.getWidth()-bitmap.getHeight(),
        //coordinates.x+bitmap.getWidth()+bitmap.getHeight(), coordinates.y+bitmap.getWidth()+bitmap.getHeight());

    }

    @Override
    public void update(long gameTick) {
        if(alive) {
            if(equipedShield != null) equipedShield.update(gameTick);
            equipedHull.update(gameTick);
            equipedEngine.update(gameTick);
            if(doingAction){
                if(equipedWeapon != null){
                    equipedWeapon.fire(gameTick);
                }
            }
            if(currentCommand == PlayerCommand.BOTH){
                if(equipedEngine != null){
                    equipedEngine.doSpecial(gameTick);
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

        // Do collision detection
        for(GameEntity e : currentSector.getEntities()){
            if(e != this && e.getCollisionEvent(this).getEvent() != CollisionEvent.NOTHING && intersects(e)){
                triggerCollisionEvent(e);
            }
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
        if(!alive){ // Very ugly and simple explosion animation
            paint.setColor(Color.rgb(150+random.nextInt(155), random.nextInt(50), random.nextInt(50)));
            canvas.drawCircle(getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2 - 10 + random.nextInt(getBitmap().getWidth()),
                    getCoordinates().y - topLeftCorner.y - getBitmap().getWidth() / 2 - 10 + random.nextInt(getBitmap().getHeight()),
                    random.nextInt(40), paint);
        } else {
            if(equipedShield != null) equipedShield.paint(canvas, paint, topLeftCorner);
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


    private void triggerCollisionEvent(GameEntity e){
        CollisionEvent event = e.getCollisionEvent(this);
        switch (event.getEvent()){
            case CollisionEvent.VICTORY:
                currentSector.triggerVictory();
                flyToTarget(e.getCoordinates(), currentSector.getVictoryFinalizeTime());
                break;
            case CollisionEvent.DAMAGE:
                takeDamage(event.getDamage());
                break;
            case CollisionEvent.DEFEAT:
                currentSector.triggerDefeat();
                break;
        }
    }
}
