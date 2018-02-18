package com.group18.cs446.spacequest.game.objects;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * An interface implemented by the game
 * entities that provides them with the
 * essential UI related functionality.
 */
public interface GameEntity {

    void update();

    Bitmap getBitmap();
    Point getCoordinates();

    default boolean contains(Point p){
        return false;
    }

}
