package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.group18.cs446.spacequest.R;

import java.util.Random;


public class Asteroid implements GameEntity {
    private static final int MIN_DISTANCE = 10;
    private static final int MAX_DISTACE = 30;

    private Bitmap bitmap;

    private Point coordinates = new Point();
    private Point speed = new Point();

    // Arc speed and angle are in degrees
    private float arcSpeed;
    private float angle;

    private Random random = new Random();

    public Asteroid(Point centre, Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroid);

        double spawnAngle = random.nextDouble() * 2 * Math.PI;
        System.out.println("Angle: " + spawnAngle);
        //double spawnDistance = MIN_DISTANCE + random.nextDouble() * (MAX_DISTANCE - MIN_DISTANCE);
        double spawnDistance = 10;

        coordinates.set((int) (centre.x + Math.cos(spawnAngle) * spawnDistance),
                        (int) (centre.y + Math.sin(spawnAngle) * spawnDistance));
        System.out.println(coordinates);


        arcSpeed = random.nextBoolean() ? random.nextFloat() + 0.2f : - random.nextFloat() - 0.2f;
        /*speed.x = random.nextBoolean() ? random.nextInt(5) + 1 : - random.nextInt(5) - 1;
        speed.y = random.nextBoolean() ? random.nextInt(5) + 1 : - random.nextInt(5) - 1;*/
        speed.x = random.nextBoolean() ? 1 : -1;
        speed.y = random.nextBoolean() ? 1 : -1;
        System.out.println("sx: " + speed.x + ", sy: " + speed.y);
        //speed.set(1, 1);
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
        //System.out.println("xpos: " + coordinates.x + ", ypos: " + coordinates.y);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getAngle() {
        return angle;
    }
}
