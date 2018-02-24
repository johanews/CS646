package com.group18.cs446.spacequest.game.objects.hostile.npship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.enums.CollisionEvent;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.hostile.Enemy;
import com.group18.cs446.spacequest.game.objects.ship.Weapon;
import com.group18.cs446.spacequest.game.objects.ship.components.BasicLaser;

public class BasicEnemy implements Enemy {
    private int maxHealth = 10;
    private int currentHealth;
    private Point coordinates;
    private GameEntity target;
    private Weapon weapon;
    private Sector sector;
    private Bitmap bitmap;
    private int angle;
    private int speed = 6;
    private int sightDistance = 2000;

    public BasicEnemy(Point spawnPoint, Context context, Sector currentSector){
        this.coordinates = spawnPoint;
        this.currentHealth = maxHealth;
        this.weapon = new BasicLaser(this, context);
        this.sector = currentSector;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_1);
        this.angle = 0;
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
                //angle =  (int)((180/Math.PI)*(dy != 0 ? (dx < 0 ? -1 : 1)*Math.atan(dx/dy) : (dx > 0 ? Math.PI/2: 3*Math.PI/2)));
                angle = ((int)(Math.atan2(dx, dy)*180/Math.PI)+180)%360;
                int adjustedSpeed = speed;
                if(distanceToTarget > 1) {
                    coordinates.offset((int) (dx * adjustedSpeed / distanceToTarget), (int) (dy * adjustedSpeed / distanceToTarget));
                }
            }
            if(distanceToTarget < 300){
                weapon.fire(gameTick);
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
        return CollisionEvent.NOTHING;
    }

    @Override
    public void takeDamage(int damage) {
        this.currentHealth -= damage;
        if(this.currentHealth <= 0){ // Trigger death, later replace this with somehting else
            sector.removeEntity(this);
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
