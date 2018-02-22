package com.group18.cs446.spacequest.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.game.enums.CollisionEvent;

public class Projectile implements GameEntity {
    private static final int despawnDistance = 20000;
    private Point coordinates;
    private Point velocity;
    private Bitmap bitmap;
    private GameEntity source;
    private Sector sector;
    public Projectile(Point coordinates, Point velocity, Bitmap bitmap, GameEntity source, Sector sector){
        this.coordinates = coordinates;
        this.velocity = velocity;
        this.bitmap = bitmap;
        this.source = source;
        this.sector = sector;
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public void update(long gameTick) {
        coordinates.offset(velocity.x, velocity.y);
        int dx = coordinates.x - sector.getPlayer().getCoordinates().x;
        int dy = coordinates.y - sector.getPlayer().getCoordinates().y;
        if(dx*dx+dy*dy > despawnDistance*despawnDistance){
            sector.removeEntity(this);
        }
    }

    @Override
    public void paint(Canvas canvas, Paint paint, Point topLeftCorner) {
        canvas.drawBitmap(bitmap, coordinates.x-topLeftCorner.x, coordinates.y-topLeftCorner.y, paint);
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public Rect getBounds() {
        return null;
    }

    @Override
    public CollisionEvent getCollisionEvent(GameEntity e) {
        if(e == source){
            return CollisionEvent.NOTHING;
        } else {
            return CollisionEvent.DAMAGE;
        }
    }
}
