package com.group18.cs446.spacequest.game.objects.ship.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.game.CollisionEvent;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.Sector;

public class BasicProjectile implements GameEntity {
    private static final int despawnDistance = 2000;
    private Point coordinates;
    private Point velocity;
    private Bitmap bitmap;
    private GameEntity source;
    private Sector sector;
    private CollisionEvent collisionEvent;
    public BasicProjectile(Point coordinates, Point velocity, Bitmap bitmap, GameEntity source, Sector sector){
        this.coordinates = coordinates;
        this.velocity = velocity;
        this.bitmap = bitmap;
        this.source = source;
        this.sector = sector;
        this.collisionEvent = new CollisionEvent(CollisionEvent.DAMAGE, 1);
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
        Rect bounds = getBounds();
        entityLoop:
        for(GameEntity e : sector.getEntities()){
            if(e == this || e == source) continue;
            Rect intersection = new Rect(e.getBounds());
            if(intersection.intersect(bounds)){
                for(int x = intersection.left; x < intersection.right; x++){
                    for(int y = intersection.top; y < intersection.bottom; y++){
                        if(bitmap.getPixel(x - getBounds().left, y-getBounds().top) != Color.TRANSPARENT){
                            if(e.getBitmap().getPixel(x - e.getBounds().left, y - e.getBounds().top) != Color.TRANSPARENT){
                                e.takeDamage(50);
                                continue entityLoop;
                            }
                        }
                    }
                }
            }
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
        return new Rect(coordinates.x-bitmap.getWidth()/2, coordinates.y - bitmap.getHeight()/2, coordinates.x+bitmap.getWidth()/2, coordinates.y+bitmap.getWidth()/2);
    }

    /*@Override
    public CollisionEvent getCollisionEvent(GameEntity e) {
        if(e == source){
            return new CollisionEvent(CollisionEvent.NOTHING);
        } else {
            return collisionEvent;
        }
    }*/

    @Override
    public Sector getCurrentSector() {
        return sector;
    }
}
