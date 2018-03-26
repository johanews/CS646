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

public class TrailProjectile implements GameEntity {

    private static final int despawnDistance = 2000;
    private Point coordinates;
    private static Bitmap bitmap;
    private GameEntity source;
    private Sector sector;
    private Damage damage;
    private CollisionEvent collisionEvent;
    private int despawnTime;
    private int angle;

    public TrailProjectile(Point coordinates, Bitmap provBitmap, GameEntity source, Sector sector){
        this.coordinates = coordinates;
        if(bitmap == null) bitmap = Bitmap.createScaledBitmap(provBitmap, provBitmap.getWidth(), provBitmap.getHeight(), false);
        this.source = source;
        this.sector = sector;
        this.damage = new Damage(DamageType.LASER, 10);
        this.collisionEvent = new CollisionEvent(CollisionEvent.DAMAGE, damage);
        this.despawnTime = 75;
        //this.coordinates.x -= bitmap.getWidth()/2;
        //this.coordinates.y -= bitmap.getHeight()/2;
        this.angle = source.getAngle();
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public void update(long gameTick) {
        this.despawnTime--;
        int dx = coordinates.x - sector.getPlayer().getCoordinates().x;
        int dy = coordinates.y - sector.getPlayer().getCoordinates().y;
        if(dx*dx+dy*dy > despawnDistance*despawnDistance || despawnTime <= 0){
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
                        if(e.getBitmap().getPixel(x - e.getBounds().left, y - e.getBounds().top) != Color.TRANSPARENT){
                            e.takeDamage(damage);
                            continue entityLoop;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void paint(CanvasComponent canvas, Paint paint, Point topLeftCorner) {
        canvas.save();
        canvas.rotate(-angle, getCoordinates().x - topLeftCorner.x, getCoordinates().y - topLeftCorner.y);
        canvas.drawBitmap(bitmap, coordinates.x-topLeftCorner.x-bitmap.getWidth()/2, coordinates.y-topLeftCorner.y-bitmap.getHeight()/2, paint);
        canvas.restore();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public Rect getBounds() {
        int dy = bitmap.getHeight()/2;
        int dyBoost = bitmap.getHeight()%2;
        int dx = bitmap.getWidth()/2;
        int dxBoost = bitmap.getWidth()%2;
        return new Rect(coordinates.x-dx, coordinates.y - dy, coordinates.x+dx + dxBoost, coordinates.y+dy+dyBoost);
    }

    @Override
    public Sector getCurrentSector() {
        return sector;
    }
}
