package com.group18.cs446.spacequest.game.objects.loot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.collision.CollisionEvent;
import com.group18.cs446.spacequest.game.collision.Damage;
import com.group18.cs446.spacequest.game.collision.DamageType;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;

import java.util.Random;

public class MoneyDrop implements GameEntity {
    private static Bitmap bitmap;
    private Point coordinates = new Point();
    private Sector currentSector;
    private CollisionEvent collisionEvent;

    public MoneyDrop(Sector sector, Point center, Context context, int amount){
        this.currentSector = sector;
        if(bitmap == null) bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.money_1);
        coordinates.set(center.x, center.y);
        this.collisionEvent = new CollisionEvent(CollisionEvent.GET_MONEY, amount);
    }

    @Override
    public Point getCoordinates(){
        return coordinates;
    }


    @Override
    public CollisionEvent getCollisionEvent(GameEntity entity){
        return collisionEvent;
    }
    @Override
    public void update(long gameTick){
        if(this.collisionEvent.getValue() == 0){
            currentSector.removeEntity(this);
        }
    }

    @Override
    public void takeDamage(Damage damage){
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
        paint.reset();
        canvas.restore();
    }

    @Override
    public Bitmap getBitmap(){
        return bitmap;
    }

    @Override
    public boolean contains(Point p) {
        Point centre = new Point(coordinates.x + bitmap.getWidth() / 2, coordinates.y + bitmap.getHeight()/2);
        return Math.abs(p.x - centre.x) < bitmap.getWidth() / 2 && Math.abs(p.y - centre.y) < bitmap.getHeight() / 2;
    }

    @Override
    public boolean intersects(GameEntity e) {
        return false;
    }

    @Override
    public int getAngle(){
        return 0;
    }

    @Override
    public Sector getCurrentSector() {
        return currentSector;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public Rect getBounds(){
        return new Rect(coordinates.x-bitmap.getWidth()/2, coordinates.y-bitmap.getHeight()/2,
                coordinates.x+bitmap.getWidth()/2, coordinates.y+bitmap.getHeight()/2);
    }
}
