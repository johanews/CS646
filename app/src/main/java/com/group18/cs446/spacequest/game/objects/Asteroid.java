package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.enums.CollisionEvent;

import java.util.Random;

public class Asteroid implements GameEntity {
    private static final int MIN_DISTANCE = 1500;
    private static final int MAX_DISTANCE = 3000;

    private Bitmap bitmap;

    private Point coordinates = new Point();
    private Point speed = new Point();
    private Sector currentSector;

    private float arcSpeed;
    private float angle;

    private Random random = new Random();

    public Asteroid(Sector sector, Point center, Context context){
        this.currentSector = sector;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroid_1);
        double spawnAngle = random.nextDouble() * 2 * Math.PI;
        double spawnDistance = MIN_DISTANCE + random.nextDouble() * (MAX_DISTANCE - MIN_DISTANCE);
        coordinates.set((int)(center.x+Math.cos(spawnAngle)*spawnDistance),
                (int)(center.y+Math.sin(spawnAngle)*spawnDistance));
        arcSpeed = random.nextBoolean() ? random.nextFloat() + 0.2F : -random.nextFloat();
        speed.x = random.nextBoolean() ? random.nextInt(5)+1 : -random.nextInt(5) -1;
        speed.y = random.nextBoolean() ? random.nextInt(5)+1 : -random.nextInt(5) -1;
    }

    @Override
    public Point getCoordinates(){
        return coordinates;
    }

    @Override
    public void update(long gameTick){
        coordinates.x += speed.x;
        coordinates.y += speed.y;
        angle = (angle+arcSpeed)%360;
        int deltaX = currentSector.getPlayer().getCoordinates().x - coordinates.x;
        int deltaY = currentSector.getPlayer().getCoordinates().y - coordinates.y;
        if((deltaX*deltaX)+(deltaY*deltaY) > MAX_DISTANCE*MAX_DISTANCE){
            currentSector.removeEntity(this);
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

    public float getAngle(){
        return angle;
    }

    @Override
    public Rect getBounds(){
        return new Rect(coordinates.x-bitmap.getWidth()/2, coordinates.y-bitmap.getHeight()/2,
                coordinates.x+bitmap.getWidth()/2, coordinates.y+bitmap.getHeight()/2);
    }

    @Override
    public CollisionEvent getCollisionEvent() {
        return CollisionEvent.DEFEAT;
    }
}
