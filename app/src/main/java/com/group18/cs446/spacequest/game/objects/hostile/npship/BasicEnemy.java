package com.group18.cs446.spacequest.game.objects.hostile.npship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.CollisionEvent;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.hostile.Enemy;
import com.group18.cs446.spacequest.game.objects.ship.Weapon;
import com.group18.cs446.spacequest.game.objects.ship.components.BasicLaser;
import com.group18.cs446.spacequest.game.objects.ship.components.ChainLaser;
import com.group18.cs446.spacequest.game.objects.ship.components.DualLaser;

import java.util.Random;

public class BasicEnemy implements Enemy {
    private int maxHealth, currentHealth;
    private int speed;
    private int angle;
    private int sightDistance;
    private int fireDistance;
    private int hoverDistance;
    private Point coordinates;
    private GameEntity target;
    private Weapon weapon;
    private Sector sector;
    private Bitmap bitmap;
    private Random random = new Random();
    private CollisionEvent collisionEvent = new CollisionEvent(CollisionEvent.DAMAGE,200);

    public BasicEnemy(Point spawnPoint, Context context, Sector currentSector){
        this.coordinates = new Point(spawnPoint);
        this.speed = 15;
        this.sightDistance = 3000;
        this.fireDistance = 650;
        this.hoverDistance = 500;
        this.maxHealth = 100;
        this.sector = currentSector;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_1);
        this.angle = 0;
        if(random.nextInt(10) < 8){ // 80% basic laser
            this.weapon = new BasicLaser(this, context);
        } else {
            if(random.nextBoolean()){ // 10% dual laser
                this.weapon = new DualLaser(this, context);
                this.speed = 10;
            } else { // 10% chainlaser
                this.weapon = new ChainLaser(this, context);
                this.fireDistance = 900;
                this.hoverDistance = 600;
                this.maxHealth = 50;
                this.speed = 10;
            }
        }
        this.currentHealth = maxHealth;
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public void update(long gameTick) {
        if(target == null){
            // Attempt to lock on player
            int dx = sector.getPlayer().getCoordinates().x - coordinates.x;
            int dy = sector.getPlayer().getCoordinates().y - coordinates.y;
            if(dx*dx + dy*dy < sightDistance*sightDistance){
                setTarget(sector.getPlayer());
            }
        }
        if(target != null){
            int dx = target.getCoordinates().x - coordinates.x;
            int dy = target.getCoordinates().y - coordinates.y;
            int distanceToTarget = (int)Math.sqrt(dx*dx + dy*dy);
            if(distanceToTarget > sightDistance){
                target = null;
            } else {
                angle = ((int)(Math.atan2(dx, dy)*180/Math.PI)+180)%360;
                int adjustedSpeed = speed;
                if(distanceToTarget < hoverDistance){
                    adjustedSpeed = (adjustedSpeed*(2*distanceToTarget-hoverDistance))/hoverDistance;
                }
                if(distanceToTarget != 0) {
                    coordinates.offset((dx * adjustedSpeed / distanceToTarget), (dy * adjustedSpeed / distanceToTarget));
                }
            }
            if(distanceToTarget < fireDistance){
                weapon.fire(gameTick);
            }
            if(currentHealth < maxHealth){
                sector.addEntityToBack(new SmokeParticle(sector, coordinates.x, coordinates.y, bitmap.getWidth()/4, Color.GRAY,20));
            }
        }
    }

    @Override
    public void paint(Canvas canvas, Paint paint, Point topLeftCorner) {
        canvas.save();
        canvas.rotate(-getAngle(), getCoordinates().x - topLeftCorner.x, getCoordinates().y - topLeftCorner.y);
        canvas.drawBitmap(
                getBitmap(),
                getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2,
                getCoordinates().y - topLeftCorner.y - getBitmap().getHeight() / 2,
                paint);
        canvas.restore();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean intersects(GameEntity e) {
        return false;
    }

    @Override
    public Rect getBounds() {
        return new Rect(coordinates.x - bitmap.getWidth()/2, coordinates.y-bitmap.getWidth()/2, coordinates.x+bitmap.getWidth()/2, coordinates.y+bitmap.getHeight()/2);
    }

    @Override
    public CollisionEvent getCollisionEvent(GameEntity e) {
        return collisionEvent;
    }

    @Override
    public void takeDamage(int damage) {
        this.currentHealth -= damage;
        if(this.currentHealth <= 0){ // Trigger death, later replace this with somehting else
            sector.removeEntity(this);
            sector.addEntityToBack(new SmokeParticle(sector, coordinates.x, coordinates.y, bitmap.getWidth()/2, Color.GRAY,30));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x-20, coordinates.y-10, bitmap.getWidth()/6, Color.GRAY,20));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x+20, coordinates.y-30, bitmap.getWidth()/4, Color.RED,50));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x-15, coordinates.y+30, bitmap.getWidth()/5, Color.DKGRAY,30));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x+15, coordinates.y+10, bitmap.getWidth()/7, Color.DKGRAY,10));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x-1, coordinates.y+10, bitmap.getWidth()/3, Color.DKGRAY,40));

        }
    }

    @Override
    public int getAngle() {
        return angle;
    }

    @Override
    public Sector getCurrentSector() {
        return sector;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setTarget(GameEntity e) {
        this.target = e;
    }
}
