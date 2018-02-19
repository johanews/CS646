package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.PlayerCommand;
import com.group18.cs446.spacequest.R;

/**
 * Created by Owen on 2018-02-08.
 */

public class Player implements GameEntity{
    private Point coordinates; // Now represents the center of the character, not the top left
    private int speed, maxHealth, currentHealth, heading, regen, turnSpeed;
    private Bitmap bitmap;
    private PlayerCommand currentCommand;
    private Rect bounds;

    private boolean controlledByPlayer = true;
    private Point target;
    private int updatesToTarget;

    public Player(Context context){
        int startingX = 1000, startingY = 1000;
        coordinates = new Point(startingX, startingY);
        speed = 10;
        maxHealth = 1000;
        currentHealth = 1000;
        regen = 3;
        turnSpeed = 4;
        heading = 0; // Direction in degrees
        currentCommand = PlayerCommand.NONE;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        bounds = null;
    }

    public void flyToTarget(Point p, int time){
        controlledByPlayer = false;
        target = p;
        updatesToTarget = time;

    }
    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public Rect getBounds(){
        // If the spaceship was not rotated, then the bounds would be (x, y, width, height)
        // However, since the spaceship could be rotated we must return the smalled rect that the rotated
        // ship could fit inside
        // angle 0 = upright, means
        if(bounds != null){ // moving will set this to null
            return bounds;
        }
        // TODO currently doesnt work properly for rotated ship
        return new Rect(coordinates.x - bitmap.getWidth()/2, coordinates.y - bitmap.getHeight()/2,
                coordinates.x + bitmap.getWidth()/2, coordinates.y + bitmap.getHeight()/2);
        //return new Rect(coordinates.x-bitmap.getWidth()-bitmap.getHeight(), coordinates.y-bitmap.getWidth()-bitmap.getHeight(),
        //        coordinates.x+bitmap.getWidth()+bitmap.getHeight(), coordinates.y+bitmap.getWidth()+bitmap.getHeight());

    }

    @Override
    public void update() {
        if (currentHealth > 0 && currentHealth < maxHealth) {
            currentHealth = (currentHealth + regen > maxHealth) ? maxHealth : currentHealth + regen;
        }
        if(controlledByPlayer) {
            switch (currentCommand) {
                case RIGHT:
                    heading = (heading + 360 - turnSpeed) % 360;
                    break;
                case LEFT:
                    heading = (heading + 360 + turnSpeed) % 360;
                    break;
                case BOTH:
                case NONE:
                    // Nothing needs to be done
                    break;
            }
            coordinates.y -= Math.cos(heading*Math.PI/180)*speed;
            coordinates.x -= Math.sin(heading*Math.PI/180)*speed;
        } else {
            if(updatesToTarget > 0) {
                double normalized = Math.sqrt((coordinates.y - target.y)*(coordinates.y - target.y)
                        + (coordinates.x - target.x)*(coordinates.x - target.x));
                coordinates.y -= ((coordinates.y - target.y) / normalized) * speed;
                coordinates.x -= ((coordinates.x - target.x) / normalized) * speed;
                heading+= 3;
                updatesToTarget--;
            }
        }
        bounds = null; // invalidate bounds since we moved
        if(Math.random()>0.9)System.out.println("Player location: "+coordinates);
    }

    public int getAngle(){
        return heading;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }

    public void removeCommand(PlayerCommand playerCommand) {
        if(currentCommand == PlayerCommand.BOTH){
            currentCommand = playerCommand == PlayerCommand.LEFT ? PlayerCommand.RIGHT : PlayerCommand.LEFT;
        } else if (currentCommand == playerCommand){
            currentCommand = PlayerCommand.NONE;
        }
    }

    public void addCommand(PlayerCommand playerCommand) {
        if(playerCommand == PlayerCommand.LEFT){
            switch (currentCommand){
                case NONE:
                    currentCommand = PlayerCommand.LEFT;
                    break;
                case RIGHT:
                    currentCommand = PlayerCommand.BOTH;
                    break;
                case BOTH:
                case LEFT:
                    return;
            }
        } else if(playerCommand == PlayerCommand.RIGHT){
            switch (currentCommand){
                case NONE:
                    currentCommand = PlayerCommand.RIGHT;
                    break;
                case LEFT:
                    currentCommand = PlayerCommand.BOTH;
                    break;
                case BOTH:
                case RIGHT:
                    return;
            }
        }
    }

    @Override
    public void paint(Canvas canvas, Paint paint, Point topLeftCorner) {
        //Alternatively, pass this to Player to paint themselves here
        canvas.save();
        canvas.rotate(-getAngle(), getCoordinates().x - topLeftCorner.x, getCoordinates().y - topLeftCorner.y);
        canvas.drawBitmap(
                getBitmap(),
                getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2,
                getCoordinates().y - topLeftCorner.y - getBitmap().getHeight() / 2,
                paint);
        canvas.restore();
    }

    public void reset() { // Everything thats required for making a new player
        coordinates = new Point(1000, 1000);
        speed = 10;
        maxHealth = 1000;
        currentHealth = 1000;
        regen = 3;
        turnSpeed = 4;
        heading = 0; // Direction in degrees
        currentCommand = PlayerCommand.NONE;
        controlledByPlayer = true;
        // Should be the same bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

    @Override
    public boolean intersects(GameEntity e){
        if(!controlledByPlayer){
            return false;
        }
        Rect intersect = getBounds();
        if(intersect.intersect(e.getBounds())){
            // This just means the rectangle bounds intersect
            // now we will do a pixel by pixel search to see if they actually overlap
            System.out.println("INTERSECTING?");
            for(int x = intersect.left; x < intersect.right; x++){
                for(int y = intersect.top; y < intersect.bottom; y++){
                    if(bitmap.getPixel(x - getBounds().left, y-getBounds().top) != Color.TRANSPARENT){
                        if(e.getBitmap().getPixel(x - e.getBounds().left, y - e.getBounds().top) != Color.TRANSPARENT){
                            System.out.println("Intersecting.");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
