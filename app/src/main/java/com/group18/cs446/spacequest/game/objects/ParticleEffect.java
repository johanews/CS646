package com.group18.cs446.spacequest.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Owen on 2018-02-09.
 */

public class ParticleEffect implements GameEntity {
    private Point coordinates;
    private int totalDuration;
    private int lifetime;
    public ParticleEffect(int x, int y, int ticks){
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
    public void update() {
        lifetime++;
        if(lifetime >= totalDuration){

        }
    }

    @Override
    public void paint(Canvas canvas, Paint paint, Point topLeftCorner) {
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(30);
        canvas.drawPoint(coordinates.x+topLeftCorner.x, coordinates.y+topLeftCorner.y, paint);
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
