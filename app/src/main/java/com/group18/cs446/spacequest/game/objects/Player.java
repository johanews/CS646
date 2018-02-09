package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

import com.group18.cs446.spacequest.PlayerCommand;
import com.group18.cs446.spacequest.R;

/**
 * Created by Owen on 2018-02-08.
 */

public class Player implements GameEntity{
    Point coordinates;
    int speed, maxHealth, currentHealth, heading, regen, turnSpeed;
    Bitmap bitmap;
    PlayerCommand currentCommand;

    public Player(Context context){
        coordinates = new Point(100, 100);
        speed = 10;
        maxHealth = 1000;
        currentHealth = 1000;
        regen = 3;
        turnSpeed = 1;
        heading = 0; // Direction in degrees
        currentCommand = PlayerCommand.LEFT;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }
    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public void update() {
        if(currentHealth > 0 && currentHealth < maxHealth) {
            currentHealth =(currentHealth + regen > maxHealth) ? maxHealth : currentHealth + regen;
        }
        switch (currentCommand){
            case LEFT:
                heading = (heading + 360 - turnSpeed)%360;
                break;
            case RIGHT:
                heading = (heading + 360 + turnSpeed)%360;
                break;
            case NONE:
                // Nothing needs to be done
                break;
        }

        coordinates.y -= Math.cos(heading*Math.PI/180)*speed;
        coordinates.x -= Math.sin(heading*Math.PI/180)*speed;
    }

    public int getAngle(){
        return heading;
    }

    @Override
    public Bitmap getBitmap() {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(heading);
//        return Bitmap.createBitmap(bitmap, -10, -15, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }

}
