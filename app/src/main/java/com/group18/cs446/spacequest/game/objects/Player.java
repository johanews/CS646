package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.group18.cs446.spacequest.PlayerCommand;
import com.group18.cs446.spacequest.R;

public class Player implements GameEntity {

    private Point coordinates;

    private int speed;
    private int maxHealth;
    private int currentHealth;
    private int heading;        // direction in degrees
    private int regen;
    private int turnSpeed;

    private Bitmap bitmap;
    private PlayerCommand currentCommand;

    public Player(Context context){

        coordinates = new Point(100, 100);
        speed = 10;
        maxHealth = 1000;
        currentHealth = 1000;
        regen = 3;
        turnSpeed = 5;
        heading = 0;
        currentCommand = PlayerCommand.NONE;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

    public int getAngle(){
        return heading;
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public void update() {

        if(currentHealth > 0 && currentHealth < maxHealth) {
            currentHealth = (currentHealth + regen > maxHealth) ? maxHealth : currentHealth + regen;
        }

        switch (currentCommand){

            case LEFT:
                heading = (heading + 360 + turnSpeed) % 360;
                break;
            case RIGHT:
                heading = (heading + 360 - turnSpeed) % 360;
                break;
            case NONE:
                break; // nothing needs to be done
        }

        coordinates.x -= Math.sin(Math.toRadians(heading)) * speed;
        coordinates.y -= Math.cos(Math.toRadians(heading)) * speed;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }

    public void setCurrentCommand(PlayerCommand cmd) {
        currentCommand = cmd;
    }

}
