package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.group18.cs446.spacequest.R;

import java.util.Random;

/**
 * Created by vegard on 13.02.2018.
 */

public class Asteroid implements GameEntity {
    private static final int MIN_DISTANCE = 100;
    private static final int MAX_DISTACE = 300;
    //private static final int RADIUS = ?;

    private static Bitmap bitmap;

    private Point coordinates;
    private Point speed;

    // Arc speed and angle are in degrees
    private double arcSpeed;
    private double angle;

    private Random random = new Random();

    public Asteroid(Point centre) {
        
    }

    // Loads the bitmap for the asteroids. Must be called before any instances are created.
    public static void setBitmap(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroid);
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public void update() {
        coordinates.x += speed.x;
        coordinates.y += speed.y;
        angle = (angle + arcSpeed) % 360;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
