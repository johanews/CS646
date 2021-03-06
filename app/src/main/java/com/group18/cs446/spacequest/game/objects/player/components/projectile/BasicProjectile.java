package com.group18.cs446.spacequest.game.objects.player.components.projectile;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.game.collision.CollisionEvent;
import com.group18.cs446.spacequest.game.collision.Damage;
import com.group18.cs446.spacequest.game.collision.DamageType;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.vfx.CanvasComponent;

public class BasicProjectile implements GameEntity {

    private static final int despawnDistance = 2000;
    private Point coordinates;
    private Point velocity;
    private static Bitmap bitmap;
    private GameEntity source;
    private Sector sector;
    private Damage damage;
    private CollisionEvent collisionEvent;

    public BasicProjectile(Point coordinates, Point velocity, Bitmap provBitmap, GameEntity source, Sector sector){
        this.coordinates = coordinates;
        this.velocity = velocity;
        if(bitmap == null) bitmap = provBitmap;
        this.source = source;
        this.sector = sector;
        this.damage = new Damage(DamageType.LASER, 50);
        this.collisionEvent = new CollisionEvent(CollisionEvent.DAMAGE, damage);
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
                                e.takeDamage(damage);
                                continue entityLoop;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void paint(CanvasComponent canvas, Paint paint, Point topLeftCorner) {
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

    @Override
    public Sector getCurrentSector() {
        return sector;
    }
}
