package com.group18.cs446.spacequest.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class SmokeParticle implements GameEntity {
    private Point coordinates;
    private int totalDuration;
    private int lifetime;
    private Sector sector;
    public SmokeParticle(Sector sector, int x, int y, int ticks){
        this.sector = sector;
        coordinates = new Point(x, y);
        totalDuration = ticks;
        lifetime = 0;
    }
    @Override
    public Point getCoordinates() {
        return coordinates;
    }
    @Override
    public Rect getBounds(){
        return new Rect(0, 0, 0, 0);
    }

    @Override
    public Sector getCurrentSector() {
        return sector;
    }

    @Override
    public void update(long gameTick) {
        if(lifetime >= totalDuration){
            sector.removeEntity(this);
        } else {
            lifetime++;
        }
    }

    @Override
    public void paint(Canvas canvas, Paint paint, Point topLeftCorner) {
        if(lifetime >= totalDuration) return;
        paint.setColor(Color.GRAY);
        paint.setAlpha((255*(totalDuration-lifetime))/totalDuration);
        canvas.drawCircle(coordinates.x-topLeftCorner.x, coordinates.y-topLeftCorner.y, 15, paint);
        paint.setAlpha(255);
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
